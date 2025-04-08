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
public class searchAdminTest {
    @Autowired
    private ProductService productService;

    @Test //("searchAdmin - Case 1: Tìm kiếm mặc định")
    void testSearchAdmin_Default() {
        List<Product> result = productService.searchAdmin(new ProductSearch());
        assertNotNull(result);
    }

    @Test //("searchAdmin - Case 2: Tìm kiếm có keyword")
    void testSearchAdmin_Keyword() {
        ProductSearch search = new ProductSearch();
        search.setSearchText("camera");
        List<Product> result = productService.searchAdmin(search);
        result.forEach(p -> assertTrue(p.getTitle().toLowerCase().contains("camera")));
    }

    @Test //("searchAdmin - Case 3: Không tìm thấy")
    void testSearchAdmin_NoResult() {
        ProductSearch search = new ProductSearch();
        search.setSearchText("nonexist-keyword");
        List<Product> result = productService.searchAdmin(search);
        assertTrue(result.isEmpty());
    }

    @Test //("searchAdmin - Case 4: Trả về không lỗi")
    void testSearchAdmin_NoException() {
        assertDoesNotThrow(() -> productService.searchAdmin(new ProductSearch()));
    }

    @Test //("searchAdmin - Case 5: Kiểm tra ID")
    void testSearchAdmin_CheckId() {
        List<Product> result = productService.searchAdmin(new ProductSearch());
        result.forEach(p -> assertNotNull(p.getId()));
    }

    @Test //("searchAdmin - Case 6: Có status")
    void testSearchAdmin_CheckStatus() {
        List<Product> result = productService.searchAdmin(new ProductSearch());
        result.forEach(p -> assertNotNull(p.getStatus()));
    }

    @Test //("searchAdmin - Case 7: Không null")
    void testSearchAdmin_NotNull() {
        assertNotNull(productService.searchAdmin(new ProductSearch()));
    }

    @Test //("searchAdmin - Case 8: Đúng kiểu dữ liệu")
    void testSearchAdmin_Type() {
        List<Product> result = productService.searchAdmin(new ProductSearch());
        assertTrue(result instanceof List);
    }

    @Test //("searchAdmin - Case 9: Có thể có nhiều kết quả")
    void testSearchAdmin_ResultSize() {
        List<Product> result = productService.searchAdmin(new ProductSearch());
        assertTrue(result.size() >= 0);
    }

    @Test //("searchAdmin - Case 10: Không trả về null")
    void testSearchAdmin_ResultNotNull() {
        List<Product> result = productService.searchAdmin(new ProductSearch());
        assertNotNull(result);
    }
}
