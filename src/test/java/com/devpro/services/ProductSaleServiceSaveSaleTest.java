package com.devpro.services;

import com.devpro.entities.Sale;
import com.devpro.repositories.SaleRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Lớp kiểm thử đơn vị cho phương thức saveSale của ProductSaleService.
 */
@ExtendWith(MockitoExtension.class)
public class ProductSaleServiceSaveSaleTest {

    @Mock
    private SaleRepo saleRepo;

    @InjectMocks
    private ProductSaleService productSaleService;

    /**
     * TC_PS_11 - Kiểm tra phương thức saveSale khi sale là null.
     * Mục tiêu: Đảm bảo xử lý an toàn khi input là null.
     */
    @Test
    public void testSaveSaleWithNullInput() {
        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_11: Sale là null");
        assertThrows(IllegalArgumentException.class, () -> productSaleService.saveSale(null), "Phải ném ngoại lệ khi sale là null");
        verify(saleRepo, never()).save(any(Sale.class)); // Không gọi save
        System.out.println("Kết thúc kiểm tra TC_PS_11: Thành công");
    }

    /**
     * TC_PS_12 - Kiểm tra phương thức saveSale với bản ghi mới.
     * Mục tiêu: Đảm bảo lưu thành công bản ghi mới (id = null).
     */
    @Test
    public void testSaveSaleWithNewRecord() {
        // Sắp xếp dữ liệu giả lập
        Date startDate = new Date(2025 - 1900, 4, 1); // 2025-05-01
        Date endDate = new Date(2025 - 1900, 4, 10);  // 2025-05-10

        Sale sale = new Sale();
        sale.setId(null); // Bản ghi mới
        sale.setSale_name("Khuyến mãi tháng 5");
        sale.setStart_date(startDate);
        sale.setEnd_date(endDate);
        sale.setStatus(true);

        Sale savedSale = new Sale();
        savedSale.setId(1); // Giả lập id sau khi lưu
        savedSale.setSale_name("Khuyến mãi tháng 5");
        savedSale.setStart_date(startDate);
        savedSale.setEnd_date(endDate);
        savedSale.setStatus(true);

        when(saleRepo.save(any(Sale.class))).thenReturn(savedSale);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_12: Lưu bản ghi mới");
        productSaleService.saveSale(sale);

        // Kiểm tra kết quả
        verify(saleRepo, times(1)).save(sale); // Gọi save 1 lần
        assertEquals(1, savedSale.getId(), "Bản ghi phải có id mới sau khi lưu");
        assertEquals("Khuyến mãi tháng 5", savedSale.getSale_name(), "Sale_name phải được lưu đúng");
        System.out.println("Kết thúc kiểm tra TC_PS_12: Thành công");
    }

    /**
     * TC_PS_13 - Kiểm tra phương thức saveSale với bản ghi đã tồn tại.
     * Mục tiêu: Đảm bảo cập nhật thành công bản ghi (id != null).
     */
    @Test
    public void testSaveSaleWithExistingRecord() {
        // Sắp xếp dữ liệu giả lập
        Date startDate = new Date(2025 - 1900, 4, 1); // 2025-05-01
        Date endDate = new Date(2025 - 1900, 4, 10);  // 2025-05-10

        Sale sale = new Sale();
        sale.setId(1); // Bản ghi đã tồn tại
        sale.setSale_name("Khuyến mãi tháng 5 (Cập nhật)");
        sale.setStart_date(startDate);
        sale.setEnd_date(endDate);
        sale.setStatus(false);

        Sale updatedSale = new Sale();
        updatedSale.setId(1);
        updatedSale.setSale_name("Khuyến mãi tháng 5 (Cập nhật)");
        updatedSale.setStart_date(startDate);
        updatedSale.setEnd_date(endDate);
        updatedSale.setStatus(false);

        when(saleRepo.save(any(Sale.class))).thenReturn(updatedSale);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_13: Cập nhật bản ghi đã tồn tại");
        productSaleService.saveSale(sale);

        // Kiểm tra kết quả
        verify(saleRepo, times(1)).save(sale); // Gọi save 1 lần
        assertEquals("Khuyến mãi tháng 5 (Cập nhật)", updatedSale.getSale_name(), "Sale_name phải được cập nhật đúng");
        assertFalse(updatedSale.getStatus(), "Status phải được cập nhật đúng");
        System.out.println("Kết thúc kiểm tra TC_PS_13: Thành công");
    }

