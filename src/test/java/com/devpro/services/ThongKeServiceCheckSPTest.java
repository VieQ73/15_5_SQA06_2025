package com.devpro.services;

import com.devpro.entities.Order;
import com.devpro.entities.OrderProducts;
import com.devpro.entities.Product;
import com.devpro.model.ThongKe;
import com.devpro.repositories.OrderProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class ThongKeServiceCheckSPTest {

    private static final Logger logger = LoggerFactory.getLogger(ThongKeServiceCheckSPTest.class);

    @InjectMocks
    private ThongKeService thongKeService;

    @Mock
    private OrderProductRepo orderProductRepo;

    @BeforeEach
    public void setUp() {
        // Initialize mocks if needed
    }

    // TC_TK_01 - testCheckSP_EmptyList: Check with an empty list
    @Test
    public void testCheckSP_EmptyList() {
        logger.info("Bắt đầu TC_TK_01 - testCheckSP_EmptyList: Kiểm tra với danh sách ThongKe rỗng.");
        List<ThongKe> listTK = new ArrayList<>();
        Integer id = 1;
        logger.info("Dữ liệu chuẩn bị: listTK = [], id = 1");

        boolean result = thongKeService.checkSP(listTK, id);

        assertFalse(result, "Phải trả về false khi danh sách rỗng");
        logger.info("Kết quả TC_TK_01 - testCheckSP_EmptyList: Kết thúc test case.");
    }

    // TC_TK_02 - testCheckSP_SingleMatch: Check with a single matching product
    @Test
    public void testCheckSP_SingleMatch() {
        logger.info("Bắt đầu TC_TK_02 - testCheckSP_SingleMatch: Kiểm tra với 1 sản phẩm khớp.");
        List<ThongKe> listTK = new ArrayList<>();
        ThongKe tk = new ThongKe();
        Product product = new Product();
        product.setId(1);
        tk.setProduct(product);
        listTK.add(tk);
        Integer id = 1;
        logger.info("Dữ liệu chuẩn bị: listTK = [ThongKe(product.id=1)], id = 1");

        boolean result = thongKeService.checkSP(listTK, id);

        assertTrue(result, "Phải trả về true khi tìm thấy sản phẩm khớp");
        logger.info("Kết quả TC_TK_02 - testCheckSP_SingleMatch: Kết thúc test case.");
    }

    // TC_TK_03 - testCheckSP_SingleNoMatch: Check with a single non-matching product
    @Test
    public void testCheckSP_SingleNoMatch() {
        logger.info("Bắt đầu TC_TK_03 - testCheckSP_SingleNoMatch: Kiểm tra với 1 sản phẩm không khớp.");
        List<ThongKe> listTK = new ArrayList<>();
        ThongKe tk = new ThongKe();
        Product product = new Product();
        product.setId(2);
        tk.setProduct(product);
        listTK.add(tk);
        Integer id = 1;
        logger.info("Dữ liệu chuẩn bị: listTK = [ThongKe(product.id=2)], id = 1");

        boolean result = thongKeService.checkSP(listTK, id);

        assertFalse(result, "Phải trả về false khi không tìm thấy sản phẩm khớp");
        logger.info("Kết quả TC_TK_03 - testCheckSP_SingleNoMatch: Kết thúc test case.");
    }

    // TC_TK_04 - testCheckSP_MultipleProductsMatch: Check with multiple products, one matches
    @Test
    public void testCheckSP_MultipleProductsMatch() {
        logger.info("Bắt đầu TC_TK_04 - testCheckSP_MultipleProductsMatch: Kiểm tra với nhiều sản phẩm, có 1 sản phẩm khớp.");
        List<ThongKe> listTK = new ArrayList<>();
        ThongKe tk1 = new ThongKe();
        Product p1 = new Product();
        p1.setId(2);
        tk1.setProduct(p1);
        ThongKe tk2 = new ThongKe();
        Product p2 = new Product();
        p2.setId(1);
        tk2.setProduct(p2);
        listTK.add(tk1);
        listTK.add(tk2);
        Integer id = 1;
        logger.info("Dữ liệu chuẩn bị: listTK = [ThongKe(product.id=2), ThongKe(product.id=1)], id = 1");

        boolean result = thongKeService.checkSP(listTK, id);

        assertTrue(result, "Phải trả về true khi tìm thấy sản phẩm khớp");
        logger.info("Kết quả TC_TK_04 - testCheckSP_MultipleProductsMatch: Kết thúc test case.");
    }

    // TC_TK_05 - testCheckSP_MultipleProductsNoMatch: Check with multiple products, none match
    @Test
    public void testCheckSP_MultipleProductsNoMatch() {
        logger.info("Bắt đầu TC_TK_05 - testCheckSP_MultipleProductsNoMatch: Kiểm tra với nhiều sản phẩm, không có sản phẩm khớp.");
        List<ThongKe> listTK = new ArrayList<>();
        ThongKe tk1 = new ThongKe();
        Product p1 = new Product();
        p1.setId(2);
        tk1.setProduct(p1);
        ThongKe tk2 = new ThongKe();
        Product p2 = new Product();
        p2.setId(3);
        tk2.setProduct(p2);
        listTK.add(tk1);
        listTK.add(tk2);
        Integer id = 1;
        logger.info("Dữ liệu chuẩn bị: listTK = [ThongKe(product.id=2), ThongKe(product.id=3)], id = 1");

        boolean result = thongKeService.checkSP(listTK, id);

        assertFalse(result, "Phải trả về false khi không tìm thấy sản phẩm khớp");
        logger.info("Kết quả TC_TK_05 - testCheckSP_MultipleProductsNoMatch: Kết thúc test case.");
    }

    // TC_TK_06 - testCheckSP_NullId: Check with a null ID
    @Test
    public void testCheckSP_NullId() {
        logger.info("Bắt đầu TC_TK_06 - testCheckSP_NullId: Kiểm tra với id là null.");
        List<ThongKe> listTK = new ArrayList<>();
        ThongKe tk = new ThongKe();
        Product product = new Product();
        product.setId(1);
        tk.setProduct(product);
        listTK.add(tk);
        Integer id = null;
        logger.info("Dữ liệu chuẩn bị: listTK = [ThongKe(product.id=1)], id = null");

        boolean result = thongKeService.checkSP(listTK, id);

        assertFalse(result, "Phải trả về false khi id là null");
        logger.info("Kết quả TC_TK_06 - testCheckSP_NullId: Kết thúc test case.");
    }

    // TC_TK_07 - testCheckSP_NullProduct: Check with a ThongKe having a null Product
    @Test
    public void testCheckSP_NullProduct() {
        logger.info("Bắt đầu TC_TK_07 - testCheckSP_NullProduct: Kiểm tra với ThongKe có Product là null.");
        List<ThongKe> listTK = new ArrayList<>();
        ThongKe tk = new ThongKe();
        tk.setProduct(null);
        listTK.add(tk);
        Integer id = 1;
        logger.info("Dữ liệu chuẩn bị: listTK = [ThongKe(product=null)], id = 1");

        assertThrows(NullPointerException.class, () -> thongKeService.checkSP(listTK, id),
                "Phải ném NullPointerException khi Product là null");

        logger.info("Kết quả TC_TK_07 - testCheckSP_NullProduct: Kết thúc test case.");
    }

    // TC_TK_08 - testCheckSP_NullList: Check with a null list
    @Test
    public void testCheckSP_NullList() {
        logger.info("Bắt đầu TC_TK_08 - testCheckSP_NullList: Kiểm tra với danh sách ThongKe là null.");
        List<ThongKe> listTK = null;
        Integer id = 1;
        logger.info("Dữ liệu chuẩn bị: listTK = null, id = 1");

        assertThrows(NullPointerException.class, () -> thongKeService.checkSP(listTK, id),
                "Phải ném NullPointerException khi danh sách là null");

        logger.info("Kết quả TC_TK_08 - testCheckSP_NullList: Kết thúc test case.");
    }

    // TC_TK_09 - testCheckSP_LargeList: Check with a large list
    @Test
    public void testCheckSP_LargeList() {
        logger.info("Bắt đầu TC_TK_09 - testCheckSP_LargeList: Kiểm tra với danh sách lớn.");
        List<ThongKe> listTK = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            ThongKe tk = new ThongKe();
            Product product = new Product();
            product.setId(i);
            tk.setProduct(product);
            listTK.add(tk);
        }
        Integer id = 500;
        logger.info("Dữ liệu chuẩn bị: listTK = [ThongKe(product.id=1..1000)], id = 500");

        boolean result = thongKeService.checkSP(listTK, id);

        assertTrue(result, "Phải trả về true khi tìm thấy sản phẩm khớp trong danh sách lớn");
        logger.info("Kết quả TC_TK_09 - testCheckSP_LargeList: Kết thúc test case.");
    }

    // TC_TK_10 - testCheckSP_DuplicateProducts: Check with duplicate product IDs in the list
    @Test
    public void testCheckSP_DuplicateProducts() {
        logger.info("Bắt đầu TC_TK_10 - testCheckSP_DuplicateProducts: Kiểm tra với danh sách có sản phẩm trùng lặp.");
        List<ThongKe> listTK = new ArrayList<>();
        ThongKe tk1 = new ThongKe();
        Product p1 = new Product();
        p1.setId(1);
        tk1.setProduct(p1);
        ThongKe tk2 = new ThongKe();
        Product p2 = new Product();
        p2.setId(1);
        tk2.setProduct(p2);
        listTK.add(tk1);
        listTK.add(tk2);
        Integer id = 1;
        logger.info("Dữ liệu chuẩn bị: listTK = [ThongKe(product.id=1), ThongKe(product.id=1)], id = 1");

        boolean result = thongKeService.checkSP(listTK, id);

        assertTrue(result, "Phải trả về true khi tìm thấy sản phẩm khớp, bất kể trùng lặp");
        logger.info("Kết quả TC_TK_10 - testCheckSP_DuplicateProducts: Kết thúc test case.");
    }
}
