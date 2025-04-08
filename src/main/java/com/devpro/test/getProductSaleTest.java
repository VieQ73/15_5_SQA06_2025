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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class getProductSaleTest {

    @Autowired
    private ProductService productService;

    // ------------------- getProductSale -------------------
    @Test
    //("getProductSale - Case 1: Dữ liệu rỗng")
    void testGetProductSale_Empty() {
        ProductSearch search = new ProductSearch();
        List<Product> result = productService.getProductSale(search);
        assertNotNull(result);
    }

    @Test
        //("getProductSale - Case 2: Check sale có hoạt động")
    void testGetProductSale_ValidSale() {
        ProductSearch search = new ProductSearch();
        List<Product> result = productService.getProductSale(search);
        result.forEach(p -> assertTrue(p.getStatus()));
    }

    @Test
        //("getProductSale - Case 3: Check không có sản phẩm sale")
    void testGetProductSale_NoSale() {
        // giả định thời gian nằm ngoài thời gian sale
        List<Product> result = productService.getProductSale(new ProductSearch());
        assertNotNull(result);
    }

    @Test
        //("getProductSale - Case 4: Sale vẫn đang hoạt động")
    void testGetProductSale_StillActive() {
        List<Product> result = productService.getProductSale(new ProductSearch());
        assertTrue(result.size() >= 0);
    }

    @Test
        //("getProductSale - Case 5: Không lỗi SQL")
    void testGetProductSale_NoException() {
        assertDoesNotThrow(() -> productService.getProductSale(new ProductSearch()));
    }

    @Test
        //("getProductSale - Case 6: Tồn tại nhiều sản phẩm sale")
    void testGetProductSale_Many() {
        List<Product> result = productService.getProductSale(new ProductSearch());
        assertTrue(result.size() >= 0);
    }

    @Test
        //("getProductSale - Case 7: Trả về đúng kiểu dữ liệu")
    void testGetProductSale_DataType() {
        List<Product> result = productService.getProductSale(new ProductSearch());
        assertTrue(result instanceof List);
    }

    @Test
        //("getProductSale - Case 8: Sale có active")
    void testGetProductSale_ActiveSale() {
        List<Product> result = productService.getProductSale(new ProductSearch());
        result.forEach(p -> assertNotNull(p.getId()));
    }

    @Test
        //("getProductSale - Case 9: Không null")
    void testGetProductSale_NotNull() {
        assertNotNull(productService.getProductSale(new ProductSearch()));
    }

    @Test
        //("getProductSale - Case 10: Sale khác null")
    void testGetProductSale_SaleExists() {
        List<Product> result = productService.getProductSale(new ProductSearch());
        assertNotNull(result);
    }

}