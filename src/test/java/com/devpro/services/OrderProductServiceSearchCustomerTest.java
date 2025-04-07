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
 * Lớp kiểm thử đơn vị cho phương thức searchCustomer của OrderProductService.
 */
@ExtendWith(MockitoExtension.class)
public class OrderProductServiceSearchCustomerTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private OrderProductService orderProductService;

    /**
     * TC_OP_11 - Kiểm tra phương thức searchCustomer khi id là null.
     * Mục tiêu: Đảm bảo xử lý an toàn khi id là null.
     */
    @Test
    public void testSearchCustomerWithNullId() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new IllegalArgumentException("ID khách hàng không được null"));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_11: id là null");
        assertThrows(IllegalArgumentException.class, () -> orderProductService.searchCustomer(null), "Phải ném ngoại lệ khi id là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_11: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_12 - Kiểm tra phương thức searchCustomer khi id âm.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi id không hợp lệ.
     */
    @Test
    public void testSearchCustomerWithNegativeId() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_12: id âm");
        Integer negativeId = -5;
        List<Order> resultList = orderProductService.searchCustomer(negativeId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi id âm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_12: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_13 - Kiểm tra phương thức searchCustomer khi id không tồn tại.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi id không có trong hệ thống.
     */
    @Test
    public void testSearchCustomerWithNonExistentId() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_13: id không tồn tại");
        Integer nonExistentId = 9999;
        List<Order> resultList = orderProductService.searchCustomer(nonExistentId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi id không tồn tại");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_13: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_14 - Kiểm tra phương thức searchCustomer với id hợp lệ.
     * Mục tiêu: Đảm bảo trả về đúng đơn hàng của khách hàng.
     */
    @Test
    public void testSearchCustomerWithValidId() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order order1 = new Order();
        order1.setId(1);
        order1.setUser(customer); // Thiết lập created_by thông qua Customer
        order1.setStatus(1);
        order1.setTotal(new BigDecimal("1000"));
        order1.setUpdated_date(new Date());

        Order order2 = new Order();
        order2.setId(2);
        order2.setUser(customer); // Thiết lập created_by thông qua Customer
        order2.setStatus(2);
        order2.setTotal(new BigDecimal("2000"));
        order2.setUpdated_date(new Date());

        mockOrderList.add(order1);
        mockOrderList.add(order2);
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_14: id hợp lệ");
        Integer validId = 1;
        List<Order> resultList = orderProductService.searchCustomer(validId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 đơn hàng với created_by = 1");
        assertTrue(resultList.stream().allMatch(o -> o.getUser().getId() == 1), "Tất cả đơn hàng phải thuộc id = 1");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_14: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_15 - Kiểm tra phương thức searchCustomer với đơn hàng có total âm.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (total âm).
     */
    @Test
    public void testSearchCustomerWithInvalidTotal() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order order = new Order();
        order.setId(1);
        order.setUser(customer); // Thiết lập created_by thông qua Customer
        order.setStatus(1);
        order.setTotal(new BigDecimal("-100")); // Total âm (dữ liệu lỗi)
        order.setUpdated_date(new Date());

        mockOrderList.add(order);
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_15: Đơn hàng total âm");
        Integer validId = 1;
        List<Order> resultList = orderProductService.searchCustomer(validId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 đơn hàng");
        assertEquals(new BigDecimal("-100"), resultList.get(0).getTotal(), "Đơn hàng có total âm");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_15: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_16 - Kiểm tra phương thức searchCustomer với id rất lớn.
     * Mục tiêu: Đảm bảo xử lý đúng khi id gần Integer.MAX_VALUE.
     */
    @Test
    public void testSearchCustomerWithMaxId() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_16: id rất lớn");
        Integer maxId = Integer.MAX_VALUE - 1;
        List<Order> resultList = orderProductService.searchCustomer(maxId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng vì id không tồn tại");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_16: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_17 - Kiểm tra phương thức searchCustomer với status không hợp lệ.
     * Mục tiêu: Đảm bảo xử lý đúng khi đơn hàng có status không hợp lệ.
     */
    @Test
    public void testSearchCustomerWithMixedData() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order order = new Order();
        order.setId(1);
        order.setUser(customer); // Thiết lập created_by thông qua Customer
        order.setStatus(-1); // Status không hợp lệ
        order.setTotal(new BigDecimal("1000"));
        order.setUpdated_date(new Date());

        mockOrderList.add(order);
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_17: Status không hợp lệ");
        Integer validId = 1;
        List<Order> resultList = orderProductService.searchCustomer(validId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 đơn hàng");
        assertEquals(-1, resultList.get(0).getStatus(), "Đơn hàng có status không hợp lệ");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_17: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_18 - Kiểm tra phương thức searchCustomer khi có truy cập đồng thời.
     * Mục tiêu: Đảm bảo không crash khi DB trả về dữ liệu không đồng bộ.
     */
    @Test
    public void testSearchCustomerWithConcurrentAccess() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList1 = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order order1 = new Order();
        order1.setId(1);
        order1.setUser(customer); // Thiết lập created_by thông qua Customer
        order1.setStatus(1);
        order1.setTotal(new BigDecimal("1000"));
        order1.setUpdated_date(new Date());
        mockOrderList1.add(order1);

        List<Order> mockOrderList2 = new ArrayList<>(); // Dữ liệu thay đổi (giả lập đồng thời)
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList1).thenReturn(mockOrderList2);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_18: Truy cập đồng thời");
        Integer validId = 1;
        List<Order> resultList = orderProductService.searchCustomer(validId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 đơn hàng (lần gọi đầu)");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_18: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_19 - Kiểm tra phương thức searchCustomer khi DB timeout.
     * Mục tiêu: Đảm bảo xử lý đúng khi DB timeout.
     */
    @Test
    public void testSearchCustomerWithDatabaseTimeout() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new JpaSystemException(new RuntimeException("Timeout DB")));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_19: Timeout cơ sở dữ liệu");
        Integer validId = 1;
        assertThrows(JpaSystemException.class, () -> orderProductService.searchCustomer(validId), "Phải ném ngoại lệ khi DB timeout");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_19: Thành công");
        // Không cần rollback vì không thay đổi dữ liệu
    }

    /**
     * TC_OP_20 - Kiểm tra phương thức searchCustomer với nhiều trạng thái đơn hàng.
     * Mục tiêu: Đảm bảo trả về tất cả đơn hàng của id, không lọc status.
     */
    @Test
    public void testSearchCustomerWithMultipleStatuses() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order order1 = new Order();
        order1.setId(1);
        order1.setUser(customer); // Thiết lập created_by thông qua Customer
        order1.setStatus(0); // Đang chờ xác nhận
        order1.setTotal(new BigDecimal("1000"));
        order1.setUpdated_date(new Date());

        Order order2 = new Order();
        order2.setId(2);
        order2.setUser(customer); // Thiết lập created_by thông qua Customer
        order2.setStatus(1); // Đang giao hàng
        order2.setTotal(new BigDecimal("2000"));
        order2.setUpdated_date(new Date());

        Order order3 = new Order();
        order3.setId(3);
        order3.setUser(customer); // Thiết lập created_by thông qua Customer
        order3.setStatus(3); // Đã hủy
        order3.setTotal(new BigDecimal("3000"));
        order3.setUpdated_date(new Date());

        mockOrderList.add(order1);
        mockOrderList.add(order2);
        mockOrderList.add(order3);
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_20: Nhiều trạng thái đơn hàng");
        Integer validId = 1;
        List<Order> resultList = orderProductService.searchCustomer(validId);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(3, resultList.size(), "Phải trả về 3 đơn hàng với created_by = 1");
        assertTrue(resultList.stream().anyMatch(o -> o.getStatus() == 0), "Phải chứa đơn hàng status = 0");
        assertTrue(resultList.stream().anyMatch(o -> o.getStatus() == 1), "Phải chứa đơn hàng status = 1");
        assertTrue(resultList.stream().anyMatch(o -> o.getStatus() == 3), "Phải chứa đơn hàng status = 3");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_20: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }
}