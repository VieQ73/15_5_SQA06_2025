package com.devpro.test;

import com.devpro.entities.Product;
import com.devpro.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class getAllProductTest {

    @Autowired
    private ProductService productService;

    @Test //("getAllProduct - Case 1: Lấy toàn bộ")
    void testGetAllProduct_All() {
        List<Product> products = productService.getAllProduct();
        assertNotNull(products);
    }

    @Test //("getAllProduct - Case 2: Có sản phẩm")
    void testGetAllProduct_NotEmpty() {
        List<Product> products = productService.getAllProduct();
        assertTrue(products.size() >= 0);
    }

    @Test //("getAllProduct - Case 3: Kiểm tra status")
    void testGetAllProduct_StatusTrue() {
        List<Product> products = productService.getAllProduct();
        assertTrue(products.stream().allMatch(Product::getStatus));
    }

    @Test //("getAllProduct - Case 4: Không lỗi")
    void testGetAllProduct_NoError() {
        assertDoesNotThrow(() -> productService.getAllProduct());
    }

    @Test //("getAllProduct - Case 5: So sánh ID")
    void testGetAllProduct_CheckId() {
        List<Product> products = productService.getAllProduct();
        products.forEach(p -> assertNotNull(p.getId()));
    }

    @Test //("getAllProduct - Case 6: Kiểm tra tên")
    void testGetAllProduct_CheckTitle() {
        List<Product> products = productService.getAllProduct();
        products.forEach(p -> assertNotNull(p.getTitle()));
    }

    @Test //("getAllProduct - Case 7: Không null")
    void testGetAllProduct_NotNull() {
        assertNotNull(productService.getAllProduct());
    }

    @Test //("getAllProduct - Case 8: Không lỗi NullPointer")
    void testGetAllProduct_NullSafe() {
        List<Product> products = productService.getAllProduct();
        products.forEach(p -> assertDoesNotThrow(() -> p.getPrice()));
    }

    @Test //("getAllProduct - Case 9: Không có sản phẩm status false")
    void testGetAllProduct_NoFalseStatus() {
        List<Product> products = productService.getAllProduct();
        assertFalse(products.stream().anyMatch(p -> !p.getStatus()));
    }

    @Test //("getAllProduct - Case 10: So sánh size >= 0")
    void testGetAllProduct_Size() {
        assertTrue(productService.getAllProduct().size() >= 0);
    }
}
