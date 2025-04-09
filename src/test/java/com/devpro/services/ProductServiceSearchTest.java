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
public class ProductServiceSearchTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceSearchTest.class);

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

    // TC_P_84 - testSearch_NoFilters: No filters provided
    @Test
    public void testSearch_NoFilters() {
        logger.info("Bắt đầu TC_P_84 - testSearch_NoFilters: Kiểm tra tìm kiếm không có bộ lọc.");
        ProductSearch productSearch = new ProductSearch();
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query trả về 1 sản phẩm", productSearch);

        List<Product> result = productService.search(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_84 - testSearch_NoFilters: Kết thúc test case.");
    }

    // TC_P_85 - testSearch_WithCategoryId: Filter by category ID
    @Test
    public void testSearch_WithCategoryId() {
        logger.info("Bắt đầu TC_P_85 - testSearch_WithCategoryId: Kiểm tra tìm kiếm với categoryId.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setCategoryId(1);
        List<Product> products = new ArrayList<>();
        Product p1 = new Product();
        p1.setId(1);
        p1.setTitle("Product 1");
        p1.setStatus(true);
        Category category = new Category();
        category.setId(1);
        p1.setCategory(category); // Use setCategory instead of setCategoryId
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch.categoryId = 1, query trả về 1 sản phẩm");

        List<Product> result = productService.search(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        assertEquals(1, result.get(0).getCategory().getId(), "Category ID phải đúng"); // Updated assertion
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_85 - testSearch_WithCategoryId: Kết thúc test case.");
    }

    // TC_P_86 - testSearch_WithProductId: Filter by product ID
    @Test
    public void testSearch_WithProductId() {
        logger.info("Bắt đầu TC_P_86 - testSearch_WithProductId: Kiểm tra tìm kiếm với productId.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setId(1);
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch.id = 1, query trả về 1 sản phẩm");

        List<Product> result = productService.search(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_86 - testSearch_WithProductId: Kết thúc test case.");
    }

    // TC_P_87 - testSearch_WithSeoProduct: Filter by product SEO
    @Test
    public void testSearch_WithSeoProduct() {
        logger.info("Bắt đầu TC_P_87 - testSearch_WithSeoProduct: Kiểm tra tìm kiếm với seoProduct.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setSeoProduct("product-1");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true); p1.setSeo("product-1");
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch.seoProduct = product-1, query trả về 1 sản phẩm");

        List<Product> result = productService.search(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_87 - testSearch_WithSeoProduct: Kết thúc test case.");
    }

    // TC_P_88 - testSearch_WithSeoCategory: Filter by category SEO
    @Test
    public void testSearch_WithSeoCategory() {
        logger.info("Bắt đầu TC_P_88 - testSearch_WithSeoCategory: Kiểm tra tìm kiếm với seoCategory.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setSeoCategoty("category-1");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product();
        p1.setId(1);
        p1.setTitle("Product 1");
        p1.setStatus(true);
        Category category = new Category();
        category.setId(1);
        p1.setCategory(category); // Use setCategory
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch.seoCategory = category-1, query trả về 1 sản phẩm");

        List<Product> result = productService.search(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        assertEquals("Product 1", result.get(0).getTitle(), "Sản phẩm phải đúng");
        assertEquals(1, result.get(0).getCategory().getId(), "Category ID phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_88 - testSearch_WithSeoCategory: Kết thúc test case.");
    }

    // TC_P_89 - testSearch_WithPagination: Search with pagination
    @Test
    public void testSearch_WithPagination() {
        logger.info("Bắt đầu TC_P_89 - testSearch_WithPagination: Kiểm tra tìm kiếm với phân trang.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setOffset(0);
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Product p = new Product(); p.setId(i); p.setTitle("Product " + i); p.setStatus(true);
            products.add(p);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        when(query.setFirstResult(0)).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        logger.info("Dữ liệu chuẩn bị: productSearch.offset = 0, query trả về 20 sản phẩm");

        List<Product> result = productService.search(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.size() <= 20, "Phải trả về tối đa số sản phẩm theo phân trang");
        assertEquals(20, productSearch.getCount(), "Count phải là 20");
        verify(query, times(1)).setFirstResult(0);
        verify(query, times(1)).setMaxResults(anyInt());
        logger.info("Kết quả TC_P_89 - testSearch_WithPagination: Kết thúc test case.");
    }

    // TC_P_90 - testSearch_NullProductSearch: ProductSearch is null
    @Test
    public void testSearch_NullProductSearch() {
        logger.info("Bắt đầu TC_P_90 - testSearch_NullProductSearch: Kiểm tra tìm kiếm với productSearch null.");
        ProductSearch productSearch = null;
        List<Product> products = new ArrayList<>();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch = null, query trả về danh sách rỗng");

        List<Product> result = productService.search(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_90 - testSearch_NullProductSearch: Kết thúc test case.");
    }

    // TC_P_91 - testSearch_QueryThrowsException: Query execution fails
    @Test
    public void testSearch_QueryThrowsException() {
        logger.info("Bắt đầu TC_P_91 - testSearch_QueryThrowsException: Kiểm tra khi query ném ngoại lệ.");
        ProductSearch productSearch = new ProductSearch();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new RuntimeException("Database error"));
        logger.info("Dữ liệu chuẩn bị: productSearch = {}, query ném ngoại lệ", productSearch);

        assertThrows(RuntimeException.class, () -> productService.search(productSearch), "Phải ném RuntimeException khi query thất bại");

        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_91 - testSearch_QueryThrowsException: Kết thúc test case.");
    }

    // TC_P_92 - testSearch_EmptyFilters: All filters are empty
    @Test
    public void testSearch_EmptyFilters() {
        logger.info("Bắt đầu TC_P_92 - testSearch_EmptyFilters: Kiểm tra tìm kiếm với bộ lọc rỗng.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setCategoryId(null);
        productSearch.setId(null);
        productSearch.setSeoProduct("");
        productSearch.setSeoCategoty("");
        List<Product> products = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1); p1.setTitle("Product 1"); p1.setStatus(true);
        products.add(p1);
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch với bộ lọc rỗng, query trả về 1 sản phẩm");

        List<Product> result = productService.search(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 sản phẩm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_92 - testSearch_EmptyFilters: Kết thúc test case.");
    }

    // TC_P_93 - testSearch_InvalidCategoryId: Invalid category ID
    @Test
    public void testSearch_InvalidCategoryId() {
        logger.info("Bắt đầu TC_P_93 - testSearch_InvalidCategoryId: Kiểm tra tìm kiếm với categoryId không hợp lệ.");
        ProductSearch productSearch = new ProductSearch();
        productSearch.setCategoryId(-1);
        List<Product> products = new ArrayList<>();
        when(entityManager.createNativeQuery(anyString(), eq(Product.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(products);
        logger.info("Dữ liệu chuẩn bị: productSearch.categoryId = -1, query trả về danh sách rỗng");

        List<Product> result = productService.search(productSearch);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách sản phẩm phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Product.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_P_93 - testSearch_InvalidCategoryId: Kết thúc test case.");
    }
}
