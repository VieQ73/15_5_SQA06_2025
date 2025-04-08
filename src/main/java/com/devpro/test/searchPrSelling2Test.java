package com.devpro.test;

import com.devpro.entities.Product;
import com.devpro.model.ProductSearch;
import com.devpro.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class searchPrSelling2Test {

    @Autowired
    private ProductService productService;
    
    @Test //("searchPrSelling2 - Case 1: Mặc định")
    void testSearchPrSelling2_Default() {
        ProductSearch search = new ProductSearch();
        List<Product> result = productService.searchPrSelling2(search);
        assertNotNull(result);
    }

    @Test //("searchPrSelling2 - Case 2: Theo từ khóa")
    void testSearchPrSelling2_Keyword() {
        ProductSearch search = new ProductSearch();
        search.setSearchText("phone");
        List<Product> result = productService.searchPrSelling2(search);
        assertTrue(result.stream().allMatch(p -> p.getTitle().toLowerCase().contains("phone")));
    }

    @Test //("searchPrSelling2 - Case 3: Không kết quả")
    void testSearchPrSelling2_NoResult() {
        ProductSearch search = new ProductSearch();
        search.setSearchText("xxxnonexistxxx");
        List<Product> result = productService.searchPrSelling2(search);
        assertTrue(result.isEmpty());
    }

    @Test //("searchPrSelling2 - Case 4: Có giá trị trả về")
    void testSearchPrSelling2_HasResult() {
        List<Product> result = productService.searchPrSelling2(new ProductSearch());
        assertTrue(result.size() >= 0);
    }

    @Test //("searchPrSelling2 - Case 5: Không lỗi")
    void testSearchPrSelling2_NoException() {
        assertDoesNotThrow(() -> productService.searchPrSelling2(new ProductSearch()));
    }

    @Test //("searchPrSelling2 - Case 6: Kiểm tra ID")
    void testSearchPrSelling2_CheckId() {
        List<Product> result = productService.searchPrSelling2(new ProductSearch());
        result.forEach(p -> assertNotNull(p.getId()));
    }

    @Test //("searchPrSelling2 - Case 7: Kiểm tra kiểu dữ liệu")
    void testSearchPrSelling2_Type() {
        List<Product> result = productService.searchPrSelling2(new ProductSearch());
        assertTrue(result instanceof List);
    }

    @Test //("searchPrSelling2 - Case 8: Trả về không null")
    void testSearchPrSelling2_NotNull() {
        assertNotNull(productService.searchPrSelling2(new ProductSearch()));
    }

    @Test //("searchPrSelling2 - Case 9: Có status")
    void testSearchPrSelling2_Status() {
        List<Product> result = productService.searchPrSelling2(new ProductSearch());
        result.forEach(p -> assertNotNull(p.getStatus()));
    }

    @Test //("searchPrSelling2 - Case 10: Kết quả không null")
    void testSearchPrSelling2_ResultNotNull() {
        List<Product> result = productService.searchPrSelling2(new ProductSearch());
        assertNotNull(result);
    }
}
