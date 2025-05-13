package com.devpro.seleniumqlsp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lớp kiểm thử chức năng quản lý sản phẩm trong giao diện admin bằng Selenium WebDriver.
 * Chứa các test case đã pass.
 */
public class AdminProductTest1 {
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

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\Drivers\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        testResults = new ArrayList<>();

        try {
            login("admin", "admin");
            wait.until(ExpectedConditions.urlToBe("http://localhost:9090/admin"));
        } catch (Exception e) {
            System.err.println("Lỗi đăng nhập trong setUp: " + e.getMessage());
            throw new RuntimeException("Không thể đăng nhập vào tài khoản admin: " + e.getMessage());
        }
    }

    private void logTestResults() {
        try (FileWriter writer = new FileWriter("test_results_passed.log", true)) {
            for (TestResult result : testResults) {
                writer.write(result.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    private void login(String username, String password) {
        driver.get("http://localhost:9090/cai-nay-la-mapping-trong-adminlogincontroller");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.className("login100-form-btn")).click();
    }

    private void navigateToProductList() {
        driver.get("http://localhost:9090/admin");
        WebElement productMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Quản lý sản phẩm']]")));
        productMenu.click();
        WebElement listProducts = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/listProducts']")));
        listProducts.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.table-data2")));
    }

    private void navigateToAddProduct() {
        driver.get("http://localhost:9090/admin");
        WebElement productMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Quản lý sản phẩm']]")));
        productMenu.click();
        WebElement addProduct = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/addProduct']")));
        addProduct.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("form-addProduct")));
    }

    @Test
    public void test_SELE_QLSP_04_ViewProductList() {
        String testCaseId = "SELE_QLSP_04";
        String scenario = "Kiểm tra hiển thị danh sách sản phẩm";
        String expectedResult = "Danh sách sản phẩm hiển thị với các cột: #, Tên, Hình ảnh, Danh mục, Giá, Số lượng, Trạng thái";
        String actualResult;
        String status;

        try {
            navigateToProductList();
            List<WebElement> productRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            assertFalse(productRows.isEmpty(), "Danh sách sản phẩm không hiển thị!");
            WebElement firstProduct = productRows.get(0);
            assertTrue(firstProduct.findElement(By.cssSelector("td:nth-child(2)")).getText().length() > 0, "Tên sản phẩm trống!");
            assertTrue(Integer.parseInt(firstProduct.findElement(By.cssSelector("td:nth-child(6)")).getText()) >= 0, "Số lượng không hợp lệ!");
            actualResult = "Danh sách sản phẩm hiển thị với các cột hợp lệ";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_05_HiddenProductStatus() {
        String testCaseId = "SELE_QLSP_05";
        String scenario = "Kiểm tra hiển thị sản phẩm với trạng thái ẩn";
        String expectedResult = "Sản phẩm ẩn hiển thị với badge đỏ và biểu tượng \"x\"";
        String actualResult = "Không xác định";
        String status = "Fail";

        try {
            navigateToProductList();
            List<WebElement> productRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            boolean hiddenProductFound = false;
            if (productRows.isEmpty()) {
                actualResult = "Danh sách sản phẩm rỗng";
                System.out.println("Danh sách sản phẩm rỗng. Vui lòng thêm sản phẩm trước khi chạy test này.");
            } else {
                for (WebElement row : productRows) {
                    try {
                        WebElement statusBadge = row.findElement(By.cssSelector("td:nth-child(7) span.badge"));
                        String statusValue = row.findElement(By.cssSelector("td:nth-child(7) input")).getAttribute("value");
                        if ("false".equals(statusValue)) {
                            assertTrue(statusBadge.getAttribute("style").contains("background: red"), "Sản phẩm ẩn không hiển thị badge đỏ!");
                            assertTrue(statusBadge.findElement(By.cssSelector("i.fas.fa-times")).isDisplayed(), "Sản phẩm ẩn không hiển thị biểu tượng 'x'!");
                            hiddenProductFound = true;
                            actualResult = "Sản phẩm ẩn hiển thị với badge đỏ và biểu tượng \"x\"";
                            status = "Pass";
                            break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                if (!hiddenProductFound) {
                    actualResult = "Không tìm thấy sản phẩm ẩn";
                    System.out.println("Không tìm thấy sản phẩm ẩn. Vui lòng ẩn một sản phẩm trước khi chạy test này.");
                }
            }
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_07_AddProductEmptyTitle() {
        String testCaseId = "SELE_QLSP_07";
        String scenario = "Kiểm tra thêm sản phẩm với tên rỗng";
        String expectedResult = "Hiển thị thông báo lỗi: \"Tên sản phẩm không được để trống!\"";
        String actualResult;
        String status;

        try {
            navigateToAddProduct();
            driver.findElement(By.id("price")).sendKeys("200000");
            driver.findElement(By.id("amount")).sendKeys("50");
            // Chờ Summernote cho mô tả ngắn sẵn sàng
            WebElement shortDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtShortlDescription + .note-editor .note-editable")));
            shortDesc.sendKeys("Son môi cao cấp");
            driver.findElement(By.name("product_image")).sendKeys("C:\\Users\\ASUS\\IdeaProjects\\DoAnTotNghiepHaUI\\upload\\Kem-Kone-Thai-Lan-4544.jpg");
            StringBuilder longDescription = new StringBuilder();
            for (int i = 0; i < 1002; i++) {
                longDescription.append("A");
            }
            WebElement detailDesc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#txtDetailDescription + .note-editor .note-editable")));
            detailDesc.sendKeys(longDescription.toString());
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#title + span.form-message")));
            assertTrue(errorMessage.getText().contains("Tên sản phẩm không được để trống"), "Không hiển thị lỗi tên rỗng!");
            actualResult = "Hiển thị thông báo lỗi: \"Tên sản phẩm không được để trống!\"";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_13_EditProductEmptyTitle() {
        String testCaseId = "SELE_QLSP_13";
        String scenario = "Kiểm tra sửa sản phẩm với tên rỗng";
        String expectedResult = "Hiển thị thông báo lỗi: \"Tên sản phẩm không được để trống!\"";
        String actualResult;
        String status;

        try {
            navigateToProductList();
            List<WebElement> productRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            assertFalse(productRows.isEmpty(), "Danh sách sản phẩm rỗng!");
            WebElement firstProduct = productRows.get(0);
            firstProduct.findElement(By.cssSelector("a[href*='/admin/product/']")).click();
            driver.findElement(By.id("title")).clear();
            driver.findElement(By.id("price")).clear();
            driver.findElement(By.id("price")).sendKeys("210020");
            driver.findElement(By.id("amount")).clear();
            driver.findElement(By.id("amount")).sendKeys("100");
            driver.findElement(By.cssSelector("button.contact2-form-btn")).click();
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#title + span.form-message")));
            assertTrue(errorMessage.getText().contains("Tên sản phẩm không được để trống"), "Không hiển thị lỗi tên rỗng!");
            actualResult = "Hiển thị thông báo lỗi: \"Tên sản phẩm không được để trống!\"";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_15_HideProduct() {
        String testCaseId = "SELE_QLSP_15";
        String scenario = "Kiểm tra ẩn sản phẩm";
        String expectedResult = "Sản phẩm được ẩn (trạng thái = 0), danh sách cập nhật";
        String actualResult;
        String status;

        try {
            navigateToProductList();
            List<WebElement> productRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            assertFalse(productRows.isEmpty(), "Danh sách sản phẩm rỗng!");
            WebElement firstProduct = productRows.get(0);
            String productTitle = firstProduct.findElement(By.cssSelector("td:nth-child(2)")).getText();
            firstProduct.findElement(By.cssSelector("button.item[data-target*='myModal']")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-footer .btn-danger"))).click();
            navigateToProductList();
            boolean productHidden = false;
            for (WebElement row : driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"))) {
                if (row.findElement(By.cssSelector("td:nth-child(2)")).getText().equals(productTitle)) {
                    String statusValue = row.findElement(By.cssSelector("td:nth-child(7) input")).getAttribute("value");
                    if ("false".equals(statusValue)) {
                        productHidden = true;
                        break;
                    }
                }
            }
            assertTrue(productHidden, "Sản phẩm không được ẩn!");
            actualResult = "Sản phẩm được ẩn, danh sách cập nhật";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_16_CancelHideProduct() {
        String testCaseId = "SELE_QLSP_16";
        String scenario = "Kiểm tra hủy ẩn sản phẩm";
        String expectedResult = "Sản phẩm giữ nguyên trạng thái ban đầu";
        String actualResult;
        String status;

        try {
            navigateToProductList();
            List<WebElement> productRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            assertFalse(productRows.isEmpty(), "Danh sách sản phẩm rỗng!");
            WebElement firstProduct = productRows.get(0);
            String productTitle = firstProduct.findElement(By.cssSelector("td:nth-child(2)")).getText();
            String initialStatus = firstProduct.findElement(By.cssSelector("td:nth-child(7) input")).getAttribute("value");
            firstProduct.findElement(By.cssSelector("button.item[data-target*='myModal']")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-footer .btn-default"))).click();
            navigateToProductList();
            boolean statusUnchanged = false;
            for (WebElement row : driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"))) {
                if (row.findElement(By.cssSelector("td:nth-child(2)")).getText().equals(productTitle)) {
                    String currentStatus = row.findElement(By.cssSelector("td:nth-child(7) input")).getAttribute("value");
                    if (currentStatus.equals(initialStatus)) {
                        statusUnchanged = true;
                        break;
                    }
                }
            }
            assertTrue(statusUnchanged, "Trạng thái sản phẩm bị thay đổi!");
            actualResult = "Sản phẩm giữ nguyên trạng thái ban đầu";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_17_ProductMenuToggle() {
        String testCaseId = "SELE_QLSP_17";
        String scenario = "Kiểm tra menu \"Quản lý sản phẩm\" xổ xuống/thu lại";
        String expectedResult = "Menu xổ xuống hiển thị 2 lựa chọn, nhấn lại thì thu lại";
        String actualResult;
        String status;

        try {
            driver.get("http://localhost:9090/admin");
            WebElement productMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(., 'Quản lý sản phẩm')]")));
            productMenu.click();
            WebElement submenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/admin/listProducts']")));
            assertTrue(submenu.isDisplayed(), "Menu không xổ xuống!");
            productMenu.click();
            Thread.sleep(500);
            assertFalse(driver.findElement(By.cssSelector("a[href='/admin/listProducts']")).isDisplayed(), "Menu không thu lại!");
            actualResult = "Menu xổ xuống và thu lại thành công";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_18_WrongMenuNavigation() {
        String testCaseId = "SELE_QLSP_18";
        String scenario = "Kiểm tra chuyển hướng sai menu";
        String expectedResult = "Không chuyển đến trang Quản lý sản phẩm (/admin/listProducts hoặc /admin/addProduct)";
        String actualResult;
        String status;

        try {
            driver.get("http://localhost:9090/admin");
            WebElement categoryMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(., 'Quản lý danh mục')]")));
            categoryMenu.click();
            WebElement listCategory = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/admin/listCategory']")));
            listCategory.click();
            assertNotEquals("http://localhost:9090/admin/listProducts", driver.getCurrentUrl(), "Chuyển hướng sai đến danh sách sản phẩm!");
            assertNotEquals("http://localhost:9090/admin/addProduct", driver.getCurrentUrl(), "Chuyển hướng sai đến thêm sản phẩm!");
            actualResult = "Không chuyển hướng đến trang Quản lý sản phẩm";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_21_SortProductByName() {
        String testCaseId = "SELE_QLSP_21";
        String scenario = "Kiểm tra sắp xếp bảng sản phẩm theo tên (A-Z)";
        String expectedResult = "Danh sách sản phẩm được sắp xếp theo thứ tự alphabet (A-Z)";
        String actualResult;
        String status;

        try {
            navigateToProductList();
            WebElement nameHeader = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("table.table-data2 thead th:nth-child(2)")));
            nameHeader.click();
            Thread.sleep(500);
            List<WebElement> productRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            assertFalse(productRows.isEmpty(), "Danh sách sản phẩm rỗng!");
            List<String> productNames = new ArrayList<>();
            for (WebElement row : productRows) {
                productNames.add(row.findElement(By.cssSelector("td:nth-child(2)")).getText());
            }
            boolean isSorted = true;
            for (int i = 1; i < productNames.size(); i++) {
                if (productNames.get(i - 1).compareTo(productNames.get(i)) > 0) {
                    isSorted = false;
                    break;
                }
            }
            assertTrue(isSorted, "Danh sách sản phẩm không được sắp xếp A-Z!");
            actualResult = "Danh sách sản phẩm được sắp xếp A-Z";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_22_SearchProduct() {
        String testCaseId = "SELE_QLSP_22";
        String scenario = "Kiểm tra tìm kiếm sản phẩm trong bảng";
        String expectedResult = "Hiển thị các sản phẩm chứa từ khóa tìm kiếm";
        String actualResult;
        String status;

        try {
            navigateToProductList();
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.dataTables_filter input[type='search']")));
            searchBox.sendKeys("Son");
            Thread.sleep(500);
            List<WebElement> productRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            assertFalse(productRows.isEmpty(), "Không tìm thấy sản phẩm nào!");
            boolean allMatch = true;
            for (WebElement row : productRows) {
                String productName = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
                if (!productName.toLowerCase().contains("son")) {
                    allMatch = false;
                    break;
                }
            }
            assertTrue(allMatch, "Kết quả tìm kiếm không khớp với từ khóa!");
            actualResult = "Hiển thị các sản phẩm chứa từ khóa 'Son'";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_23_PaginationNextPage() {
        String testCaseId = "SELE_QLSP_23";
        String scenario = "Kiểm tra chuyển sang trang tiếp theo trong bảng sản phẩm";
        String expectedResult = "Chuyển sang trang tiếp theo, hiển thị các sản phẩm mới";
        String actualResult;
        String status;

        try {
            navigateToProductList();
            List<WebElement> initialRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            assertFalse(initialRows.isEmpty(), "Danh sách sản phẩm rỗng!");
            List<String> initialNames = new ArrayList<>();
            for (WebElement row : initialRows) {
                initialNames.add(row.findElement(By.cssSelector("td:nth-child(2)")).getText());
            }
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".dataTables_paginate .next")));
            nextButton.click();
            Thread.sleep(500);
            List<WebElement> newRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            boolean differentPage = false;
            for (WebElement row : newRows) {
                String productName = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
                if (!initialNames.contains(productName)) {
                    differentPage = true;
                    break;
                }
            }
            assertTrue(differentPage, "Không chuyển sang trang mới!");
            actualResult = "Chuyển sang trang tiếp theo thành công";
            status = "Pass";
        } catch (Exception e) {
            actualResult = "Lỗi: " + e.getMessage();
            status = "Fail";
        }
        testResults.add(new TestResult(testCaseId, scenario, expectedResult, actualResult, status));
        System.out.println(testResults.get(testResults.size() - 1));
    }

    @Test
    public void test_SELE_QLSP_25_StatusBadgeDisplay() {
        String testCaseId = "SELE_QLSP_25";
        String scenario = "Kiểm tra hiển thị badge trạng thái (xanh cho active, đỏ cho hidden)";
        String expectedResult = "Badge xanh cho trạng thái active, đỏ cho trạng thái hidden";
        String actualResult;
        String status;

        try {
            navigateToProductList();
            List<WebElement> productRows = driver.findElements(By.cssSelector("table.table-data2 tbody tr.tr-shadow"));
            assertFalse(productRows.isEmpty(), "Danh sách sản phẩm rỗng!");
            boolean activeFound = false, hiddenFound = false;
            for (WebElement row : productRows) {
                WebElement badge = row.findElement(By.cssSelector("td:nth-child(7) span.badge"));
                String statusValue = row.findElement(By.cssSelector("td:nth-child(7) input")).getAttribute("value");
                if ("true".equals(statusValue)) {
                    assertTrue(badge.getAttribute("style").contains("background: green"), "Badge không hiển thị màu xanh cho active!");
                    activeFound = true;
                } else if ("false".equals(statusValue)) {
                    assertTrue(badge.getAttribute("style").contains("background: red"), "Badge không hiển thị màu đỏ cho hidden!");
                    hiddenFound = true;
                }
                if (activeFound && hiddenFound) break;
            }
            if (!activeFound || !hiddenFound) {
                actualResult = "Không tìm thấy cả sản phẩm active và hidden";
                status = "Fail";
                System.out.println("Vui lòng đảm bảo có sản phẩm active và hidden.");
            } else {
                actualResult = "Badge hiển thị đúng màu cho trạng thái";
                status = "Pass";
            }
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