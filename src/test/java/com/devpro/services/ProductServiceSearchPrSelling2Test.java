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
public class ProductServiceSearchPrSelling2Test {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceSearchPrSelling2Test.class);

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

    // TC_P_94 - testSearchPrSelling2_NoProducts: No best-selling products
    @Test
    public void testSearchPrSelling2_NoProducts() {
        logger.info("Bắt đầu TC_P_94 - testSearchPrSelling2_NoProducts: Kiểm tra khi không có sản phẩm bán chạy.");
        ProductSearch productSearch = new ProductSearch();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về danh sách rỗng", productSearch);

        List<Product> result = productService.searchPrSelling2(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_94 - testSearchPrSelling2_NoProducts: Kết thúc test case.");
    }

    // TC_P_95 - testSearchPrSelling2_SingleProduct: One best-selling product
    @Test
    public void testSearchPrSelling2_SingleProduct() {
        logger.info("Bắt đầu TC_P_95 - testSearchPrSelling2_SingleProduct: Kiểm tra khi có một sản phẩm bán chạy.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 1 sản phẩm", productSearch);

        List<Product> result = productService.searchPrSelling2(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_95 - testSearchPrSelling2_SingleProduct: Kết thúc test case.");
    }

    // TC_P_96 - testSearchPrSelling2_ExactSixProducts: Exactly six products
    @Test
    public void testSearchPrSelling2_ExactSixProducts() {
        logger.info("Bắt đầu TC_P_96 - testSearchPrSelling2_ExactSixProducts: Kiểm tra khi có đúng 6 sản phẩm bán chạy.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            Product p = new Product(); p.setId(i); p.setTitle("Product " + i); p.setStatus(true);
            products.add(p);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 6 sản phẩm", productSearch);

        List<Product> result = productService.searchPrSelling2(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(6, result.size(), "Phải trả về đúng 6 sản phẩm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_96 - testSearchPrSelling2_ExactSixProducts: Kết thúc test case.");
    }

    // TC_P_97 - testSearchPrSelling2_MoreThanSixProducts: More than six products
    @Test
    public void testSearchPrSelling2_MoreThanSixProducts() {
        logger.info("Bắt đầu TC_P_97 - testSearchPrSelling2_MoreThanSixProducts: Kiểm tra khi có hơn 6 sản phẩm bán chạy.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Product p = new Product(); p.setId(i); p.setTitle("Product " + i); p.setStatus(true);
            products.add(p);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 10 sản phẩm", productSearch);

        List<Product> result = productService.searchPrSelling2(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(10, result.size(), "Phải trả về tất cả sản phẩm do query không giới hạn chính xác");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_97 - testSearchPrSelling2_MoreThanSixProducts: Kết thúc test case.");
    }

    // TC_P_98 - testSearchPrSelling2_NullProductSearch: ProductSearch is null
    @Test
    public void testSearchPrSelling2_NullProductSearch() {
        logger.info("Bắt đầu TC_P_98 - testSearchPrSelling2_NullProductSearch: Kiểm tra khi productSearch là null.");
        ProductSearch productSearch = null;
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        logger.info("Dữ liệu chuẩn bị: productSearch = null, query trả về danh sách rỗng");

        List<Product> result = productService.searchPrSelling2(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_98 - testSearchPrSelling2_NullProductSearch: Kết thúc test case.");
    }

    // TC_P_99 - testSearchPrSelling2_QueryThrowsException: Query execution fails
    @Test
    public void testSearchPrSelling2_QueryThrowsException() {
        logger.info("Bắt đầu TC_P_99 - testSearchPrSelling2_QueryThrowsException: Kiểm tra khi query ném ngoại lệ.");
        ProductSearch productSearch = new ProductSearch();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new RuntimeException("Database error"));
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query ném ngoại lệ", productSearch);

        assertThrows(RuntimeException.class, () -> productService.searchPrSelling2(productSearch), "Phải ném RuntimeException khi query thất bại");

        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_99 - testSearchPrSelling2_QueryThrowsException: Kết thúc test case.");
    }

    // TC_P_100 - testSearchPrSelling2_NullProductInResult: Result contains null product
    @Test
    public void testSearchPrSelling2_NullProductInResult() {
        logger.info("Bắt đầu TC_P_100 - testSearchPrSelling2_NullProductInResult: Kiểm tra khi kết quả chứa sản phẩm null.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        products.add(null);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 1 sản phẩm null", productSearch);

        List<Product> result = productService.searchPrSelling2(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertNull(result.get(0), "Sản phẩm phải là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_100 - testSearchPrSelling2_NullProductInResult: Kết thúc test case.");
    }

    // TC_P_101 - testSearchPrSelling2_ConcurrentCalls: Multiple concurrent calls
    @Test
    public void testSearchPrSelling2_ConcurrentCalls() {
        logger.info("Bắt đầu TC_P_101 - testSearchPrSelling2_ConcurrentCalls: Kiểm tra nhiều lời gọi đồng thời.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 1 sản phẩm", productSearch);

        List<Product> result1 = productService.searchPrSelling2(productSearch);
        List<Product> result2 = productService.searchPrSelling2(productSearch);

        assertNotNull(result1, "Kết quả lần 1 không được null");
        assertNotNull(result2, "Kết quả lần 2 không được null");
        assertEquals(1, result1.size(), "Lần 1 phải trả về 1 sản phẩm");
        assertEquals(1, result2.size(), "Lần 2 phải trả về 1 sản phẩm");
        verify(entityManager, times(2)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(2)).getResultList();
        logger.info("Kết quả TC_P_101 - testSearchPrSelling2_ConcurrentCalls: Kết thúc test case.");
    }

    // TC_P_102 - testSearchPrSelling2_DuplicateProducts: Duplicate products in result
    @Test
    public void testSearchPrSelling2_DuplicateProducts() {
        logger.info("Bắt đầu TC_P_102 - testSearchPrSelling2_DuplicateProducts: Kiểm tra khi kết quả chứa sản phẩm trùng lặp.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1); products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 2 sản phẩm (1 trùng lặp)", productSearch);

        List<Product> result = productService.searchPrSelling2(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 sản phẩm (bao gồm trùng lặp)");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_102 - testSearchPrSelling2_DuplicateProducts: Kết thúc test case.");
    }

    // TC_P_103 - testSearchPrSelling2_LessThanSixProducts: Fewer than six products
    @Test
    public void testSearchPrSelling2_LessThanSixProducts() {
        logger.info("Bắt đầu TC_P_103 - testSearchPrSelling2_LessThanSixProducts: Kiểm tra khi có ít hơn 6 sản phẩm bán chạy.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Product p = new Product(); p.setId(i); p.setTitle("Product " + i); p.setStatus(true);
            products.add(p);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 3 sản phẩm", productSearch);

        List<Product> result = productService.searchPrSelling2(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(3, result.size(), "Phải trả về 3 sản phẩm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_103 - testSearchPrSelling2_LessThanSixProducts: Kết thúc test case.");
    }
}
