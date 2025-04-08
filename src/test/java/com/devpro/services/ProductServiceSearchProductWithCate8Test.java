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
 * Lớp kiểm thử đơn vị cho ProductService, tập trung vào phương thức searchProductWithCate8().
 * Sử dụng Mockito để mock các phụ thuộc như EntityManager.
 * Log trạng thái dữ liệu và kết quả bằng SLF4J.
 */
public class ProductServiceSearchProductWithCate8Test {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceSearchProductWithCate8Test.class);

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

    // Test case 1: idCate hợp lệ, có nhiều sản phẩm khớp (trả về đúng 8 sản phẩm)
    @Test
    public void testSearchProductWithCate8_ValidIdCateWithManyProducts() {
        logger.info("Bắt đầu testSearchProductWithCate8_ValidIdCateWithManyProducts: Kiểm tra searchProductWithCate8 với idCate hợp lệ và có nhiều sản phẩm khớp.");
        // Chuẩn bị dữ liệu
        int idCate = 1;
        List<Product> mockProducts = new ArrayList<>();
        for (int i = 1; i <= 10; i++) { // Tạo 10 sản phẩm, nhưng chỉ lấy 8 sản phẩm do limit 0,8
            Product product = new Product();
            product.setId(i);
            product.setTitle("Product " + i);
            product.setPrice(new BigDecimal(i * 100));
            product.setStatus(true); // status = 1
            mockProducts.add(product);
        }
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id=1 order by rand() limit 0,8;", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts.subList(0, 8)); // Chỉ lấy 8 sản phẩm đầu tiên
        logger.info("Dữ liệu trước khi gọi: idCate = {}, mockProducts = {}", idCate, mockProducts);

        // Thực hiện
        List<Product> result = productService.searchProductWithCate8(idCate);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id=1 order by rand() limit 0,8;", Product.class);
        verify(mockQuery, times(1)).getResultList();
        assertEquals(8, result.size(), "Danh sách trả về phải có đúng 8 sản phẩm");
        logger.info("Hoàn thành testSearchProductWithCate8_ValidIdCateWithManyProducts.");
    }

    // Test case 2: idCate hợp lệ, có ít sản phẩm khớp (ít hơn 8 sản phẩm)
    @Test
    public void testSearchProductWithCate8_ValidIdCateWithFewProducts() {
        logger.info("Bắt đầu testSearchProductWithCate8_ValidIdCateWithFewProducts: Kiểm tra searchProductWithCate8 với idCate hợp lệ và có ít sản phẩm khớp.");
        // Chuẩn bị dữ liệu
        int idCate = 2;
        List<Product> mockProducts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) { // Tạo 5 sản phẩm
            Product product = new Product();
            product.setId(i);
            product.setTitle("Product " + i);
            product.setPrice(new BigDecimal(i * 100));
            product.setStatus(true);
            mockProducts.add(product);
        }
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id=2 order by rand() limit 0,8;", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: idCate = {}, mockProducts = {}", idCate, mockProducts);

        // Thực hiện
        List<Product> result = productService.searchProductWithCate8(idCate);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id=2 order by rand() limit 0,8;", Product.class);
        verify(mockQuery, times(1)).getResultList();
        assertEquals(5, result.size(), "Danh sách trả về phải có đúng 5 sản phẩm");
        logger.info("Hoàn thành testSearchProductWithCate8_ValidIdCateWithFewProducts.");
    }

    // Test case 3: idCate hợp lệ, không có sản phẩm nào khớp
    @Test
    public void testSearchProductWithCate8_ValidIdCateWithNoProducts() {
        logger.info("Bắt đầu testSearchProductWithCate8_ValidIdCateWithNoProducts: Kiểm tra searchProductWithCate8 với idCate hợp lệ nhưng không có sản phẩm nào khớp.");
        // Chuẩn bị dữ liệu
        int idCate = 3;
        List<Product> mockProducts = new ArrayList<>();
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id=3 order by rand() limit 0,8;", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: idCate = {}, mockProducts = {}", idCate, mockProducts);

        // Thực hiện
        List<Product> result = productService.searchProductWithCate8(idCate);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id=3 order by rand() limit 0,8;", Product.class);
        verify(mockQuery, times(1)).getResultList();
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng");
        logger.info("Hoàn thành testSearchProductWithCate8_ValidIdCateWithNoProducts.");
    }

    // Test case 4: idCate là giá trị âm
    @Test
    public void testSearchProductWithCate8_NegativeIdCate() {
        logger.info("Bắt đầu testSearchProductWithCate8_NegativeIdCate: Kiểm tra searchProductWithCate8 với idCate là giá trị âm.");
        // Chuẩn bị dữ liệu
        int idCate = -1;
        List<Product> mockProducts = new ArrayList<>();
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id=-1 order by rand() limit 0,8;", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: idCate = {}, mockProducts = {}", idCate, mockProducts);

        // Thực hiện
        List<Product> result = productService.searchProductWithCate8(idCate);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id=-1 order by rand() limit 0,8;", Product.class);
        verify(mockQuery, times(1)).getResultList();
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng vì idCate âm không hợp lệ");
        logger.info("Hoàn thành testSearchProductWithCate8_NegativeIdCate. Lưu ý: Phương thức cần kiểm tra giá trị idCate để tránh giá trị không hợp lệ.");
    }

    // Test case 5: idCate là 0
    @Test
    public void testSearchProductWithCate8_ZeroIdCate() {
        logger.info("Bắt đầu testSearchProductWithCate8_ZeroIdCate: Kiểm tra searchProductWithCate8 với idCate là 0.");
        // Chuẩn bị dữ liệu
        int idCate = 0;
        List<Product> mockProducts = new ArrayList<>();
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id=0 order by rand() limit 0,8;", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: idCate = {}, mockProducts = {}", idCate, mockProducts);

        // Thực hiện
        List<Product> result = productService.searchProductWithCate8(idCate);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id=0 order by rand() limit 0,8;", Product.class);
        verify(mockQuery, times(1)).getResultList();
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng vì idCate = 0 không hợp lệ");
        logger.info("Hoàn thành testSearchProductWithCate8_ZeroIdCate. Lưu ý: Phương thức cần kiểm tra giá trị idCate để tránh giá trị không hợp lệ.");
    }

    // Test case 6: idCate hợp lệ, nhưng tất cả sản phẩm có status = 0
    @Test
    public void testSearchProductWithCate8_ValidIdCateWithInactiveProducts() {
        logger.info("Bắt đầu testSearchProductWithCate8_ValidIdCateWithInactiveProducts: Kiểm tra searchProductWithCate8 với idCate hợp lệ nhưng tất cả sản phẩm có status = 0.");
        // Chuẩn bị dữ liệu
        int idCate = 4;
        List<Product> mockProducts = new ArrayList<>();
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id=4 order by rand() limit 0,8;", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: idCate = {}, mockProducts = {}", idCate, mockProducts);

        // Thực hiện
        List<Product> result = productService.searchProductWithCate8(idCate);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id=4 order by rand() limit 0,8;", Product.class);
        verify(mockQuery, times(1)).getResultList();
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng vì không có sản phẩm nào có status = 1");
        logger.info("Hoàn thành testSearchProductWithCate8_ValidIdCateWithInactiveProducts.");
    }

    // Test case 7: entityManager.createNativeQuery() ném ngoại lệ
    @Test
    public void testSearchProductWithCate8_CreateQueryException() {
        logger.info("Bắt đầu testSearchProductWithCate8_CreateQueryException: Kiểm tra searchProductWithCate8 khi entityManager.createNativeQuery() ném ngoại lệ.");
        // Chuẩn bị dữ liệu
        int idCate = 5;
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id=5 order by rand() limit 0,8;", Product.class))
                .thenThrow(new RuntimeException("Error creating query"));
        logger.info("Dữ liệu trước khi gọi: idCate = {}", idCate);

        // Thực hiện & Kiểm tra
        assertThrows(RuntimeException.class, () -> {
            productService.searchProductWithCate8(idCate);
        }, "Phải ném RuntimeException khi entityManager.createNativeQuery() thất bại");

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: Ném ngoại lệ như mong đợi.");

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id=5 order by rand() limit 0,8;", Product.class);
        verify(mockQuery, never()).getResultList();
        logger.info("Hoàn thành testSearchProductWithCate8_CreateQueryException.");
    }

    // Test case 8: query.getResultList() ném ngoại lệ
    @Test
    public void testSearchProductWithCate8_QueryResultException() {
        logger.info("Bắt đầu testSearchProductWithCate8_QueryResultException: Kiểm tra searchProductWithCate8 khi query.getResultList() ném ngoại lệ.");
        // Chuẩn bị dữ liệu
        int idCate = 6;
        when(entityManager.createNativeQuery("select * from tbl_product where status = 1 and category_id=6 order by rand() limit 0,8;", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenThrow(new RuntimeException("Database query error"));
        logger.info("Dữ liệu trước khi gọi: idCate = {}", idCate);

        // Thực hiện & Kiểm tra
        assertThrows(RuntimeException.class, () -> {
            productService.searchProductWithCate8(idCate);
        }, "Phải ném RuntimeException khi query.getResultList() thất bại");

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: Ném ngoại lệ như mong đợi.");

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("select * from tbl_product where status = 1 and category_id=6 order by rand() limit 0,8;", Product.class);
        verify(mockQuery, times(1)).getResultList();
        logger.info("Hoàn thành testSearchProductWithCate8_QueryResultException.");
    }
}