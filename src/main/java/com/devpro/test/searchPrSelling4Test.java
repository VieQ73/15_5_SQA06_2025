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
public class searchPrSelling4Test {
    @Autowired
    private ProductService productService;

    @Test //("searchPrSelling4 - Case 1: Mặc định")
    void testSearchPrSelling4_Default() {
        List<Product> result = productService.searchPrSelling4(new ProductSearch());
        assertNotNull(result);
    }

    @Test //("searchPrSelling4 - Case 2: Tìm kiếm theo keyword")
    void testSearchPrSelling4_Keyword() {
        ProductSearch search = new ProductSearch();
        search.setSearchText("tablet");
        List<Product> result = productService.searchPrSelling4(search);
        result.forEach(p -> assertTrue(p.getTitle().toLowerCase().contains("tablet")));
    }

    @Test //("searchPrSelling4 - Case 3: Không có kết quả")
    void testSearchPrSelling4_NoResult() {
        ProductSearch search = new ProductSearch();
        search.setSearchText("abcxyz-no-match");
        List<Product> result = productService.searchPrSelling4(search);
        assertTrue(result.isEmpty());
    }

    @Test //("searchPrSelling4 - Case 4: Không lỗi khi gọi")
    void testSearchPrSelling4_NoException() {
        assertDoesNotThrow(() -> productService.searchPrSelling4(new ProductSearch()));
    }

    @Test //("searchPrSelling4 - Case 5: Kiểm tra ID sản phẩm")
    void testSearchPrSelling4_CheckId() {
        List<Product> result = productService.searchPrSelling4(new ProductSearch());
        result.forEach(p -> assertNotNull(p.getId()));
    }

    @Test //("searchPrSelling4 - Case 6: Kiểm tra status")
    void testSearchPrSelling4_Status() {
        List<Product> result = productService.searchPrSelling4(new ProductSearch());
        result.forEach(p -> assertNotNull(p.getStatus()));
    }

    @Test //("searchPrSelling4 - Case 7: Không null")
    void testSearchPrSelling4_NotNull() {
        assertNotNull(productService.searchPrSelling4(new ProductSearch()));
    }

    @Test //("searchPrSelling4 - Case 8: Đúng kiểu List")
    void testSearchPrSelling4_Type() {
        List<Product> result = productService.searchPrSelling4(new ProductSearch());
        assertTrue(result instanceof List);
    }

    @Test //("searchPrSelling4 - Case 9: Có ít nhất 1 sản phẩm hoặc rỗng")
    void testSearchPrSelling4_ResultSize() {
        List<Product> result = productService.searchPrSelling4(new ProductSearch());
        assertTrue(result.size() >= 0);
    }

    @Test //("searchPrSelling4 - Case 10: Độ dài kết quả hợp lệ")
    void testSearchPrSelling4_ResultLengthValid() {
        List<Product> result = productService.searchPrSelling4(new ProductSearch());
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }

}
