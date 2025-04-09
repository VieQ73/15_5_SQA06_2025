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
public class ThongKeServiceThongKeTheoTimeTest {

    private static final Logger logger = LoggerFactory.getLogger(ThongKeServiceThongKeTheoTimeTest.class);

    @InjectMocks
    private ThongKeService thongKeService;

    @Mock
    private OrderProductRepo orderProductRepo;

    @BeforeEach
    public void setUp() {

    }

    // TC_TK_11 - testThongKeTheoTime_EmptyList: No order products in the database
    @Test
    public void testThongKeTheoTime_EmptyList() throws ParseException {
        logger.info("Bắt đầu TC_TK_11 - testThongKeTheoTime_EmptyList: Kiểm tra khi không có đơn hàng nào.");
        Long ngayBD = 1625097600000L; // 2021-07-01
        Long ngayKT = 1627689600000L; // 2021-07-31
        when(orderProductRepo.findAll()).thenReturn(new ArrayList<>());
        logger.info("Dữ liệu chuẩn bị: ngayBD = 2021-07-01, ngayKT = 2021-07-31, DB rỗng");

        List<ThongKe> result = thongKeService.thongKeTheoTime(ngayBD, ngayKT);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách ThongKe phải rỗng");
        verify(orderProductRepo, times(1)).findAll();
        logger.info("Kết quả TC_TK_11 - testThongKeTheoTime_EmptyList: Kết thúc test case.");
    }

    // TC_TK_12 - testThongKeTheoTime_SingleProductInRange: One order product in the time range
    @Test
    public void testThongKeTheoTime_SingleProductInRange() throws ParseException {
        logger.info("Bắt đầu TC_TK_12 - testThongKeTheoTime_SingleProductInRange: Kiểm tra với 1 đơn hàng trong khoảng thời gian.");
        Long ngayBD = 1625097600000L; // 2021-07-01
        Long ngayKT = 1627689600000L; // 2021-07-31
        List<OrderProducts> listPS = new ArrayList<>();
        OrderProducts op = new OrderProducts();
        Order order = new Order();
        order.setStatus(2);
        order.setUpdated_date(new Date(1625184000000L)); // 2021-07-02
        op.setOrder(order);
        Product product = new Product();
        product.setId(1);
        op.setProduct(product);
        op.setQuality(2);
        op.setPrice(new BigDecimal("1000"));
        listPS.add(op);
        when(orderProductRepo.findAll()).thenReturn(listPS);
        logger.info("Dữ liệu chuẩn bị: ngayBD = 2021-07-01, ngayKT = 2021-07-31, 1 đơn hàng (status=2, quality=2, price=1000)");

        List<ThongKe> result = thongKeService.thongKeTheoTime(ngayBD, ngayKT);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 ThongKe");
        assertEquals(2, result.get(0).getTongSoLuong(), "Tổng số lượng phải là 2");
        assertEquals(new BigDecimal("2000"), result.get(0).getTongGia(), "Tổng giá phải là 2000");
        verify(orderProductRepo, times(1)).findAll();
        logger.info("Kết quả TC_TK_12 - testThongKeTheoTime_SingleProductInRange: Kết thúc test case.");
    }

    // TC_TK_13 - testThongKeTheoTime_SingleProductOutOfRange: One order product outside the time range
    @Test
    public void testThongKeTheoTime_SingleProductOutOfRange() throws ParseException {
        logger.info("Bắt đầu TC_TK_13 - testThongKeTheoTime_SingleProductOutOfRange: Kiểm tra với 1 đơn hàng ngoài khoảng thời gian.");
        Long ngayBD = 1625097600000L; // 2021-07-01
        Long ngayKT = 1627689600000L; // 2021-07-31
        List<OrderProducts> listPS = new ArrayList<>();
        OrderProducts op = new OrderProducts();
        Order order = new Order();
        order.setStatus(2);
        order.setUpdated_date(new Date(1622505600000L)); // 2021-06-01
        op.setOrder(order);
        Product product = new Product();
        product.setId(1);
        op.setProduct(product);
        op.setQuality(2);
        op.setPrice(new BigDecimal("1000"));
        listPS.add(op);
        when(orderProductRepo.findAll()).thenReturn(listPS);
        logger.info("Dữ liệu chuẩn bị: ngayBD = 2021-07-01, ngayKT = 2021-07-31, 1 đơn hàng (status=2, updated_date=2021-06-01)");

        List<ThongKe> result = thongKeService.thongKeTheoTime(ngayBD, ngayKT);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách ThongKe phải rỗng");
        verify(orderProductRepo, times(1)).findAll();
        logger.info("Kết quả TC_TK_13 - testThongKeTheoTime_SingleProductOutOfRange: Kết thúc test case.");
    }

