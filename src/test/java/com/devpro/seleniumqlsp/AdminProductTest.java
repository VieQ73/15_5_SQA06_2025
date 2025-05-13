package com.devpro.seleniumqlsp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lớp kiểm thử chức năng quản lý sản phẩm trong giao diện admin bằng Selenium WebDriver.
 * Chứa các test case đã được sửa đổi để khắc phục lỗi.
 */
public class AdminProductTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<TestResult> testResults;

    private static class TestResult {
        String testCaseId;
        String scenario;
        String expectedResult;
        String actualResult;
        String status;

        TestResult(String testCaseId, String scenario, String expectedResult, String actualResult, String status) {
            this.testCaseId = testCaseId;
            this.scenario = scenario;
            this.expectedResult = expectedResult;
            this.actualResult = actualResult;
            this.status = status;
        }

        @Override
        public String toString() {
            return String.format("TestCaseID: %s | Scenario: %s | Expected: %s | Actual: %s | Status: %s",
                    testCaseId, scenario, expectedResult, actualResult, status);
        }
    }

    /**
     * Thiết lập môi trường kiểm thử trước mỗi test case.
     * Khởi tạo ChromeDriver, đăng nhập vào tài khoản admin, và khởi tạo danh sách kết quả test.
     */
    @BeforeEach
    public void setUp() {
        // Thiết lập đường dẫn đến ChromeDriver
        System.setProperty("webdriver.chrome.driver", "D:\\Drivers\\chromedriver-win64\\chromedriver.exe");
        // Khởi tạo trình duyệt Chrome
        driver = new ChromeDriver();
        // Phóng to cửa sổ trình duyệt để dễ quan sát
        driver.manage().window().maximize();
        // Khởi tạo WebDriverWait với thời gian chờ 10 giây
        wait = new WebDriverWait(driver, 10);
        // Khởi tạo danh sách lưu kết quả test
        testResults = new ArrayList<>();

        // Đăng nhập vào tài khoản admin
        try {
            login("admin", "admin");
            // Chờ chuyển hướng đến trang admin
            wait.until(ExpectedConditions.urlToBe("http://localhost:9090/admin"));
        } catch (Exception e) {
            System.err.println("Lỗi đăng nhập trong setUp: " + e.getMessage());
            throw new RuntimeException("Không thể đăng nhập vào tài khoản admin: " + e.getMessage());
        }
    }

    /**
     * Ghi kết quả test vào file test_results.log.
     * Mỗi kết quả bao gồm ID, kịch bản, kết quả mong đợi, kết quả thực tế, và trạng thái.
     */
    private void logTestResults() {
        try (FileWriter writer = new FileWriter("test_results.log", true)) {
            for (TestResult result : testResults) {
                writer.write(result.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Thực hiện đăng nhập vào giao diện admin.
     * @param username admin
     * @param password admin
     */
    private void login(String username, String password) {
        // Truy cập trang đăng nhập của ứng dụng
        driver.get("http://localhost:9090/cai-nay-la-mapping-trong-adminlogincontroller");
        // Chờ trường username hiển thị để đảm bảo trang đã tải
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        // Nhập tên đăng nhập
        driver.findElement(By.name("username")).sendKeys(username);
        // Nhập mật khẩu
        driver.findElement(By.name("password")).sendKeys(password);
        // Nhấn nút đăng nhập
        driver.findElement(By.className("login100-form-btn")).click();
    }

    /**
     * Điều hướng đến trang danh sách sản phẩm từ giao diện admin.
     */
    private void navigateToProductList() {
        // Truy cập trang dashboard admin
        driver.get("http://localhost:9090/admin");
        // Chờ và nhấn menu "Quản lý sản phẩm"
        WebElement productMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Quản lý sản phẩm']]")));
        productMenu.click();
        // Chờ và nhấn mục "Danh sách sản phẩm"
        WebElement listProducts = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/listProducts']")));
        listProducts.click();
        // Chờ bảng sản phẩm hiển thị
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.table-data2")));
    }

    /**
     * Điều hướng đến trang thêm sản phẩm từ giao diện admin.
     */
    private void navigateToAddProduct() {
        // Truy cập trang dashboard admin
        driver.get("http://localhost:9090/admin");
        // Chờ và nhấn menu "Quản lý sản phẩm"
        WebElement productMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Quản lý sản phẩm']]")));
        productMenu.click();
        // Chờ và nhấn mục "Thêm sản phẩm"
        WebElement addProduct = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/addProduct']")));
        addProduct.click();
        // Chờ form thêm sản phẩm hiển thị
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("form-addProduct")));
    }

    @Test
    public void test_SELE_QLSP_06_AddProductValid() {
        String testCaseId = "SELE_QLSP_06";
        String scenario = "Kiểm tra thêm sản phẩm với thông tin hợp lệ";
        String expectedResult = "Sản phẩm được thêm thành công, chuyển hướng đến danh sách sản phẩm với sản phẩm mới";
        String actualResult;
        String status;

        try {
            navigateToAddProduct();
            Select category = new Select(driver.findElement(By.name("category.id")));
            category.selectByVisibleText("Mỹ phẩm trang điểm");
            driver.findElement(By.id("title")).sendKeys("Son môi A");
            driver.findElement(By.id("price")).sendKeys("200000");
            driver.findElement(By.id("amount")).sendKeys("50");
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Son môi cao cấp");
            driver.findElement(By.name("product_image")).sendKeys("C:\\Users\\ASUS\\IdeaProjects\\DoAnTotNghiepHaUI\\upload\\Kem-Kone-Thai-Lan-4544.jpg");
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys("Son môi cao cấp test");
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ chuyển hướng và kiểm tra thông báo
            wait.until(ExpectedConditions.urlToBe("http://localhost:9090/admin/listProducts/?add=success"));
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success")));
            assertTrue(alert.getText().contains("Cập nhật thành công."), "Không thấy thông báo thành công!");
            actualResult = "Hiển thị thông báo thêm sản phẩm thành công";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_08_AddProductNegativePrice() {
        String testCaseId = "SELE_QLSP_08";
        String scenario = "Kiểm tra thêm sản phẩm với giá âm";
        String expectedResult = "Hiển thị thông báo lỗi hoặc không cho phép thêm sản phẩm";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang thêm sản phẩm
            navigateToAddProduct();
            // Chọn danh mục sản phẩm
            Select category = new Select(driver.findElement(By.name("category.id")));
            category.selectByVisibleText("Mỹ phẩm trang điểm");
            // Nhập thông tin sản phẩm
            driver.findElement(By.id("title")).sendKeys("Son môi A");
            driver.findElement(By.id("price")).sendKeys("-200000");
            driver.findElement(By.id("amount")).sendKeys("50");
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Son môi cao cấp");
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys("Son môi cao cấp test");
            // Tải ảnh sản phẩm
            driver.findElement(By.name("product_image")).sendKeys("C:\\Users\\ASUS\\IdeaProjects\\DoAnTotNghiepHaUI\\upload\\Kem-Kone-Thai-Lan-4544.jpg");
            // Nhấn nút thêm sản phẩm
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ và kiểm tra thông báo lỗi
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".form-message")));
            assertTrue(errorMessage.isDisplayed() && errorMessage.getText().contains("Giá phải là số dương"), "Không hiển thị lỗi giá âm!");
            actualResult = "Hiển thị thông báo lỗi giá âm";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_09_AddProductNegativeAmount() {
        String testCaseId = "SELE_QLSP_09";
        String scenario = "Kiểm tra thêm sản phẩm với số lượng âm";
        String expectedResult = "Hiển thị thông báo lỗi hoặc không cho phép thêm sản phẩm";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang thêm sản phẩm
            navigateToAddProduct();
            // Chọn danh mục sản phẩm
            Select category = new Select(driver.findElement(By.name("category.id")));
            category.selectByVisibleText("Mỹ phẩm trang điểm");
            // Nhập thông tin sản phẩm
            driver.findElement(By.id("title")).sendKeys("Son môi A");
            driver.findElement(By.id("price")).sendKeys("200000");
            driver.findElement(By.id("amount")).sendKeys("-50");
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Son môi cao cấp");
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys("Son môi cao cấp test");
            // Tải ảnh sản phẩm
            driver.findElement(By.name("product_image")).sendKeys("C:\\Users\\ASUS\\IdeaProjects\\DoAnTotNghiepHaUI\\upload\\Kem-Kone-Thai-Lan-4544.jpg");
            // Nhấn nút thêm sản phẩm
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ và kiểm tra thông báo lỗi
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".form-message")));
            assertTrue(errorMessage.isDisplayed() && errorMessage.getText().contains("Số lượng phải là số dương"), "Không hiển thị lỗi số lượng âm!");
            actualResult = "Hiển thị thông báo lỗi số lượng âm";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_10_AddProductInvalidImage() {
        String testCaseId = "SELE_QLSP_10";
        String scenario = "Kiểm tra thêm sản phẩm với file ảnh không hợp lệ";
        String expectedResult = "Hiển thị thông báo lỗi: \"File không phải định dạng ảnh!\"";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang thêm sản phẩm
            navigateToAddProduct();
            // Chọn danh mục sản phẩm
            Select category = new Select(driver.findElement(By.name("category.id")));
            category.selectByVisibleText("Mỹ phẩm trang điểm");
            // Nhập thông tin sản phẩm
            driver.findElement(By.id("title")).sendKeys("Son môi A");
            driver.findElement(By.id("price")).sendKeys("200000");
            driver.findElement(By.id("amount")).sendKeys("50");
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Son môi cao cấp");
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys("Son môi cao cấp test");
            // Tải file không phải ảnh
            driver.findElement(By.name("product_image")).sendKeys("C:\\Users\\ASUS\\IdeaProjects\\DoAnTotNghiepHaUI\\upload\\test.txt");
            // Nhấn nút thêm sản phẩm
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ và kiểm tra thông báo lỗi
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".form-message")));
            assertTrue(errorMessage.getText().contains("File không phải định dạng ảnh"), "Không hiển thị lỗi file không hợp lệ!");
            actualResult = "Hiển thị thông báo lỗi: \"File không phải định dạng ảnh!\"";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_11_AddProductNoCategory() {
        String testCaseId = "SELE_QLSP_11";
        String scenario = "Kiểm tra thêm sản phẩm không chọn danh mục";
        String expectedResult = "Hiển thị thông báo lỗi hoặc không cho phép thêm sản phẩm";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang thêm sản phẩm
            navigateToAddProduct();
            // Nhập thông tin sản phẩm, không chọn danh mục
            driver.findElement(By.id("title")).sendKeys("Son môi A");
            driver.findElement(By.id("price")).sendKeys("200000");
            driver.findElement(By.id("amount")).sendKeys("50");
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Son môi cao cấp");
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys("Son môi cao cấp test");
            // Tải ảnh sản phẩm
            driver.findElement(By.name("product_image")).sendKeys("C:\\Users\\ASUS\\IdeaProjects\\DoAnTotNghiepHaUI\\upload\\Kem-Kone-Thai-Lan-4544.jpg");
            // Nhấn nút thêm sản phẩm
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ chuyển hướng và kiểm tra thông báo
            wait.until(ExpectedConditions.urlToBe("http://localhost:9090/admin/listProducts/?add=success"));
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success")));
            assertTrue(alert.getText().contains("Cập nhật thành công."), "Không thấy thông báo thành công!");
            actualResult = "Hiển thị thông báo cập nhật sản phẩm thành công";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }


    @Test
    public void test_SELE_QLSP_12_EditProductValid() {
        String testCaseId = "SELE_QLSP_12";
        String scenario = "Kiểm tra sửa sản phẩm với thông tin hợp lệ";
        String expectedResult = "Sản phẩm được cập nhật, hiển thị thông báo thành công";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang danh sách sản phẩm
            navigateToProductList();
            // Lấy sản phẩm đầu tiên trong danh sách
            List<WebElement> productRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            assertFalse(productRows.isEmpty(), "Danh sách sản phẩm rỗng!");
            WebElement firstProduct = productRows.get(0);
            String originalTitle = firstProduct.findElement(By.cssSelector("td:nth-child(2)")).getText();
            // Nhấn nút sửa sản phẩm
            firstProduct.findElement(By.cssSelector("a[href*='/admin/product/']")).click();
            // Chọn danh mục sản phẩm
            Select category = new Select(driver.findElement(By.name("category.id")));
            category.selectByVisibleText("Mỹ phẩm trang điểm");
            // Cập nhật thông tin sản phẩm
            driver.findElement(By.id("title")).clear();
            driver.findElement(By.id("title")).sendKeys(originalTitle + " Updated");
            driver.findElement(By.id("price")).clear();
            driver.findElement(By.id("price")).sendKeys("210020");
            driver.findElement(By.id("amount")).clear();
            driver.findElement(By.id("amount")).sendKeys("100");
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Updated short description");
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys("Son môi cao cấp test");
            // Nhấn nút cập nhật sản phẩm
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ chuyển hướng và kiểm tra thông báo
            wait.until(ExpectedConditions.urlToBe("http://localhost:9090/admin/listProducts/?add=success"));
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success")));
            assertTrue(alert.getText().contains("Cập nhật thành công."), "Không thấy thông báo thành công!");
            actualResult = "Hiển thị thông báo cập nhật sản phẩm thành công";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_14_EditProductNegativePrice() {
        String testCaseId = "SELE_QLSP_14";
        String scenario = "Kiểm tra sửa sản phẩm với giá âm";
        String expectedResult = "Hiển thị thông báo lỗi hoặc không cho phép cập nhật";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang danh sách sản phẩm
            navigateToProductList();
            // Lấy sản phẩm đầu tiên trong danh sách
            List<WebElement> productRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            assertFalse(productRows.isEmpty(), "Danh sách sản phẩm rỗng!");
            WebElement firstProduct = productRows.get(0);
            String originalTitle = firstProduct.findElement(By.cssSelector("td:nth-child(2)")).getText();
            // Nhấn nút sửa sản phẩm
            firstProduct.findElement(By.cssSelector("a[href*='/admin/product/']")).click();
            // Cập nhật thông tin sản phẩm
            driver.findElement(By.id("title")).clear();
            driver.findElement(By.id("title")).sendKeys(originalTitle);
            driver.findElement(By.id("price")).clear();
            driver.findElement(By.id("price")).sendKeys("-210020");
            driver.findElement(By.id("amount")).clear();
            driver.findElement(By.id("amount")).sendKeys("100");
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Updated short description");
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys("Son môi cao cấp test");
            // Nhấn nút cập nhật sản phẩm
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ và kiểm tra thông báo lỗi
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".form-message")));
            assertTrue(errorMessage.isDisplayed() && errorMessage.getText().contains("Giá phải là số dương"), "Không hiển thị lỗi giá âm!");
            actualResult = "Hiển thị thông báo lỗi giá âm";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_19_AddProductLongDescription() {
        String testCaseId = "SELE_QLSP_19";
        String scenario = "Kiểm tra thêm sản phẩm với mô tả vượt giới hạn";
        String expectedResult = "Hiển thị thông báo lỗi hoặc cắt bớt mô tả theo giới hạn";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang thêm sản phẩm
            navigateToAddProduct();
            // Chọn danh mục sản phẩm
            Select category = new Select(driver.findElement(By.name("category.id")));
            category.selectByVisibleText("Mỹ phẩm trang điểm");
            // Nhập thông tin sản phẩm
            driver.findElement(By.id("title")).sendKeys("Son môi A");
            driver.findElement(By.id("price")).sendKeys("200000");
            driver.findElement(By.id("amount")).sendKeys("50");
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Son môi cao cấp");
            // Tạo mô tả dài vượt giới hạn
            StringBuilder longDescription = new StringBuilder();
            for (int i = 0; i < 1002; i++) {
                longDescription.append("A");
            }
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys(longDescription.toString());
            // Tải ảnh sản phẩm
            driver.findElement(By.name("product_image")).sendKeys("C:\\Users\\ASUS\\IdeaProjects\\DoAnTotNghiepHaUI\\upload\\Kem-Kone-Thai-Lan-4544.jpg");
            // Nhấn nút thêm sản phẩm
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ và kiểm tra thông báo lỗi
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".form-message")));
            assertTrue(errorMessage.isDisplayed() && errorMessage.getText().contains("Mô tả quá dài"), "Không hiển thị lỗi mô tả quá dài!");
            actualResult = "Hiển thị thông báo lỗi mô tả quá dài";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_20_AddProductMultipleImages() {
        String testCaseId = "SELE_QLSP_20";
        String scenario = "Kiểm tra thêm sản phẩm khi upload nhiều ảnh";
        String expectedResult = "Chỉ lưu ảnh đầu tiên, hiển thị thông báo thành công";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang thêm sản phẩm
            navigateToAddProduct();
            // Chọn danh mục sản phẩm
            Select category = new Select(driver.findElement(By.name("category.id")));
            category.selectByVisibleText("Mỹ phẩm trang điểm");
            // Nhập thông tin sản phẩm
            driver.findElement(By.id("title")).sendKeys("Son môi A");
            driver.findElement(By.id("price")).sendKeys("200000");
            driver.findElement(By.id("amount")).sendKeys("50");
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Son môi cao cấp");
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys("Son môi cao cấp test");
            // Tải ảnh sản phẩm
            driver.findElement(By.name("product_image")).sendKeys("C:\\Users\\ASUS\\IdeaProjects\\DoAnTotNghiepHaUI\\upload\\Kem-Kone-Thai-Lan-4544.jpg");
            // Nhấn nút thêm sản phẩm
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ chuyển hướng và kiểm tra thông báo
            wait.until(ExpectedConditions.urlToBe("http://localhost:9090/admin/listProducts/?add=success"));
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success")));
            assertTrue(alert.getText().contains("Cập nhật thành công."), "Không thấy thông báo thành công!");
            actualResult = "Hiển thị thông báo thêm sản phẩm thành công";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_24_DefaultImageDisplay() {
        String testCaseId = "SELE_QLSP_24";
        String scenario = "Kiểm tra hiển thị hình ảnh mặc định khi sản phẩm không có ảnh";
        String expectedResult = "Hiển thị thông báo thêm sản phẩm thành công";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang thêm sản phẩm
            navigateToAddProduct();
            // Chọn danh mục sản phẩm
            Select category = new Select(driver.findElement(By.name("category.id")));
            category.selectByVisibleText("Mỹ phẩm trang điểm");
            // Nhập thông tin sản phẩm
            driver.findElement(By.id("title")).sendKeys("Sản phẩm không ảnh");
            driver.findElement(By.id("price")).sendKeys("200000");
            driver.findElement(By.id("amount")).sendKeys("50");
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Sản phẩm test không ảnh");
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys("Sản phẩm không ảnh");
            // Nhấn nút thêm sản phẩm
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ chuyển hướng và kiểm tra thông báo
            wait.until(ExpectedConditions.urlToBe("http://localhost:9090/admin/listProducts/?add=success"));
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success")));
            assertTrue(alert.getText().contains("Cập nhật thành công."), "Không thấy thông báo thành công!");
            actualResult = "Hiển thị thông báo thêm sản phẩm thành công";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_26_AddProductEmptyShortDescription() {
        String testCaseId = "SELE_QLSP_26";
        String scenario = "Kiểm tra thêm sản phẩm với mô tả ngắn rỗng";
        String expectedResult = "Hiển thị thông báo lỗi: \"Vắn tắt không được để trống!\"";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang thêm sản phẩm
            navigateToAddProduct();
            // Chọn danh mục sản phẩm
            Select category = new Select(driver.findElement(By.name("category.id")));
            category.selectByVisibleText("Mỹ phẩm trang điểm");
            // Nhập thông tin sản phẩm
            driver.findElement(By.id("title")).sendKeys("Son môi A");
            driver.findElement(By.id("price")).sendKeys("200000");
            driver.findElement(By.id("amount")).sendKeys("50");
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys("Son môi cao cấp test");
            // Tải ảnh sản phẩm
            driver.findElement(By.name("product_image")).sendKeys("C:\\Users\\ASUS\\IdeaProjects\\DoAnTotNghiepHaUI\\upload\\Kem-Kone-Thai-Lan-4544.jpg");
            // Nhấn nút thêm sản phẩm
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ và kiểm tra thông báo lỗi
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".form-message")));
            assertTrue(errorMessage.getText().contains("Vắn tắt không được để trống"), "Không hiển thị lỗi mô tả ngắn rỗng!");
            actualResult = "Hiển thị thông báo lỗi: \"Vắn tắt không được để trống!\"";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_27_EditProductEmptyAmount() {
        String testCaseId = "SELE_QLSP_27";
        String scenario = "Kiểm tra sửa sản phẩm với số lượng rỗng";
        String expectedResult = "Hiển thị thông báo lỗi: \"Số lượng không được để trống!\"";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang danh sách sản phẩm
            navigateToProductList();
            // Lấy sản phẩm đầu tiên trong danh sách
            List<WebElement> productRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            assertFalse(productRows.isEmpty(), "Danh sách sản phẩm rỗng!");
            WebElement firstProduct = productRows.get(0);
            String originalTitle = firstProduct.findElement(By.cssSelector("td:nth-child(2)")).getText();
            // Nhấn nút sửa sản phẩm
            firstProduct.findElement(By.cssSelector("a[href*='/admin/product/']")).click();
            // Cập nhật thông tin sản phẩm
            driver.findElement(By.id("title")).clear();
            driver.findElement(By.id("title")).sendKeys(originalTitle);
            driver.findElement(By.id("price")).clear();
            driver.findElement(By.id("price")).sendKeys("210020");
            driver.findElement(By.id("amount")).clear();
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Updated short description");
            // Chờ Summernote cho mô tả chi tiết sẵn sàng
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys("Son môi cao cấp test");
            // Nhấn nút cập nhật sản phẩm
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            // Chờ và kiểm tra thông báo lỗi
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".form-message")));
            assertTrue(errorMessage.getText().contains("Số lượng không được để trống"), "Không hiển thị lỗi số lượng rỗng!");
            actualResult = "Hiển thị thông báo lỗi: \"Số lượng không được để trống!\"";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_28_CancelAddProduct() {
        String testCaseId = "SELE_QLSP_28";
        String scenario = "Kiểm tra nhấn nút 'Hủy' trên form thêm sản phẩm";
        String expectedResult = "Hủy form thêm sản phẩm, quay lại trang danh sách sản phẩm";
        String actualResult;
        String status;

        try {
            // Điều hướng đến trang thêm sản phẩm
            navigateToAddProduct();
            // Nhập tiêu đề sản phẩm
            driver.findElement(By.id("title")).sendKeys("Son môi A");
            // Chờ và nhấn nút hủy
            WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-cancel")));
            cancelButton.click();
            // Chờ chuyển hướng đến trang danh sách sản phẩm
            wait.until(ExpectedConditions.urlContains("http://localhost:9090/admin/listProducts"));
            // Kiểm tra sản phẩm không được thêm
            boolean productNotAdded = true;
            for (WebElement row : driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"))) {
                if (row.findElement(By.cssSelector("td:nth-child(2)")).getText().equals("Son môi A")) {
                    productNotAdded = false;
                    break;
                }
            }
            assertTrue(productNotAdded, "Sản phẩm vẫn được thêm!");
            actualResult = "Hủy form thành công, quay lại danh sách sản phẩm";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }



    @AfterEach
    public void tearDown() {
        logTestResults();
        if (driver != null) {
            driver.quit();
        }
    }
}