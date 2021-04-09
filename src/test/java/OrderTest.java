import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private WebDriver driver;

    @BeforeAll
        static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\Temp\\chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestSuccessOrderIfCorrectFilling() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Мария Гребенькова");
        elements.get(1).sendKeys("+79876543210");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldTestWarnIfIncorrectTel() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Мария Гре");
        elements.get(1).sendKeys("+798765");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldTestWarnIfNoName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(1).sendKeys("+79876543210");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(0).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldTestWarnIfIncorrextName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Mary");
        elements.get(1).sendKeys("+79876543210");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldTestWarnIfNoTel() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Мария Гре");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(1).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldTestChangeColorOfCheckBoxIfInvalid() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Мария Гребенькова");
        elements.get(1).sendKeys("+79876543210");
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("checkbox__text")).getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", text);
    }


}