    // TC_TK_14 - testThongKeTheoTime_WrongStatus: Order with status not equal to 2
    @Test
    public void testThongKeTheoTime_WrongStatus() throws ParseException {
        logger.info("Bắt đầu TC_TK_14 - testThongKeTheoTime_WrongStatus: Kiểm tra với đơn hàng có status không phải 2.");
        Long ngayBD = 1625097600000L; // 2021-07-01
        Long ngayKT = 1627689600000L; // 2021-07-31
        List<OrderProducts> listPS = new ArrayList<>();
        OrderProducts op = new OrderProducts();
        Order order = new Order();
        order.setStatus(1);
        order.setUpdated_date(new Date(1625184000000L)); // 2021-07-02
        op.setOrder(order);
        Product product = new Product();
        product.setId(1);
        op.setProduct(product);
        op.setQuality(2);
        op.setPrice(new BigDecimal("1000"));
        listPS.add(op);
        when(orderProductRepo.findAll()).thenReturn(listPS);
        logger.info("Dữ liệu chuẩn bị: ngayBD = 2021-07-01, ngayKT = 2021-07-31, 1 đơn hàng (status=1)");

        List<ThongKe> result = thongKeService.thongKeTheoTime(ngayBD, ngayKT);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách ThongKe phải rỗng");
        verify(orderProductRepo, times(1)).findAll();
        logger.info("Kết quả TC_TK_14 - testThongKeTheoTime_WrongStatus: Kết thúc test case.");
    }

    // TC_TK_15 - testThongKeTheoTime_MultipleProductsSameId: Multiple order products with the same product ID
    @Test
    public void testThongKeTheoTime_MultipleProductsSameId() throws ParseException {
        logger.info("Bắt đầu TC_TK_15 - testThongKeTheoTime_MultipleProductsSameId: Kiểm tra với nhiều đơn hàng có cùng sản phẩm.");
        Long ngayBD = 1625097600000L; // 2021-07-01
        Long ngayKT = 1627689600000L; // 2021-07-31
        List<OrderProducts> listPS = new ArrayList<>();
        OrderProducts op1 = new OrderProducts();
        Order order1 = new Order();
        order1.setStatus(2);
        order1.setUpdated_date(new Date(1625184000000L)); // 2021-07-02
        op1.setOrder(order1);
        Product product = new Product();
        product.setId(1);
        op1.setProduct(product);
        op1.setQuality(2);
        op1.setPrice(new BigDecimal("1000"));
        OrderProducts op2 = new OrderProducts();
        Order order2 = new Order();
        order2.setStatus(2);
        order2.setUpdated_date(new Date(1625270400000L)); // 2021-07-03
        op2.setOrder(order2);
        op2.setProduct(product);
        op2.setQuality(3);
        op2.setPrice(new BigDecimal("1000"));
        listPS.add(op1);
        listPS.add(op2);
        when(orderProductRepo.findAll()).thenReturn(listPS);
        logger.info("Dữ liệu chuẩn bị: ngayBD = 2021-07-01, ngayKT = 2021-07-31, 2 đơn hàng (product.id=1, quality=2/3, price=1000)");

        List<ThongKe> result = thongKeService.thongKeTheoTime(ngayBD, ngayKT);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 ThongKe");
        assertEquals(5, result.get(0).getTongSoLuong(), "Tổng số lượng phải là 5");
        assertEquals(new BigDecimal("5000"), result.get(0).getTongGia(), "Tổng giá phải là 5000");
        verify(orderProductRepo, times(1)).findAll();
        logger.info("Kết quả TC_TK_15 - testThongKeTheoTime_MultipleProductsSameId: Kết thúc test case.");
    }

