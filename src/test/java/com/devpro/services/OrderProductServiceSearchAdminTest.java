package com.devpro.services;

import com.devpro.entities.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.jpa.JpaSystemException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Lớp kiểm thử đơn vị cho phương thức searchAdmin của OrderProductService.
 */
@ExtendWith(MockitoExtension.class)
public class OrderProductServiceSearchAdminTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private OrderProductService orderProductService;

    /**
     * Thiết lập trước mỗi bài kiểm thử.
     * Giả lập hành vi của EntityManager để tạo truy vấn SQL với điều kiện status <> 3.
     */
    @BeforeEach
    public void setUp() {
        when(entityManager.createNativeQuery(anyString(), eq(Order.class))).thenReturn(query);
    }

    /**
     * TC_OP_01 - Kiểm tra phương thức searchAdmin khi saleOrder là null.
     * Mục tiêu: Đảm bảo trả về tất cả đơn hàng có status != 3, sắp xếp theo updated_date giảm dần.
     */
    @Test
    public void testSearchAdminWithNullSaleOrder() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Order validOrder = new Order();
        validOrder.setStatus(1); // Đang giao hàng
        validOrder.setUpdated_date(new Date());
        Order canceledOrder = new Order();
        canceledOrder.setStatus(3); // Đã hủy
        canceledOrder.setUpdated_date(new Date());
        mockOrderList.add(validOrder);
        mockOrderList.add(canceledOrder);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_01: saleOrder là null");
        List<Order> resultList = orderProductService.searchAdmin(null);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Chỉ trả về 1 đơn hàng với status != 3");
        assertEquals(1, resultList.get(0).getStatus(), "Đơn hàng phải có status = 1");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_01: Thành công");
        // Không cần rollback vì đây là phương thức đọc dữ liệu
    }

    /**
     * TC_OP_02 - Kiểm tra phương thức searchAdmin khi cơ sở dữ liệu rỗng.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi không có dữ liệu.
     */
    @Test
    public void testSearchAdminWithEmptyDatabase() {
        // Sắp xếp dữ liệu giả lập
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_02: Cơ sở dữ liệu rỗng");
        Order emptyOrderInput = new Order();
        List<Order> resultList = orderProductService.searchAdmin(emptyOrderInput);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi DB không có dữ liệu");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_02: Thành công");
        // Không cần rollback vì không thay đổi dữ liệu
    }

    /**
     * TC_OP_03 - Kiểm tra phương thức searchAdmin với đơn hàng status = 3.
     * Mục tiêu: Đảm bảo đơn hàng "Đã hủy" (status = 3) bị loại bỏ.
     */
    @Test
    public void testSearchAdminWithStatus3Order() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Order canceledOrder = new Order();
        canceledOrder.setStatus(3); // Đã hủy
        canceledOrder.setUpdated_date(new Date());
        mockOrderList.add(canceledOrder);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_03: Đơn hàng status = 3");
        Order inputOrder = new Order();
        inputOrder.setStatus(3);
        List<Order> resultList = orderProductService.searchAdmin(inputOrder);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Đơn hàng status = 3 phải bị loại bỏ");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_03: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_04 - Kiểm tra phương thức searchAdmin với đơn hàng status != 3.
     * Mục tiêu: Đảm bảo đơn hàng hợp lệ (status = 1) được trả về.
     */
    @Test
    public void testSearchAdminWithStatusNot3() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Order validOrder = new Order();
        validOrder.setStatus(1); // Đang giao hàng
        validOrder.setUpdated_date(new Date());
        mockOrderList.add(validOrder);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_04: Đơn hàng status != 3");
        Order inputOrder = new Order();
        inputOrder.setStatus(1);
        List<Order> resultList = orderProductService.searchAdmin(inputOrder);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 đơn hàng với status = 1");
        assertEquals(1, resultList.get(0).getStatus(), "Đơn hàng phải có status = 1");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_04: Thành công");
        // Không cần rollback vì không thay đổi dữ liệu
    }

    /**
     * TC_OP_05 - Kiểm tra phương thức searchAdmin với status không hợp lệ.
     * Mục tiêu: Đảm bảo xử lý đúng khi status ngoài phạm vi 0-3 (ví dụ: 4).
     */
    @Test
    public void testSearchAdminWithInvalidStatus() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Order invalidOrder = new Order();
        invalidOrder.setStatus(4); // Status không hợp lệ
        invalidOrder.setUpdated_date(new Date());
        mockOrderList.add(invalidOrder);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_05: Status không hợp lệ");
        Order inputOrder = new Order();
        inputOrder.setStatus(4);
        List<Order> resultList = orderProductService.searchAdmin(inputOrder);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Đơn hàng với status không hợp lệ (4) phải bị loại bỏ");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_05: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_06 - Kiểm tra phương thức searchAdmin với dữ liệu lớn.
     * Mục tiêu: Đảm bảo hiệu suất tốt và chỉ trả về đơn hàng status != 3.
     */
    @Test
    public void testSearchAdminWithLargeData() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Order order = new Order();
            order.setStatus(i % 3); // Status 0, 1, 2 luân phiên
            order.setUpdated_date(new Date());
            mockOrderList.add(order);
        }
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_06: Dữ liệu lớn");
        Order inputOrder = new Order();
        long startTime = System.currentTimeMillis();
        List<Order> resultList = orderProductService.searchAdmin(inputOrder);
        long endTime = System.currentTimeMillis();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        System.out.println("Thời gian thực thi: " + (endTime - startTime) + "ms");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.size() > 0, "Phải trả về đơn hàng với status != 3");
        assertTrue(resultList.stream().noneMatch(o -> o.getStatus() == 3), "Không chứa đơn hàng status = 3");
        assertTrue((endTime - startTime) < 1000, "Hiệu suất phải dưới 1 giây");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_06: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_07 - Kiểm tra phương thức searchAdmin với các đơn hàng có ngày cập nhật khác nhau.
     * Mục tiêu: Đảm bảo sắp xếp đúng theo updated_date giảm dần.
     */
    @Test
    public void testSearchAdminWithOldUpdatedDate() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Order olderOrder = new Order();
        olderOrder.setStatus(0); // Đang chờ xác nhận
        olderOrder.setUpdated_date(new Date(2023 - 1900, 1, 1));
        Order newerOrder = new Order();
        newerOrder.setStatus(1); // Đang giao hàng
        newerOrder.setUpdated_date(new Date(2024 - 1900, 1, 1));
        mockOrderList.add(olderOrder);
        mockOrderList.add(newerOrder);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_07: Sắp xếp theo updated_date");
        Order inputOrder = new Order();
        List<Order> resultList = orderProductService.searchAdmin(inputOrder);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 đơn hàng");
        assertTrue(resultList.get(0).getUpdated_date().after(resultList.get(1).getUpdated_date()), "Phải sắp xếp theo updated_date giảm dần");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_07: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_08 - Kiểm tra phương thức searchAdmin khi updated_date là null.
     * Mục tiêu: Đảm bảo xử lý đúng và trả về đơn hàng status != 3.
     */
    @Test
    public void testSearchAdminWithNullUpdatedDate() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Order nullDateOrder = new Order();
        nullDateOrder.setStatus(2); // Đã nhận hàng
        nullDateOrder.setUpdated_date(null);
        mockOrderList.add(nullDateOrder);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_08: updated_date null");
        Order inputOrder = new Order();
        List<Order> resultList = orderProductService.searchAdmin(inputOrder);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 đơn hàng với status != 3");
        assertEquals(2, resultList.get(0).getStatus(), "Đơn hàng phải có status = 2");
        assertNull(resultList.get(0).getUpdated_date(), "Đơn hàng phải có updated_date null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_08: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_OP_09 - Kiểm tra phương thức searchAdmin khi truy vấn DB gặp lỗi.
     * Mục tiêu: Đảm bảo ném ngoại lệ khi DB thất bại.
     */
    @Test
    public void testSearchAdminWithDatabaseError() {
        // Sắp xếp dữ liệu giả lập
        when(query.getResultList()).thenThrow(new JpaSystemException(new RuntimeException("Lỗi DB")));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_09: Lỗi cơ sở dữ liệu");
        Order inputOrder = new Order();
        assertThrows(JpaSystemException.class, () -> orderProductService.searchAdmin(inputOrder), "Phải ném ngoại lệ khi DB lỗi");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_09: Thành công");
        // Không cần rollback vì không thay đổi dữ liệu
    }

    /**
     * TC_OP_10 - Kiểm tra phương thức searchAdmin với nhiều trạng thái khác nhau.
     * Mục tiêu: Đảm bảo chỉ trả về đơn hàng status 0, 1, 2 và sắp xếp theo updated_date giảm dần.
     */
    @Test
    public void testSearchAdminWithMixedStatuses() {
        // Sắp xếp dữ liệu giả lập
        List<Order> mockOrderList = new ArrayList<>();
        Order pendingOrder = new Order();
        pendingOrder.setStatus(0); // Đang chờ xác nhận
        pendingOrder.setUpdated_date(new Date(2024 - 1900, 1, 1));
        Order shippingOrder = new Order();
        shippingOrder.setStatus(1); // Đang giao hàng
        shippingOrder.setUpdated_date(new Date(2024 - 1900, 1, 2));
        Order deliveredOrder = new Order();
        deliveredOrder.setStatus(2); // Đã nhận hàng
        deliveredOrder.setUpdated_date(new Date(2024 - 1900, 1, 3));
        Order canceledOrder = new Order();
        canceledOrder.setStatus(3); // Đã hủy
        canceledOrder.setUpdated_date(new Date(2024 - 1900, 1, 4));
        mockOrderList.add(pendingOrder);
        mockOrderList.add(shippingOrder);
        mockOrderList.add(deliveredOrder);
        mockOrderList.add(canceledOrder);
        when(query.getResultList()).thenReturn(mockOrderList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_OP_10: Nhiều trạng thái");
        Order inputOrder = new Order();
        List<Order> resultList = orderProductService.searchAdmin(inputOrder);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " đơn hàng");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(3, resultList.size(), "Phải trả về 3 đơn hàng với status 0, 1, 2");
        assertTrue(resultList.stream().noneMatch(o -> o.getStatus() == 3), "Không chứa đơn hàng status = 3");
        assertTrue(resultList.get(0).getUpdated_date().after(resultList.get(1).getUpdated_date()), "Phải sắp xếp theo updated_date giảm dần");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Order.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_OP_10: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }
}