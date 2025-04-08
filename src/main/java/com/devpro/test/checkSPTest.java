package com.devpro.test;

import com.devpro.entities.Product;
import com.devpro.model.ProductCustom;
import com.devpro.model.ProductSearch;
import com.devpro.model.ThongKe;
import com.devpro.services.ProductService;
import com.devpro.services.ThongKeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class checkSPTest {

    @Autowired
    private ThongKeService thongKeService;

    @Test //("checkSP - Case 1: ID tồn tại")
    void testCheckSP_Exist() {
        ThongKe tk = new ThongKe();
        Product product = new Product();
        product.setId(1);
        tk.setProduct(product);
        List<ThongKe> list = Collections.singletonList(tk);
        assertTrue(thongKeService.checkSP(list, 1));
    }

    @Test //("checkSP - Case 2: ID không tồn tại")
    void testCheckSP_NotExist() {
        ThongKe tk = new ThongKe();
        Product product = new Product();
        product.setId(2);
        tk.setProduct(product);
        List<ThongKe> list = Collections.singletonList(tk);
        assertFalse(thongKeService.checkSP(list, 3));
    }

    @Test //("checkSP - Case 3: Danh sách rỗng")
    void testCheckSP_EmptyList() {
        assertFalse(thongKeService.checkSP(Collections.emptyList(), 1));
    }

    @Test //("checkSP - Case 4: ID null")
    void testCheckSP_NullId() {
        ThongKe tk = new ThongKe();
        tk.setProduct(new Product());
        assertFalse(thongKeService.checkSP(Collections.singletonList(tk), null));
    }

    @Test //("checkSP - Case 5: Product null")
    void testCheckSP_NullProduct() {
        ThongKe tk = new ThongKe();
        tk.setProduct(null);
        assertFalse(thongKeService.checkSP(Collections.singletonList(tk), 1));
    }

    @Test //("checkSP - Case 6: Nhiều phần tử, có ID đúng")
    void testCheckSP_Multiple_Match() {
        Product p1 = new Product(); p1.setId(5);
        Product p2 = new Product(); p2.setId(10);
        ThongKe tk1 = new ThongKe(); tk1.setProduct(p1);
        ThongKe tk2 = new ThongKe(); tk2.setProduct(p2);
        assertTrue(thongKeService.checkSP(Arrays.asList(tk1, tk2), 10));
    }

    @Test //("checkSP - Case 7: Nhiều phần tử, không có ID đúng")
    void testCheckSP_Multiple_NoMatch() {
        Product p1 = new Product(); p1.setId(1);
        Product p2 = new Product(); p2.setId(2);
        ThongKe tk1 = new ThongKe(); tk1.setProduct(p1);
        ThongKe tk2 = new ThongKe(); tk2.setProduct(p2);
        assertFalse(thongKeService.checkSP(Arrays.asList(tk1, tk2), 3));
    }

    @Test //("checkSP - Case 8: Product không có ID")
    void testCheckSP_ProductNoId() {
        Product p = new Product();
        ThongKe tk = new ThongKe();
        tk.setProduct(p);
        assertFalse(thongKeService.checkSP(Collections.singletonList(tk), 5));
    }

    @Test //("checkSP - Case 9: Truyền vào ID âm")
    void testCheckSP_NegativeId() {
        Product p = new Product(); p.setId(-1);
        ThongKe tk = new ThongKe(); tk.setProduct(p);
        assertFalse(thongKeService.checkSP(Collections.singletonList(tk), -2));
    }

    @Test //("checkSP - Case 10: Truyền vào danh sách null")
    void testCheckSP_NullList() {
        assertFalse(thongKeService.checkSP(null, 1));
    }

}
