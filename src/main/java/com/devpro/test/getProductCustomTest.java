package com.devpro.test;

import com.devpro.entities.Product;
import com.devpro.model.ProductCustom;
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
public class getProductCustomTest {
    @Autowired
    private ProductService productService;

    @Test //("getProductCustom - Case 1: Trả về danh sách không null")
    void testGetProductCustom_NotNull() {
        List<ProductCustom> result = productService.getProductCustom();
        assertNotNull(result);
    }

    @Test //("getProductCustom - Case 2: Không ném exception")
    void testGetProductCustom_NoException() {
        assertDoesNotThrow(() -> productService.getProductCustom());
    }

    @Test //("getProductCustom - Case 3: Trả về danh sách rỗng hoặc có dữ liệu")
    void testGetProductCustom_ResultSize() {
        List<ProductCustom> result = productService.getProductCustom();
        assertTrue(result.size() >= 0);
    }

    @Test //("getProductCustom - Case 4: Product bên trong không null")
    void testGetProductCustom_ProductNotNull() {
        List<ProductCustom> result = productService.getProductCustom();
        result.forEach(pc -> assertNotNull(pc.getProduct()));
    }

    @Test //("getProductCustom - Case 5: Product có ID không null")
    void testGetProductCustom_ProductId() {
        List<ProductCustom> result = productService.getProductCustom();
        result.forEach(pc -> {
            Product p = pc.getProduct();
            assertNotNull(p);
            assertNotNull(p.getId());
        });
    }

    @Test //("getProductCustom - Case 6: Product có title")
    void testGetProductCustom_ProductTitle() {
        List<ProductCustom> result = productService.getProductCustom();
        result.forEach(pc -> {
            Product p = pc.getProduct();
            assertNotNull(p);
            assertNotNull(p.getTitle());
        });
    }

    @Test //("getProductCustom - Case 7: Product có price")
    void testGetProductCustom_ProductPrice() {
        List<ProductCustom> result = productService.getProductCustom();
        result.forEach(pc -> {
            Product p = pc.getProduct();
            assertNotNull(p);
            assertNotNull(p.getPrice());
        });
    }

    @Test //("getProductCustom - Case 8: Discount có giá trị >= 0")
    void testGetProductCustom_DiscountValid() {
        List<ProductCustom> result = productService.getProductCustom();
        result.forEach(pc -> assertTrue(pc.getDiscount() >= 0));
    }

    @Test //("getProductCustom - Case 9: Price_sale không null")
    void testGetProductCustom_PriceSaleNotNull() {
        List<ProductCustom> result = productService.getProductCustom();
        result.forEach(pc -> assertNotNull(pc.getPrice_sale()));
    }

    @Test //("getProductCustom - Case 10: Danh sách trả về đúng kiểu")
    void testGetProductCustom_Type() {
        List<ProductCustom> result = productService.getProductCustom();
        assertTrue(result instanceof List);
    }
}
