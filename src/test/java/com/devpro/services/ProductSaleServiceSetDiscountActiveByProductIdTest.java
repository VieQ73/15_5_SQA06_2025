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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Lớp kiểm thử đơn vị cho phương thức setDiscountActiveByProductId của ProductSaleService.
 */
@ExtendWith(MockitoExtension.class)
public class ProductSaleServiceSetDiscountActiveByProductIdTest {

    @Mock
    private ProductSaleRepo productSaleRepo;

    @InjectMocks
    private ProductSaleService productSaleService;

    /**
     * TC_PS_61 - Kiểm tra phương thức setDiscountActiveByProductId khi id là null.
     * Mục tiêu: Đảm bảo ném ngoại lệ khi id là null.
     */
    @Test
    public void testSetDiscountActiveByProductIdWithNullId() {
        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_61: Id là null");
        assertThrows(IllegalArgumentException.class, () -> productSaleService.setDiscountActiveByProductId(null), "Phải ném ngoại lệ khi id là null");
        verify(productSaleRepo, never()).save(any(ProductSale.class)); // Không gọi save()
        System.out.println("Kết thúc kiểm tra TC_PS_61: Thành công");
        // Không cần rollback vì không truy vấn DB
    }

    /**
     * TC_PS_62 - Kiểm tra phương thức setDiscountActiveByProductId khi id không hợp lệ.
     * Mục tiêu: Đảm bảo không gọi save() khi id âm hoặc ở giá trị biên.
     */
    @Test
    public void testSetDiscountActiveByProductIdWithInvalidId() {
        // Mock getProductSaleByIdProduct trả về danh sách rỗng
        ProductSaleService productSaleServiceSpy = spy(productSaleService);
        doReturn(new ArrayList<ProductSale>()).when(productSaleServiceSpy).getProductSaleByIdProduct(anyInt());

        // Thực hiện kiểm thử với id âm
        System.out.println("Bắt đầu kiểm tra TC_PS_62: Id không hợp lệ (âm)");
        productSaleServiceSpy.setDiscountActiveByProductId(-1);

        // Kiểm tra kết quả
        verify(productSaleRepo, never()).save(any(ProductSale.class)); // Không gọi save()

        // Thực hiện kiểm thử với id ở giá trị biên
        System.out.println("Kiểm tra TC_PS_62: Id ở giá trị biên (Integer.MAX_VALUE)");
        productSaleServiceSpy.setDiscountActiveByProductId(Integer.MAX_VALUE);

        // Kiểm tra kết quả
        verify(productSaleRepo, never()).save(any(ProductSale.class)); // Không gọi save()
        System.out.println("Kết thúc kiểm tra TC_PS_62: Thành công");
        // Không cần rollback vì không gọi save()
    }

    /**
     * TC_PS_63 - Kiểm tra phương thức setDiscountActiveByProductId khi id không tồn tại trong DB.
     * Mục tiêu: Đảm bảo không gọi save() khi id không tồn tại.
     */
    @Test
    public void testSetDiscountActiveByProductIdWithNonExistentId() {
        // Mock getProductSaleByIdProduct trả về danh sách rỗng
        ProductSaleService productSaleServiceSpy = spy(productSaleService);
        doReturn(new ArrayList<ProductSale>()).when(productSaleServiceSpy).getProductSaleByIdProduct(anyInt());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_63: Id không tồn tại trong DB");
        productSaleServiceSpy.setDiscountActiveByProductId(999);

        // Kiểm tra kết quả
        verify(productSaleRepo, never()).save(any(ProductSale.class)); // Không gọi save()
        System.out.println("Kết thúc kiểm tra TC_PS_63: Thành công");
        // Không cần rollback vì không gọi save()
    }

