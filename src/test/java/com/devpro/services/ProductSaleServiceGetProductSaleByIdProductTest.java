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
 * Lớp kiểm thử đơn vị cho phương thức getProductSaleByIdProduct của ProductSaleService.
 */
@ExtendWith(MockitoExtension.class)
public class ProductSaleServiceGetProductSaleByIdProductTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @InjectMocks
    private ProductSaleService productSaleService;

    /**
     * TC_PS_51 - Kiểm tra phương thức getProductSaleByIdProduct khi id là null.
     * Mục tiêu: Đảm bảo ném ngoại lệ khi id là null.
     */
    @Test
    public void testGetProductSaleByIdProductWithNullId() {
        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_51: Id là null");
        assertThrows(IllegalArgumentException.class, () -> productSaleService.getProductSaleByIdProduct(null), "Phải ném ngoại lệ khi id là null");
        verify(entityManager, never()).createNativeQuery(anyString(), eq(ProductSale.class)); // Không gọi DB
        System.out.println("Kết thúc kiểm tra TC_PS_51: Thành công");
        // Không cần rollback vì không truy vấn DB
    }

    /**
     * TC_PS_52 - Kiểm tra phương thức getProductSaleByIdProduct khi id không hợp lệ.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi id âm hoặc ở giá trị biên.
     */
    @Test
    public void testGetProductSaleByIdProductWithInvalidId() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử với id âm
        System.out.println("Bắt đầu kiểm tra TC_PS_52: Id không hợp lệ (âm)");
        List<ProductSale> resultList1 = productSaleService.getProductSaleByIdProduct(-1);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về (id = -1): " + resultList1.size() + " bản ghi");
        assertNotNull(resultList1, "Danh sách kết quả không được null");
        assertTrue(resultList1.isEmpty(), "Danh sách phải rỗng khi id âm");

        // Thực hiện kiểm thử với id ở giá trị biên
        System.out.println("Kiểm tra TC_PS_52: Id ở giá trị biên (Integer.MAX_VALUE)");
        List<ProductSale> resultList2 = productSaleService.getProductSaleByIdProduct(Integer.MAX_VALUE);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về (id = Integer.MAX_VALUE): " + resultList2.size() + " bản ghi");
        assertNotNull(resultList2, "Danh sách kết quả không được null");
        assertTrue(resultList2.isEmpty(), "Danh sách phải rỗng khi id ở giá trị biên");
        verify(entityManager, times(2)).createNativeQuery(anyString(), eq(ProductSale.class)); // Gọi DB 2 lần
        System.out.println("Kết thúc kiểm tra TC_PS_52: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_53 - Kiểm tra phương thức getProductSaleByIdProduct khi id không tồn tại trong DB.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi id không tồn tại.
     */
    @Test
    public void testGetProductSaleByIdProductWithNonExistentId() {
        // Sắp xếp dữ liệu giả lập
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_53: Id không tồn tại trong DB");
        List<ProductSale> resultList = productSaleService.getProductSaleByIdProduct(999);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi id không tồn tại");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_53: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_54 - Kiểm tra phương thức getProductSaleByIdProduct khi không có bản ghi khớp với id.
     * Mục tiêu: Đảm bảo trả về danh sách rỗng khi không có dữ liệu khớp.
     */
    @Test
    public void testGetProductSaleByIdProductWithNoMatchingRecords() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(2); // product_id khác với id cần tìm

        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(new Date(2025 - 1900, 4, 1));
        sale.setEnd_date(new Date(2025 - 1900, 4, 10));

        ProductSale productSale = new ProductSale();
        productSale.setId(1);
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(20);
        productSale.setActive(true);

        mockProductSaleList.add(productSale);
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>()); // Không có bản ghi khớp

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_54: Không có bản ghi khớp với id");
        List<ProductSale> resultList = productSaleService.getProductSaleByIdProduct(1);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertTrue(resultList.isEmpty(), "Danh sách phải rỗng khi không có bản ghi khớp");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_54: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_55 - Kiểm tra phương thức getProductSaleByIdProduct khi có bản ghi khớp với id.
     * Mục tiêu: Đảm bảo trả về đúng danh sách các bản ghi khớp với id.
     */
    @Test
    public void testGetProductSaleByIdProductWithMatchingRecords() {
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
        System.out.println("Bắt đầu kiểm tra TC_PS_55: Có bản ghi khớp với id");
        List<ProductSale> resultList = productSaleService.getProductSaleByIdProduct(1);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(2, resultList.size(), "Phải trả về 2 bản ghi");
        assertTrue(resultList.stream().allMatch(ps -> ps.getProduct().getId() == 1), "Tất cả bản ghi phải có product_id = 1");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_55: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_56 - Kiểm tra phương thức getProductSaleByIdProduct khi bản ghi có sale_id null.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (sale_id null).
     */
    @Test
    public void testGetProductSaleByIdProductWithNullSale() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        ProductSale productSale = new ProductSale();
        productSale.setId(1);
        productSale.setProduct(product);
        productSale.setSale(null); // Sale null
        productSale.setDiscount(20);
        productSale.setActive(true);

        mockProductSaleList.add(productSale);
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_56: Sale_id null");
        List<ProductSale> resultList = productSaleService.getProductSaleByIdProduct(1);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 bản ghi");
        assertNull(resultList.get(0).getSale(), "Sale phải là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_56: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_57 - Kiểm tra phương thức getProductSaleByIdProduct khi bản ghi có discount không hợp lệ.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu lỗi (discount âm).
     */
    @Test
    public void testGetProductSaleByIdProductWithInvalidDiscount() {
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
        System.out.println("Bắt đầu kiểm tra TC_PS_57: Discount không hợp lệ");
        List<ProductSale> resultList = productSaleService.getProductSaleByIdProduct(1);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 bản ghi");
        assertEquals(-10, resultList.get(0).getDiscount(), "Discount phải là -10");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_57: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_58 - Kiểm tra phương thức getProductSaleByIdProduct khi sale_id không tồn tại trong DB.
     * Mục tiêu: Đảm bảo không crash khi dữ liệu không nhất quán (sale_id không tồn tại).
     */
    @Test
    public void testGetProductSaleByIdProductWithNonExistentSale() {
        // Sắp xếp dữ liệu giả lập
        List<ProductSale> mockProductSaleList = new ArrayList<>();

        Product product = new Product();
        product.setId(1);

        Sale sale = new Sale();
        sale.setId(999); // Sale_id không tồn tại trong DB
        sale.setStart_date(new Date(2025 - 1900, 4, 1));
        sale.setEnd_date(new Date(2025 - 1900, 4, 10));

        ProductSale productSale = new ProductSale();
        productSale.setId(1);
        productSale.setProduct(product);
        productSale.setSale(sale);
        productSale.setDiscount(20);
        productSale.setActive(true);

        mockProductSaleList.add(productSale);
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockProductSaleList);

        // Thực hiện kiểm thử
        System.out.println("Bắt đầu kiểm tra TC_PS_58: Sale_id không tồn tại trong DB");
        List<ProductSale> resultList = productSaleService.getProductSaleByIdProduct(1);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 bản ghi");
        assertEquals(999, resultList.get(0).getSale().getId(), "Sale_id phải là 999");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_58: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_59 - Kiểm tra phương thức getProductSaleByIdProduct với số lượng bản ghi lớn.
     * Mục tiêu: Đảm bảo hiệu suất tốt khi có nhiều bản ghi khớp với id.
     */
    @Test
    public void testGetProductSaleByIdProductWithLargeData() {
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
        System.out.println("Bắt đầu kiểm tra TC_PS_59: Dữ liệu lớn");
        long startTime = System.currentTimeMillis();
        List<ProductSale> resultList = productSaleService.getProductSaleByIdProduct(1);
        long endTime = System.currentTimeMillis();

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        System.out.println("Thời gian thực thi: " + (endTime - startTime) + "ms");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(10000, resultList.size(), "Phải trả về 10,000 bản ghi");
        assertTrue((endTime - startTime) < 1000, "Hiệu suất phải dưới 1 giây");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_59: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }

    /**
     * TC_PS_60 - Kiểm tra phương thức getProductSaleByIdProduct khi bản ghi active = true nhưng sale hết hạn.
     * Mục tiêu: Đảm bảo trả về bản ghi, phát hiện dữ liệu không nhất quán.
     */
    @Test
    public void testGetProductSaleByIdProductWithActiveButExpiredSale() {
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
        System.out.println("Bắt đầu kiểm tra TC_PS_60: Active = true nhưng sale hết hạn");
        List<ProductSale> resultList = productSaleService.getProductSaleByIdProduct(1);

        // Kiểm tra kết quả
        System.out.println("Kết quả trả về: " + resultList.size() + " bản ghi");
        assertNotNull(resultList, "Danh sách kết quả không được null");
        assertEquals(1, resultList.size(), "Phải trả về 1 bản ghi");
        assertTrue(resultList.get(0).getActive(), "Bản ghi phải có active = true");
        assertTrue(resultList.get(0).getSale().getEnd_date().before(new Date()), "Sale phải đã hết hạn");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class)); // Check DB call
        System.out.println("Kết thúc kiểm tra TC_PS_60: Thành công");
        // Không cần rollback vì chỉ đọc dữ liệu
    }
}