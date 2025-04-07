package com.devpro.services;

import com.devpro.entities.Order;
import com.devpro.entities.OrderProducts;
import com.devpro.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.jpa.JpaSystemException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Lớp kiểm thử đơn vị cho phương thức searchProduct của OrderProductService.
 */
@ExtendWith(MockitoExtension.class)
public class OrderProductServiceSearchProductTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private OrderProductService orderProductService;

    /**
     * TC_OP_31 - Kiểm tra phương thức searchProduct khi id âm.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi id không hợp lệ.
     */
    @Test
    public void testSearchProductWithNegativeId() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(OrderProducts.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_31: id âm");
        int negativeId = -5;
        List<OrderProducts> resultList = orderProductService.searchProduct(negativeId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " sản phẩm");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi id âm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(OrderProducts.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_31: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_32 - Kiểm tra phương thức searchProduct khi id không tồn tại.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi id không có trong hệ thống.
     */
    @Test
    public void testSearchProductWithNonExistentId() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(OrderProducts.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_32: id không tồn tại");
        int nonExistentId = 9999;
        List<OrderProducts> resultList = orderProductService.searchProduct(nonExistentId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " sản phẩm");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi id không tồn tại");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(OrderProducts.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_32: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_33 - Kiểm tra phương thức searchProduct khi đơn hàng không có sản phẩm.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi không có sản phẩm nào.
     */
    @Test
    public void testSearchProductWithNoProducts() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(OrderProducts.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_33: Đơn hàng không có sản phẩm");
        int orderId = 1;
        List<OrderProducts> resultList = orderProductService.searchProduct(orderId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " sản phẩm");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi đơn hàng không có sản phẩm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(OrderProducts.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_33: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_34 - Kiểm tra phương thức searchProduct với id hợp lệ.
     * Mục tiêu: Đảm bảo trả về đúng danh sách sản phẩm của đơn hàng.
     */
    @Test
    public void testSearchProductWithValidId() {
        // Sắp xếp dữ liệu giả lập
        List<OrderProducts> mockProductList = new ArrayList<>();
        Order order = new Order();
        order.setId(1);

        Product product1 = new Product();
        product1.setId(101);
        product1.setPrice(new BigDecimal("500"));

        Product product2 = new Product();
        product2.setId(102);
        product2.setPrice(new BigDecimal("1000"));

        OrderProducts orderProduct1 = new OrderProducts();
        orderProduct1.setId(1);
        orderProduct1.setOrder(order);
        orderProduct1.setProduct(product1);
        orderProduct1.setQuality(2);
        orderProduct1.setPrice(new BigDecimal("500"));

        OrderProducts orderProduct2 = new OrderProducts();
        orderProduct2.setId(2);
        orderProduct2.setOrder(order);
        orderProduct2.setProduct(product2);
        orderProduct2.setQuality(1);
        orderProduct2.setPrice(new BigDecimal("1000"));

        mockProductList.add(orderProduct1);
        mockProductList.add(orderProduct2);
        when(entityManager.createNativeQuery(anyString(), eq(OrderProducts.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_34: id hợp lệ");
        int orderId = 1;
        List<OrderProducts> resultList = orderProductService.searchProduct(orderId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " sản phẩm");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 sản phẩm cho đơn hàng id = 1");
        assertEquals(2, resultList.get(0).getQuality(), "Số lượng sản phẩm 1 phải là 2");
        assertEquals(new BigDecimal("1000"), resultList.get(1).getPrice(), "Giá sản phẩm 2 phải là 1000");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(OrderProducts.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_34: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_35 - Kiểm tra phương thức searchProduct với số lượng âm.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (quality âm).
     */
    @Test
    public void testSearchProductWithNegativeQuality() {
        // Sắp xếp dữ liệu giả lập
        List<OrderProducts> mockProductList = new ArrayList<>();
        Order order = new Order();
        order.setId(1);

        Product product = new Product();
        product.setId(101);
        product.setPrice(new BigDecimal("500"));

        OrderProducts orderProduct = new OrderProducts();
        orderProduct.setId(1);
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuality(-10); // Số lượng âm (dữ liệu lỗi)
        orderProduct.setPrice(new BigDecimal("500"));

        mockProductList.add(orderProduct);
        when(entityManager.createNativeQuery(anyString(), eq(OrderProducts.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_35: Số lượng âm");
        int orderId = 1;
        List<OrderProducts> resultList = orderProductService.searchProduct(orderId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " sản phẩm");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 sản phẩm");
        assertEquals(-10, resultList.get(0).getQuality(), "Số lượng sản phẩm phải là -10");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(OrderProducts.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_35: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_36 - Kiểm tra phương thức searchProduct với giá âm.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (price âm).
     */
    @Test
    public void testSearchProductWithNegativePrice() {
        // Sắp xếp dữ liệu giả lập
        List<OrderProducts> mockProductList = new ArrayList<>();
        Order order = new Order();
        order.setId(1);

        Product product = new Product();
        product.setId(101);
        product.setPrice(new BigDecimal("500"));

        OrderProducts orderProduct = new OrderProducts();
        orderProduct.setId(1);
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuality(2);
        orderProduct.setPrice(new BigDecimal("-100")); // Giá âm (dữ liệu lỗi)

        mockProductList.add(orderProduct);
        when(entityManager.createNativeQuery(anyString(), eq(OrderProducts.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_36: Giá âm");
        int orderId = 1;
        List<OrderProducts> resultList = orderProductService.searchProduct(orderId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " sản phẩm");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 sản phẩm");
        assertEquals(new BigDecimal("-100"), resultList.get(0).getPrice(), "Giá sản phẩm phải là -100");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(OrderProducts.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_36: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_37 - Kiểm tra phương thức searchProduct khi product_id không tồn tại.
     * Mục tiêu: Đảm bảo không crash khi product_id không có trong tbl_product.
     */
    @Test
    public void testSearchProductWithNonExistentProduct() {
        // Sắp xếp dữ liệu giả lập
        List<OrderProducts> mockProductList = new ArrayList<>();
        Order order = new Order();
        order.setId(1);

        OrderProducts orderProduct = new OrderProducts();
        orderProduct.setId(1);
        orderProduct.setOrder(order);
        orderProduct.setProduct(null); // product_id không tồn tại
        orderProduct.setQuality(2);
        orderProduct.setPrice(new BigDecimal("500"));

        mockProductList.add(orderProduct);
        when(entityManager.createNativeQuery(anyString(), eq(OrderProducts.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_37: product_id không tồn tại");
        int orderId = 1;
        List<OrderProducts> resultList = orderProductService.searchProduct(orderId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " sản phẩm");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 sản phẩm");
        assertNull(resultList.get(0).getProduct(), "Sản phẩm phải có product = null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(OrderProducts.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_37: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_38 - Kiểm tra phương thức searchProduct với số lượng sản phẩm lớn.
     * Mục tiêu: Đảm bảo hiệu suất tốt khi có nhiều sản phẩm.
     */
    @Test
    public void testSearchProductWithLargeData() {
        // Sắp xếp dữ liệu giả lập
        List<OrderProducts> mockProductList = new ArrayList<>();
        Order order = new Order();
        order.setId(1);

        Product product = new Product();
        product.setId(101);
        product.setPrice(new BigDecimal("500"));

        for (int i = 0; i < 10000; i++) {
            OrderProducts orderProduct = new OrderProducts();
            orderProduct.setId(i + 1);
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuality(1);
            orderProduct.setPrice(new BigDecimal("500"));
            mockProductList.add(orderProduct);
        }
        when(entityManager.createNativeQuery(anyString(), eq(OrderProducts.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_38: Dữ liệu lớn");
        int orderId = 1;
        long startTime = System.currentTimeMillis();
        List<OrderProducts> resultList = orderProductService.searchProduct(orderId);
        long endTime = System.currentTimeMillis();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " sản phẩm");
        System.out.println("Thời gian thực thi: " + (endTime - startTime) + "ms");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(10000, resultList.size(), "Phải trả về 10,000 sản phẩm");
        assertTrue((endTime - startTime) < 1000, "Hiệu suất phải dưới 1 giây");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(OrderProducts.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_38: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_39 - Kiểm tra phương thức searchProduct khi truy vấn DB gặp lỗi.
     * Mục tiêu: Đảm bảo ném ngoại lệ khi DB thất bại.
     */
    @Test
    public void testSearchProductWithDatabaseError() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(OrderProducts.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new JpaSystemException(new RuntimeException("Lỗi DB")));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_39: Lỗi cơ sở dữ liệu");
        int orderId = 1;
        assertThrows(JpaSystemException.class, () -> orderProductService.searchProduct(orderId), "Phải ném ngoại lệ khi DB lỗi");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(OrderProducts.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_39: Thành công");
        // Không cần rollback vì không thay đổi dữ liệu
    }

    /**
     * TC_OP_40 - Kiểm tra phương thức searchProduct khi giá không khớp.
     * Mục tiêu: Đảm bảo phát hiện giá trong tbl_order_product không khớp với tbl_product.
     */
    @Test
    public void testSearchProductWithPriceMismatch() {
        // Sắp xếp dữ liệu giả lập
        List<OrderProducts> mockProductList = new ArrayList<>();
        Order order = new Order();
        order.setId(1);

        Product product = new Product();
        product.setId(101);
        product.setPrice(new BigDecimal("500")); // Giá trong tbl_product

        OrderProducts orderProduct = new OrderProducts();
        orderProduct.setId(1);
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuality(2);
        orderProduct.setPrice(new BigDecimal("600")); // Giá trong tbl_order_product không khớp

        mockProductList.add(orderProduct);
        when(entityManager.createNativeQuery(anyString(), eq(OrderProducts.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_40: Giá không khớp");
        int orderId = 1;
        List<OrderProducts> resultList = orderProductService.searchProduct(orderId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " sản phẩm");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 sản phẩm");
        assertNotEquals(resultList.get(0).getPrice(), resultList.get(0).getProduct().getPrice(), "Giá trong tbl_order_product không khớp với tbl_product");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(OrderProducts.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_40: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }
}