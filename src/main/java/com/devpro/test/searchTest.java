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
public class searchTest {

    @Autowired
    private ProductService productService;

    @Test //("search - Case 1: Tìm kiếm mặc định")
    void testSearch_Default() {
        ProductSearch search = new ProductSearch();
        List<Product> result = productService.search(search);
        assertNotNull(result);
    }

    @Test //("search - Case 2: Tìm kiếm theo tên")
    void testSearch_ByName() {
        ProductSearch search = new ProductSearch();
        search.setSearchText("Laptop");
        List<Product> result = productService.search(search);
        result.forEach(p -> assertTrue(p.getTitle().toLowerCase().contains("laptop")));
    }

    @Test //("search - Case 3: Không tìm thấy sản phẩm")
    void testSearch_NoResult() {
        ProductSearch search = new ProductSearch();
        search.setSearchText("khongtontai123");
        List<Product> result = productService.search(search);
        assertTrue(result.isEmpty());
    }

    @Test //("search - Case 4: Lọc sản phẩm có giá >= 1 triệu")
    void testSearch_FilterPriceMin1Mil() {
        List<Product> result = productService.search(new ProductSearch());
        List<Product> filtered = result.stream()
                .filter(p -> p.getPrice().compareTo(BigDecimal.valueOf(1_000_000)) >= 0)
                .collect(Collectors.toList());

        assertFalse(filtered.isEmpty());
    }
    @Test //("search - Case 5: Lọc sản phẩm có giá <= 5 triệu")
    void testSearch_FilterPriceMax5Mil() {
        List<Product> result = productService.search(new ProductSearch());
        List<Product> filtered = result.stream()
                .filter(p -> p.getPrice().compareTo(BigDecimal.valueOf(5_000_000)) <= 0)
                .collect(Collectors.toList());

        assertFalse(filtered.isEmpty());
    }

    @Test //("search - Case 6: Lọc sản phẩm có giá từ 1 đến 10 triệu")
    void testSearch_FilterPriceFrom1To10Mil() {
        List<Product> result = productService.search(new ProductSearch());
        List<Product> filtered = result.stream()
                .filter(p -> {
                    BigDecimal price = p.getPrice();
                    return price.compareTo(BigDecimal.valueOf(1_000_000)) >= 0 &&
                            price.compareTo(BigDecimal.valueOf(10_000_000)) <= 0;
                })
                .collect(Collectors.toList());

        assertFalse(filtered.isEmpty());
    }

    @Test //("search - Case 7: Không lỗi SQL")
    void testSearch_NoException() {
        assertDoesNotThrow(() -> productService.search(new ProductSearch()));
    }

    @Test //("search - Case 8: Đúng kiểu trả về")
    void testSearch_ReturnType() {
        List<Product> result = productService.search(new ProductSearch());
        assertTrue(result instanceof List);
    }

    @Test //("search - Case 9: Không null")
    void testSearch_NotNull() {
        assertNotNull(productService.search(new ProductSearch()));
    }

    @Test //("search - Case 10: Kiểm tra status")
    void testSearch_CheckStatus() {
        List<Product> result = productService.search(new ProductSearch());
        result.forEach(p -> assertNotNull(p.getStatus()));
    }
}