    /**
     * TC_PS_64 - Kiểm tra phương thức setDiscountActiveByProductId khi không có bản ghi cho product_id.
     * Mục tiêu: Đảm bảo không gọi save() khi không có dữ liệu.
     */
    @Test
    public void testSetDiscountActiveByProductIdWithNoRecords() {
        // Mock getProductSaleByIdProduct trả về danh sách rỗng
        ProductSaleService productSaleServiceSpy = spy(productSaleService);
        doReturn(new ArrayList<ProductSale>()).when(productSaleServiceSpy).getProductSaleByIdProduct(anyInt());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_64: Không có bản ghi cho product_id");
        productSaleServiceSpy.setDiscountActiveByProductId(1);

        // Kiểm tra kết quả
        verify(productSaleRepo, never()).save(any(ProductSale.class)); // Không gọi save()
        System.out.println("Kết thúc kiểm tra TC_PS_64: Thành công");
        // Không cần rollback vì không gọi save()
    }

    /**
     * TC_PS_65 - Kiểm tra phương thức setDiscountActiveByProductId khi có bản ghi với sale đang hoạt động.
     * Mục tiêu: Đảm bảo chỉ 1 bản ghi được đặt active = true.
     */
    @Test
    public void testSetDiscountActiveByProductIdWithActiveSale() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setStart_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)); // 1 ngày trước
        sale1.setEnd_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));   // 1 ngày sau

        Sale sale2 = new Sale();
        sale2.setId(2);
        sale2.setStart_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 48)); // 2 ngày trước
        sale2.setEnd_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));   // 1 ngày sau

        ProductSale productSale1 = new ProductSale();
        productSale1.setId(1);
        productSale1.setProduct(product);
        productSale1.setSale(sale1);
        productSale1.setDiscount(20);
        productSale1.setActive(false);

        ProductSale productSale2 = new ProductSale();
        productSale2.setId(2);
        productSale2.setProduct(product);
        productSale2.setSale(sale2);
        productSale2.setDiscount(30);
        productSale2.setActive(false);

        mockProductSaleList.add(productSale1);
        mockProductSaleList.add(productSale2);

        // Mock getProductSaleByIdProduct
        ProductSaleService productSaleServiceSpy = spy(productSaleService);
        doReturn(mockProductSaleList).when(productSaleServiceSpy).getProductSaleByIdProduct(1);

        // Mock productSaleRepo.save()
        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_65: Có bản ghi với sale đang hoạt động");
        productSaleServiceSpy.setDiscountActiveByProductId(1);

        // Kiểm tra kết quả
        assertTrue(productSale1.getActive(), "Bản ghi 1 phải có active = true (start_date gần nhất)");
        assertFalse(productSale2.getActive(), "Bản ghi 2 phải có active = false");
        verify(productSaleRepo, times(2)).save(any(ProductSale.class)); // Gọi save() 2 lần
        System.out.println("Kết thúc kiểm tra TC_PS_65: Thành công");
        // Không cần rollback vì mock DB
    }

    /**
     * TC_PS_66 - Kiểm tra phương thức setDiscountActiveByProductId khi tất cả sale đã hết hạn.
     * Mục tiêu: Đảm bảo tất cả bản ghi được đặt active = false.
     */
    @Test
    public void testSetDiscountActiveByProductIdWithAllExpiredSales() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setStart_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 48)); // 2 ngày trước
        sale1.setEnd_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));   // 1 ngày trước

        Sale sale2 = new Sale();
        sale2.setId(2);
        sale2.setStart_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 72)); // 3 ngày trước
        sale2.setEnd_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 48));   // 2 ngày trước

        ProductSale productSale1 = new ProductSale();
        productSale1.setId(1);
        productSale1.setProduct(product);
        productSale1.setSale(sale1);
        productSale1.setDiscount(20);
        productSale1.setActive(true);

        ProductSale productSale2 = new ProductSale();
        productSale2.setId(2);
        productSale2.setProduct(product);
        productSale2.setSale(sale2);
        productSale2.setDiscount(30);
        productSale2.setActive(true);

        mockProductSaleList.add(productSale1);
        mockProductSaleList.add(productSale2);

        // Mock getProductSaleByIdProduct
        ProductSaleService productSaleServiceSpy = spy(productSaleService);
        doReturn(mockProductSaleList).when(productSaleServiceSpy).getProductSaleByIdProduct(1);

        // Mock productSaleRepo.save()
        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_66: Tất cả sale đã hết hạn");
        productSaleServiceSpy.setDiscountActiveByProductId(1);

        // Kiểm tra kết quả
        assertFalse(productSale1.getActive(), "Bản ghi 1 phải có active = false");
        assertFalse(productSale2.getActive(), "Bản ghi 2 phải có active = false");
        verify(productSaleRepo, times(2)).save(any(ProductSale.class)); // Gọi save() 2 lần
        System.out.println("Kết thúc kiểm tra TC_PS_66: Thành công");
        // Không cần rollback vì mock DB
    }

    /**
     * TC_PS_67 - Kiểm tra phương thức setDiscountActiveByProductId khi tất cả sale chưa bắt đầu.
     * Mục tiêu: Đảm bảo tất cả bản ghi được đặt active = false.
     */
    @Test
    public void testSetDiscountActiveByProductIdWithAllFutureSales() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setStart_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)); // 1 ngày sau
        sale1.setEnd_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 48));   // 2 ngày sau

        Sale sale2 = new Sale();
        sale2.setId(2);
        sale2.setStart_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 48)); // 2 ngày sau
        sale2.setEnd_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 72));   // 3 ngày sau

        ProductSale productSale1 = new ProductSale();
        productSale1.setId(1);
        productSale1.setProduct(product);
        productSale1.setSale(sale1);
        productSale1.setDiscount(20);
        productSale1.setActive(true);

        ProductSale productSale2 = new ProductSale();
        productSale2.setId(2);
        productSale2.setProduct(product);
        productSale2.setSale(sale2);
        productSale2.setDiscount(30);
        productSale2.setActive(true);

        mockProductSaleList.add(productSale1);
        mockProductSaleList.add(productSale2);

        // Mock getProductSaleByIdProduct
        ProductSaleService productSaleServiceSpy = spy(productSaleService);
        doReturn(mockProductSaleList).when(productSaleServiceSpy).getProductSaleByIdProduct(1);

        // Mock productSaleRepo.save()
        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_67: Tất cả sale chưa bắt đầu");
        productSaleServiceSpy.setDiscountActiveByProductId(1);

        // Kiểm tra kết quả
        assertFalse(productSale1.getActive(), "Bản ghi 1 phải có active = false");
        assertFalse(productSale2.getActive(), "Bản ghi 2 phải có active = false");
        verify(productSaleRepo, times(2)).save(any(ProductSale.class)); // Gọi save() 2 lần
        System.out.println("Kết thúc kiểm tra TC_PS_67: Thành công");
        // Không cần rollback vì mock DB
    }

    /**
     * TC_PS_68 - Kiểm tra phương thức setDiscountActiveByProductId khi có sale hết hạn, chưa bắt đầu, và đang hoạt động.
     * Mục tiêu: Đảm bảo chỉ bản ghi với sale đang hoạt động được đặt active = true.
     */
    @Test
    public void testSetDiscountActiveByProductIdWithMixedSales() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setStart_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 48)); // 2 ngày trước
        sale1.setEnd_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));   // 1 ngày trước (hết hạn)

        Sale sale2 = new Sale();
        sale2.setId(2);
        sale2.setStart_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)); // 1 ngày sau (chưa bắt đầu)
        sale2.setEnd_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 48));   // 2 ngày sau

        Sale sale3 = new Sale();
        sale3.setId(3);
        sale3.setStart_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)); // 1 ngày trước (đang hoạt động)
        sale3.setEnd_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));   // 1 ngày sau

        ProductSale productSale1 = new ProductSale();
        productSale1.setId(1);
        productSale1.setProduct(product);
        productSale1.setSale(sale1);
        productSale1.setDiscount(20);
        productSale1.setActive(true);

        ProductSale productSale2 = new ProductSale();
        productSale2.setId(2);
        productSale2.setProduct(product);
        productSale2.setSale(sale2);
        productSale2.setDiscount(30);
        productSale2.setActive(true);

        ProductSale productSale3 = new ProductSale();
        productSale3.setId(3);
        productSale3.setProduct(product);
        productSale3.setSale(sale3);
        productSale3.setDiscount(40);
        productSale3.setActive(false);

        mockProductSaleList.add(productSale1);
        mockProductSaleList.add(productSale2);
        mockProductSaleList.add(productSale3);

        // Mock getProductSaleByIdProduct
        ProductSaleService productSaleServiceSpy = spy(productSaleService);
        doReturn(mockProductSaleList).when(productSaleServiceSpy).getProductSaleByIdProduct(1);

        // Mock productSaleRepo.save()
        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_68: Có sale hết hạn, chưa bắt đầu, và đang hoạt động");
        productSaleServiceSpy.setDiscountActiveByProductId(1);

        // Kiểm tra kết quả
        assertFalse(productSale1.getActive(), "Bản ghi 1 phải có active = false (sale hết hạn)");
        assertFalse(productSale2.getActive(), "Bản ghi 2 phải có active = false (sale chưa bắt đầu)");
        assertTrue(productSale3.getActive(), "Bản ghi 3 phải có active = true (sale đang hoạt động)");
        verify(productSaleRepo, times(3)).save(any(ProductSale.class)); // Gọi save() 3 lần
        System.out.println("Kết thúc kiểm tra TC_PS_68: Thành công");
        // Không cần rollback vì mock DB
    }

    /**
     * TC_PS_69 - Kiểm tra phương thức setDiscountActiveByProductId khi sale có start_date null.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (start_date null).
     */
    @Test
    public void testSetDiscountActiveByProductIdWithNullStartDate() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(null); // Start_date null
        sale.setEnd_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)); // 1 ngày sau

        ProductSale productSale = new ProductSale();
        productSale.setId(1);
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(20);
        productSale.setActive(true);

        mockProductSaleList.add(productSale);

        // Mock getProductSaleByIdProduct
        ProductSaleService productSaleServiceSpy = spy(productSaleService);
        doReturn(mockProductSaleList).when(productSaleServiceSpy).getProductSaleByIdProduct(1);

        // Không cần mock productSaleRepo.save() vì phương thức sẽ ném ngoại lệ trước khi gọi save()

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_69: Start_date null");
        assertThrows(NullPointerException.class, () -> productSaleServiceSpy.setDiscountActiveByProductId(1), "Phải ném ngoại lệ khi start_date là null");
        System.out.println("Kết thúc kiểm tra TC_PS_69: Thành công");
        // Không cần rollback vì mock DB
    }

    /**
     * TC_PS_70 - Kiểm tra phương thức setDiscountActiveByProductId khi sale có end_date trước start_date.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (end_date < start_date).
     */
    @Test
    public void testSetDiscountActiveByProductIdWithInvalidDateRange() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)); // 1 ngày sau
        sale.setEnd_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));  // 1 ngày trước

        ProductSale productSale = new ProductSale();
        productSale.setId(1);
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(20);
        productSale.setActive(true);

        mockProductSaleList.add(productSale);

        // Mock getProductSaleByIdProduct
        ProductSaleService productSaleServiceSpy = spy(productSaleService);
        doReturn(mockProductSaleList).when(productSaleServiceSpy).getProductSaleByIdProduct(1);

        // Mock productSaleRepo.save()
        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_70: End_date trước start_date");
        productSaleServiceSpy.setDiscountActiveByProductId(1);

        // Kiểm tra kết quả
        assertFalse(productSale.getActive(), "Bản ghi phải có active = false (sale không hợp lệ)");
        verify(productSaleRepo, times(1)).save(any(ProductSale.class)); // Gọi save() 1 lần
        System.out.println("Kết thúc kiểm tra TC_PS_70: Thành công");
        // Không cần rollback vì mock DB
    }

    /**
     * TC_PS_71 - Kiểm tra phương thức setDiscountActiveByProductId khi nhiều sale có cùng start_date.
     * Mục tiêu: Đảm bảo chỉ 1 bản ghi được đặt active = true.
     */
    @Test
    public void testSetDiscountActiveByProductIdWithSameStartDate() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setStart_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)); // 1 ngày trước
        sale1.setEnd_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));   // 1 ngày sau

        Sale sale2 = new Sale();
        sale2.setId(2);
        sale2.setStart_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)); // Cùng start_date
        sale2.setEnd_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 48));   // 2 ngày sau

        ProductSale productSale1 = new ProductSale();
        productSale1.setId(1);
        productSale1.setProduct(product);
        productSale1.setSale(sale1);
        productSale1.setDiscount(20);
        productSale1.setActive(false);

        ProductSale productSale2 = new ProductSale();
        productSale2.setId(2);
        productSale2.setProduct(product);
        productSale2.setSale(sale2);
        productSale2.setDiscount(30);
        productSale2.setActive(false);

        mockProductSaleList.add(productSale1);
        mockProductSaleList.add(productSale2);

        // Mock getProductSaleByIdProduct
        ProductSaleService productSaleServiceSpy = spy(productSaleService);
        doReturn(mockProductSaleList).when(productSaleServiceSpy).getProductSaleByIdProduct(1);

        // Mock productSaleRepo.save()
        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_71: Nhiều sale có cùng start_date");
        productSaleServiceSpy.setDiscountActiveByProductId(1);

        // Kiểm tra kết quả
        long activeCount = mockProductSaleList.stream().filter(ProductSale::getActive).count();
        assertEquals(1, activeCount, "Chỉ 1 bản ghi được đặt active = true");
        verify(productSaleRepo, times(2)).save(any(ProductSale.class)); // Gọi save() 2 lần
        System.out.println("Kết thúc kiểm tra TC_PS_71: Thành công");
        // Không cần rollback vì mock DB
    }

    /**
     * TC_PS_72 - Kiểm tra phương thức setDiscountActiveByProductId với số lượng bản ghi lớn.
     * Mục tiêu: Đảm bảo hiệu suất tốt khi có nhiều bản ghi.
     */
    @Test
    public void testSetDiscountActiveByProductIdWithLargeData() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)); // 1 ngày trước
        sale.setEnd_date(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));   // 1 ngày sau

        for (int i = 0; i < 10000; i++) {
            ProductSale productSale = new ProductSale();
            productSale.setId(i + 1);
            productSale.setProduct(product);
            productSale.setSale(sale);
            productSale.setDiscount(20);
            productSale.setActive(false);
            mockProductSaleList.add(productSale);
        }

        // Mock getProductSaleByIdProduct
        ProductSaleService productSaleServiceSpy = spy(productSaleService);
        doReturn(mockProductSaleList).when(productSaleServiceSpy).getProductSaleByIdProduct(1);

        // Mock productSaleRepo.save()
        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_72: Dữ liệu lớn");
        long startTime = System.currentTimeMillis();
        productSaleServiceSpy.setDiscountActiveByProductId(1);
        long endTime = System.currentTimeMillis();

        // Kiểm tra kết quả
        System.out.println("Thời gian thực thi: " + (endTime - startTime) + "ms");
        long activeCount = mockProductSaleList.stream().filter(ProductSale::getActive).count();
        assertEquals(1, activeCount, "Chỉ 1 bản ghi được đặt active = true");
        assertTrue((endTime - startTime) < 1000, "Hiệu suất phải dưới 1 giây");
        verify(productSaleRepo, times(10000)).save(any(ProductSale.class)); // Gọi save() 10,000 lần
        System.out.println("Kết thúc kiểm tra TC_PS_72: Thành công");
        // Không cần rollback vì mock DB
    }
}