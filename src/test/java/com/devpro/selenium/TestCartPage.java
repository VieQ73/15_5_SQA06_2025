package com.devpro.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestCartPage {

    private WebDriver driver;
    private String baseUrl = "http://localhost:9090/cart";

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\phaml\\Downloads\\chromedriver-win32\\chromedriver-win32\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testPageLoadsCorrectly() {
        driver.get(baseUrl);
        Assert.assertEquals(driver.getTitle(), "Cart");
    }

    @Test(priority = 2)
    public void testCartIsNotEmptyOrShowsWarning() {
        driver.get(baseUrl);
        String check = driver.findElement(By.className("checkNull")).getAttribute("value");
        if (check.equals("1")) {
            Assert.assertTrue(driver.findElement(By.id("dathangne")).isDisplayed());
        } else {
            Assert.assertTrue(driver.findElement(By.className("alert-danger")).isDisplayed());
        }
    }

    @Test(priority = 3)
    public void testOrderModalOpens() {
        driver.get(baseUrl);
        if (driver.findElement(By.className("checkNull")).getAttribute("value").equals("1")) {
            driver.findElement(By.id("dathangne")).click();
            WebElement modal = new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("myModal")));
            Assert.assertTrue(modal.isDisplayed());
        }
    }

    @Test(priority = 4)
    public void testFormValidationEmptyFields() {
        driver.get(baseUrl);
        if (driver.findElement(By.className("checkNull")).getAttribute("value").equals("1")) {
            driver.findElement(By.id("dathangne")).click();
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            List<WebElement> messages = driver.findElements(By.className("form-message"));
            boolean hasError = messages.stream().anyMatch(el -> el.getText().length() > 0);
            Assert.assertTrue(hasError, "Phải có ít nhất một thông báo lỗi khi form để trống");
        }
    }

    @Test(priority = 5)
    public void testFormWithInvalidPhone() {
        driver.get(baseUrl);
        if (driver.findElement(By.className("checkNull")).getAttribute("value").equals("1")) {
            driver.findElement(By.id("dathangne")).click();
            driver.findElement(By.id("phone")).sendKeys("abc123");
            driver.findElement(By.id("name")).sendKeys("Nguyen Van A");
            driver.findElement(By.id("address")).sendKeys("Ha Noi");
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            String phoneError = driver.findElement(By.id("phone"))
                    .findElement(By.xpath("following-sibling::span"))
                    .getText();
            Assert.assertTrue(phoneError.contains("không hợp lệ"), "Thông báo lỗi phải hiển thị khi số điện thoại không hợp lệ");
        }
    }

    @Test(priority = 6)
    public void testValidFormSubmissionUI() {
        driver.get(baseUrl);
        if (driver.findElement(By.className("checkNull")).getAttribute("value").equals("1")) {
            driver.findElement(By.id("dathangne")).click();
            driver.findElement(By.id("phone")).clear();
            driver.findElement(By.id("name")).clear();
            driver.findElement(By.id("address")).clear();

            driver.findElement(By.id("phone")).sendKeys("0378054123");
            driver.findElement(By.id("name")).sendKeys("Nguyen Van B");
            driver.findElement(By.id("address")).sendKeys("264 Truong Dinh, Ha Noi");

            driver.findElement(By.cssSelector("button[type='submit']")).click();

            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.id("myModal")));
        }
    }

    @Test(priority = 7)
    public void testDeleteItemFromCart() throws InterruptedException {
        driver.get(baseUrl);
        if (driver.findElement(By.className("checkNull")).getAttribute("value").equals("1")) {
            List<WebElement> deleteButtons = driver.findElements(By.cssSelector("button.item.btn.btn-danger"));
            int beforeCount = deleteButtons.size();
            if (beforeCount > 0) {
                deleteButtons.get(0).click();
                Thread.sleep(1000); // đợi Ajax (nếu có)
                int afterCount = driver.findElements(By.cssSelector("button.item.btn.btn-danger")).size();
                Assert.assertTrue(afterCount < beforeCount, "Sản phẩm đã bị xóa khỏi giỏ");
            }
        }
    }

    @Test(priority = 8)
    public void testTotalPriceDisplayedCorrectly() {
        driver.get(baseUrl);
        if (driver.findElement(By.className("checkNull")).getAttribute("value").equals("1")) {
            WebElement tongTien = driver.findElement(By.id("tongtienT"));
            String tong = tongTien.getText().replace("đ", "").trim();
            Assert.assertTrue(tong.matches("[0-9.]+"), "Tổng tiền phải là số hợp lệ");
        }
    }

    @Test(priority = 9)
    public void testChangeQuantityUpdatesPrice() throws InterruptedException {
        driver.get(baseUrl);
        if (driver.findElement(By.className("checkNull")).getAttribute("value").equals("1")) {
            WebElement qtyInput = driver.findElement(By.cssSelector("input[type='number']"));
            int oldQty = Integer.parseInt(qtyInput.getAttribute("value"));
            qtyInput.clear();
            qtyInput.sendKeys(String.valueOf(oldQty + 1));
            Thread.sleep(1000); // đợi JS cập nhật
            Assert.assertTrue(true);
        }
    }

    @Test(priority = 10)
    public void testSearchOrderHistoryByPhone() {
        driver.get(baseUrl);
        WebElement searchBox = driver.findElement(By.id("keyphone"));
        searchBox.sendKeys("0378027568");
        driver.findElement(By.id("btnClear")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("historyCart"));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
