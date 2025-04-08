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
 * Lớp kiểm thử đơn vị cho ProductSaleService, tập trung vào phương thức getDiscountByIdProduct().
 * Sử dụng Mockito để mock các phụ thuộc như EntityManager, ProductSaleRepo và SaleRepo.
 * Log kết quả minh chứng bằng SLF4J.
 */
public class ProductSaleServiceGetDiscountByIdProductTest {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductSaleServiceGetDiscountByIdProductTest.class);

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

    // TC_getDiscountByIdProduct_01: Có ProductSale khớp với productId và active = true
    @Test
    public void testGetDiscountByIdProduct_WithMatchingProductIdAndActive() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_01: Kiểm tra productId hợp lệ và active = true.");
        // Chuẩn bị: Một ProductSale với productId = 1, active = true, discount = 10
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setDiscount(10);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức
        int result = productSaleService.getDiscountByIdProduct(1);

        // Kiểm tra và log
        assertEquals(10, result, "Discount phải là 10");
        logger.info("Kết quả TC_getDiscountByIdProduct_01: Trả về discount = 10 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_02: Có ProductSale khớp với productId nhưng active = false
    @Test
    public void testGetDiscountByIdProduct_WithMatchingProductIdButNotActive() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_02: Kiểm tra productId hợp lệ nhưng active = false.");
        // Chuẩn bị: Một ProductSale với productId = 1, active = false, discount = 20
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setDiscount(20);
        ps.setActive(false);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức
        int result = productSaleService.getDiscountByIdProduct(1);

        // Kiểm tra và log
        assertEquals(0, result, "Discount phải là 0 vì active = false");
        logger.info("Kết quả TC_getDiscountByIdProduct_02: Trả về discount = 0 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_03: Không có ProductSale nào khớp với productId
    @Test
    public void testGetDiscountByIdProduct_WithNoMatchingProductId() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_03: Kiểm tra productId không tồn tại.");
        // Chuẩn bị: Một ProductSale với productId = 1, nhưng gọi với productId = 2
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setDiscount(10);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức với productId = 2
        int result = productSaleService.getDiscountByIdProduct(2);

        // Kiểm tra và log
        assertEquals(0, result, "Discount phải là 0 vì không tìm thấy productId");
        logger.info("Kết quả TC_getDiscountByIdProduct_03: Trả về discount = 0 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_04: Danh sách ProductSale rỗng
    @Test
    public void testGetDiscountByIdProduct_WithEmptyList() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_04: Kiểm tra danh sách rỗng.");
        // Chuẩn bị: Danh sách ProductSale rỗng
        when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện: Gọi phương thức
        int result = productSaleService.getDiscountByIdProduct(1);

        // Kiểm tra và log
        assertEquals(0, result, "Discount phải là 0 vì danh sách rỗng");
        logger.info("Kết quả TC_getDiscountByIdProduct_04: Trả về discount = 0 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_05: productId là null
    @Test
    public void testGetDiscountByIdProduct_WithNullProductId() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_05: Kiểm tra productId là null.");
        // Chuẩn bị: Một ProductSale với productId = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setDiscount(10);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức với productId = null
        int result = productSaleService.getDiscountByIdProduct(null);

        // Kiểm tra và log
        assertEquals(0, result, "Discount phải là 0 khi productId là null");
        logger.info("Kết quả TC_getDiscountByIdProduct_05: Trả về discount = 0 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_06: Nhiều ProductSale khớp với productId, nhưng chỉ một cái active = true
    @Test
    public void testGetDiscountByIdProduct_WithMultipleMatchingProductId() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_06: Kiểm tra nhiều ProductSale khớp với productId.");
        // Chuẩn bị: 2 ProductSale với productId = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai1 = new Sale();
        ProductSale ps1 = new ProductSale();
        ps1.setId(1);
        ps1.setProduct(sanPham);
        ps1.setSale(khuyenMai1);
        ps1.setDiscount(10);
        ps1.setActive(true);
        Sale khuyenMai2 = new Sale();
        ProductSale ps2 = new ProductSale();
        ps2.setId(2);
        ps2.setProduct(sanPham);
        ps2.setSale(khuyenMai2);
        ps2.setDiscount(20);
        ps2.setActive(false);
        danhSachProductSale.add(ps1);
        danhSachProductSale.add(ps2);

        // Thực hiện: Gọi phương thức
        int result = productSaleService.getDiscountByIdProduct(1);

        // Kiểm tra và log
        assertEquals(10, result, "Discount phải là 10 vì chỉ ps1 active = true");
        logger.info("Kết quả TC_getDiscountByIdProduct_06: Trả về discount = 10 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_07: Nhiều ProductSale khớp với productId, nhưng tất cả active = false
    @Test
    public void testGetDiscountByIdProduct_WithMultipleMatchingButNotActive() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_07: Kiểm tra nhiều ProductSale nhưng không active.");
        // Chuẩn bị: 2 ProductSale với productId = 1, cả 2 active = false
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai1 = new Sale();
        ProductSale ps1 = new ProductSale();
        ps1.setId(1);
        ps1.setProduct(sanPham);
        ps1.setSale(khuyenMai1);
        ps1.setDiscount(10);
        ps1.setActive(false);
        Sale khuyenMai2 = new Sale();
        ProductSale ps2 = new ProductSale();
        ps2.setId(2);
        ps2.setProduct(sanPham);
        ps2.setSale(khuyenMai2);
        ps2.setDiscount(20);
        ps2.setActive(false);
        danhSachProductSale.add(ps1);
        danhSachProductSale.add(ps2);

        // Thực hiện: Gọi phương thức
        int result = productSaleService.getDiscountByIdProduct(1);

        // Kiểm tra và log
        assertEquals(0, result, "Discount phải là 0 vì không có ProductSale nào active");
        logger.info("Kết quả TC_getDiscountByIdProduct_07: Trả về discount = 0 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_08: ProductSale với product = null
    @Test
    public void testGetDiscountByIdProduct_WithNullProduct() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_08: Kiểm tra ProductSale với product = null.");
        // Chuẩn bị: Một ProductSale với product = null
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(null);
        ps.setSale(new Sale());
        ps.setDiscount(10);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(NullPointerException.class, () -> {
            productSaleService.getDiscountByIdProduct(1);
        }, "Phải ném NullPointerException khi product là null");

        // Log
        logger.info("Kết quả TC_getDiscountByIdProduct_08: Ném NullPointerException như mong đợi.");
    }

    // TC_getDiscountByIdProduct_09: ProductSale với discount = null
    @Test
    public void testGetDiscountByIdProduct_WithNullDiscount() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_09: Kiểm tra ProductSale với discount = null.");
        // Chuẩn bị: Một ProductSale với discount = null
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setDiscount(null);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(NullPointerException.class, () -> {
            productSaleService.getDiscountByIdProduct(1);
        }, "Phải ném NullPointerException khi discount là null");

        // Log
        logger.info("Kết quả TC_getDiscountByIdProduct_09: Ném NullPointerException như mong đợi.");
    }

    // TC_getDiscountByIdProduct_10: ProductSale với discount âm
    @Test
    public void testGetDiscountByIdProduct_WithNegativeDiscount() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_10: Kiểm tra ProductSale với discount âm.");
        // Chuẩn bị: Một ProductSale với discount = -5
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setDiscount(-5);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức
        int result = productSaleService.getDiscountByIdProduct(1);

        // Kiểm tra và log
        assertEquals(-5, result, "Discount phải là -5");
        logger.info("Kết quả TC_getDiscountByIdProduct_10: Trả về discount = -5 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_11: ProductSale với discount = 0
    @Test
    public void testGetDiscountByIdProduct_WithZeroDiscount() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_11: Kiểm tra ProductSale với discount = 0.");
        // Chuẩn bị: Một ProductSale với discount = 0
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setDiscount(0);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức
        int result = productSaleService.getDiscountByIdProduct(1);

        // Kiểm tra và log
        assertEquals(0, result, "Discount phải là 0");
        logger.info("Kết quả TC_getDiscountByIdProduct_11: Trả về discount = 0 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_12: Nhiều ProductSale với productId khác nhau
    @Test
    public void testGetDiscountByIdProduct_WithMultipleProducts() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_12: Kiểm tra nhiều ProductSale với productId khác nhau.");
        // Chuẩn bị: 2 ProductSale với productId khác nhau
        Product sanPham1 = new Product();
        sanPham1.setId(1);
        Sale khuyenMai1 = new Sale();
        ProductSale ps1 = new ProductSale();
        ps1.setId(1);
        ps1.setProduct(sanPham1);
        ps1.setSale(khuyenMai1);
        ps1.setDiscount(10);
        ps1.setActive(true);
        Product sanPham2 = new Product();
        sanPham2.setId(2);
        Sale khuyenMai2 = new Sale();
        ProductSale ps2 = new ProductSale();
        ps2.setId(2);
        ps2.setProduct(sanPham2);
        ps2.setSale(khuyenMai2);
        ps2.setDiscount(15);
        ps2.setActive(true);
        danhSachProductSale.add(ps1);
        danhSachProductSale.add(ps2);

        // Thực hiện: Gọi phương thức với productId = 1
        int result = productSaleService.getDiscountByIdProduct(1);

        // Kiểm tra và log
        assertEquals(10, result, "Discount phải là 10 cho productId = 1");
        logger.info("Kết quả TC_getDiscountByIdProduct_12: Trả về discount = 10 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_13: productId âm
    @Test
    public void testGetDiscountByIdProduct_WithNegativeProductId() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_13: Kiểm tra productId âm.");
        // Chuẩn bị: Một ProductSale với productId = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setDiscount(10);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức với productId = -1
        int result = productSaleService.getDiscountByIdProduct(-1);

        // Kiểm tra và log
        assertEquals(0, result, "Discount phải là 0 vì productId âm không khớp");
        logger.info("Kết quả TC_getDiscountByIdProduct_13: Trả về discount = 0 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_14: productId = 0
    @Test
    public void testGetDiscountByIdProduct_WithZeroProductId() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_14: Kiểm tra productId = 0.");
        // Chuẩn bị: Một ProductSale với productId = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setDiscount(10);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức với productId = 0
        int result = productSaleService.getDiscountByIdProduct(0);

        // Kiểm tra và log
        assertEquals(0, result, "Discount phải là 0 vì productId = 0 không khớp");
        logger.info("Kết quả TC_getDiscountByIdProduct_14: Trả về discount = 0 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_15: Lỗi truy vấn cơ sở dữ liệu
    @Test
    public void testGetDiscountByIdProduct_WithDatabaseError() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_15: Kiểm tra lỗi truy vấn cơ sở dữ liệu.");
        // Chuẩn bị: Mock getProductSale() ném RuntimeException
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class)))
                .thenThrow(new RuntimeException("Lỗi cơ sở dữ liệu"));

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(RuntimeException.class, () -> {
            productSaleService.getDiscountByIdProduct(1);
        }, "Phải ném ngoại lệ khi truy vấn thất bại");

        // Kiểm tra tương tác và log
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_getDiscountByIdProduct_15: Ném ngoại lệ như mong đợi.");
    }

    // TC_getDiscountByIdProduct_16: Số lượng lớn ProductSale
    @Test
    public void testGetDiscountByIdProduct_WithLargeDataSet() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_16: Kiểm tra với số lượng lớn ProductSale.");
        // Chuẩn bị: 100 ProductSale, 1 ProductSale với productId = 1
        for (int i = 1; i <= 99; i++) {
            Product sanPham = new Product();
            sanPham.setId(i + 1); // productId từ 2 đến 100
            Sale khuyenMai = new Sale();
            ProductSale ps = new ProductSale();
            ps.setId(i);
            ps.setProduct(sanPham);
            ps.setSale(khuyenMai);
            ps.setDiscount(5);
            ps.setActive(true);
            danhSachProductSale.add(ps);
        }
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        ProductSale ps = new ProductSale();
        ps.setId(100);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setDiscount(10);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức với productId = 1
        int result = productSaleService.getDiscountByIdProduct(1);

        // Kiểm tra và log
        assertEquals(10, result, "Discount phải là 10 cho productId = 1");
        logger.info("Kết quả TC_getDiscountByIdProduct_16: Trả về discount = 10 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_17: ProductSale với active = null
    @Test
    public void testGetDiscountByIdProduct_WithNullActive() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_17: Kiểm tra ProductSale với active = null.");
        // Chuẩn bị: Một ProductSale với active = null
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setDiscount(10);
        ps.setActive(null);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức
        int result = productSaleService.getDiscountByIdProduct(1);

        // Kiểm tra và log
        assertEquals(0, result, "Discount phải là 0 vì active = null");
        logger.info("Kết quả TC_getDiscountByIdProduct_17: Trả về discount = 0 như mong đợi.");
    }

    // TC_getDiscountByIdProduct_18: ProductSale với sale = null
    @Test
    public void testGetDiscountByIdProduct_WithNullSale() {
        logger.info("Bắt đầu TC_getDiscountByIdProduct_18: Kiểm tra ProductSale với sale = null.");
        // Chuẩn bị: Một ProductSale với sale = null
        Product sanPham = new Product();
        sanPham.setId(1);
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(null);
        ps.setDiscount(10);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        // Thực hiện: Gọi phương thức
        int result = productSaleService.getDiscountByIdProduct(1);

        // Kiểm tra và log
        assertEquals(10, result, "Discount phải là 10 vì sale không ảnh hưởng đến logic");
        logger.info("Kết quả TC_getDiscountByIdProduct_18: Trả về discount = 10 như mong đợi.");
    }
}