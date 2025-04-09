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
public class ProductServiceGetAllProductTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceGetAllProductTest.class);

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

    // TC_P_74 - testGetAllProduct_NoActiveProducts: No active products
    @Test
    public void testGetAllProduct_NoActiveProducts() {
        logger.info("Bắt đầu TC_P_74 - testGetAllProduct_NoActiveProducts: Kiểm tra khi không có sản phẩm active.");
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        logger.info("Dữ liệu chuẩn bị: query trả về danh sách rỗng");

        List<Product> result = productService.getAllProduct();

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_74 - testGetAllProduct_NoActiveProducts: Kết thúc test case.");
    }

    // TC_P_75 - testGetAllProduct_SingleActiveProduct: One active product
    @Test
    public void testGetAllProduct_SingleActiveProduct() {
        logger.info("Bắt đầu TC_P_75 - testGetAllProduct_SingleActiveProduct: Kiểm tra khi có một sản phẩm active.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: query trả về 1 sản phẩm");

        List<Product> result = productService.getAllProduct();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_75 - testGetAllProduct_SingleActiveProduct: Kết thúc test case.");
    }

    // TC_P_76 - testGetAllProduct_MultipleActiveProducts: Multiple active products
    @Test
    public void testGetAllProduct_MultipleActiveProducts() {
        logger.info("Bắt đầu TC_P_76 - testGetAllProduct_MultipleActiveProducts: Kiểm tra khi có nhiều sản phẩm active.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        Product p2 = new Product(); p2.setId(2); p2.setTitle("Product 2"); p2.setStatus(true);
        products.add(p1); products.add(p2);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: query trả về 2 sản phẩm");

        List<Product> result = productService.getAllProduct();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 sản phẩm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_76 - testGetAllProduct_MultipleActiveProducts: Kết thúc test case.");
    }

    // TC_P_77 - testGetAllProduct_QueryThrowsException: Query execution fails
    @Test
    public void testGetAllProduct_QueryThrowsException() {
        logger.info("Bắt đầu TC_P_77 - testGetAllProduct_QueryThrowsException: Kiểm tra khi query ném ngoại lệ.");
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new RuntimeException("Database error"));
        logger.info("Dữ liệu chuẩn bị: query ném ngoại lệ");

        assertThrows(RuntimeException.class, () -> productService.getAllProduct(), "Phải ném RuntimeException khi query thất bại");

        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_77 - testGetAllProduct_QueryThrowsException: Kết thúc test case.");
    }

    // TC_P_78 - testGetAllProduct_NullProductInResult: Result contains null product
    @Test
    public void testGetAllProduct_NullProductInResult() {
        logger.info("Bắt đầu TC_P_78 - testGetAllProduct_NullProductInResult: Kiểm tra khi kết quả chứa sản phẩm null.");
        List<Product> products = new ArrayList<>();
        products.add(null);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: query trả về 1 sản phẩm null");

        List<Product> result = productService.getAllProduct();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertNull(result.get(0), "Sản phẩm phải là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_78 - testGetAllProduct_NullProductInResult: Kết thúc test case.");
    }

    // TC_P_79 - testGetAllProduct_LargeNumberOfProducts: Many active products
    @Test
    public void testGetAllProduct_LargeNumberOfProducts() {
        logger.info("Bắt đầu TC_P_79 - testGetAllProduct_LargeNumberOfProducts: Kiểm tra khi có nhiều sản phẩm active.");
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Product p = new Product(); p.setId(i); p.setTitle("Product " + i); p.setStatus(true);
            products.add(p);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: query trả về 100 sản phẩm");

        List<Product> result = productService.getAllProduct();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(100, result.size(), "Phải trả về 100 sản phẩm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_79 - testGetAllProduct_LargeNumberOfProducts: Kết thúc test case.");
    }

    // TC_P_80 - testGetAllProduct_DuplicateProducts: Duplicate products in result
    @Test
    public void testGetAllProduct_DuplicateProducts() {
        logger.info("Bắt đầu TC_P_80 - testGetAllProduct_DuplicateProducts: Kiểm tra khi kết quả chứa sản phẩm trùng lặp.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1); products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: query trả về 2 sản phẩm (1 trùng lặp)");

        List<Product> result = productService.getAllProduct();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 sản phẩm (bao gồm trùng lặp)");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_80 - testGetAllProduct_DuplicateProducts: Kết thúc test case.");
    }

    // TC_P_81 - testGetAllProduct_ConcurrentCalls: Multiple concurrent calls
    @Test
    public void testGetAllProduct_ConcurrentCalls() {
        logger.info("Bắt đầu TC_P_81 - testGetAllProduct_ConcurrentCalls: Kiểm tra nhiều lời gọi đồng thời.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: query trả về 1 sản phẩm");

        List<Product> result1 = productService.getAllProduct();
        List<Product> result2 = productService.getAllProduct();

        assertNotNull(result1, "Kết quả lần 1 không được null");
        assertNotNull(result2, "Kết quả lần 2 không được null");
        assertEquals(1, result1.size(), "Lần 1 phải trả về 1 sản phẩm");
        assertEquals(1, result2.size(), "Lần 2 phải trả về 1 sản phẩm");
        verify(entityManager, times(2)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(2)).getResultList();
        logger.info("Kết quả TC_P_81 - testGetAllProduct_ConcurrentCalls: Kết thúc test case.");
    }

    // TC_P_82 - testGetAllProduct_MixedStatusProducts: Products with mixed status
    @Test
    public void testGetAllProduct_MixedStatusProducts() {
        logger.info("Bắt đầu TC_P_82 - testGetAllProduct_MixedStatusProducts: Kiểm tra khi kết quả chứa sản phẩm mixed status.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        Product p2 = new Product(); p2.setId(2); p2.setTitle("Product 2"); p2.setStatus(false);
        products.add(p1); products.add(p2);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: query trả về 2 sản phẩm (1 active, 1 inactive)");

        List<Product> result = productService.getAllProduct();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 sản phẩm (do query không lọc status chính xác)");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_82 - testGetAllProduct_MixedStatusProducts: Kết thúc test case.");
    }

    // TC_P_83 - testGetAllProduct_EmptyDatabase: Database is empty
    @Test
    public void testGetAllProduct_EmptyDatabase() {
        logger.info("Bắt đầu TC_P_83 - testGetAllProduct_EmptyDatabase: Kiểm tra khi database rỗng.");
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        logger.info("Dữ liệu chuẩn bị: query trả về danh sách rỗng");

        List<Product> result = productService.getAllProduct();

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_83 - testGetAllProduct_EmptyDatabase: Kết thúc test case.");
    }
}