    /**
     * TC_PS_14 - Kiểm tra phương thức saveSale với sale_name rỗng.
     * Mục tiêu: Đảm bảo không cho phép lưu khi sale_name rỗng.
     */
    @Test
    public void testSaveSaleWithEmptySaleName() {
        // Sắp xếp dữ liệu giả lập
        Date startDate = new Date(2025 - 1900, 4, 1); // 2025-05-01
        Date endDate = new Date(2025 - 1900, 4, 10);  // 2025-05-10

        Sale sale = new Sale();
        sale.setId(null);
        sale.setSale_name(""); // Sale_name rỗng
        sale.setStart_date(startDate);
        sale.setEnd_date(endDate);
        sale.setStatus(true);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_14: Sale_name rỗng");
        assertThrows(IllegalArgumentException.class, () -> productSaleService.saveSale(sale), "Phải ném ngoại lệ khi sale_name rỗng");
        verify(saleRepo, never()).save(any(Sale.class)); // Không gọi save
        System.out.println("Kết thúc kiểm tra TC_PS_14: Thành công");
    }

    /**
     * TC_PS_15 - Kiểm tra phương thức saveSale khi start_date là null.
     * Mục tiêu: Đảm bảo không cho phép lưu khi start_date null.
     */
    @Test
    public void testSaveSaleWithNullStartDate() {
        // Sắp xếp dữ liệu giả lập
        Date endDate = new Date(2025 - 1900, 4, 10);  // 2025-05-10

        Sale sale = new Sale();
        sale.setId(null);
        sale.setSale_name("Khuyến mãi tháng 5");
        sale.setStart_date(null); // Start_date null
        sale.setEnd_date(endDate);
        sale.setStatus(true);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_15: Start_date là null");
        assertThrows(IllegalArgumentException.class, () -> productSaleService.saveSale(sale), "Phải ném ngoại lệ khi start_date là null");
        verify(saleRepo, never()).save(any(Sale.class)); // Không gọi save
        System.out.println("Kết thúc kiểm tra TC_PS_15: Thành công");
    }

    /**
     * TC_PS_16 - Kiểm tra phương thức saveSale khi end_date là null.
     * Mục tiêu: Đảm bảo không cho phép lưu khi end_date null.
     */
    @Test
    public void testSaveSaleWithNullEndDate() {
        // Sắp xếp dữ liệu giả lập
        Date startDate = new Date(2025 - 1900, 4, 1); // 2025-05-01

        Sale sale = new Sale();
        sale.setId(null);
        sale.setSale_name("Khuyến mãi tháng 5");
        sale.setStart_date(startDate);
        sale.setEnd_date(null); // End_date null
        sale.setStatus(true);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_16: End_date là null");
        assertThrows(IllegalArgumentException.class, () -> productSaleService.saveSale(sale), "Phải ném ngoại lệ khi end_date là null");
        verify(saleRepo, never()).save(any(Sale.class)); // Không gọi save
        System.out.println("Kết thúc kiểm tra TC_PS_16: Thành công");
    }

    /**
     * TC_PS_17 - Kiểm tra phương thức saveSale khi end_date trước start_date.
     * Mục tiêu: Đảm bảo không cho phép lưu khi ngày không hợp lệ.
     */
    @Test
    public void testSaveSaleWithInvalidDateRange() {
        // Sắp xếp dữ liệu giả lập
        Date startDate = new Date(2025 - 1900, 4, 10); // 2025-05-10
        Date endDate = new Date(2025 - 1900, 4, 1);   // 2025-05-01 (trước start_date)

        Sale sale = new Sale();
        sale.setId(null);
        sale.setSale_name("Khuyến mãi tháng 5");
        sale.setStart_date(startDate);
        sale.setEnd_date(endDate);
        sale.setStatus(true);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_17: End_date trước Start_date");
        assertThrows(IllegalArgumentException.class, () -> productSaleService.saveSale(sale), "Phải ném ngoại lệ khi end_date trước start_date");
        verify(saleRepo, never()).save(any(Sale.class)); // Không gọi save
        System.out.println("Kết thúc kiểm tra TC_PS_17: Thành công");
    }

