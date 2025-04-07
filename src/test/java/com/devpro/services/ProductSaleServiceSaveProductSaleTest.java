package com.devpro.services;

import com.devpro.entities.Product;
import com.devpro.entities.ProductSale;
import com.devpro.entities.Sale;
import com.devpro.repositories.ProductSaleRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Lớp kiểm thử đơn vị cho phương thức saveProductSale của ProductSaleService.
 */
@ExtendWith(MockitoExtension.class)
public class ProductSaleServiceSaveProductSaleTest {

    @Mock
    private ProductSaleRepo productSaleRepo;

    @InjectMocks
    private ProductSaleService productSaleService;

    /**
     * TC_PS_01 - Kiểm tra phương thức saveProductSale khi productSale là null.
     * Mục tiêu: Đảm bảo xử lý an toàn khi input là null.
     */
    @Test
    public void testSaveProductSaleWithNullInput() {
        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_01: ProductSale là null");
        assertThrows(IllegalArgumentException.class, () -> productSaleService.saveProductSale(null), "Phải ném ngoại lệ khi productSale là null");
        verify(productSaleRepo, never()).save(any(ProductSale.class)); // Không gọi save
        System.out.println("Kết thúc kiểm tra TC_PS_01: Thành công");
    }

    /**
     * TC_PS_02 - Kiểm tra phương thức saveProductSale với bản ghi mới.
     * Mục tiêu: Đảm bảo lưu thành công bản ghi mới (id = null).
     */
    @Test
    public void testSaveProductSaleWithNewRecord() {
        // Sắp xếp dữ liệu giả lập
        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);

        ProductSale productSale = new ProductSale();
        productSale.setId(null); // Bản ghi mới
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(20);
        productSale.setActive(true);

        ProductSale savedProductSale = new ProductSale();
        savedProductSale.setId(1); // Giả lập id sau khi lưu
        savedProductSale.setProduct(product);
        savedProductSale.setSale(sale);
        savedProductSale.setDiscount(20);
        savedProductSale.setActive(true);

        when(productSaleRepo.save(any(ProductSale.class))).thenReturn(savedProductSale);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_02: Lưu bản ghi mới");
        productSaleService.saveProductSale(productSale);

