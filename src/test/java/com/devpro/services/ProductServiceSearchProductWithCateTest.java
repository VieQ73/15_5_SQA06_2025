package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devpro.entities.Product;

/**
 * Lớp kiểm thử đơn vị cho ProductService, tập trung vào phương thức searchProductWithCate().
 * Sử dụng Mockito để mock các phụ thuộc như EntityManager.
 * Log trạng thái dữ liệu và kết quả bằng SLF4J.
 */
public class ProductServiceSearchProductWithCateTest {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceSearchProductWithCateTest.class);

    @InjectMocks
    private ProductService productService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query mockQuery;

    @BeforeEach
    public void thietLap() {
        // Khởi tạo mock
        MockitoAnnotations.initMocks(this);
        logger.info("Thiết lập dữ liệu kiểm thử hoàn tất.");
    }

    @AfterEach
    public void hoanTac() {
        // Reset mock
        reset(mockQuery, entityManager);
        logger.info("Hoàn tác dữ liệu kiểm thử hoàn tất.");
    }

    // Test case 1: seo hợp lệ, có danh mục và sản phẩm khớp
    @Test
    public void testSearchProductWithCate_ValidSeoWithMatchingCategoryAndProducts() {
        logger.info("Bắt đầu testSearchProductWithCate_ValidSeoWithMatchingCategoryAndProducts: Kiểm tra searchProductWithCate với seo hợp lệ, có danh mục và sản phẩm khớp.");
        // Chuẩn bị dữ liệu
        String seo = "electronics";
        List<Product> mockProducts = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Product product = new Product();
            product.setId(i);
            product.setTitle("Product " + i);
            product.setPrice(new BigDecimal(i * 100));
            product.setStatus(true);
            mockProducts.add(product);
        }
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='electronics')", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: seo = '{}', mockProducts = {}", seo, mockProducts);

        // Thực hiện
        List<Product> result = productService.searchProductWithCate(seo);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='electronics')", Product.class);
        verify(mockQuery, times(1)).getResultList();
        assertEquals(3, result.size(), "Danh sách trả về phải có đúng 3 sản phẩm");
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Hoàn thành testSearchProductWithCate_ValidSeoWithMatchingCategoryAndProducts.");
    }

    // Test case 2: seo hợp lệ, có danh mục khớp nhưng không có sản phẩm
    @Test
    public void testSearchProductWithCate_ValidSeoWithMatchingCategoryButNoProducts() {
        logger.info("Bắt đầu testSearchProductWithCate_ValidSeoWithMatchingCategoryButNoProducts: Kiểm tra searchProductWithCate với seo hợp lệ, có danh mục nhưng không có sản phẩm.");
        // Chuẩn bị dữ liệu
        String seo = "clothing";
        List<Product> mockProducts = new ArrayList<>();
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='clothing')", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: seo = '{}', mockProducts = {}", seo, mockProducts);

        // Thực hiện
        List<Product> result = productService.searchProductWithCate(seo);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='clothing')", Product.class);
        verify(mockQuery, times(1)).getResultList();
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng");
        logger.info("Hoàn thành testSearchProductWithCate_ValidSeoWithMatchingCategoryButNoProducts.");
    }

    // Test case 3: seo hợp lệ, không có danh mục nào khớp
    @Test
    public void testSearchProductWithCate_ValidSeoWithNoMatchingCategory() {
        logger.info("Bắt đầu testSearchProductWithCate_ValidSeoWithNoMatchingCategory: Kiểm tra searchProductWithCate với seo hợp lệ nhưng không có danh mục nào khớp.");
        // Chuẩn bị dữ liệu
        String seo = "nonexistent";
        List<Product> mockProducts = new ArrayList<>();
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='nonexistent')", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: seo = '{}', mockProducts = {}", seo, mockProducts);

        // Thực hiện
        List<Product> result = productService.searchProductWithCate(seo);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='nonexistent')", Product.class);
        verify(mockQuery, times(1)).getResultList();
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng");
        logger.info("Hoàn thành testSearchProductWithCate_ValidSeoWithNoMatchingCategory.");
    }

    // Test case 4: seo là null
    @Test
    public void testSearchProductWithCate_SeoIsNull() {
        logger.info("Bắt đầu testSearchProductWithCate_SeoIsNull: Kiểm tra searchProductWithCate với seo là null.");
        // Chuẩn bị dữ liệu
        String seo = null;
        logger.info("Dữ liệu trước khi gọi: seo = {}", seo);

        // Thực hiện & Kiểm tra
        assertThrows(NullPointerException.class, () -> {
            productService.searchProductWithCate(seo);
        }, "Phải ném NullPointerException khi seo là null");

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: Ném ngoại lệ như mong đợi.");

        // Kiểm tra
        verify(entityManager, never()).createNativeQuery(anyString(), eq(Product.class));
        verify(mockQuery, never()).getResultList();
        logger.info("Hoàn thành testSearchProductWithCate_SeoIsNull. Lưu ý: Phương thức cần kiểm tra giá trị seo để tránh ngoại lệ.");
    }

    // Test case 5: seo là chuỗi rỗng
    @Test
    public void testSearchProductWithCate_SeoIsEmpty() {
        logger.info("Bắt đầu testSearchProductWithCate_SeoIsEmpty: Kiểm tra searchProductWithCate với seo là chuỗi rỗng.");
        // Chuẩn bị dữ liệu
        String seo = "";
        List<Product> mockProducts = new ArrayList<>();
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='')", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: seo = '{}', mockProducts = {}", seo, mockProducts);

        // Thực hiện
        List<Product> result = productService.searchProductWithCate(seo);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='')", Product.class);
        verify(mockQuery, times(1)).getResultList();
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng vì seo rỗng không hợp lệ");
        logger.info("Hoàn thành testSearchProductWithCate_SeoIsEmpty. Lưu ý: Phương thức cần kiểm tra giá trị seo để tránh giá trị không hợp lệ.");
    }

    // Test case 6: seo chứa ký tự đặc biệt (kiểm tra nguy cơ SQL injection)
    @Test
    public void testSearchProductWithCate_SeoWithSpecialCharacters() {
        logger.info("Bắt đầu testSearchProductWithCate_SeoWithSpecialCharacters: Kiểm tra searchProductWithCate với seo chứa ký tự đặc biệt.");
        // Chuẩn bị dữ liệu
        String seo = "' OR '1'='1";
        List<Product> mockProducts = new ArrayList<>();
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='' OR '1'='1')", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: seo = '{}', mockProducts = {}", seo, mockProducts);

        // Thực hiện
        List<Product> result = productService.searchProductWithCate(seo);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='' OR '1'='1')", Product.class);
        verify(mockQuery, times(1)).getResultList();
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng (giả lập trường hợp không có sản phẩm)");
        logger.info("Hoàn thành testSearchProductWithCate_SeoWithSpecialCharacters. Lưu ý: Phương thức cần sửa để sử dụng parameterized query tránh SQL injection.");
    }

    // Test case 7: entityManager.createNativeQuery() ném ngoại lệ
    @Test
    public void testSearchProductWithCate_CreateQueryException() {
        logger.info("Bắt đầu testSearchProductWithCate_CreateQueryException: Kiểm tra searchProductWithCate khi entityManager.createNativeQuery() ném ngoại lệ.");
        // Chuẩn bị dữ liệu
        String seo = "electronics";
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='electronics')", Product.class))
                .thenThrow(new RuntimeException("Error creating query"));
        logger.info("Dữ liệu trước khi gọi: seo = '{}'", seo);

        // Thực hiện & Kiểm tra
        assertThrows(RuntimeException.class, () -> {
            productService.searchProductWithCate(seo);
        }, "Phải ném RuntimeException khi entityManager.createNativeQuery() thất bại");

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: Ném ngoại lệ như mong đợi.");

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='electronics')", Product.class);
        verify(mockQuery, never()).getResultList();
        logger.info("Hoàn thành testSearchProductWithCate_CreateQueryException.");
    }

    // Test case 8: query.getResultList() ném ngoại lệ
    @Test
    public void testSearchProductWithCate_QueryResultException() {
        logger.info("Bắt đầu testSearchProductWithCate_QueryResultException: Kiểm tra searchProductWithCate khi query.getResultList() ném ngoại lệ.");
        // Chuẩn bị dữ liệu
        String seo = "electronics";
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='electronics')", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenThrow(new RuntimeException("Database query error"));
        logger.info("Dữ liệu trước khi gọi: seo = '{}'", seo);

        // Thực hiện & Kiểm tra
        assertThrows(RuntimeException.class, () -> {
            productService.searchProductWithCate(seo);
        }, "Phải ném RuntimeException khi query.getResultList() thất bại");

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: Ném ngoại lệ như mong đợi.");

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id in (select id from tbl_category where seo='electronics')", Product.class);
        verify(mockQuery, times(1)).getResultList();
        logger.info("Hoàn thành testSearchProductWithCate_QueryResultException.");
    }
}