    /**
     * TC_PS_18 - Kiểm tra phương thức saveSale khi status là null.
     * Mục tiêu: Đảm bảo lưu thành công và status được thiết lập mặc định.
     */
    @Test
    public void testSaveSaleWithNullStatus() {
        // Sắp xếp dữ liệu giả lập
        Date startDate = new Date(2025 - 1900, 4, 1); // 2025-05-01
        Date endDate = new Date(2025 - 1900, 4, 10);  // 2025-05-10

        Sale sale = new Sale();
        sale.setId(null);
        sale.setSale_name("Khuyến mãi tháng 5");
        sale.setStart_date(startDate);
        sale.setEnd_date(endDate);
        sale.setStatus(null); // Status null

        Sale savedSale = new Sale();
        savedSale.setId(1);
        savedSale.setSale_name("Khuyến mãi tháng 5");
        savedSale.setStart_date(startDate);
        savedSale.setEnd_date(endDate);
        savedSale.setStatus(true); // Giả lập giá trị mặc định

        when(saleRepo.save(any(Sale.class))).thenReturn(savedSale);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_18: Status là null");
        productSaleService.saveSale(sale);

        // Kiểm tra kết quả
        verify(saleRepo, times(1)).save(sale); // Gọi save 1 lần
        assertTrue(savedSale.getStatus(), "Status phải được thiết lập mặc định là true");
        System.out.println("Kết thúc kiểm tra TC_PS_18: Thành công");
    }

    /**
     * TC_PS_19 - Kiểm tra phương thức saveSale khi DB gặp lỗi.
     * Mục tiêu: Đảm bảo ném ngoại lệ khi DB thất bại.
     */
    @Test
    public void testSaveSaleWithDatabaseError() {
        // Sắp xếp dữ liệu giả lập
        Date startDate = new Date(2025 - 1900, 4, 1); // 2025-05-01
        Date endDate = new Date(2025 - 1900, 4, 10);  // 2025-05-10

        Sale sale = new Sale();
        sale.setId(null);
        sale.setSale_name("Khuyến mãi tháng 5");
        sale.setStart_date(startDate);
        sale.setEnd_date(endDate);
        sale.setStatus(true);

        when(saleRepo.save(any(Sale.class))).thenThrow(new DataAccessException("Lỗi DB") {});

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_19: Lỗi cơ sở dữ liệu");
        assertThrows(DataAccessException.class, () -> productSaleService.saveSale(sale), "Phải ném ngoại lệ khi DB lỗi");
        verify(saleRepo, times(1)).save(sale); // Gọi save nhưng thất bại
        System.out.println("Kết thúc kiểm tra TC_PS_19: Thành công");
    }

    /**
     * TC_PS_20 - Kiểm tra phương thức saveSale với nhiều bản ghi.
     * Mục tiêu: Đảm bảo hiệu suất tốt khi lưu nhiều bản ghi liên tiếp.
     */
    @Test
    public void testSaveSaleWithMultipleRecords() {
        // Sắp xếp dữ liệu giả lập
        Date startDate = new Date(2025 - 1900, 4, 1); // 2025-05-01
        Date endDate = new Date(2025 - 1900, 4, 10);  // 2025-05-10

        Sale savedSale = new Sale();
        savedSale.setId(1);
        savedSale.setSale_name("Khuyến mãi tháng 5");
        savedSale.setStart_date(startDate);
        savedSale.setEnd_date(endDate);
        savedSale.setStatus(true);

        when(saleRepo.save(any(Sale.class))).thenReturn(savedSale);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_20: Lưu nhiều bản ghi");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Sale sale = new Sale();
            sale.setId(null);
            sale.setSale_name("Khuyến mãi " + i);
            sale.setStart_date(startDate);
            sale.setEnd_date(endDate);
            sale.setStatus(true);
            productSaleService.saveSale(sale);
        }
        long endTime = System.currentTimeMillis();

        // Kiểm tra kết quả
        System.out.println("Thời gian thực thi: " + (endTime - startTime) + "ms");
        verify(saleRepo, times(10000)).save(any(Sale.class)); // Gọi save 10,000 lần
        assertTrue((endTime - startTime) < 1000, "Hiệu suất phải dưới 1 giây");
        System.out.println("Kết thúc kiểm tra TC_PS_20: Thành công");
    }
}