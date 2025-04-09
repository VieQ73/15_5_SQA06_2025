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
public class ProductServiceGetProductSaleTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceGetProductSaleTest.class);

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

    // TC_P_64 - testGetProductSale_NoProducts: No products on sale
    @Test
    public void testGetProductSale_NoProducts() {
        logger.info("Bắt đầu TC_P_64 - testGetProductSale_NoProducts: Kiểm tra khi không có sản phẩm nào đang sale.");
        ProductSearch productSearch = new ProductSearch();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về danh sách rỗng", productSearch);

        List<Product> result = productService.getProductSale(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_64 - testGetProductSale_NoProducts: Kết thúc test case.");
    }

    // TC_P_65 - testGetProductSale_SingleProduct: One product on sale
    @Test
    public void testGetProductSale_SingleProduct() {
        logger.info("Bắt đầu TC_P_65 - testGetProductSale_SingleProduct: Kiểm tra khi có một sản phẩm đang sale.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1");
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 1 sản phẩm", productSearch);

        List<Product> result = productService.getProductSale(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_65 - testGetProductSale_SingleProduct: Kết thúc test case.");
    }

    // TC_P_66 - testGetProductSale_MultipleProducts: Multiple products on sale
    @Test
    public void testGetProductSale_MultipleProducts() {
        logger.info("Bắt đầu TC_P_66 - testGetProductSale_MultipleProducts: Kiểm tra khi có nhiều sản phẩm đang sale.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1");
        Product p2 = new Product(); p2.setId(2); p2.setTitle("Product 2");
        products.add(p1); products.add(p2);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 2 sản phẩm", productSearch);

        List<Product> result = productService.getProductSale(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 sản phẩm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_66 - testGetProductSale_MultipleProducts: Kết thúc test case.");
    }

    // TC_P_67 - testGetProductSale_NullProductSearch: ProductSearch is null
    @Test
    public void testGetProductSale_NullProductSearch() {
        logger.info("Bắt đầu TC_P_67 - testGetProductSale_NullProductSearch: Kiểm tra khi productSearch là null.");
        ProductSearch productSearch = null;
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        logger.info("Dữ liệu chuẩn bị: productSearch = null, query trả về danh sách rỗng");

        List<Product> result = productService.getProductSale(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_67 - testGetProductSale_NullProductSearch: Kết thúc test case.");
    }

    // TC_P_68 - testGetProductSale_QueryThrowsException: Query execution fails
    @Test
    public void testGetProductSale_QueryThrowsException() {
        logger.info("Bắt đầu TC_P_68 - testGetProductSale_QueryThrowsException: Kiểm tra khi query ném ngoại lệ.");
        ProductSearch productSearch = new ProductSearch();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new RuntimeException("Database error"));
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query ném ngoại lệ", productSearch);

        assertThrows(RuntimeException.class, () -> productService.getProductSale(productSearch), "Phải ném RuntimeException khi query thất bại");

        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_68 - testGetProductSale_QueryThrowsException: Kết thúc test case.");
    }

    // TC_P_69 - testGetProductSale_LargeNumberOfProducts: Many products on sale
    @Test
    public void testGetProductSale_LargeNumberOfProducts() {
        logger.info("Bắt đầu TC_P_69 - testGetProductSale_LargeNumberOfProducts: Kiểm tra khi có nhiều sản phẩm đang sale.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Product p = new Product(); p.setId(i); p.setTitle("Product " + i);
            products.add(p);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 100 sản phẩm", productSearch);

        List<Product> result = productService.getProductSale(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(100, result.size(), "Phải trả về 100 sản phẩm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_69 - testGetProductSale_LargeNumberOfProducts: Kết thúc test case.");
    }

    // TC_P_70 - testGetProductSale_DuplicateProducts: Query returns duplicate products
    @Test
    public void testGetProductSale_DuplicateProducts() {
        logger.info("Bắt đầu TC_P_70 - testGetProductSale_DuplicateProducts: Kiểm tra khi query trả về sản phẩm trùng lặp.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1");
        products.add(p1); products.add(p1); // Duplicate
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 2 sản phẩm (1 trùng lặp)", productSearch);

        List<Product> result = productService.getProductSale(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 sản phẩm (bao gồm trùng lặp)");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_70 - testGetProductSale_DuplicateProducts: Kết thúc test case.");
    }

    // TC_P_71 - testGetProductSale_NullProductInResult: Result contains null product
    @Test
    public void testGetProductSale_NullProductInResult() {
        logger.info("Bắt đầu TC_P_71 - testGetProductSale_NullProductInResult: Kiểm tra khi kết quả chứa sản phẩm null.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        products.add(null);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 1 sản phẩm null", productSearch);

        List<Product> result = productService.getProductSale(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertNull(result.get(0), "Sản phẩm phải là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_71 - testGetProductSale_NullProductInResult: Kết thúc test case.");
    }

    // TC_P_72 - testGetProductSale_EmptyProductSearchFields: ProductSearch with empty fields
    @Test
    public void testGetProductSale_EmptyProductSearchFields() {
        logger.info("Bắt đầu TC_P_72 - testGetProductSale_EmptyProductSearchFields: Kiểm tra productSearch với các trường rỗng.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setCategoryId(null);
        productSearch.setSeoProduct("");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1");
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch với trường rỗng, query trả về 1 sản phẩm");

        List<Product> result = productService.getProductSale(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_72 - testGetProductSale_EmptyProductSearchFields: Kết thúc test case.");
    }

    // TC_P_73 - testGetProductSale_ConcurrentCalls: Multiple concurrent calls to method
    @Test
    public void testGetProductSale_ConcurrentCalls() {
        logger.info("Bắt đầu TC_P_73 - testGetProductSale_ConcurrentCalls: Kiểm tra nhiều lời gọi đồng thời.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1");
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 1 sản phẩm", productSearch);

        List<Product> result1 = productService.getProductSale(productSearch);
        List<Product> result2 = productService.getProductSale(productSearch);

        assertNotNull(result1, "Kết quả lần 1 không được null");
        assertNotNull(result2, "Kết quả lần 2 không được null");
        assertEquals(1, result1.size(), "Lần 1 phải trả về 1 sản phẩm");
        assertEquals(1, result2.size(), "Lần 2 phải trả về 1 sản phẩm");
        verify(entityManager, times(2)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(2)).getResultList();
        logger.info("Kết quả TC_P_73 - testGetProductSale_ConcurrentCalls: Kết thúc test case.");
    }
}