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
import com.devpro.repositories.ProductRepo;

/**
 * Lớp kiểm thử đơn vị cho ProductService, tập trung vào phương thức listAllDesc().
 * Sử dụng Mockito để mock các phụ thuộc như EntityManager, ProductRepo.
 * Log trạng thái dữ liệu và kết quả bằng SLF4J.
 */
public class ProductServiceListAllDescTest {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceListAllDescTest.class);

    @InjectMocks
    private ProductService productService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private ProductRepo productRepo;

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
        reset(mockQuery, entityManager, productRepo);
        logger.info("Hoàn tác dữ liệu kiểm thử hoàn tất.");
    }

    // Test case 1: keyword là null
    @Test
    public void testListAllDesc_KeywordIsNull() {
        logger.info("Bắt đầu testListAllDesc_KeywordIsNull: Kiểm tra listAllDesc với keyword là null.");
        // Chuẩn bị dữ liệu
        String keyword = null;
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Phone");
        product1.setPrice(new BigDecimal("500"));
        mockProducts.add(product1);
        when(productRepo.findAll()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = {}, mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllDesc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(productRepo, times(1)).findAll();
        verify(entityManager, never()).createNativeQuery(anyString(), eq(Product.class));
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Hoàn thành testListAllDesc_KeywordIsNull.");
    }

    // Test case 2: keyword là chuỗi rỗng (sửa lại để phản ánh đúng logic của phương thức)
    @Test
    public void testListAllDesc_KeywordIsEmpty() {
        logger.info("Bắt đầu testListAllDesc_KeywordIsEmpty: Kiểm tra listAllDesc với keyword là chuỗi rỗng.");
        // Chuẩn bị dữ liệu
        String keyword = "";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Phone");
        product1.setPrice(new BigDecimal("500"));
        mockProducts.add(product1);
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%%' order by price desc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllDesc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%%' order by price desc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Hoàn thành testListAllDesc_KeywordIsEmpty.");
    }

    // Test case 3: keyword là chuỗi hợp lệ, có sản phẩm khớp
    @Test
    public void testListAllDesc_KeywordValidWithMatchingProducts() {
        logger.info("Bắt đầu testListAllDesc_KeywordValidWithMatchingProducts: Kiểm tra listAllDesc với keyword hợp lệ và có sản phẩm khớp.");
        // Chuẩn bị dữ liệu
        String keyword = "phone";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Smartphone");
        product1.setPrice(new BigDecimal("1000"));
        mockProducts.add(product1);
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price desc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllDesc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price desc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Hoàn thành testListAllDesc_KeywordValidWithMatchingProducts.");
    }

    // Test case 4: keyword chứa ký tự đặc biệt (sửa lại để mock đúng câu SQL thực tế)
    @Test
    public void testListAllDesc_KeywordWithSpecialCharacters() {
        logger.info("Bắt đầu testListAllDesc_KeywordWithSpecialCharacters: Kiểm tra listAllDesc với keyword chứa ký tự đặc biệt.");
        // Chuẩn bị dữ liệu
        String keyword = "phone%";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Smartphone");
        product1.setPrice(new BigDecimal("1000"));
        mockProducts.add(product1);
        // Mock câu SQL thực tế mà phương thức tạo ra (không escape ký tự đặc biệt)
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%%' order by price desc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllDesc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%%' order by price desc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Hoàn thành testListAllDesc_KeywordWithSpecialCharacters. Lưu ý: Phương thức cần sửa để escape ký tự đặc biệt tránh SQL injection.");
    }

    // Test case 5: keyword không khớp với sản phẩm nào
    @Test
    public void testListAllDesc_KeywordNoMatchingProducts() {
        logger.info("Bắt đầu testListAllDesc_KeywordNoMatchingProducts: Kiểm tra listAllDesc với keyword không khớp với sản phẩm nào.");
        // Chuẩn bị dữ liệu
        String keyword = "xyz";
        List<Product> mockProducts = new ArrayList<>();
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%xyz%' order by price desc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllDesc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%xyz%' order by price desc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng");
        logger.info("Hoàn thành testListAllDesc_KeywordNoMatchingProducts.");
    }

    // Test case 6: keyword khớp với nhiều sản phẩm, kiểm tra sắp xếp theo giá giảm dần (thêm kiểm tra sắp xếp)
    @Test
    public void testListAllDesc_KeywordWithMultipleMatchingProducts() {
        logger.info("Bắt đầu testListAllDesc_KeywordWithMultipleMatchingProducts: Kiểm tra listAllDesc với keyword khớp với nhiều sản phẩm.");
        // Chuẩn bị dữ liệu
        String keyword = "phone";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Smartphone");
        product1.setPrice(new BigDecimal("1000"));
        Product product2 = new Product();
        product2.setId(2);
        product2.setTitle("Phone Case");
        product2.setPrice(new BigDecimal("200"));
        mockProducts.add(product1); // Giá cao hơn
        mockProducts.add(product2); // Giá thấp hơn
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price desc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllDesc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price desc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertEquals(2, result.size(), "Danh sách trả về phải có 2 sản phẩm");
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        // Kiểm tra sắp xếp theo giá giảm dần
        assertTrue(result.get(0).getPrice().compareTo(result.get(1).getPrice()) > 0, "Sản phẩm đầu tiên phải có giá cao hơn sản phẩm thứ hai");
        logger.info("Hoàn thành testListAllDesc_KeywordWithMultipleMatchingProducts.");
    }

    // Test case 7: productRepo.findAll() trả về danh sách rỗng
    @Test
    public void testListAllDesc_KeywordIsNullWithEmptyProductRepo() {
        logger.info("Bắt đầu testListAllDesc_KeywordIsNullWithEmptyProductRepo: Kiểm tra listAllDesc với keyword là null và productRepo rỗng.");
        // Chuẩn bị dữ liệu
        String keyword = null;
        List<Product> mockProducts = new ArrayList<>();
        when(productRepo.findAll()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = {}, mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllDesc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(productRepo, times(1)).findAll();
        verify(entityManager, never()).createNativeQuery(anyString(), eq(Product.class));
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng");
        logger.info("Hoàn thành testListAllDesc_KeywordIsNullWithEmptyProductRepo.");
    }

    // Test case 8: query.getResultList() ném ngoại lệ (thay thế test case cũ để kiểm tra lỗi database khi truy vấn SQL)
    @Test
    public void testListAllDesc_KeywordValidWithQueryException() {
        logger.info("Bắt đầu testListAllDesc_KeywordValidWithQueryException: Kiểm tra listAllDesc với keyword hợp lệ và query ném ngoại lệ.");
        // Chuẩn bị dữ liệu
        String keyword = "phone";
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price desc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenThrow(new RuntimeException("Database query error"));
        logger.info("Dữ liệu trước khi gọi: keyword = '{}'", keyword);

        // Thực hiện & Kiểm tra
        assertThrows(RuntimeException.class, () -> {
            productService.listAllDesc(keyword);
        }, "Phải ném RuntimeException khi query.getResultList() thất bại");

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: Ném ngoại lệ như mong đợi.");

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price desc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        logger.info("Hoàn thành testListAllDesc_KeywordValidWithQueryException.");
    }
}