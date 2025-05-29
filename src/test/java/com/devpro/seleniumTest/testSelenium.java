package com.devpro.seleniumTest;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class testSelenium {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 25);
    }



    //Truy cập trang add sale(1)
    @Test
    public void testGetAddSalePage() {
        driver.get("http://localhost:9090/admin/addSale");
        assertTrue(driver.getPageSource().contains("Thêm quà tặng"));
    }

    //Truy cập trang list sale(2)
    @Test
    public void testGetListSalePage() {
        driver.get("http://localhost:9090/admin/listSale");
        assertTrue(driver.getPageSource().contains("Danh sách khuyến mãi"));
    }

    //Thêm đợt khuyến mãi mới (POST)(3)
    @Test
    public void testAddSale() {
        driver.get("http://localhost:9090/admin/addSale");

        WebElement nameInput = driver.findElement(By.name("sale_name"));
        WebElement startDate = driver.findElement(By.name("start_date"));
        WebElement endDate = driver.findElement(By.name("end_date"));
        WebElement submit = driver.findElement(By.cssSelector("button.contact2-form-btn"));

        nameInput.sendKeys("Test Sale");
        startDate.sendKeys("02022020");
        endDate.sendKeys("06142020");
        submit.click();

        wait.until(ExpectedConditions.urlContains("/admin/listSale"));
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
        String message = alert.getText();
        System.out.println("Thông báo hiển thị: " + message);
        assertTrue(message.contains("Success! Cập nhật thành công."));
    }

    //Truy cập sửa sale bằng id(4)
    @Test
    public void testEditSaleById() {
        driver.get("http://localhost:9090/admin/sale/1"); // giả sử có ID = 1
        assertTrue(driver.getPageSource().contains("Thêm quà tặng"));
    }

    //Truy cập trang sản phẩm của khuyến mãi có id = 1 (5)
    @Test
    public void testGetProductSaleList() {
        driver.get("http://localhost:9090/admin/listProductSale/1");
        assertTrue(driver.getPageSource().contains("Danh sách sản phẩm khuyến mại"));
    }



    //Không nhập tên khi thêm sale → lỗi(6)
    @Test
    public void testAddSaleMissingName() {
        driver.get("http://localhost:9090/admin/addSale");
        driver.findElement(By.name("start_date")).sendKeys("05052022");
        driver.findElement(By.name("end_date")).sendKeys("07052022");
        WebElement submit = driver.findElement(By.cssSelector("button.contact2-form-btn"));
        submit.click();
        assertTrue(driver.getPageSource().contains("Tên đợt khuyến mãi không được để trống!"));
    }

    //Ngày bắt đầu > ngày kết thúc(7)
    @Test
    public void testInvalidDateRange() {
        driver.get("http://localhost:9090/admin/addSale");
        driver.findElement(By.name("sale_name")).sendKeys("Lỗi Ngày");
        driver.findElement(By.name("start_date")).sendKeys("02052022");
        driver.findElement(By.name("end_date")).sendKeys("02022022");
        WebElement submit = driver.findElement(By.cssSelector("button.contact2-form-btn"));
        submit.click();
        assertTrue(driver.getPageSource().contains("Ngày không hợp lệ"));
    }

    //Sửa đợt khuyến mãi (8)
    @Test
    public void testUpdateSaleDate() {
        driver.get("http://localhost:9090/admin/sale/1");
        WebElement endDate = driver.findElement(By.name("end_date"));
        endDate.clear();
        endDate.sendKeys("05142022");
        WebElement submit = driver.findElement(By.cssSelector("button.contact2-form-btn"));
        submit.click();
        wait.until(ExpectedConditions.urlContains("listSale"));
    }


    // Xác nhận form có đủ input khi thêm sale(9)
    @Test
    public void testSaleFormFieldsExist() {
        driver.get("http://localhost:9090/admin/addSale");
        assertNotNull(driver.findElement(By.name("sale_name")));
        assertNotNull(driver.findElement(By.name("start_date")));
        assertNotNull(driver.findElement(By.name("end_date")));
    }

    //Kiểm tra có nút submit(10)
    @Test
    public void testSubmitButtonExists() {
        driver.get("http://localhost:9090/admin/addSale");
        assertTrue(driver.findElements(By.tagName("button")).size() > 0);
    }

    //Kiểm tra URL hợp lệ khi thêm sale thành công(11)
    @Test
    public void testRedirectUrlAfterSaleAdd() {
        driver.get("http://localhost:9090/admin/addSale");
        driver.findElement(By.name("sale_name")).sendKeys("Redirect test");
        driver.findElement(By.name("start_date")).sendKeys("07192022");
        driver.findElement(By.name("end_date")).sendKeys("09162022");
        WebElement submit = driver.findElement(By.cssSelector("button.contact2-form-btn"));
        submit.click();
        wait.until(ExpectedConditions.urlContains("/admin/listSale"));
    }

    //Kiểm tra giá trị mặc định của form sửa sale(12)
    @Test
    public void testEditSaleDefaultValues() {
        driver.get("http://localhost:9090/admin/sale/1");
        String value = driver.findElement(By.name("sale_name")).getAttribute("value");
        assertNotNull(value);
    }


    //Truy cập listProductSale không có sản phẩm id = 15(13)
    @Test
    public void testEmptyProductSaleList() {
        driver.get("http://localhost:9090/admin/listProductSale/15"); // giả sử ID 15 chưa có product
        assertTrue(driver.getPageSource().contains("Whitelabel Error Page"));
    }

    //Lặp lại thêm sale với các dữ liệu khác nhau(14)
    @Test
    public void testAddMultipleSales() {
        for (int i = 0; i < 3; i++) {
            driver.get("http://localhost:9090/admin/addSale");
            WebElement nameInput = driver.findElement(By.name("sale_name"));
            WebElement startDate = driver.findElement(By.name("start_date"));
            WebElement endDate = driver.findElement(By.name("end_date"));
            WebElement submit = driver.findElement(By.cssSelector("button.contact2-form-btn"));

            nameInput.sendKeys("Test Sale");
            startDate.sendKeys("02022020");
            endDate.sendKeys("06142020");
            submit.click();

            wait.until(ExpectedConditions.urlContains("/admin/listSale"));
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
            String message = alert.getText();
            System.out.println("Thông báo hiển thị: " + message);
            assertTrue(message.contains("Success! Cập nhật thành công."));
        }
    }
}
