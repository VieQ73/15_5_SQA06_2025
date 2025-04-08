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
 * Lớp kiểm thử đơn vị cho ProductService, tập trung vào phương thức listAllAsc().
 * Sử dụng Mockito để mock các phụ thuộc như EntityManager, ProductRepo.
 * Log trạng thái dữ liệu và kết quả bằng SLF4J.
 */
public class ProductServiceListAllAscTest {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceListAllAscTest.class);

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
    public void testListAllAsc_KeywordIsNull() {
        logger.info("Bắt đầu testListAllAsc_KeywordIsNull: Kiểm tra listAllAsc với keyword là null.");
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
        List<Product> result = productService.listAllAsc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(productRepo, times(1)).findAll();
        verify(entityManager, never()).createNativeQuery(anyString(), eq(Product.class));
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Hoàn thành testListAllAsc_KeywordIsNull.");
    }

    // Test case 2: keyword là chuỗi rỗng
    @Test
    public void testListAllAsc_KeywordIsEmpty() {
        logger.info("Bắt đầu testListAllAsc_KeywordIsEmpty: Kiểm tra listAllAsc với keyword là chuỗi rỗng.");
        // Chuẩn bị dữ liệu
        String keyword = "";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Phone");
        product1.setPrice(new BigDecimal("500"));
        mockProducts.add(product1);
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%%' order by price asc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllAsc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%%' order by price asc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Hoàn thành testListAllAsc_KeywordIsEmpty.");
    }

    // Test case 3: keyword là chuỗi hợp lệ, có sản phẩm khớp
    @Test
    public void testListAllAsc_KeywordValidWithMatchingProducts() {
        logger.info("Bắt đầu testListAllAsc_KeywordValidWithMatchingProducts: Kiểm tra listAllAsc với keyword hợp lệ và có sản phẩm khớp.");
        // Chuẩn bị dữ liệu
        String keyword = "phone";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Smartphone");
        product1.setPrice(new BigDecimal("1000"));
        mockProducts.add(product1);
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price asc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllAsc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price asc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Hoàn thành testListAllAsc_KeywordValidWithMatchingProducts.");
    }

    // Test case 4: keyword chứa ký tự đặc biệt
    @Test
    public void testListAllAsc_KeywordWithSpecialCharacters() {
        logger.info("Bắt đầu testListAllAsc_KeywordWithSpecialCharacters: Kiểm tra listAllAsc với keyword chứa ký tự đặc biệt.");
        // Chuẩn bị dữ liệu
        String keyword = "phone%";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Smartphone");
        product1.setPrice(new BigDecimal("1000"));
        mockProducts.add(product1);
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%%' order by price asc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllAsc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%%' order by price asc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Hoàn thành testListAllAsc_KeywordWithSpecialCharacters. Lưu ý: Phương thức cần sửa để escape ký tự đặc biệt tránh SQL injection.");
    }

    // Test case 5: keyword không khớp với sản phẩm nào
    @Test
    public void testListAllAsc_KeywordNoMatchingProducts() {
        logger.info("Bắt đầu testListAllAsc_KeywordNoMatchingProducts: Kiểm tra listAllAsc với keyword không khớp với sản phẩm nào.");
        // Chuẩn bị dữ liệu
        String keyword = "xyz";
        List<Product> mockProducts = new ArrayList<>();
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%xyz%' order by price asc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllAsc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%xyz%' order by price asc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng");
        logger.info("Hoàn thành testListAllAsc_KeywordNoMatchingProducts.");
    }

    // Test case 6: keyword khớp với nhiều sản phẩm, kiểm tra sắp xếp theo giá tăng dần
    @Test
    public void testListAllAsc_KeywordWithMultipleMatchingProducts() {
        logger.info("Bắt đầu testListAllAsc_KeywordWithMultipleMatchingProducts: Kiểm tra listAllAsc với keyword khớp với nhiều sản phẩm.");
        // Chuẩn bị dữ liệu
        String keyword = "phone";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Phone Case");
        product1.setPrice(new BigDecimal("200"));
        Product product2 = new Product();
        product2.setId(2);
        product2.setTitle("Smartphone");
        product2.setPrice(new BigDecimal("1000"));
        mockProducts.add(product1); // Giá thấp hơn
        mockProducts.add(product2); // Giá cao hơn
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price asc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllAsc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price asc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertEquals(2, result.size(), "Danh sách trả về phải có 2 sản phẩm");
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        // Kiểm tra sắp xếp theo giá tăng dần
        assertTrue(result.get(0).getPrice().compareTo(result.get(1).getPrice()) < 0, "Sản phẩm đầu tiên phải có giá thấp hơn sản phẩm thứ hai");
        logger.info("Hoàn thành testListAllAsc_KeywordWithMultipleMatchingProducts.");
    }

    // Test case 7: productRepo.findAll() trả về danh sách rỗng
    @Test
    public void testListAllAsc_KeywordIsNullWithEmptyProductRepo() {
        logger.info("Bắt đầu testListAllAsc_KeywordIsNullWithEmptyProductRepo: Kiểm tra listAllAsc với keyword là null và productRepo rỗng.");
        // Chuẩn bị dữ liệu
        String keyword = null;
        List<Product> mockProducts = new ArrayList<>();
        when(productRepo.findAll()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = {}, mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAllAsc(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(productRepo, times(1)).findAll();
        verify(entityManager, never()).createNativeQuery(anyString(), eq(Product.class));
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng");
        logger.info("Hoàn thành testListAllAsc_KeywordIsNullWithEmptyProductRepo.");
    }

    // Test case 8: query.getResultList() ném ngoại lệ
    @Test
    public void testListAllAsc_KeywordValidWithQueryException() {
        logger.info("Bắt đầu testListAllAsc_KeywordValidWithQueryException: Kiểm tra listAllAsc với keyword hợp lệ và query ném ngoại lệ.");
        // Chuẩn bị dữ liệu
        String keyword = "phone";
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price asc", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenThrow(new RuntimeException("Database query error"));
        logger.info("Dữ liệu trước khi gọi: keyword = '{}'", keyword);

        // Thực hiện & Kiểm tra
        assertThrows(RuntimeException.class, () -> {
            productService.listAllAsc(keyword);
        }, "Phải ném RuntimeException khi query.getResultList() thất bại");

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: Ném ngoại lệ như mong đợi.");

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by price asc", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        logger.info("Hoàn thành testListAllAsc_KeywordValidWithQueryException.");
    }
}