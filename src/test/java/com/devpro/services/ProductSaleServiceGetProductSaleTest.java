package com.devpro.services;

import com.devpro.entities.Product;
import com.devpro.entities.ProductSale;
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
 * Lớp kiểm thử đơn vị cho phương thức getProductSale của ProductSaleService.
 */
@ExtendWith(MockitoExtension.class)
public class ProductSaleServiceGetProductSaleTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private ProductSaleService productSaleService;

    /**
     * TC_PS_41 - Kiểm tra phương thức getProductSale khi không có bản ghi trong DB.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi không có dữ liệu.
     */
    @Test
    public void testGetProductSaleWithNoData() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_41: Không có bản ghi trong DB");
        List<ProductSale> resultList = productSaleService.getProductSale();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi không có dữ liệu");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_41: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_42 - Kiểm tra phương thức getProductSale khi tất cả bản ghi có active = true.
     * Mục tiêu: Đảm bảo trả về tất cả bản ghi, không lọc theo active.
     */
    @Test
    public void testGetProductSaleWithAllActiveRecords() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(new Date(2025 - 1900, 4, 1));
        sale.setEnd_date(new Date(2025 - 1900, 4, 10));

        ProductSale productSale1 = new ProductSale();
        productSale1.setId(1);
        productSale1.setProduct(product);
        productSale1.setSale(sale);
        productSale1.setDiscount(20);
        productSale1.setActive(true);

        ProductSale productSale2 = new ProductSale();
        productSale2.setId(2);
        productSale2.setProduct(product);
        productSale2.setSale(sale);
        productSale2.setDiscount(30);
        productSale2.setActive(true);

        mockProductSaleList.add(productSale1);
        mockProductSaleList.add(productSale2);
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_42: Tất cả bản ghi có active = true");
        List<ProductSale> resultList = productSaleService.getProductSale();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 bản ghi");
        assertTrue(resultList.stream().allMatch(ps -> ps.getActive()), "Tất cả bản ghi có active = true");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_42: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_43 - Kiểm tra phương thức getProductSale khi có cả active = true và false.
     * Mục tiêu: Đảm bảo trả về tất cả bản ghi, không lọc theo active.
     */
    @Test
    public void testGetProductSaleWithMixedActiveStatus() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(new Date(2025 - 1900, 4, 1));
        sale.setEnd_date(new Date(2025 - 1900, 4, 10));

        ProductSale productSale1 = new ProductSale();
        productSale1.setId(1);
        productSale1.setProduct(product);
        productSale1.setSale(sale);
        productSale1.setDiscount(20);
        productSale1.setActive(true);

        ProductSale productSale2 = new ProductSale();
        productSale2.setId(2);
        productSale2.setProduct(product);
        productSale2.setSale(sale);
        productSale2.setDiscount(30);
        productSale2.setActive(false);

        mockProductSaleList.add(productSale1);
        mockProductSaleList.add(productSale2);
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_43: Có cả active = true và false");
        List<ProductSale> resultList = productSaleService.getProductSale();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 bản ghi");
        assertEquals(1, resultList.stream().filter(ps -> ps.getActive()).count(), "Phải có 1 bản ghi với active = true");
        assertEquals(1, resultList.stream().filter(ps -> !ps.getActive()).count(), "Phải có 1 bản ghi với active = false");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_43: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_44 - Kiểm tra phương thức getProductSale khi có bản ghi trùng product_id và sale_id.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu trùng lặp (có thể là lỗi nghiệp vụ).
     */
    @Test
    public void testGetProductSaleWithDuplicateRecords() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(new Date(2025 - 1900, 4, 1));
        sale.setEnd_date(new Date(2025 - 1900, 4, 10));

        ProductSale productSale1 = new ProductSale();
        productSale1.setId(1);
        productSale1.setProduct(product);
        productSale1.setSale(sale);
        productSale1.setDiscount(20);
        productSale1.setActive(true);

        ProductSale productSale2 = new ProductSale();
        productSale2.setId(2);
        productSale2.setProduct(product); // Trùng product_id
        productSale2.setSale(sale);       // Trùng sale_id
        productSale2.setDiscount(30);
        productSale2.setActive(false);

        mockProductSaleList.add(productSale1);
        mockProductSaleList.add(productSale2);
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_44: Bản ghi trùng product_id và sale_id");
        List<ProductSale> resultList = productSaleService.getProductSale();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 bản ghi");
        assertEquals(2, resultList.stream().filter(ps -> ps.getProduct().getId() == 1 && ps.getSale().getId() == 1).count(), "Phải có 2 bản ghi trùng product_id và sale_id");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_44: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_45 - Kiểm tra phương thức getProductSale khi bản ghi có product_id null.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (product_id null).
     */
    @Test
    public void testGetProductSaleWithNullProduct() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(new Date(2025 - 1900, 4, 1));
        sale.setEnd_date(new Date(2025 - 1900, 4, 10));

        ProductSale productSale = new ProductSale();
        productSale.setId(1);
        productSale.setProduct(null); // Product null
        productSale.setSale(sale);
        productSale.setDiscount(20);
        productSale.setActive(true);

        mockProductSaleList.add(productSale);
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_45: Product_id null");
        List<ProductSale> resultList = productSaleService.getProductSale();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 bản ghi");
        assertNull(resultList.get(0).getProduct(), "Product phải là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_45: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_46 - Kiểm tra phương thức getProductSale khi bản ghi có discount không hợp lệ.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (discount âm).
     */
    @Test
    public void testGetProductSaleWithInvalidDiscount() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(new Date(2025 - 1900, 4, 1));
        sale.setEnd_date(new Date(2025 - 1900, 4, 10));

        ProductSale productSale = new ProductSale();
        productSale.setId(1);
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(-10); // Discount âm
        productSale.setActive(true);

        mockProductSaleList.add(productSale);
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_46: Discount không hợp lệ");
        List<ProductSale> resultList = productSaleService.getProductSale();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 bản ghi");
        assertEquals(-10, resultList.get(0).getDiscount(), "Discount phải là -10");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_46: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_47 - Kiểm tra phương thức getProductSale khi discount ở giá trị biên.
     * Mục tiêu: Đảm bảo trả về đúng các bản ghi với discount ở giá trị biên (0, 100).
     */
    @Test
    public void testGetProductSaleWithBoundaryDiscount() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(new Date(2025 - 1900, 4, 1));
        sale.setEnd_date(new Date(2025 - 1900, 4, 10));

        ProductSale productSale1 = new ProductSale();
        productSale1.setId(1);
        productSale1.setProduct(product);
        productSale1.setSale(sale);
        productSale1.setDiscount(0); // Giá trị biên dưới
        productSale1.setActive(true);

        ProductSale productSale2 = new ProductSale();
        productSale2.setId(2);
        productSale2.setProduct(product);
        productSale2.setSale(sale);
        productSale2.setDiscount(100); // Giá trị biên trên
        productSale2.setActive(false);

        mockProductSaleList.add(productSale1);
        mockProductSaleList.add(productSale2);
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_47: Discount ở giá trị biên");
        List<ProductSale> resultList = productSaleService.getProductSale();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 bản ghi");
        assertTrue(resultList.stream().anyMatch(ps -> ps.getDiscount() == 0), "Phải có bản ghi với discount = 0");
        assertTrue(resultList.stream().anyMatch(ps -> ps.getDiscount() == 100), "Phải có bản ghi với discount = 100");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_47: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_48 - Kiểm tra phương thức getProductSale với số lượng bản ghi lớn.
     * Mục tiêu: Đảm bảo hiệu suất tốt khi có nhiều bản ghi.
     */
    @Test
    public void testGetProductSaleWithLargeData() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(new Date(2025 - 1900, 4, 1));
        sale.setEnd_date(new Date(2025 - 1900, 4, 10));

        for (int i = 0; i < 10000; i++) {
            ProductSale productSale = new ProductSale();
            productSale.setId(i + 1);
            productSale.setProduct(product);
            productSale.setSale(sale);
            productSale.setDiscount(20);
            productSale.setActive(i % 2 == 0); // Xen kẽ active true/false
            mockProductSaleList.add(productSale);
        }
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_48: Dữ liệu lớn");
        long startTime = System.currentTimeMillis();
        List<ProductSale> resultList = productSaleService.getProductSale();
        long endTime = System.currentTimeMillis();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        System.out.println("Thời gian thực thi: " + (endTime - startTime) + "ms");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(10000, resultList.size(), "Phải trả về 10,000 bản ghi");
        assertTrue((endTime - startTime) < 1000, "Hiệu suất phải dưới 1 giây");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_48: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_49 - Kiểm tra phương thức getProductSale khi DB gặp lỗi.
     * Mục tiêu: Đảm bảo ném ngoại lệ khi DB thất bại.
     */
    @Test
    public void testGetProductSaleWithDatabaseError() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(new JpaSystemException(new RuntimeException("Lỗi DB")));

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_49: Lỗi cơ sở dữ liệu");
        assertThrows(JpaSystemException.class, () -> productSaleService.getProductSale(), "Phải ném ngoại lệ khi DB lỗi");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_49: Thành công");
        // Không cần rollback vì không thay đổi dữ liệu
    }

    /**
     * TC_PS_50 - Kiểm tra phương thức getProductSale khi bản ghi active = true nhưng sale hết hạn.
     * Mục tiêu: Đảm bảo trả về bản ghi, phát hiện dữ liệu không nhất quán.
     */
    @Test
    public void testGetProductSaleWithActiveButExpiredSale() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(new Date(2024 - 1900, 4, 1)); // 2024-05-01
        sale.setEnd_date(new Date(2024 - 1900, 4, 10));  // 2024-05-10 (hết hạn)

        ProductSale productSale = new ProductSale();
        productSale.setId(1);
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(20);
        productSale.setActive(true); // Active nhưng sale đã hết hạn

        mockProductSaleList.add(productSale);
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_50: Active = true nhưng sale hết hạn");
        List<ProductSale> resultList = productSaleService.getProductSale();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 bản ghi");
        assertTrue(resultList.get(0).getActive(), "Bản ghi phải có active = true");
        assertTrue(resultList.get(0).getSale().getEnd_date().before(new Date()), "Sale phải đã hết hạn");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_50: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }
}