    // TC_TK_16 - testThongKeTheoTime_MultipleProductsDifferentIds: Multiple order products with different product IDs
    @Test
    public void testThongKeTheoTime_MultipleProductsDifferentIds() throws ParseException {
        logger.info("Bắt đầu TC_TK_16 - testThongKeTheoTime_MultipleProductsDifferentIds: Kiểm tra với nhiều đơn hàng có sản phẩm khác nhau.");
        Long ngayBD = 1625097600000L; // 2021-07-01
        Long ngayKT = 1627689600000L; // 2021-07-31
        List<OrderProducts> listPS = new ArrayList<>();
        OrderProducts op1 = new OrderProducts();
        Order order1 = new Order();
        order1.setStatus(2);
        order1.setUpdated_date(new Date(1625184000000L)); // 2021-07-02
        op1.setOrder(order1);
        Product p1 = new Product();
        p1.setId(1);
        op1.setProduct(p1);
        op1.setQuality(2);
        op1.setPrice(new BigDecimal("1000"));
        OrderProducts op2 = new OrderProducts();
        Order order2 = new Order();
        order2.setStatus(2);
        order2.setUpdated_date(new Date(1625270400000L)); // 2021-07-03
        op2.setOrder(order2);
        Product p2 = new Product();
        p2.setId(2);
        op2.setProduct(p2);
        op2.setQuality(3);
        op2.setPrice(new BigDecimal("2000"));
        listPS.add(op1);
        listPS.add(op2);
        when(orderProductRepo.findAll()).thenReturn(listPS);
        logger.info("Dữ liệu chuẩn bị: ngayBD = 2021-07-01, ngayKT = 2021-07-31, 2 đơn hàng (product.id=1/2, quality=2/3, price=1000/2000)");

        List<ThongKe> result = thongKeService.thongKeTheoTime(ngayBD, ngayKT);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 ThongKe");
        assertEquals(2, result.get(0).getTongSoLuong(), "Tổng số lượng sản phẩm 1 phải là 2");
        assertEquals(new BigDecimal("2000"), result.get(0).getTongGia(), "Tổng giá sản phẩm 1 phải là 2000");
        assertEquals(3, result.get(1).getTongSoLuong(), "Tổng số lượng sản phẩm 2 phải là 3");
        assertEquals(new BigDecimal("6000"), result.get(1).getTongGia(), "Tổng giá sản phẩm 2 phải là 6000");
        verify(orderProductRepo, times(1)).findAll();
        logger.info("Kết quả TC_TK_16 - testThongKeTheoTime_MultipleProductsDifferentIds: Kết thúc test case.");
    }

    // TC_TK_17 - testThongKeTheoTime_NullPrice: Order product with null price
    @Test
    public void testThongKeTheoTime_NullPrice() throws ParseException {
        logger.info("Bắt đầu TC_TK_17 - testThongKeTheoTime_NullPrice: Kiểm tra với đơn hàng có price là null.");
        Long ngayBD = 1625097600000L; // 2021-07-01
        Long ngayKT = 1627689600000L; // 2021-07-31
        List<OrderProducts> listPS = new ArrayList<>();
        OrderProducts op = new OrderProducts();
        Order order = new Order();
        order.setStatus(2);
        order.setUpdated_date(new Date(1625184000000L)); // 2021-07-02
        op.setOrder(order);
        Product product = new Product();
        product.setId(1);
        op.setProduct(product);
        op.setQuality(2);
        op.setPrice(null);
        listPS.add(op);
        when(orderProductRepo.findAll()).thenReturn(listPS);
        logger.info("Dữ liệu chuẩn bị: ngayBD = 2021-07-01, ngayKT = 2021-07-31, 1 đơn hàng (price=null)");

        assertThrows(NullPointerException.class, () -> thongKeService.thongKeTheoTime(ngayBD, ngayKT),
                "Phải ném NullPointerException khi price là null");

        verify(orderProductRepo, times(1)).findAll();
        logger.info("Kết quả TC_TK_17 - testThongKeTheoTime_NullPrice: Kết thúc test case.");
    }