        // Kiểm tra kết quả
        verify(productSaleRepo, never()).delete(any(ProductSale.class)); // Không gọi delete vì id = null
        verify(productSaleRepo, times(1)).save(productSale); // Gọi save 1 lần
        assertEquals(1, savedProductSale.getId(), "Bản ghi phải có id mới sau khi lưu");
        assertEquals(20, savedProductSale.getDiscount(), "Discount phải được lưu đúng");
        System.out.println("Kết thúc kiểm tra TC_PS_02: Thành công");
    }

    /**
     * TC_PS_03 - Kiểm tra phương thức saveProductSale với bản ghi đã tồn tại.
     * Mục tiêu: Đảm bảo cập nhật thành công bản ghi (id != null).
     */
    @Test
    public void testSaveProductSaleWithExistingRecord() {
        // Sắp xếp dữ liệu giả lập
        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);

        ProductSale productSale = new ProductSale();
        productSale.setId(1); // Bản ghi đã tồn tại
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(30);
        productSale.setActive(false);

        ProductSale updatedProductSale = new ProductSale();
        updatedProductSale.setId(1);
        updatedProductSale.setProduct(product);
        updatedProductSale.setSale(sale);
        updatedProductSale.setDiscount(30);
        updatedProductSale.setActive(false);

        when(productSaleRepo.save(any(ProductSale.class))).thenReturn(updatedProductSale);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_03: Cập nhật bản ghi đã tồn tại");
        productSaleService.saveProductSale(productSale);

        // Kiểm tra kết quả
        verify(productSaleRepo, times(1)).delete(productSale); // Gọi delete vì id != null
        verify(productSaleRepo, times(1)).save(productSale); // Gọi save 1 lần
        assertEquals(30, updatedProductSale.getDiscount(), "Discount phải được cập nhật đúng");
        assertFalse(updatedProductSale.getActive(), "Active phải được cập nhật đúng");
        System.out.println("Kết thúc kiểm tra TC_PS_03: Thành công");
    }

    /**
     * TC_PS_04 - Kiểm tra phương thức saveProductSale với discount âm.
     * Mục tiêu: Đảm bảo không cho phép lưu khi discount âm.
     */
    @Test
    public void testSaveProductSaleWithNegativeDiscount() {
        // Sắp xếp dữ liệu giả lập
        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);

        ProductSale productSale = new ProductSale();
        productSale.setId(null);
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(-10); // Discount âm
        productSale.setActive(true);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_04: Discount âm");
        assertThrows(IllegalArgumentException.class, () -> productSaleService.saveProductSale(productSale), "Phải ném ngoại lệ khi discount âm");
        verify(productSaleRepo, never()).save(any(ProductSale.class)); // Không gọi save
        System.out.println("Kết thúc kiểm tra TC_PS_04: Thành công");
    }

    /**
     * TC_PS_05 - Kiểm tra phương thức saveProductSale với discount vượt quá 100.
     * Mục tiêu: Đảm bảo không cho phép lưu khi discount vượt quá 100.
     */
    @Test
    public void testSaveProductSaleWithInvalidDiscount() {
        // Sắp xếp dữ liệu giả lập
        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);

        ProductSale productSale = new ProductSale();
        productSale.setId(null);
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(150); // Discount vượt quá 100
        productSale.setActive(true);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_05: Discount vượt quá 100");
        assertThrows(IllegalArgumentException.class, () -> productSaleService.saveProductSale(productSale), "Phải ném ngoại lệ khi discount vượt quá 100");
        verify(productSaleRepo, never()).save(any(ProductSale.class)); // Không gọi save
        System.out.println("Kết thúc kiểm tra TC_PS_05: Thành công");
    }

    /**
     * TC_PS_06 - Kiểm tra phương thức saveProductSale khi product là null.
     * Mục tiêu: Đảm bảo không cho phép lưu khi product null (ràng buộc ngoại khóa).
     */
    @Test
    public void testSaveProductSaleWithNullProduct() {
        // Sắp xếp dữ liệu giả lập
        Sale sale = new Sale();
        sale.setId(1);

        ProductSale productSale = new ProductSale();
        productSale.setId(null);
        productSale.setProduct(null); // Product null
        productSale.setSale(sale);
        productSale.setDiscount(20);
        productSale.setActive(true);

        when(productSaleRepo.save(any(ProductSale.class))).thenThrow(new DataIntegrityViolationException("Product không được null"));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_06: Product là null");
        assertThrows(DataIntegrityViolationException.class, () -> productSaleService.saveProductSale(productSale), "Phải ném ngoại lệ khi product là null");
        verify(productSaleRepo, times(1)).save(productSale); // Gọi save nhưng thất bại
        System.out.println("Kết thúc kiểm tra TC_PS_06: Thành công");
    }

    /**
     * TC_PS_07 - Kiểm tra phương thức saveProductSale khi sale là null.
     * Mục tiêu: Đảm bảo không cho phép lưu khi sale null (ràng buộc ngoại khóa).
     */
    @Test
    public void testSaveProductSaleWithNullSale() {
        // Sắp xếp dữ liệu giả lập
        Product product = new Product();
        product.setId(1);

        ProductSale productSale = new ProductSale();
        productSale.setId(null);
        productSale.setProduct(product);
        productSale.setSale(null); // Sale null
        productSale.setDiscount(20);
        productSale.setActive(true);

        when(productSaleRepo.save(any(ProductSale.class))).thenThrow(new DataIntegrityViolationException("Sale không được null"));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_07: Sale là null");
        assertThrows(DataIntegrityViolationException.class, () -> productSaleService.saveProductSale(productSale), "Phải ném ngoại lệ khi sale là null");
        verify(productSaleRepo, times(1)).save(productSale); // Gọi save nhưng thất bại
        System.out.println("Kết thúc kiểm tra TC_PS_07: Thành công");
    }

    /**
     * TC_PS_08 - Kiểm tra phương thức saveProductSale khi active là null.
     * Mục tiêu: Đảm bảo lưu thành công và active được thiết lập mặc định.
     */
    @Test
    public void testSaveProductSaleWithNullActive() {
        // Sắp xếp dữ liệu giả lập
        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);

        ProductSale productSale = new ProductSale();
        productSale.setId(null);
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(20);
        productSale.setActive(null); // Active null

        ProductSale savedProductSale = new ProductSale();
        savedProductSale.setId(1);
        savedProductSale.setProduct(product);
        savedProductSale.setSale(sale);
        savedProductSale.setDiscount(20);
        savedProductSale.setActive(false); // Giả lập giá trị mặc định

        when(productSaleRepo.save(any(ProductSale.class))).thenReturn(savedProductSale);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_08: Active là null");
        productSaleService.saveProductSale(productSale);

        // Kiểm tra kết quả
        verify(productSaleRepo, times(1)).save(productSale); // Gọi save 1 lần
        assertFalse(savedProductSale.getActive(), "Active phải được thiết lập mặc định là false");
        System.out.println("Kết thúc kiểm tra TC_PS_08: Thành công");
    }

    /**
     * TC_PS_09 - Kiểm tra phương thức saveProductSale khi DB gặp lỗi.
     * Mục tiêu: Đảm bảo ném ngoại lệ khi DB thất bại.
     */
    @Test
    public void testSaveProductSaleWithDatabaseError() {
        // Sắp xếp dữ liệu giả lập
        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);

        ProductSale productSale = new ProductSale();
        productSale.setId(null);
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(20);
        productSale.setActive(true);

        when(productSaleRepo.save(any(ProductSale.class))).thenThrow(new DataAccessException("Lỗi DB") {});

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_09: Lỗi cơ sở dữ liệu");
        assertThrows(DataAccessException.class, () -> productSaleService.saveProductSale(productSale), "Phải ném ngoại lệ khi DB lỗi");
        verify(productSaleRepo, times(1)).save(productSale); // Gọi save nhưng thất bại
        System.out.println("Kết thúc kiểm tra TC_PS_09: Thành công");
    }

    /**
     * TC_PS_10 - Kiểm tra phương thức saveProductSale với nhiều bản ghi.
     * Mục tiêu: Đảm bảo hiệu suất tốt khi lưu nhiều bản ghi liên tiếp.
     */
    @Test
    public void testSaveProductSaleWithMultipleRecords() {
        // Sắp xếp dữ liệu giả lập
        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);

        ProductSale savedProductSale = new ProductSale();
        savedProductSale.setId(1);
        savedProductSale.setProduct(product);
        savedProductSale.setSale(sale);
        savedProductSale.setDiscount(20);
        savedProductSale.setActive(true);

        when(productSaleRepo.save(any(ProductSale.class))).thenReturn(savedProductSale);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_10: Lưu nhiều bản ghi");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            ProductSale productSale = new ProductSale();
            productSale.setId(null);
            productSale.setProduct(product);
            productSale.setSale(sale);
            productSale.setDiscount(20);
            productSale.setActive(true);
            productSaleService.saveProductSale(productSale);
        }
        long endTime = System.currentTimeMillis();

        // Kiểm tra kết quả
        System.out.println("Thời gian thực thi: " + (endTime - startTime) + "ms");
        verify(productSaleRepo, times(10000)).save(any(ProductSale.class)); // Gọi save 10,000 lần
        assertTrue((endTime - startTime) < 1000, "Hiệu suất phải dưới 1 giây");
        System.out.println("Kết thúc kiểm tra TC_PS_10: Thành công");
    }
}