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
public class ProductServiceGetProductCustomTest {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceGetProductCustomTest.class);

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

    // TC_P_124 - testGetProductCustom_NoProducts: No products available
    @Test
    public void testGetProductCustom_NoProducts() {
        logger.info("Bắt đầu TC_P_124 - testGetProductCustom_NoProducts: Kiểm tra khi không có sản phẩm nào.");
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        logger.info("Dữ liệu chuẩn bị: query trả về danh sách rỗng");

        List<ProductCustom> result = productService.getProductCustom();

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách ProductCustom phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        verify(productSaleService, never()).getDiscountByIdProduct(anyInt());
        logger.info("Kết quả TC_P_124 - testGetProductCustom_NoProducts: Kết thúc test case.");
    }

    // TC_P_125 - testGetProductCustom_SingleProductNoDiscount: One product without discount
    @Test
    public void testGetProductCustom_SingleProductNoDiscount() {
        logger.info("Bắt đầu TC_P_125 - testGetProductCustom_SingleProductNoDiscount: Kiểm tra một sản phẩm không có discount.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setPrice(new BigDecimal("1000"));
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        when(productSaleService.getDiscountByIdProduct(1)).thenReturn(0);
        logger.info("Dữ liệu chuẩn bị: 1 sản phẩm, price = 1000, discount = 0%");

        List<ProductCustom> result = productService.getProductCustom();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 ProductCustom");
        assertEquals(new BigDecimal("1000"), result.get(0).getPrice_sale(), "Giá sau discount phải bằng giá gốc");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(productSaleService, times(1)).getDiscountByIdProduct(1);
        logger.info("Kết quả TC_P_125 - testGetProductCustom_SingleProductNoDiscount: Kết thúc test case.");
    }

    // TC_P_126 - testGetProductCustom_SingleProductWithDiscount: One product with discount
    @Test
    public void testGetProductCustom_SingleProductWithDiscount() {
        logger.info("Bắt đầu TC_P_126 - testGetProductCustom_SingleProductWithDiscount: Kiểm tra một sản phẩm có discount.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setPrice(new BigDecimal("1000"));
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        when(productSaleService.getDiscountByIdProduct(1)).thenReturn(10);
        logger.info("Dữ liệu chuẩn bị: 1 sản phẩm, price = 1000, discount = 10%");

        List<ProductCustom> result = productService.getProductCustom();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 ProductCustom");
        assertEquals(new BigDecimal("900"), result.get(0).getPrice_sale(), "Giá sau discount phải là 900");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(productSaleService, times(1)).getDiscountByIdProduct(1);
        logger.info("Kết quả TC_P_126 - testGetProductCustom_SingleProductWithDiscount: Kết thúc test case.");
    }

    // TC_P_127 - testGetProductCustom_MultipleProductsMixedDiscounts: Multiple products with mixed discounts
    @Test
    public void testGetProductCustom_MultipleProductsMixedDiscounts() {
        logger.info("Bắt đầu TC_P_127 - testGetProductCustom_MultipleProductsMixedDiscounts: Kiểm tra nhiều sản phẩm với discount khác nhau.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setPrice(new BigDecimal("1000"));
        Product p2 = new Product(); p2.setId(2); p2.setTitle("Product 2"); p2.setPrice(new BigDecimal("2000"));
        products.add(p1); products.add(p2);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        when(productSaleService.getDiscountByIdProduct(1)).thenReturn(10);
        when(productSaleService.getDiscountByIdProduct(2)).thenReturn(0);
        logger.info("Dữ liệu chuẩn bị: 2 sản phẩm, price = 1000/2000, discount = 10%/0%");

        List<ProductCustom> result = productService.getProductCustom();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 ProductCustom");
        assertEquals(new BigDecimal("900"), result.get(0).getPrice_sale(), "Giá sau discount sản phẩm 1 phải là 900");
        assertEquals(new BigDecimal("2000"), result.get(1).getPrice_sale(), "Giá sau discount sản phẩm 2 phải là 2000");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(productSaleService, times(1)).getDiscountByIdProduct(1);
        verify(productSaleService, times(1)).getDiscountByIdProduct(2);
        logger.info("Kết quả TC_P_127 - testGetProductCustom_MultipleProductsMixedDiscounts: Kết thúc test case.");
    }

    // TC_P_128 - testGetProductCustom_NullPrice: Product with null price
    @Test
    public void testGetProductCustom_NullPrice() {
        logger.info("Bắt đầu TC_P_128 - testGetProductCustom_NullPrice: Kiểm tra sản phẩm có giá null.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setPrice(null);
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        when(productSaleService.getDiscountByIdProduct(1)).thenReturn(10);
        logger.info("Dữ liệu chuẩn bị: 1 sản phẩm, price = null, discount = 10%");

        assertThrows(NullPointerException.class, () -> productService.getProductCustom(), "Phải ném NullPointerException khi giá là null");

        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(productSaleService, times(1)).getDiscountByIdProduct(1);
        logger.info("Kết quả TC_P_128 - testGetProductCustom_NullPrice: Kết thúc test case.");
    }

    // TC_P_129 - testGetProductCustom_ZeroPrice: Product with zero price
    @Test
    public void testGetProductCustom_ZeroPrice() {
        logger.info("Bắt đầu TC_P_129 - testGetProductCustom_ZeroPrice: Kiểm tra sản phẩm có giá 0.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setPrice(BigDecimal.ZERO);
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        when(productSaleService.getDiscountByIdProduct(1)).thenReturn(10);
        logger.info("Dữ liệu chuẩn bị: 1 sản phẩm, price = 0, discount = 10%");

        List<ProductCustom> result = productService.getProductCustom();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 ProductCustom");
        assertEquals(BigDecimal.ZERO, result.get(0).getPrice_sale(), "Giá sau discount phải là 0");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(productSaleService, times(1)).getDiscountByIdProduct(1);
        logger.info("Kết quả TC_P_129 - testGetProductCustom_ZeroPrice: Kết thúc test case.");
    }

    // TC_P_130 - testGetProductCustom_NegativeDiscount: Negative discount value
    @Test
    public void testGetProductCustom_NegativeDiscount() {
        logger.info("Bắt đầu TC_P_130 - testGetProductCustom_NegativeDiscount: Kiểm tra sản phẩm với discount âm.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setPrice(new BigDecimal("1000"));
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        when(productSaleService.getDiscountByIdProduct(1)).thenReturn(-10);
        logger.info("Dữ liệu chuẩn bị: 1 sản phẩm, price = 1000, discount = -10%");

        List<ProductCustom> result = productService.getProductCustom();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 ProductCustom");
        assertEquals(new BigDecimal("1100"), result.get(0).getPrice_sale(), "Giá sau discount âm phải tăng lên 1100");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(productSaleService, times(1)).getDiscountByIdProduct(1);
        logger.info("Kết quả TC_P_130 - testGetProductCustom_NegativeDiscount: Kết thúc test case.");
    }

    // TC_P_131 - testGetProductCustom_QueryThrowsException: Query execution fails
    @Test
    public void testGetProductCustom_QueryThrowsException() {
        logger.info("Bắt đầu TC_P_131 - testGetProductCustom_QueryThrowsException: Kiểm tra khi query ném ngoại lệ.");
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new RuntimeException("Database error"));
        logger.info("Dữ liệu chuẩn bị: query ném ngoại lệ");

        assertThrows(RuntimeException.class, () -> productService.getProductCustom(), "Phải ném RuntimeException khi query thất bại");

        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        verify(productSaleService, never()).getDiscountByIdProduct(anyInt());
        logger.info("Kết quả TC_P_131 - testGetProductCustom_QueryThrowsException: Kết thúc test case.");
    }

    // TC_P_132 - testGetProductCustom_DiscountServiceThrowsException: Discount service fails
    @Test
    public void testGetProductCustom_DiscountServiceThrowsException() {
        logger.info("Bắt đầu TC_P_132 - testGetProductCustom_DiscountServiceThrowsException: Kiểm tra khi discount service ném ngoại lệ.");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setPrice(new BigDecimal("1000"));
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        when(productSaleService.getDiscountByIdProduct(1)).thenThrow(new RuntimeException("Discount service error"));
        logger.info("Dữ liệu chuẩn bị: 1 sản phẩm, price = 1000, discount service ném ngoại lệ");

        assertThrows(RuntimeException.class, () -> productService.getProductCustom(), "Phải ném RuntimeException khi discount service thất bại");

        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(productSaleService, times(1)).getDiscountByIdProduct(1);
        logger.info("Kết quả TC_P_132 - testGetProductCustom_DiscountServiceThrowsException: Kết thúc test case.");
    }

    // TC_P_133 - testGetProductCustom_LargeNumberOfProducts: Many products
    @Test
    public void testGetProductCustom_LargeNumberOfProducts() {
        logger.info("Bắt đầu TC_P_133 - testGetProductCustom_LargeNumberOfProducts: Kiểm tra với nhiều sản phẩm.");
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Product p = new Product(); p.setId(i); p.setTitle("Product " + i); p.setPrice(new BigDecimal("1000"));
            products.add(p);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        when(productSaleService.getDiscountByIdProduct(anyInt())).thenReturn(5);
        logger.info("Dữ liệu chuẩn bị: 100 sản phẩm, price = 1000, discount = 5%");

        List<ProductCustom> result = productService.getProductCustom();

        assertNotNull(result, "Kết quả không được null");
        assertEquals(100, result.size(), "Phải trả về 100 ProductCustom");
        assertEquals(new BigDecimal("950"), result.get(0).getPrice_sale(), "Giá sau discount phải là 950");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(productSaleService, times(100)).getDiscountByIdProduct(anyInt());
        logger.info("Kết quả TC_P_133 - testGetProductCustom_LargeNumberOfProducts: Kết thúc test case.");
    }
}