    // TC_TK_18 - testThongKeTheoTime_NullUpdatedDate: Order with null updated_date
    @Test
    public void testThongKeTheoTime_NullUpdatedDate() throws ParseException {
        logger.info("Bắt đầu TC_TK_18 - testThongKeTheoTime_NullUpdatedDate: Kiểm tra với đơn hàng có updated_date là null.");
        Long ngayBD = 1625097600000L; // 2021-07-01
        Long ngayKT = 1627689600000L; // 2021-07-31
        List<OrderProducts> listPS = new ArrayList<>();
        OrderProducts op = new OrderProducts();
        Order order = new Order();
        order.setStatus(2);
        order.setUpdated_date(null);
        op.setOrder(order);
        Product product = new Product();
        product.setId(1);
        op.setProduct(product);
        op.setQuality(2);
        op.setPrice(new BigDecimal("1000"));
        listPS.add(op);
        when(orderProductRepo.findAll()).thenReturn(listPS);
        logger.info("Dữ liệu chuẩn bị: ngayBD = 2021-07-01, ngayKT = 2021-07-31, 1 đơn hàng (updated_date=null)");

        assertThrows(NullPointerException.class, () -> thongKeService.thongKeTheoTime(ngayBD, ngayKT),
                "Phải ném NullPointerException khi updated_date là null");

        verify(orderProductRepo, times(1)).findAll();
        logger.info("Kết quả TC_TK_18 - testThongKeTheoTime_NullUpdatedDate: Kết thúc test case.");
    }

    // TC_TK_19 - testThongKeTheoTime_LargeNumberOfOrders: Large number of order products
    @Test
    public void testThongKeTheoTime_LargeNumberOfOrders() throws ParseException {
        logger.info("Bắt đầu TC_TK_19 - testThongKeTheoTime_LargeNumberOfOrders: Kiểm tra với số lượng lớn đơn hàng.");
        Long ngayBD = 1625097600000L; // 2021-07-01
        Long ngayKT = 1627689600000L; // 2021-07-31
        List<OrderProducts> listPS = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            OrderProducts op = new OrderProducts();
            Order order = new Order();
            order.setStatus(2);
            order.setUpdated_date(new Date(1625184000000L)); // 2021-07-02
            op.setOrder(order);
            Product product = new Product();
            product.setId(i);
            op.setProduct(product);
            op.setQuality(1);
            op.setPrice(new BigDecimal("1000"));
            listPS.add(op);
        }
        when(orderProductRepo.findAll()).thenReturn(listPS);
        logger.info("Dữ liệu chuẩn bị: ngayBD = 2021-07-01, ngayKT = 2021-07-31, 100 đơn hàng (quality=1, price=1000)");

        List<ThongKe> result = thongKeService.thongKeTheoTime(ngayBD, ngayKT);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(100, result.size(), "Phải trả về 100 ThongKe");
        assertEquals(1, result.get(0).getTongSoLuong(), "Tổng số lượng mỗi sản phẩm phải là 1");
        assertEquals(new BigDecimal("1000"), result.get(0).getTongGia(), "Tổng giá mỗi sản phẩm phải là 1000");
        verify(orderProductRepo, times(1)).findAll();
        logger.info("Kết quả TC_TK_19 - testThongKeTheoTime_LargeNumberOfOrders: Kết thúc test case.");
    }

    // TC_TK_20 - testThongKeTheoTime_RepoThrowsException: OrderProductRepo throws an exception
    @Test
    public void testThongKeTheoTime_RepoThrowsException() throws ParseException {
        logger.info("Bắt đầu TC_TK_20 - testThongKeTheoTime_RepoThrowsException: Kiểm tra khi repo ném ngoại lệ.");
        Long ngayBD = 1625097600000L; // 2021-07-01
        Long ngayKT = 1627689600000L; // 2021-07-31
        when(orderProductRepo.findAll()).thenThrow(new RuntimeException("Database error"));
        logger.info("Dữ liệu chuẩn bị: ngayBD = 2021-07-01, ngayKT = 2021-07-31, repo ném ngoại lệ");

        assertThrows(RuntimeException.class, () -> thongKeService.thongKeTheoTime(ngayBD, ngayKT),
                "Phải ném RuntimeException khi repo thất bại");

        verify(orderProductRepo, times(1)).findAll();
        logger.info("Kết quả TC_TK_20 - testThongKeTheoTime_RepoThrowsException: Kết thúc test case.");
    }
}
