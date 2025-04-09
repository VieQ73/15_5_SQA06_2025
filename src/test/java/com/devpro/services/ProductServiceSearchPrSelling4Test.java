package com.devpro.services;

import com.devpro.entities.Product;
import com.devpro.model.ProductCustom;
import com.devpro.model.ProductSearch;
import com.devpro.repositories.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class ProductServiceSearchPrSelling4Test {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceSearchPrSelling4Test.class);

    @Mock
    private EntityManager entityManager;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private ProductSaleService productSaleService;

    @Mock
    private Query query;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        reset(entityManager, productRepo, productSaleService, query);
    }

    // TC_P_104 - testSearchPrSelling4_NoProducts: No best-selling products
    @Test
    public void testSearchPrSelling4_NoProducts() {
        logger.info("Bắt đầu TC_P_104 - testSearchPrSelling4_NoProducts: Kiểm tra khi không có sản phẩm bán chạy.");
        ProductSearch productSearch = new ProductSearch();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về danh sách rỗng", productSearch);

        List<Product> result = productService.searchPrSelling4(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_104 - testSearchPrSelling4_NoProducts: Kết thúc test case.");
    }

    // TC_P_105 - testSearchPrSelling4_SingleProduct: One best-selling product
    @Test
    public void testSearchPrSelling4_SingleProduct() {
        logger.info("Bắt đầu TC_P_105 - testSearchPrSelling4_SingleProduct: Kiểm tra khi có một sản phẩm bán chạy.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 1 sản phẩm", productSearch);

        List<Product> result = productService.searchPrSelling4(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_105 - testSearchPrSelling4_SingleProduct: Kết thúc test case.");
    }

    // TC_P_106 - testSearchPrSelling4_ExactFourProducts: Exactly four products
    @Test
    public void testSearchPrSelling4_ExactFourProducts() {
        logger.info("Bắt đầu TC_P_106 - testSearchPrSelling4_ExactFourProducts: Kiểm tra khi có đúng 4 sản phẩm bán chạy.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Product p = new Product(); p.setId(i); p.setTitle("Product " + i); p.setStatus(true);
            products.add(p);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 4 sản phẩm", productSearch);

        List<Product> result = productService.searchPrSelling4(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(4, result.size(), "Phải trả về đúng 4 sản phẩm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_106 - testSearchPrSelling4_ExactFourProducts: Kết thúc test case.");
    }

    // TC_P_107 - testSearchPrSelling4_MoreThanFourProducts: More than four products
    @Test
    public void testSearchPrSelling4_MoreThanFourProducts() {
        logger.info("Bắt đầu TC_P_107 - testSearchPrSelling4_MoreThanFourProducts: Kiểm tra khi có hơn 4 sản phẩm bán chạy.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            Product p = new Product(); p.setId(i); p.setTitle("Product " + i); p.setStatus(true);
            products.add(p);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 6 sản phẩm", productSearch);

        List<Product> result = productService.searchPrSelling4(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(6, result.size(), "Phải trả về tất cả sản phẩm do query không giới hạn chính xác");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_107 - testSearchPrSelling4_MoreThanFourProducts: Kết thúc test case.");
    }

    // TC_P_108 - testSearchPrSelling4_NullProductSearch: ProductSearch is null
    @Test
    public void testSearchPrSelling4_NullProductSearch() {
        logger.info("Bắt đầu TC_P_108 - testSearchPrSelling4_NullProductSearch: Kiểm tra khi productSearch là null.");
        ProductSearch productSearch = null;
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        logger.info("Dữ liệu chuẩn bị: productSearch = null, query trả về danh sách rỗng");

        List<Product> result = productService.searchPrSelling4(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_108 - testSearchPrSelling4_NullProductSearch: Kết thúc test case.");
    }

    // TC_P_109 - testSearchPrSelling4_QueryThrowsException: Query execution fails
    @Test
    public void testSearchPrSelling4_QueryThrowsException() {
        logger.info("Bắt đầu TC_P_109 - testSearchPrSelling4_QueryThrowsException: Kiểm tra khi query ném ngoại lệ.");
        ProductSearch productSearch = new ProductSearch();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new RuntimeException("Database error"));
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query ném ngoại lệ", productSearch);

        assertThrows(RuntimeException.class, () -> productService.searchPrSelling4(productSearch), "Phải ném RuntimeException khi query thất bại");

        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_109 - testSearchPrSelling4_QueryThrowsException: Kết thúc test case.");
    }

    // TC_P_110 - testSearchPrSelling4_NullProductInResult: Result contains null product
    @Test
    public void testSearchPrSelling4_NullProductInResult() {
        logger.info("Bắt đầu TC_P_110 - testSearchPrSelling4_NullProductInResult: Kiểm tra khi kết quả chứa sản phẩm null.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        products.add(null);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 1 sản phẩm null", productSearch);

        List<Product> result = productService.searchPrSelling4(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertNull(result.get(0), "Sản phẩm phải là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_110 - testSearchPrSelling4_NullProductInResult: Kết thúc test case.");
    }

    // TC_P_111 - testSearchPrSelling4_ConcurrentCalls: Multiple concurrent calls
    @Test
    public void testSearchPrSelling4_ConcurrentCalls() {
        logger.info("Bắt đầu TC_P_111 - testSearchPrSelling4_ConcurrentCalls: Kiểm tra nhiều lời gọi đồng thời.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 1 sản phẩm", productSearch);

        List<Product> result1 = productService.searchPrSelling4(productSearch);
        List<Product> result2 = productService.searchPrSelling4(productSearch);

        assertNotNull(result1, "Kết quả lần 1 không được null");
        assertNotNull(result2, "Kết quả lần 2 không được null");
        assertEquals(1, result1.size(), "Lần 1 phải trả về 1 sản phẩm");
        assertEquals(1, result2.size(), "Lần 2 phải trả về 1 sản phẩm");
        verify(entityManager, times(2)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(2)).getResultList();
        logger.info("Kết quả TC_P_111 - testSearchPrSelling4_ConcurrentCalls: Kết thúc test case.");
    }

    // TC_P_112 - testSearchPrSelling4_DuplicateProducts: Duplicate products in result
    @Test
    public void testSearchPrSelling4_DuplicateProducts() {
        logger.info("Bắt đầu TC_P_112 - testSearchPrSelling4_DuplicateProducts: Kiểm tra khi kết quả chứa sản phẩm trùng lặp.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1); products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 2 sản phẩm (1 trùng lặp)", productSearch);

        List<Product> result = productService.searchPrSelling4(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 sản phẩm (bao gồm trùng lặp)");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_112 - testSearchPrSelling4_DuplicateProducts: Kết thúc test case.");
    }

    // TC_P_113 - testSearchPrSelling4_LessThanFourProducts: Fewer than four products
    @Test
    public void testSearchPrSelling4_LessThanFourProducts() {
        logger.info("Bắt đầu TC_P_113 - testSearchPrSelling4_LessThanFourProducts: Kiểm tra khi có ít hơn 4 sản phẩm bán chạy.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Product p = new Product(); p.setId(i); p.setTitle("Product " + i); p.setStatus(true);
            products.add(p);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 2 sản phẩm", productSearch);

        List<Product> result = productService.searchPrSelling4(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 sản phẩm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_113 - testSearchPrSelling4_LessThanFourProducts: Kết thúc test case.");
    }
}
