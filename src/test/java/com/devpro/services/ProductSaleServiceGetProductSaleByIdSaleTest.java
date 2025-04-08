package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devpro.entities.Product;
import com.devpro.entities.ProductSale;
import com.devpro.entities.Sale;
import com.devpro.repositories.ProductSaleRepo;
import com.devpro.repositories.SaleRepo;

/**
 * Lớp kiểm thử đơn vị cho ProductSaleService, tập trung vào phương thức getProductSaleByIdSale().
 * Sử dụng Mockito để mock các phụ thuộc như EntityManager, ProductSaleRepo và SaleRepo.
 * Log kết quả minh chứng bằng SLF4J.
 */
public class ProductSaleServiceGetProductSaleByIdSaleTest {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductSaleServiceGetProductSaleByIdSaleTest.class);

    @InjectMocks
    private ProductSaleService productSaleService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private ProductSaleRepo productSaleRepo;

    @Mock
    private SaleRepo saleRepo;

    @Mock
    private Query mockQuery;

    private List<ProductSale> danhSachProductSale;

    @BeforeEach
    public void thietLap() {
        MockitoAnnotations.initMocks(this);
        danhSachProductSale = new ArrayList<>();
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(danhSachProductSale);
        logger.info("Thiết lập dữ liệu kiểm thử hoàn tất.");
    }

    @AfterEach
    public void hoanTac() {
        danhSachProductSale.clear();
        reset(mockQuery, entityManager, productSaleRepo, saleRepo);
        logger.info("Hoàn tác dữ liệu kiểm thử hoàn tất.");
    }

    // TC_getProductSaleByIdSale_01: Tồn tại ProductSale với sale_id hợp lệ
    @Test
    public void testGetProductSaleByIdSale_WithValidId() {
        logger.info("Bắt đầu TC_getProductSaleByIdSale_01: Kiểm tra sale_id hợp lệ và tồn tại.");
        // Chuẩn bị: Mock trả về 1 ProductSale với sale_id = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setId(1);
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức
        List<ProductSale> result = productSaleService.getProductSaleByIdSale(1);

        // Kiểm tra và log
        assertNotNull(result, "Danh sách không được null");
        assertEquals(1, result.size(), "Danh sách phải chứa 1 ProductSale");
        assertEquals(1, result.get(0).getSale().getId(), "sale_id của ProductSale phải là 1");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_getProductSaleByIdSale_01: Trả về danh sách chứa 1 ProductSale như mong đợi.");
    }

    // TC_getProductSaleByIdSale_02: Nhiều ProductSale khớp với sale_id
    @Test
    public void testGetProductSaleByIdSale_WithMultipleProductSales() {
        logger.info("Bắt đầu TC_getProductSaleByIdSale_02: Kiểm tra nhiều ProductSale khớp với sale_id.");
        // Chuẩn bị: Mock trả về 3 ProductSale với sale_id = 1
        Sale khuyenMai = new Sale();
        khuyenMai.setId(1);
        for (int i = 1; i <= 3; i++) {
            Product sanPham = new Product();
            sanPham.setId(i);
            ProductSale ps = new ProductSale();
            ps.setId(i);
            ps.setProduct(sanPham);
            ps.setSale(khuyenMai);
            danhSachProductSale.add(ps);
        }

        // Thực hiện: Gọi phương thức
        List<ProductSale> result = productSaleService.getProductSaleByIdSale(1);

        // Kiểm tra và log
        assertNotNull(result, "Danh sách không được null");
        assertEquals(3, result.size(), "Danh sách phải chứa 3 ProductSale");
        for (ProductSale ps : result) {
            assertEquals(1, ps.getSale().getId(), "sale_id của ProductSale phải là 1");
        }
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_getProductSaleByIdSale_02: Trả về danh sách chứa 3 ProductSale như mong đợi.");
    }

    // TC_getProductSaleByIdSale_03: Không có ProductSale nào khớp với sale_id
    @Test
    public void testGetProductSaleByIdSale_WithNoMatchingSaleId() {
        logger.info("Bắt đầu TC_getProductSaleByIdSale_03: Kiểm tra không có ProductSale khớp với sale_id.");
        // Chuẩn bị: Mock trả về danh sách rỗng
        when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện: Gọi phương thức
        List<ProductSale> result = productSaleService.getProductSaleByIdSale(2);

        // Kiểm tra và log
        assertNotNull(result, "Danh sách không được null");
        assertTrue(result.isEmpty(), "Danh sách phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_getProductSaleByIdSale_03: Trả về danh sách rỗng như mong đợi.");
    }

    // TC_getProductSaleByIdSale_04: sale_id là null
    @Test
    public void testGetProductSaleByIdSale_WithNullId() {
        logger.info("Bắt đầu TC_getProductSaleByIdSale_04: Kiểm tra sale_id là null.");
        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(IllegalArgumentException.class, () -> {
            productSaleService.getProductSaleByIdSale(null);
        }, "Phải ném IllegalArgumentException khi sale_id là null");

        // Kiểm tra tương tác và log
        verify(entityManager, never()).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_getProductSaleByIdSale_04: Ném IllegalArgumentException như mong đợi.");
    }

    // TC_getProductSaleByIdSale_05: sale_id âm
    @Test
    public void testGetProductSaleByIdSale_WithNegativeId() {
        logger.info("Bắt đầu TC_getProductSaleByIdSale_05: Kiểm tra sale_id âm.");
        // Chuẩn bị: Mock trả về danh sách rỗng
        when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện: Gọi phương thức
        List<ProductSale> result = productSaleService.getProductSaleByIdSale(-1);

        // Kiểm tra và log
        assertNotNull(result, "Danh sách không được null");
        assertTrue(result.isEmpty(), "Danh sách phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_getProductSaleByIdSale_05: Trả về danh sách rỗng như mong đợi.");
    }

    // TC_getProductSaleByIdSale_06: sale_id = 0
    @Test
    public void testGetProductSaleByIdSale_WithZeroId() {
        logger.info("Bắt đầu TC_getProductSaleByIdSale_06: Kiểm tra sale_id = 0.");
        // Chuẩn bị: Mock trả về danh sách rỗng
        when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện: Gọi phương thức
        List<ProductSale> result = productSaleService.getProductSaleByIdSale(0);

        // Kiểm tra và log
        assertNotNull(result, "Danh sách không được null");
        assertTrue(result.isEmpty(), "Danh sách phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_getProductSaleByIdSale_06: Trả về danh sách rỗng như mong đợi.");
    }

    // TC_getProductSaleByIdSale_07: Lỗi truy vấn cơ sở dữ liệu
    @Test
    public void testGetProductSaleByIdSale_WithDatabaseError() {
        logger.info("Bắt đầu TC_getProductSaleByIdSale_07: Kiểm tra lỗi truy vấn cơ sở dữ liệu.");
        // Chuẩn bị: Mock createNativeQuery ném RuntimeException
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class)))
                .thenThrow(new RuntimeException("Lỗi cơ sở dữ liệu"));

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(RuntimeException.class, () -> {
            productSaleService.getProductSaleByIdSale(1);
        }, "Phải ném RuntimeException khi truy vấn thất bại");

        // Kiểm tra tương tác và log
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_getProductSaleByIdSale_07: Ném RuntimeException như mong đợi.");
    }

    // TC_getProductSaleByIdSale_08: ProductSale có các thuộc tính null
    @Test
    public void testGetProductSaleByIdSale_WithNullFields() {
        logger.info("Bắt đầu TC_getProductSaleByIdSale_08: Kiểm tra ProductSale có các thuộc tính null.");
        // Chuẩn bị: Mock trả về 1 ProductSale với product = null, sale = null
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(null);
        ps.setSale(null);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức
        List<ProductSale> result = productSaleService.getProductSaleByIdSale(1);

        // Kiểm tra và log
        assertNotNull(result, "Danh sách không được null");
        assertEquals(1, result.size(), "Danh sách phải chứa 1 ProductSale");
        assertNull(result.get(0).getProduct(), "product phải là null");
        assertNull(result.get(0).getSale(), "sale phải là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_getProductSaleByIdSale_08: Trả về danh sách chứa ProductSale với các thuộc tính null như mong đợi.");
    }

    // TC_getProductSaleByIdSale_09: Số lượng lớn ProductSale
    @Test
    public void testGetProductSaleByIdSale_WithLargeDataSet() {
        logger.info("Bắt đầu TC_getProductSaleByIdSale_09: Kiểm tra với số lượng lớn ProductSale.");
        // Chuẩn bị: Mock trả về 100 ProductSale với sale_id = 1
        Sale khuyenMai = new Sale();
        khuyenMai.setId(1);
        for (int i = 1; i <= 100; i++) {
            Product sanPham = new Product();
            sanPham.setId(i);
            ProductSale ps = new ProductSale();
            ps.setId(i);
            ps.setProduct(sanPham);
            ps.setSale(khuyenMai);
            danhSachProductSale.add(ps);
        }

        // Thực hiện: Gọi phương thức
        List<ProductSale> result = productSaleService.getProductSaleByIdSale(1);

        // Kiểm tra và log
        assertNotNull(result, "Danh sách không được null");
        assertEquals(100, result.size(), "Danh sách phải chứa 100 ProductSale");
        for (ProductSale ps : result) {
            assertEquals(1, ps.getSale().getId(), "sale_id của ProductSale phải là 1");
        }
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_getProductSaleByIdSale_09: Trả về danh sách chứa 100 ProductSale như mong đợi.");
    }

    // TC_getProductSaleByIdSale_10: Gọi nhiều lần với cùng sale_id
    @Test
    public void testGetProductSaleByIdSale_MultipleCalls() {
        logger.info("Bắt đầu TC_getProductSaleByIdSale_10: Kiểm tra gọi nhiều lần với cùng sale_id.");
        // Chuẩn bị: Mock trả về 1 ProductSale với sale_id = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setId(1);
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức nhiều lần
        List<ProductSale> result1 = productSaleService.getProductSaleByIdSale(1);
        List<ProductSale> result2 = productSaleService.getProductSaleByIdSale(1);
        List<ProductSale> result3 = productSaleService.getProductSaleByIdSale(1);

        // Kiểm tra và log
        assertNotNull(result1, "Danh sách lần 1 không được null");
        assertNotNull(result2, "Danh sách lần 2 không được null");
        assertNotNull(result3, "Danh sách lần 3 không được null");
        assertEquals(1, result1.size(), "Danh sách lần 1 phải chứa 1 ProductSale");
        assertEquals(1, result2.size(), "Danh sách lần 2 phải chứa 1 ProductSale");
        assertEquals(1, result3.size(), "Danh sách lần 3 phải chứa 1 ProductSale");
        assertEquals(result1.get(0).getId(), result2.get(0).getId(), "ProductSale lần 1 và lần 2 phải có cùng id");
        assertEquals(result2.get(0).getId(), result3.get(0).getId(), "ProductSale lần 2 và lần 3 phải có cùng id");
        verify(entityManager, times(3)).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_getProductSaleByIdSale_10: Trả về cùng một danh sách ProductSale mỗi lần gọi như mong đợi.");
    }
}