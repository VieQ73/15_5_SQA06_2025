package com.devpro.services;

import com.devpro.entities.Sale;
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
 * Lớp kiểm thử đơn vị cho phương thức getSaleAdmin của ProductSaleService.
 */
@ExtendWith(MockitoExtension.class)
public class ProductSaleServiceGetSaleAdminTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private ProductSaleService productSaleService;

    /**
     * TC_PS_31 - Kiểm tra phương thức getSaleAdmin khi không có bản ghi trong DB.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi không có dữ liệu.
     */
    @Test
    public void testGetSaleAdminWithNoData() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_31: Không có bản ghi trong DB");
        List<Sale> resultList = productSaleService.getSaleAdmin();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi không có dữ liệu");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Sale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_31: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_32 - Kiểm tra phương thức getSaleAdmin khi tất cả bản ghi có status = true.
     * Mục tiêu: Đảm bảo trả về tất cả bản ghi, không lọc theo status.
     */
    @Test
    public void testGetSaleAdminWithAllActiveSales() {
        // Sắp xếp dữ liệu giả lập
        List<Sale> mockSaleList = new ArrayList<>();

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setSale_name("Khuyến mãi tháng 5");
        sale1.setStart_date(new Date(2025 - 1900, 4, 1));
        sale1.setEnd_date(new Date(2025 - 1900, 4, 10));
        sale1.setStatus(true);

        Sale sale2 = new Sale();
        sale2.setId(2);
        sale2.setSale_name("Khuyến mãi tháng 6");
        sale2.setStart_date(new Date(2025 - 1900, 5, 1));
        sale2.setEnd_date(new Date(2025 - 1900, 5, 10));
        sale2.setStatus(true);

        mockSaleList.add(sale1);
        mockSaleList.add(sale2);
        when(entityManager.createNativeQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_32: Tất cả bản ghi có status = true");
        List<Sale> resultList = productSaleService.getSaleAdmin();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 bản ghi");
        assertTrue(resultList.stream().allMatch(s -> s.getStatus()), "Tất cả bản ghi có status = true");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Sale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_32: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_33 - Kiểm tra phương thức getSaleAdmin khi tất cả bản ghi có status = false.
     * Mục tiêu: Đảm bảo trả về tất cả bản ghi, không lọc theo status.
     */
    @Test
    public void testGetSaleAdminWithAllInactiveSales() {
        // Sắp xếp dữ liệu giả lập
        List<Sale> mockSaleList = new ArrayList<>();

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setSale_name("Khuyến mãi tháng 5");
        sale1.setStart_date(new Date(2025 - 1900, 4, 1));
        sale1.setEnd_date(new Date(2025 - 1900, 4, 10));
        sale1.setStatus(false);

        Sale sale2 = new Sale();
        sale2.setId(2);
        sale2.setSale_name("Khuyến mãi tháng 6");
        sale2.setStart_date(new Date(2025 - 1900, 5, 1));
        sale2.setEnd_date(new Date(2025 - 1900, 5, 10));
        sale2.setStatus(false);

        mockSaleList.add(sale1);
        mockSaleList.add(sale2);
        when(entityManager.createNativeQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_33: Tất cả bản ghi có status = false");
        List<Sale> resultList = productSaleService.getSaleAdmin();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 bản ghi");
        assertTrue(resultList.stream().noneMatch(s -> s.getStatus()), "Tất cả bản ghi có status = false");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Sale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_33: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_34 - Kiểm tra phương thức getSaleAdmin khi có cả status = true và false.
     * Mục tiêu: Đảm bảo trả về tất cả bản ghi, không lọc theo status.
     */
    @Test
    public void testGetSaleAdminWithMixedStatus() {
        // Sắp xếp dữ liệu giả lập
        List<Sale> mockSaleList = new ArrayList<>();

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setSale_name("Khuyến mãi tháng 5");
        sale1.setStart_date(new Date(2025 - 1900, 4, 1));
        sale1.setEnd_date(new Date(2025 - 1900, 4, 10));
        sale1.setStatus(true);

        Sale sale2 = new Sale();
        sale2.setId(2);
        sale2.setSale_name("Khuyến mãi tháng 6");
        sale2.setStart_date(new Date(2025 - 1900, 5, 1));
        sale2.setEnd_date(new Date(2025 - 1900, 5, 10));
        sale2.setStatus(false);

        mockSaleList.add(sale1);
        mockSaleList.add(sale2);
        when(entityManager.createNativeQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_34: Có cả status = true và false");
        List<Sale> resultList = productSaleService.getSaleAdmin();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 bản ghi");
        assertEquals(1, resultList.stream().filter(s -> s.getStatus()).count(), "Phải có 1 bản ghi với status = true");
        assertEquals(1, resultList.stream().filter(s -> !s.getStatus()).count(), "Phải có 1 bản ghi với status = false");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Sale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_34: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_35 - Kiểm tra phương thức getSaleAdmin khi bản ghi có sale_name null.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (sale_name null).
     */
    @Test
    public void testGetSaleAdminWithNullSaleName() {
        // Sắp xếp dữ liệu giả lập
        List<Sale> mockSaleList = new ArrayList<>();

        Sale sale = new Sale();
        sale.setId(1);
        sale.setSale_name(null); // Sale_name null
        sale.setStart_date(new Date(2025 - 1900, 4, 1));
        sale.setEnd_date(new Date(2025 - 1900, 4, 10));
        sale.setStatus(true);

        mockSaleList.add(sale);
        when(entityManager.createNativeQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_35: Sale_name null");
        List<Sale> resultList = productSaleService.getSaleAdmin();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 bản ghi");
        assertNull(resultList.get(0).getSale_name(), "Sale_name phải là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Sale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_35: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_36 - Kiểm tra phương thức getSaleAdmin khi bản ghi có start_date null.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (start_date null).
     */
    @Test
    public void testGetSaleAdminWithNullStartDate() {
        // Sắp xếp dữ liệu giả lập
        List<Sale> mockSaleList = new ArrayList<>();

        Sale sale = new Sale();
        sale.setId(1);
        sale.setSale_name("Khuyến mãi tháng 5");
        sale.setStart_date(null); // Start_date null
        sale.setEnd_date(new Date(2025 - 1900, 4, 10));
        sale.setStatus(true);

        mockSaleList.add(sale);
        when(entityManager.createNativeQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_36: Start_date null");
        List<Sale> resultList = productSaleService.getSaleAdmin();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 bản ghi");
        assertNull(resultList.get(0).getStart_date(), "Start_date phải là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Sale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_36: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_37 - Kiểm tra phương thức getSaleAdmin khi bản ghi có end_date trước start_date.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (end_date < start_date).
     */
    @Test
    public void testGetSaleAdminWithInvalidDateRange() {
        // Sắp xếp dữ liệu giả lập
        List<Sale> mockSaleList = new ArrayList<>();

        Sale sale = new Sale();
        sale.setId(1);
        sale.setSale_name("Khuyến mãi tháng 5");
        sale.setStart_date(new Date(2025 - 1900, 4, 10));
        sale.setEnd_date(new Date(2025 - 1900, 4, 1)); // End_date trước Start_date
        sale.setStatus(true);

        mockSaleList.add(sale);
        when(entityManager.createNativeQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_37: End_date trước Start_date");
        List<Sale> resultList = productSaleService.getSaleAdmin();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 bản ghi");
        assertTrue(resultList.get(0).getEnd_date().before(resultList.get(0).getStart_date()), "End_date phải trước Start_date");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Sale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_37: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_38 - Kiểm tra phương thức getSaleAdmin với số lượng bản ghi lớn.
     * Mục tiêu: Đảm bảo hiệu suất tốt khi có nhiều bản ghi.
     */
    @Test
    public void testGetSaleAdminWithLargeData() {
        // Sắp xếp dữ liệu giả lập
        List<Sale> mockSaleList = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            Sale sale = new Sale();
            sale.setId(i + 1);
            sale.setSale_name("Khuyến mãi " + i);
            sale.setStart_date(new Date(2025 - 1900, 4, 1));
            sale.setEnd_date(new Date(2025 - 1900, 4, 10));
            sale.setStatus(i % 2 == 0); // Xen kẽ status true/false
            mockSaleList.add(sale);
        }
        when(entityManager.createNativeQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_38: Dữ liệu lớn");
        long startTime = System.currentTimeMillis();
        List<Sale> resultList = productSaleService.getSaleAdmin();
        long endTime = System.currentTimeMillis();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        System.out.println("Thời gian thực thi: " + (endTime - startTime) + "ms");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(10000, resultList.size(), "Phải trả về 10,000 bản ghi");
        assertTrue((endTime - startTime) < 1000, "Hiệu suất phải dưới 1 giây");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Sale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_38: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_39 - Kiểm tra phương thức getSaleAdmin khi DB gặp lỗi.
     * Mục tiêu: Đảm bảo ném ngoại lệ khi DB thất bại.
     */
    @Test
    public void testGetSaleAdminWithDatabaseError() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new JpaSystemException(new RuntimeException("Lỗi DB")));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_39: Lỗi cơ sở dữ liệu");
        assertThrows(JpaSystemException.class, () -> productSaleService.getSaleAdmin(), "Phải ném ngoại lệ khi DB lỗi");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Sale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_39: Thành công");
        // Không cần rollback vì không thay đổi dữ liệu
    }

    /**
     * TC_PS_40 - Kiểm tra phương thức getSaleAdmin khi có bản ghi đã hết hạn.
     * Mục tiêu: Đảm bảo trả về tất cả bản ghi, bao gồm cả bản ghi đã hết hạn.
     */
    @Test
    public void testGetSaleAdminWithExpiredSales() {
        // Sắp xếp dữ liệu giả lập
        List<Sale> mockSaleList = new ArrayList<>();

        Sale sale = new Sale();
        sale.setId(1);
        sale.setSale_name("Khuyến mãi đã hết hạn");
        sale.setStart_date(new Date(2024 - 1900, 4, 1)); // 2024-05-01
        sale.setEnd_date(new Date(2024 - 1900, 4, 10));  // 2024-05-10 (hết hạn)
        sale.setStatus(false);

        mockSaleList.add(sale);
        when(entityManager.createNativeQuery(anyString(), eq(Sale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_40: Bản ghi đã hết hạn");
        List<Sale> resultList = productSaleService.getSaleAdmin();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 bản ghi");
        assertTrue(resultList.get(0).getEnd_date().before(new Date()), "Bản ghi đã hết hạn");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Sale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_40: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }
}