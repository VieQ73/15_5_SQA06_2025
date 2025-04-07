package com.devpro.services;

import com.devpro.entities.Customer;
import com.devpro.entities.Order;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Lớp kiểm thử đơn vị cho phương thức findOrderByStatus của OrderProductService.
 */
@ExtendWith(MockitoExtension.class)
public class OrderProductServiceFindOrderByStatusTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private OrderProductService orderProductService;

    /**
     * TC_OP_41 - Kiểm tra phương thức findOrderByStatus khi status là null.
     * Mục tiêu: Đảm bảo xử lý an toàn khi status là null.
     */
    @Test
    public void testFindOrderByStatusWithNullStatus() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new IllegalArgumentException("Status không được null"));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_41: Status là null");
        assertThrows(IllegalArgumentException.class, () -> orderProductService.findOrderByStatus(null), "Phải ném ngoại lệ khi status là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_41: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_42 - Kiểm tra phương thức findOrderByStatus khi status âm.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi status không hợp lệ.
     */
    @Test
    public void testFindOrderByStatusWithNegativeStatus() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_42: Status âm");
        Integer negativeStatus = -1;
        List<Order> resultList = orderProductService.findOrderByStatus(negativeStatus);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi status âm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_42: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_43 - Kiểm tra phương thức findOrderByStatus khi status ngoài phạm vi hợp lệ.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi status không hợp lệ.
     */
    @Test
    public void testFindOrderByStatusWithInvalidStatus() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_43: Status ngoài phạm vi hợp lệ");
        Integer invalidStatus = 4;
        List<Order> resultList = orderProductService.findOrderByStatus(invalidStatus);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi status không hợp lệ");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_43: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_44 - Kiểm tra phương thức findOrderByStatus với status hợp lệ.
     * Mục tiêu: Đảm bảo trả về đúng danh sách đơn hàng với status hợp lệ.
     */
    @Test
    public void testFindOrderByStatusWithValidStatus() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order order1 = new Order();
        order1.setId(1);
        order1.setUser(customer);
        order1.setStatus(1); // Đang giao hàng
        order1.setTotal(new BigDecimal("1000"));
        order1.setCreatedDate(new Date());
        order1.setUpdated_date(new Date());

        Order order2 = new Order();
        order2.setId(2);
        order2.setUser(customer);
        order2.setStatus(1); // Đang giao hàng
        order2.setTotal(new BigDecimal("2000"));
        order2.setCreatedDate(new Date());
        order2.setUpdated_date(new Date());

        mockOrderList.add(order1);
        mockOrderList.add(order2);
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_44: Status hợp lệ");
        Integer status = 1;
        List<Order> resultList = orderProductService.findOrderByStatus(status);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 đơn hàng với status = 1");
        assertTrue(resultList.stream().allMatch(o -> o.getStatus() == 1), "Tất cả đơn hàng phải có status = 1");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_44: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_45 - Kiểm tra phương thức findOrderByStatus khi không có đơn hàng với status đó.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi không có đơn hàng khớp status.
     */
    @Test
    public void testFindOrderByStatusWithNoMatchingStatus() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_45: Không có đơn hàng với status đó");
        Integer status = 2;
        List<Order> resultList = orderProductService.findOrderByStatus(status);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi không có đơn hàng với status = 2");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_45: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_46 - Kiểm tra phương thức findOrderByStatus với đơn hàng có total âm.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (total âm).
     */
    @Test
    public void testFindOrderByStatusWithNegativeTotal() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order order = new Order();
        order.setId(1);
        order.setUser(customer);
        order.setStatus(1);
        order.setTotal(new BigDecimal("-100")); // Total âm (dữ liệu lỗi)
        order.setCreatedDate(new Date());
        order.setUpdated_date(new Date());

        mockOrderList.add(order);
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_46: Đơn hàng total âm");
        Integer status = 1;
        List<Order> resultList = orderProductService.findOrderByStatus(status);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 đơn hàng");
        assertEquals(new BigDecimal("-100"), resultList.get(0).getTotal(), "Đơn hàng có total âm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_46: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_47 - Kiểm tra phương thức findOrderByStatus với đơn hàng có created_date null.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (created_date null).
     */
    @Test
    public void testFindOrderByStatusWithNullCreatedDate() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order order = new Order();
        order.setId(1);
        order.setUser(customer);
        order.setStatus(1);
        order.setTotal(new BigDecimal("1000"));
        order.setCreatedDate(null); // created_date null (dữ liệu lỗi)
        order.setUpdated_date(new Date());

        mockOrderList.add(order);
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_47: Đơn hàng created_date null");
        Integer status = 1;
        List<Order> resultList = orderProductService.findOrderByStatus(status);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 đơn hàng");
        assertNull(resultList.get(0).getCreatedDate(), "Đơn hàng có created_date null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_47: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_48 - Kiểm tra phương thức findOrderByStatus với số lượng đơn hàng lớn.
     * Mục tiêu: Đảm bảo hiệu suất tốt khi có nhiều đơn hàng.
     */
    @Test
    public void testFindOrderByStatusWithLargeData() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        for (int i = 0; i < 10000; i++) {
            Order order = new Order();
            order.setId(i + 1);
            order.setUser(customer);
            order.setStatus(1);
            order.setTotal(new BigDecimal("1000"));
            order.setCreatedDate(new Date());
            order.setUpdated_date(new Date());
            mockOrderList.add(order);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_48: Dữ liệu lớn");
        Integer status = 1;
        long startTime = System.currentTimeMillis();
        List<Order> resultList = orderProductService.findOrderByStatus(status);
        long endTime = System.currentTimeMillis();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        System.out.println("Thời gian thực thi: " + (endTime - startTime) + "ms");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(10000, resultList.size(), "Phải trả về 10,000 đơn hàng");
        assertTrue((endTime - startTime) < 1000, "Hiệu suất phải dưới 1 giây");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_48: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_49 - Kiểm tra phương thức findOrderByStatus khi truy vấn DB gặp lỗi.
     * Mục tiêu: Đảm bảo ném ngoại lệ khi DB thất bại.
     */
    @Test
    public void testFindOrderByStatusWithDatabaseError() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new JpaSystemException(new RuntimeException("Lỗi DB")));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_49: Lỗi cơ sở dữ liệu");
        Integer status = 1;
        assertThrows(JpaSystemException.class, () -> orderProductService.findOrderByStatus(status), "Phải ném ngoại lệ khi DB lỗi");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_49: Thành công");
        // Không cần rollback vì không thay đổi dữ liệu
    }

    /**
     * TC_OP_50 - Kiểm tra phương thức findOrderByStatus với status ở giá trị biên.
     * Mục tiêu: Đảm bảo trả về đúng đơn hàng với status = 0 và status = 3.
     */
    @Test
    public void testFindOrderByStatusWithBoundaryStatus() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderListStatus0 = new ArrayList<>();
        List<Order> mockOrderListStatus3 = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order order1 = new Order();
        order1.setId(1);
        order1.setUser(customer);
        order1.setStatus(0); // Đang chờ xác nhận
        order1.setTotal(new BigDecimal("1000"));
        order1.setCreatedDate(new Date());
        order1.setUpdated_date(new Date());

        Order order2 = new Order();
        order2.setId(2);
        order2.setUser(customer);
        order2.setStatus(3); // Đã hủy
        order2.setTotal(new BigDecimal("2000"));
        order2.setCreatedDate(new Date());
        order2.setUpdated_date(new Date());

        mockOrderListStatus0.add(order1);
        mockOrderListStatus3.add(order2);

        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList())
                .thenReturn(mockOrderListStatus0) // Lần gọi đầu tiên cho status = 0
                .thenReturn(mockOrderListStatus3); // Lần gọi thứ hai cho status = 3

        // Thực hiện kiểm thử cho status = 0
        System.out.println("Bắt đầu kiểm tra TC_OP_50: Status biên (0 và 3)");
        Integer status0 = 0;
        List<Order> resultListStatus0 = orderProductService.findOrderByStatus(status0);

        // Kiểm tra kết quả cho status = 0
        System.out.println("Kết quả trả về cho status = 0: " + resultListStatus0.size() + " đơn hàng");
        assertNotNull(resultListStatus0, "Danh sách kết quả không được null");
        assertEquals(1, resultListStatus0.size(), "Phải trả về 1 đơn hàng với status = 0");
        assertEquals(0, resultListStatus0.get(0).getStatus(), "Đơn hàng phải có status = 0");

        // Thực hiện kiểm thử cho status = 3
        Integer status3 = 3;
        List<Order> resultListStatus3 = orderProductService.findOrderByStatus(status3);

        // Kiểm tra kết quả cho status = 3
        System.out.println("Kết quả trả về cho status = 3: " + resultListStatus3.size() + " đơn hàng");
        assertNotNull(resultListStatus3, "Danh sách kết quả không được null");
        assertEquals(1, resultListStatus3.size(), "Phải trả về 1 đơn hàng với status = 3");
        assertEquals(3, resultListStatus3.get(0).getStatus(), "Đơn hàng phải có status = 3");

        verify(entityManager, times(2)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_50: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }
}