package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
 * Lớp kiểm thử đơn vị cho ProductService, tập trung vào phương thức listAll().
 * Sử dụng Mockito để mock các phụ thuộc như EntityManager, ProductRepo.
 * Log trạng thái dữ liệu và kết quả bằng SLF4J.
 */
public class ProductServiceListAllTest {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceListAllTest.class);

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

    // TC_P_24 - testListAll_KeywordIsNull: Keyword là null
    @Test
    public void testListAll_KeywordIsNull() {
        logger.info("Bắt đầu TC_P_24 - testListAll_KeywordIsNull: Kiểm tra listAll với keyword là null.");
        // Chuẩn bị dữ liệu
        String keyword = null;
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Phone");
        mockProducts.add(product1);
        when(productRepo.findAll()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = {}, mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAll(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(productRepo, times(1)).findAll();
        verify(entityManager, never()).createNativeQuery(anyString(), eq(Product.class));
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Kết quả TC_P_24 - testListAll_KeywordIsNull: Kết thúc test case.");
    }

    // TC_P_25 - testListAll_KeywordIsEmpty: Keyword là chuỗi rỗng
    @Test
    public void testListAll_KeywordIsEmpty() {
        logger.info("Bắt đầu TC_P_25 - testListAll_KeywordIsEmpty: Kiểm tra listAll với keyword là chuỗi rỗng.");
        // Chuẩn bị dữ liệu
        String keyword = "";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Phone");
        mockProducts.add(product1);
        when(productRepo.findAll()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAll(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(productRepo, times(1)).findAll();
        verify(entityManager, never()).createNativeQuery(anyString(), eq(Product.class));
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Kết quả TC_P_25 - testListAll_KeywordIsEmpty: Kết thúc test case.");
    }

    // TC_P_26 - testListAll_KeywordValidWithMatchingProducts: Keyword hợp lệ, có sản phẩm khớp
    @Test
    public void testListAll_KeywordValidWithMatchingProducts() {
        logger.info("Bắt đầu TC_P_26 - testListAll_KeywordValidWithMatchingProducts: Kiểm tra listAll với keyword hợp lệ và có sản phẩm khớp.");
        // Chuẩn bị dữ liệu
        String keyword = "phone";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Smartphone");
        mockProducts.add(product1);
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by rand()", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAll(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by rand()", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Kết quả TC_P_26 - testListAll_KeywordValidWithMatchingProducts: Kết thúc test case.");
    }

    // TC_P_27 - testListAll_KeywordWithSpecialCharacters: Keyword chứa ký tự đặc biệt
    @Test
    public void testListAll_KeywordWithSpecialCharacters() {
        logger.info("Bắt đầu TC_P_27 - testListAll_KeywordWithSpecialCharacters: Kiểm tra listAll với keyword chứa ký tự đặc biệt.");
        // Chuẩn bị dữ liệu
        String keyword = "phone%";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Smartphone");
        mockProducts.add(product1);
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone\\%%' order by rand()", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAll(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone\\%%' order by rand()", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Kết quả TC_P_27 - testListAll_KeywordWithSpecialCharacters: Kết thúc test case.");
    }

    // TC_P_28 - testListAll_KeywordNoMatchingProducts: Keyword không khớp với sản phẩm nào
    @Test
    public void testListAll_KeywordNoMatchingProducts() {
        logger.info("Bắt đầu TC_P_28 - testListAll_KeywordNoMatchingProducts: Kiểm tra listAll với keyword không khớp với sản phẩm nào.");
        // Chuẩn bị dữ liệu
        String keyword = "xyz";
        List<Product> mockProducts = new ArrayList<>();
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%xyz%' order by rand()", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAll(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%xyz%' order by rand()", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng");
        logger.info("Kết quả TC_P_28 - testListAll_KeywordNoMatchingProducts: Kết thúc test case.");
    }

    // TC_P_29 - testListAll_KeywordWithMultipleMatchingProducts: Keyword khớp với nhiều sản phẩm
    @Test
    public void testListAll_KeywordWithMultipleMatchingProducts() {
        logger.info("Bắt đầu TC_P_29 - testListAll_KeywordWithMultipleMatchingProducts: Kiểm tra listAll với keyword khớp với nhiều sản phẩm.");
        // Chuẩn bị dữ liệu
        String keyword = "phone";
        List<Product> mockProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setTitle("Smartphone");
        Product product2 = new Product();
        product2.setId(2);
        product2.setTitle("Phone Case");
        mockProducts.add(product1);
        mockProducts.add(product2);
        when(entityManager.createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by rand()", Product.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = '{}', mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAll(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(entityManager, times(1)).createNativeQuery("SELECT * FROM tbl_product WHERE title LIKE '%phone%' order by rand()", Product.class);
        verify(mockQuery, times(1)).getResultList();
        verify(productRepo, never()).findAll();
        assertEquals(2, result.size(), "Danh sách trả về phải có 2 sản phẩm");
        assertEquals(mockProducts, result, "Danh sách trả về phải giống mockProducts");
        logger.info("Kết quả TC_P_29 - testListAll_KeywordWithMultipleMatchingProducts: Kết thúc test case.");
    }

    // TC_P_30 - testListAll_KeywordIsNullWithEmptyProductRepo: Keyword là null và productRepo rỗng
    @Test
    public void testListAll_KeywordIsNullWithEmptyProductRepo() {
        logger.info("Bắt đầu TC_P_30 - testListAll_KeywordIsNullWithEmptyProductRepo: Kiểm tra listAll với keyword là null và productRepo rỗng.");
        // Chuẩn bị dữ liệu
        String keyword = null;
        List<Product> mockProducts = new ArrayList<>();
        when(productRepo.findAll()).thenReturn(mockProducts);
        logger.info("Dữ liệu trước khi gọi: keyword = {}, mockProducts = {}", keyword, mockProducts);

        // Thực hiện
        List<Product> result = productService.listAll(keyword);

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: result = {}", result);

        // Kiểm tra
        verify(productRepo, times(1)).findAll();
        verify(entityManager, never()).createNativeQuery(anyString(), eq(Product.class));
        assertTrue(result.isEmpty(), "Danh sách trả về phải rỗng");
        logger.info("Kết quả TC_P_30 - testListAll_KeywordIsNullWithEmptyProductRepo: Kết thúc test case.");
    }

    // TC_P_31 - testListAll_KeywordIsNullWithProductRepoException: Keyword là null và productRepo ném ngoại lệ
    @Test
    public void testListAll_KeywordIsNullWithProductRepoException() {
        logger.info("Bắt đầu TC_P_31 - testListAll_KeywordIsNullWithProductRepoException: Kiểm tra listAll với keyword là null và productRepo ném ngoại lệ.");
        // Chuẩn bị dữ liệu
        String keyword = null;
        when(productRepo.findAll()).thenThrow(new RuntimeException("Database error"));
        logger.info("Dữ liệu trước khi gọi: keyword = {}", keyword);

        // Thực hiện & Kiểm tra
        assertThrows(RuntimeException.class, () -> {
            productService.listAll(keyword);
        }, "Phải ném RuntimeException khi productRepo.findAll() thất bại");

        // Log dữ liệu sau khi gọi
        logger.info("Dữ liệu sau khi gọi: Ném ngoại lệ như mong đợi.");

        // Kiểm tra
        verify(productRepo, times(1)).findAll();
        verify(entityManager, never()).createNativeQuery(anyString(), eq(Product.class));
        logger.info("Kết quả TC_P_31 - testListAll_KeywordIsNullWithProductRepoException: Kết thúc test case.");
    }
}