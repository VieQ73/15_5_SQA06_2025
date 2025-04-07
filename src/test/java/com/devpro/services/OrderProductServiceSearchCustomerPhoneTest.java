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
 * Lớp kiểm thử đơn vị cho phương thức searchCustomerPhone của OrderProductService.
 */
@ExtendWith(MockitoExtension.class)
public class OrderProductServiceSearchCustomerPhoneTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private OrderProductService orderProductService;

    /**
     * TC_OP_21 - Kiểm tra phương thức searchCustomerPhone khi số điện thoại là null.
     * Mục tiêu: Đảm bảo xử lý an toàn khi phone là null.
     */
    @Test
    public void testSearchCustomerPhoneWithNullPhone() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new IllegalArgumentException("Số điện thoại không được null"));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_21: Số điện thoại là null");
        assertThrows(IllegalArgumentException.class, () -> orderProductService.searchCustomerPhone(null, 1), "Phải ném ngoại lệ khi phone là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_21: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_22 - Kiểm tra phương thức searchCustomerPhone khi số điện thoại rỗng.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi phone rỗng.
     */
    @Test
    public void testSearchCustomerPhoneWithEmptyPhone() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_22: Số điện thoại rỗng");
        String emptyPhone = "";
        int status = 1;
        List<Order> resultList = orderProductService.searchCustomerPhone(emptyPhone, status);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi phone rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_22: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_23 - Kiểm tra phương thức searchCustomerPhone khi số điện thoại không hợp lệ.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi phone chứa ký tự đặc biệt.
     */
    @Test
    public void testSearchCustomerPhoneWithInvalidPhone() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_23: Số điện thoại không hợp lệ");
        String invalidPhone = "abc!@#";
        int status = 1;
        List<Order> resultList = orderProductService.searchCustomerPhone(invalidPhone, status);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi phone không hợp lệ");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_23: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_24 - Kiểm tra phương thức searchCustomerPhone khi số điện thoại không tồn tại.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi không có khách hàng với phone đó.
     */
    @Test
    public void testSearchCustomerPhoneWithNonExistentPhone() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_24: Số điện thoại không tồn tại");
        String nonExistentPhone = "0123456789";
        int status = 1;
        List<Order> resultList = orderProductService.searchCustomerPhone(nonExistentPhone, status);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi phone không tồn tại");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_24: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_25 - Kiểm tra phương thức searchCustomerPhone với status không hợp lệ.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi status âm.
     */
    @Test
    public void testSearchCustomerPhoneWithInvalidStatus() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_25: Status không hợp lệ");
        String phone = "0987654321";
        int invalidStatus = -1;
        List<Order> resultList = orderProductService.searchCustomerPhone(phone, invalidStatus);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi status không hợp lệ");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_25: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_26 - Kiểm tra phương thức searchCustomerPhone với phone và status hợp lệ.
     * Mục tiêu: Đảm bảo trả về đúng đơn hàng của khách hàng.
     */
    @Test
    public void testSearchCustomerPhoneWithValidInput() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order order1 = new Order();
        order1.setId(1);
        order1.setUser(customer); // Thiết lập created_by thông qua Customer
        order1.setStatus(1); // Đang giao hàng
        order1.setTotal(new BigDecimal("1000"));
        order1.setUpdated_date(new Date());

        Order order2 = new Order();
        order2.setId(2);
        order2.setUser(customer); // Thiết lập created_by thông qua Customer
        order2.setStatus(1); // Đang giao hàng
        order2.setTotal(new BigDecimal("2000"));
        order2.setUpdated_date(new Date());

        mockOrderList.add(order1);
        mockOrderList.add(order2);
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_26: Phone và status hợp lệ");
        String phone = "0987654321";
        int status = 1;
        List<Order> resultList = orderProductService.searchCustomerPhone(phone, status);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 đơn hàng với status = 1");
        assertTrue(resultList.stream().allMatch(o -> o.getStatus() == 1), "Tất cả đơn hàng phải có status = 1");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_26: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_27 - Kiểm tra phương thức searchCustomerPhone khi không có đơn hàng với status đó.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi không có đơn hàng khớp status.
     */
    @Test
    public void testSearchCustomerPhoneWithNoMatchingStatus() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order order = new Order();
        order.setId(1);
        order.setUser(customer); // Thiết lập created_by thông qua Customer
        order.setStatus(1); // Đang giao hàng
        order.setTotal(new BigDecimal("1000"));
        order.setUpdated_date(new Date());

        mockOrderList.add(order);
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_27: Không có đơn hàng với status đó");
        String phone = "0987654321";
        int status = 2; // Đã nhận hàng
        List<Order> resultList = orderProductService.searchCustomerPhone(phone, status);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi không có đơn hàng với status = 2");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_27: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_28 - Kiểm tra phương thức searchCustomerPhone với sắp xếp theo updated_date.
     * Mục tiêu: Đảm bảo đơn hàng được sắp xếp theo updated_date giảm dần.
     */
    @Test
    public void testSearchCustomerPhoneWithSortedUpdatedDate() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        Order olderOrder = new Order();
        olderOrder.setId(1);
        olderOrder.setUser(customer); // Thiết lập created_by thông qua Customer
        olderOrder.setStatus(1);
        olderOrder.setTotal(new BigDecimal("1000"));
        olderOrder.setUpdated_date(new Date(2023 - 1900, 1, 1));

        Order newerOrder = new Order();
        newerOrder.setId(2);
        newerOrder.setUser(customer); // Thiết lập created_by thông qua Customer
        newerOrder.setStatus(1);
        newerOrder.setTotal(new BigDecimal("2000"));
        newerOrder.setUpdated_date(new Date(2024 - 1900, 1, 1));

        mockOrderList.add(olderOrder);
        mockOrderList.add(newerOrder);
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_28: Sắp xếp theo updated_date");
        String phone = "0987654321";
        int status = 1;
        List<Order> resultList = orderProductService.searchCustomerPhone(phone, status);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 đơn hàng");
        assertTrue(resultList.get(0).getUpdated_date().after(resultList.get(1).getUpdated_date()), "Phải sắp xếp theo updated_date giảm dần");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_28: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_29 - Kiểm tra phương thức searchCustomerPhone khi truy vấn DB gặp lỗi.
     * Mục tiêu: Đảm bảo ném ngoại lệ khi DB thất bại.
     */
    @Test
    public void testSearchCustomerPhoneWithDatabaseError() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new JpaSystemException(new RuntimeException("Lỗi DB")));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_29: Lỗi cơ sở dữ liệu");
        String phone = "0987654321";
        int status = 1;
        assertThrows(JpaSystemException.class, () -> orderProductService.searchCustomerPhone(phone, status), "Phải ném ngoại lệ khi DB lỗi");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_29: Thành công");
        // Không cần rollback vì không thay đổi dữ liệu
    }

    /**
     * TC_OP_30 - Kiểm tra phương thức searchCustomerPhone với dữ liệu lớn.
     * Mục tiêu: Đảm bảo hiệu suất tốt khi có nhiều đơn hàng.
     */
    @Test
    public void testSearchCustomerPhoneWithLargeData() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);

        for (int i = 0; i < 10000; i++) {
            Order order = new Order();
            order.setId(i + 1);
            order.setUser(customer); // Thiết lập created_by thông qua Customer
            order.setStatus(1);
            order.setTotal(new BigDecimal("1000"));
            order.setUpdated_date(new Date());
            mockOrderList.add(order);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_30: Dữ liệu lớn");
        String phone = "0987654321";
        int status = 1;
        long startTime = System.currentTimeMillis();
        List<Order> resultList = orderProductService.searchCustomerPhone(phone, status);
        long endTime = System.currentTimeMillis();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        System.out.println("Thời gian thực thi: " + (endTime - startTime) + "ms");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(10000, resultList.size(), "Phải trả về 10,000 đơn hàng");
        assertTrue((endTime - startTime) < 1000, "Hiệu suất phải dưới 1 giây");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_30: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }
}