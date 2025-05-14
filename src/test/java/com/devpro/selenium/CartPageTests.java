package com.devpro.selenium;

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

public class CartPageTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        // Set path to ChromeDriver (update with your path)
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\phaml\\Downloads\\chromedriver-win32\\chromedriver-win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 15);

    }

    private void addProductToCart() throws InterruptedException {
        // Navigate to homepage
        driver.get("http://localhost:9090/products/S%E1%BB%AFa%20R%E1%BB%ADa%20M%E1%BA%B7t%20H%E1%BB%93ng%20S%C3%A2m%20Ecotop%20Red%20Ginseng%20Foam%20Cleansing-966324");
        WebElement product = driver.findElement(By.className("addhi"));
        product.click();
        driver.get("http://localhost:9090/cart");
        Thread.sleep(2000);
    }

    //  Xác minh thông báo "Không có sản phẩm trong giỏ hàng" hiển thị đúng khi giỏ hàng rỗng.
    @Test
    public void TC_SELENIUM_01_testCartShowsEmptyMessageWhenNoProducts() throws InterruptedException {
        driver.get("http://localhost:9090/cart");
        Thread.sleep(2000);
        try {
            WebElement tbody = driver.findElement(By.cssSelector("table.table tbody"));
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));
            System.out.println(rows.size());
            assertTrue(rows.size() == 0);
        } catch (Exception e) {
        }
        WebElement div = driver.findElement(By.cssSelector(".alert.alert-danger"));
        WebElement st = div.findElement(By.tagName("strong"));
        assertEquals(st.getText().trim(), "!Không");
    }

    /*
    *  Kiểm tra rằng giỏ hàng hiển thị danh sách sản phẩm khi có sản phẩm trong đó.
    * */
    @Test
    public void TC_SELENIUM_02_testCartDisplaysProductsWhenNotEmpty() throws InterruptedException {
        addProductToCart();
        // Tìm phần tbody của bảng
        WebElement tbody = driver.findElement(By.cssSelector("table.table tbody"));
        // Lấy tất cả các dòng trong tbody
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        // Lọc ra các dòng không phải dòng tổng tiền
        List<WebElement> dataRows = new ArrayList<>();
        for (WebElement row : rows) {
            if (!row.getText().contains("Tổng tiền")) {
                dataRows.add(row);
            }
        }
        System.out.println(dataRows.size());
        assertTrue(dataRows.size() > 0);

    }

    //Kiểm tra liên kết tên sản phẩm có dẫn đúng đến trang chi tiết sản phẩm
    @Test
    public void TC_SELENIUM_03_testProductNameLinksToProductPage() throws InterruptedException {
        addProductToCart();
        WebElement tbody = driver.findElement(By.cssSelector("table.table tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("a"));

        // Lưu text và href trước khi click
        List<String[]> productLinks = new ArrayList<>();
        for (WebElement row : rows) {
            productLinks.add(new String[]{row.getText().trim(), row.getAttribute("href")});
        }

        for (String[] product : productLinks) {
            String expectedName = product[0];
            String url = product[1];

            // Điều hướng trực tiếp thay vì click
            driver.navigate().to(url);
            Thread.sleep(1000); // nên dùng WebDriverWait thay vì sleep

            WebElement t = driver.findElement(By.cssSelector(".pro-infor-title")); // thêm dấu chấm nếu là class
            System.out.println(t.getText());
            assertEquals(expectedName, t.getText().trim());

            // Quay lại trang trước nếu cần kiểm tra nhiều link
            driver.navigate().back();
            Thread.sleep(1000);
        }
    }

    // Xác minh người dùng có thể thay đổi số lượng sản phẩm và hệ thống cập nhật đúng
    @Test
    public void TC_SELENIUM_04_testQuantityInputAllowsValidUpdates() throws InterruptedException {
        addProductToCart();

        WebElement tbody = driver.findElement(By.cssSelector("table.table tbody"));

        Thread.sleep(2000);
        // Tìm input theo class .so_luong_sp66
        WebElement quantityInput = tbody.findElement(By.tagName("input"));

        // Xoá giá trị cũ trước khi nhập
        quantityInput.clear();
        quantityInput.sendKeys("3");

        // Chờ thay đổi DOM nếu có script onchange xử lý (nên dùng WebDriverWait)
        Thread.sleep(1000); // hoặc WebDriverWait nếu DOM có cập nhật

        // Kiểm tra lại giá trị trong input đã thay đổi đúng chưa
        assertEquals("3", quantityInput.getAttribute("value"));
    }


    // Kiểm tra rằng hệ thống không cho phép số lượng âm.
    @Test
    public void TC_SELENIUM_05_testQuantityInputRejectsNegativeNumbers() throws InterruptedException {
        addProductToCart();
        WebElement tbody = driver.findElement(By.cssSelector("table.table tbody"));

        Thread.sleep(2000);
        // Tìm input theo class .so_luong_sp66
        WebElement quantityInput = tbody.findElement(By.tagName("input"));

        // Xoá giá trị cũ trước khi nhập
        quantityInput.clear();
        quantityInput.sendKeys("-3");

        // Chờ thay đổi DOM nếu có script onchange xử lý (nên dùng WebDriverWait)
        Thread.sleep(1000); // hoặc WebDriverWait nếu DOM có cập nhật

        // Kiểm tra lại giá trị trong input đã thay đổi đúng chưa
        assertNotEquals("-3", quantityInput.getAttribute("value"));

    }

    //Kiểm tra giới hạn tối đa của số lượng sản phẩm
    @Test
    public void TC_SELENIUM_06_testQuantityInputRejectsValuesAboveMaximum() throws InterruptedException {
        addProductToCart();
        WebElement tbody = driver.findElement(By.cssSelector("table.table tbody"));

        Thread.sleep(2000);
        // Tìm input theo class .so_luong_sp66
        WebElement quantityInput = tbody.findElement(By.tagName("input"));

        // Xoá giá trị cũ trước khi nhập
        quantityInput.clear();
        quantityInput.sendKeys("999999");

        // Chờ thay đổi DOM nếu có script onchange xử lý (nên dùng WebDriverWait)
        Thread.sleep(1000); // hoặc WebDriverWait nếu DOM có cập nhật
        String s = quantityInput.getAttribute("value");
        // Kiểm tra lại giá trị trong input đã thay đổi đúng chưa
        assertTrue(!s.contains("999999"));
    }

    //Đảm bảo tổng tiền được cập nhật khi thay đổi số lượng sản phẩm
    @Test
    public void TC_SELENIUM_07_testTotalPriceUpdatesAfterQuantityChange() throws InterruptedException {
        addProductToCart();
        WebElement checkNull = driver.findElement(By.className("checkNull"));
        if (checkNull.getAttribute("value").equals("1")) {
            WebElement totalPrice = driver.findElement(By.id("tongtienT"));
            String initialTotal = totalPrice.getText();
            WebElement quantityInput = driver.findElement(By.cssSelector(".soluong input[type='number']"));
            quantityInput.clear();
            quantityInput.sendKeys("2");
            quantityInput.sendKeys(Keys.ENTER);
            wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.id("tongtienT"), initialTotal)));
            assertNotEquals(totalPrice.getText(), initialTotal, "Total price should update");
        } else {
            throw new SkipException("Cart is empty, skipping total price update test");
        }
    }

    // Kiểm tra nút xóa sản phẩm hoạt động chính xác và cập nhật lại giao diện.
    @Test
    public void TC_SELENIUM_08_testDeleteButtonRemovesProduct() throws InterruptedException {
        addProductToCart();
        WebElement checkNull = driver.findElement(By.className("checkNull"));
        if (checkNull.getAttribute("value").equals("1")) {
            WebElement productRow = driver.findElement(By.cssSelector("tbody tr"));
            String rowId = productRow.getAttribute("id");
            driver.findElement(By.cssSelector(".btn-danger")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(rowId)));
            try {
                driver.findElement(By.id(rowId));
                fail("Product should be removed");
            } catch (NoSuchElementException e) {
                // Expected
            }
        } else {
            throw new SkipException("Cart is empty, skipping delete product test");
        }
    }

    // Kiểm tra nút “Đặt hàng” hiển thị khi giỏ hàng không rỗng.
    @Test
    public void TC_SELENIUM_09_testOrderButtonVisibilityWhenCartNotEmpty() throws InterruptedException {
        addProductToCart();
        try {
            WebElement chec = driver.findElement(By.id("dathangne"));
            assertTrue(chec.isDisplayed());
        } catch (NoSuchElementException e) {
            assertTrue(0 == 1);
        }
    }

    //  Kiểm tra nút “Đặt hàng” bị ẩn khi giỏ hàng rỗng.
    @Test
    public void TC_SELENIUM_10_testOrderButtonHiddenWhenCartEmpty() {
        driver.get("http://localhost:9090/cart");

        List<WebElement> buttons = driver.findElements(By.id("dathangne"));

        // Nếu nút không tồn tại => PASS
        if (buttons.isEmpty()) {
            assertTrue(true);
        } else {
            // Nếu có, thì phải KHÔNG hiển thị
            assertFalse(buttons.get(0).isDisplayed(), "Nút 'Đặt hàng' vẫn hiển thị khi giỏ hàng rỗng!");
        }
    }

    // Đảm bảo rằng khi nhấn "Đặt hàng", hộp thoại (modal) điền thông tin đơn hàng sẽ mở ra.
    @Test
    public void TC_SELENIUM_11_testOrderFormModalOpens() throws InterruptedException {
        addProductToCart();
        driver.findElement(By.id("dathangne")).click();
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModal")));
        assertTrue(modal.getAttribute("class").contains("show"), "Modal should be visible");
    }

    //Xác minh không có lỗi hiển thị nếu người dùng nhập số điện thoại hợp lệ.
    @Test
    public void TC_SELENIUM_12_testPhoneNumberValidationValidInput() throws InterruptedException {
        addProductToCart();
        driver.findElement(By.id("dathangne")).click();
        WebElement phoneInput = driver.findElement(By.id("phone"));
        phoneInput.sendKeys("0378054123");
        phoneInput.sendKeys(Keys.TAB);
        WebElement error = driver.findElement(By.cssSelector("#phone ~ .form-message"));
        assertTrue(error.getText().isEmpty(), "No error for valid phone number");
    }

    //Đảm bảo hiển thị thông báo lỗi khi số điện thoại nhập sai định dạng
    @Test
    public void TC_SELENIUM_13_testPhoneNumberValidationInvalidInput() throws InterruptedException {
        addProductToCart();
        driver.findElement(By.id("dathangne")).click();
        WebElement phoneInput = driver.findElement(By.id("phone"));
        phoneInput.sendKeys("abc");
        phoneInput.sendKeys(Keys.TAB);
        WebElement error = driver.findElement(By.cssSelector("#phone ~ .form-message"));
        assertTrue(error.getText().contains("Số điện thoại không hợp lệ!"));

    }

    //Kiểm tra rằng nếu tên người nhận để trống thì hệ thống báo lỗi.
    @Test
    public void TC_SELENIUM_14_testNameFieldValidationEmptyInput() throws InterruptedException {
        addProductToCart();
        driver.findElement(By.id("dathangne")).click();
        driver.findElement(By.cssSelector("#form-dathang .btn-warning")).click();
        WebElement error = driver.findElement(By.cssSelector("#name ~ .form-message"));
        assertTrue(error.getText().contains("Họ tên không được để trống!"));

    }

    //Kiểm tra rằng nếu địa chỉ để trống thì hiển thị lỗi tương ứng.
    @Test
    public void TC_SELENIUM_15_testAddressFieldValidationEmptyInput() throws InterruptedException {
        addProductToCart();
            driver.findElement(By.id("dathangne")).click();
            driver.findElement(By.cssSelector("#form-dathang .btn-warning")).click();
            WebElement error = driver.findElement(By.cssSelector("#address ~ .form-message"));
            assertTrue(error.getText().contains("Địa chỉ không được để trống!"));
    }

    //Kiểm tra rằng đơn hàng được gửi thành công khi nhập đầy đủ thông tin hợp lệ.
    @Test
    public void TC_SELENIUM_16_testSuccessfulOrderSubmission() throws InterruptedException {
        addProductToCart();

            driver.findElement(By.id("dathangne")).click();
            driver.findElement(By.id("phone")).sendKeys("0378054123");
            driver.findElement(By.id("name")).sendKeys("Nguyen Van A");
            driver.findElement(By.id("address")).sendKeys("123 Hanoi");
            driver.findElement(By.cssSelector("#form-dathang .btn-warning")).click();
            try {
                wait.until(ExpectedConditions.urlContains("success"));
                assertTrue(driver.getCurrentUrl().contains("success"),
                        "Order should submit successfully");
            } catch (TimeoutException e) {
                WebElement successMessage = driver.findElement(By.cssSelector(".alert-success"));
                assertTrue(successMessage.isDisplayed(), "Success message should be displayed");
            }

    }

    //Kiểm tra rằng đơn hàng được gửi không thành công khi số lượng không hợp lệ
    @Test
    public void TC_SELENIUM_17_testNotSuccessfulOrderSubmission() throws InterruptedException {
        addProductToCart();

        WebElement tbody = driver.findElement(By.cssSelector("table.table tbody"));
        Thread.sleep(1000);

        WebElement quantityInput = tbody.findElement(By.tagName("input"));
        quantityInput.clear();
        quantityInput.sendKeys("-3");
        quantityInput.click();

        // Nhấn nút “Đặt hàng”
        driver.findElement(By.id("dathangne")).click();
        driver.findElement(By.id("phone")).sendKeys("0378054123");
        driver.findElement(By.id("name")).sendKeys("Nguyen Van A");
        driver.findElement(By.id("address")).sendKeys("123 Hanoi");
        driver.findElement(By.cssSelector("#form-dathang .btn-warning")).click();

        // Đợi 3s rồi kiểm tra KHÔNG chuyển đến trang success
        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        assertFalse(currentUrl.contains("success"), "Order should not be successful with negative quantity");

    }

    // Kiểm tra rằng khi nhập số điện thoại hợp lệ, hệ thống chuyển sang trang lịch sử đơn hàng.
    @Test
    public void TC_SELENIUM_18_testPurchaseHistorySearchValidPhone() throws InterruptedException {
        addProductToCart(); // nếu cần dữ liệu trước

        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Chờ input hiện
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("keyphone")));
        phoneInput.sendKeys("0869286532");

        // Tìm và bấm nút submit theo ID đúng
        WebElement searchButton = driver.findElement(By.cssSelector(".btn-success"));
        searchButton.click();
        // Chờ URL chuyển sang /historyCart sau khi submit
        wait.until(ExpectedConditions.urlContains("/historyCart"));
        assertTrue(driver.getCurrentUrl().contains("/historyCart"), "Should navigate to history page");
    }

    //Kiểm tra rằng khi ô tìm kiếm lịch sử để trống, hệ thống không chuyển trang.
    @Test
    public void TC_SELENIUM_19_testPurchaseHistorySearchEmptyPhone() throws InterruptedException {
        addProductToCart();
        driver.findElement(By.cssSelector(".btn-success")).click();
        assertEquals(driver.getCurrentUrl(), "http://localhost:9090/cart",
                "Should not navigate with empty phone");
    }

    //Kiểm tra nút "Lên đầu trang" hoạt động – trang cuộn lên đầu khi nhấn.
    @Test
    public void TC_SELENIUM_20_testGoToTopButtonFunctionality() throws InterruptedException {
        addProductToCart();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        driver.findElement(By.id("go-to-top")).click();
        wait.until(ExpectedConditions.jsReturnsValue("return window.pageYOffset;"));
        long scrollPosition = (long) js.executeScript("return window.pageYOffset;");
        System.out.println("scrollPosition: " + scrollPosition);
        assertEquals(scrollPosition, 0, "Page should scroll to top");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
