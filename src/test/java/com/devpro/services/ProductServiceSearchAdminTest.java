package com.devpro.services;

import com.devpro.entities.Category;
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
public class ProductServiceSearchAdminTest {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceSearchAdminTest.class);

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
    // TC_P_114 - testSearchAdmin_NoFilters: No filters provided
    @Test
    public void testSearchAdmin_NoFilters() {
        logger.info("Bắt đầu TC_P_114 - testSearchAdmin_NoFilters: Kiểm tra tìm kiếm admin không có bộ lọc.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1");
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 1 sản phẩm", productSearch);

        List<Product> result = productService.searchAdmin(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_114 - testSearchAdmin_NoFilters: Kết thúc test case.");
    }

    // TC_P_115 - testSearchAdmin_WithCategoryId: Filter by category ID
    @Test
    public void testSearchAdmin_WithCategoryId() {
        logger.info("Bắt đầu TC_P_115 - testSearchAdmin_WithCategoryId: Kiểm tra tìm kiếm admin với categoryId.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setCategoryId(1);
        List<Product> products = new ArrayList<>();
        Product p1 = new Product();
        p1.setId(1);
        p1.setTitle("Product 1");
        Category category = new Category();
        category.setId(1);
        p1.setCategory(category); // Use setCategory
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch.categoryId = 1, query trả về 1 sản phẩm");

        List<Product> result = productService.searchAdmin(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        assertEquals(1, result.get(0).getCategory().getId(), "Category ID phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_115 - testSearchAdmin_WithCategoryId: Kết thúc test case.");
    }

    // TC_P_116 - testSearchAdmin_WithProductId: Filter by product ID
    @Test
    public void testSearchAdmin_WithProductId() {
        logger.info("Bắt đầu TC_P_116 - testSearchAdmin_WithProductId: Kiểm tra tìm kiếm admin với productId.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setId(1);
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1");
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch.id = 1, query trả về 1 sản phẩm");

        List<Product> result = productService.searchAdmin(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_116 - testSearchAdmin_WithProductId: Kết thúc test case.");
    }

    // TC_P_117 - testSearchAdmin_WithSeoProduct: Filter by product SEO
    @Test
    public void testSearchAdmin_WithSeoProduct() {
        logger.info("Bắt đầu TC_P_117 - testSearchAdmin_WithSeoProduct: Kiểm tra tìm kiếm admin với seoProduct.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setSeoProduct("product-1");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setSeo("product-1");
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch.seoProduct = product-1, query trả về 1 sản phẩm");

        List<Product> result = productService.searchAdmin(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_117 - testSearchAdmin_WithSeoProduct: Kết thúc test case.");
    }

    // TC_P_118 - testSearchAdmin_WithSeoCategory: Filter by category SEO
    @Test
    public void testSearchAdmin_WithSeoCategory() {
        logger.info("Bắt đầu TC_P_118 - testSearchAdmin_WithSeoCategory: Kiểm tra tìm kiếm admin với seoCategory.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setSeoCategoty("category-1");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product();
        p1.setId(1);
        p1.setTitle("Product 1");
        Category category = new Category();
        category.setId(1);
        p1.setCategory(category); // Use setCategory
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch.seoCategory = category-1, query trả về 1 sản phẩm");

        List<Product> result = productService.searchAdmin(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        assertEquals(1, result.get(0).getCategory().getId(), "Category ID phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_118 - testSearchAdmin_WithSeoCategory: Kết thúc test case.");
    }

    // TC_P_119 - testSearchAdmin_NullProductSearch: ProductSearch is null
    @Test
    public void testSearchAdmin_NullProductSearch() {
        logger.info("Bắt đầu TC_P_119 - testSearchAdmin_NullProductSearch: Kiểm tra tìm kiếm admin với productSearch null.");
        ProductSearch productSearch = null;
        List<Product> products = new ArrayList<>();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = null, query trả về danh sách rỗng");

        List<Product> result = productService.searchAdmin(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_119 - testSearchAdmin_NullProductSearch: Kết thúc test case.");
    }

    // TC_P_120 - testSearchAdmin_QueryThrowsException: Query execution fails
    @Test
    public void testSearchAdmin_QueryThrowsException() {
        logger.info("Bắt đầu TC_P_120 - testSearchAdmin_QueryThrowsException: Kiểm tra khi query ném ngoại lệ.");
        ProductSearch productSearch = new ProductSearch();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new RuntimeException("Database error"));
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query ném ngoại lệ", productSearch);

        assertThrows(RuntimeException.class, () -> productService.searchAdmin(productSearch), "Phải ném RuntimeException khi query thất bại");

        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_120 - testSearchAdmin_QueryThrowsException: Kết thúc test case.");
    }

    // TC_P_121 - testSearchAdmin_EmptyFilters: All filters are empty
    @Test
    public void testSearchAdmin_EmptyFilters() {
        logger.info("Bắt đầu TC_P_121 - testSearchAdmin_EmptyFilters: Kiểm tra tìm kiếm admin với bộ lọc rỗng.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setCategoryId(null);
        productSearch.setId(null);
        productSearch.setSeoProduct("");
        productSearch.setSeoCategoty("");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1");
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch với bộ lọc rỗng, query trả về 1 sản phẩm");

        List<Product> result = productService.searchAdmin(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_121 - testSearchAdmin_EmptyFilters: Kết thúc test case.");
    }

    // TC_P_122 - testSearchAdmin_InvalidCategoryId: Invalid category ID
    @Test
    public void testSearchAdmin_InvalidCategoryId() {
        logger.info("Bắt đầu TC_P_122 - testSearchAdmin_InvalidCategoryId: Kiểm tra tìm kiếm admin với categoryId không hợp lệ.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setCategoryId(-1);
        List<Product> products = new ArrayList<>();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch.categoryId = -1, query trả về danh sách rỗng");

        List<Product> result = productService.searchAdmin(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_122 - testSearchAdmin_InvalidCategoryId: Kết thúc test case.");
    }

    // TC_P_123 - testSearchAdmin_MultipleFilters: Combine multiple filters
    @Test
    public void testSearchAdmin_MultipleFilters() {
        logger.info("Bắt đầu TC_P_123 - testSearchAdmin_MultipleFilters: Kiểm tra tìm kiếm admin với nhiều bộ lọc.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setCategoryId(1);
        productSearch.setSeoProduct("product-1");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product();
        p1.setId(1);
        p1.setTitle("Product 1");
        Category category = new Category();
        category.setId(1);
        p1.setCategory(category); // Use setCategory
        p1.setSeo("product-1");
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch.categoryId = 1, seoProduct = product-1, query trả về 1 sản phẩm");

        List<Product> result = productService.searchAdmin(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        assertEquals(1, result.get(0).getCategory().getId(), "Category ID phải đúng");
        assertEquals("product-1", result.get(0).getSeo(), "SEO phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_123 - testSearchAdmin_MultipleFilters: Kết thúc test case.");
    }
}
