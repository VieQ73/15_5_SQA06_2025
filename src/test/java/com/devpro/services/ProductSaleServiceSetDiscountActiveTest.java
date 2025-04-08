package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.devpro.repositories.SaleRepo;
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

/**
 * Lớp kiểm thử đơn vị cho ProductSaleService, tập trung vào phương thức setDiscountActive().
 * Sử dụng Mockito để mock các phụ thuộc như EntityManager và ProductSaleRepo.
 * Log kết quả minh chứng bằng SLF4J.
 */
public class ProductSaleServiceSetDiscountActiveTest {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductSaleServiceSetDiscountActiveTest.class);

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

    // Khai báo biến cal ở cấp độ lớp
    private Calendar cal;

    @BeforeEach
    public void thietLap() {
        MockitoAnnotations.initMocks(this);
        danhSachProductSale = new ArrayList<>();
        // Khởi tạo cal
        cal = Calendar.getInstance();
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

    // TC_setDiscountActive_01: Kiểm tra khi không có product sale nào
    @Test
    public void testSetDiscountActiveWithEmptyList() {
        logger.info("Bắt đầu TC_setDiscountActive_01: Kiểm tra danh sách rỗng.");
        // Chuẩn bị: Không có product sale trong DB
        when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Kiểm tra và log
        verify(productSaleRepo, never()).save(any(ProductSale.class));
        logger.info("Kết quả TC_setDiscountActive_01: Không có tương tác với repository.");
    }

    // TC_setDiscountActive_02: Một discount đang trong khoảng thời gian hợp lệ
    @Test
    public void testSetDiscountActiveWithOneValidDiscount() {
        logger.info("Bắt đầu TC_setDiscountActive_02: Kiểm tra một discount hợp lệ.");
        // Chuẩn bị: Một product sale với ngày hợp lệ
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setStart_date(layNgayTruoc(5));
        khuyenMai.setEnd_date(layNgaySau(5));
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setActive(false);
        danhSachProductSale.add(ps);

        when(productSaleRepo.save(any(ProductSale.class))).thenReturn(ps);

        // Log trạng thái trước
        logger.info("Trạng thái active trước khi chạy: {}", ps.getActive());

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Log trạng thái sau
        logger.info("Trạng thái active sau khi chạy: {}", ps.getActive());

        // Kiểm tra
        assertTrue(ps.getActive(), "Discount phải được đặt thành active");
        logger.info("Kết quả TC_setDiscountActive_02: Discount được kích hoạt thành công.");
    }

    // TC_setDiscountActive_03: Nhiều discount, chỉ một cái active
    @Test
    public void testSetDiscountActiveWithMultipleDiscounts() {
        logger.info("Bắt đầu TC_setDiscountActive_03: Kiểm tra nhiều discount.");
        // Chuẩn bị: Hai product sale, một trong thời gian, một đã hết hạn
        Product sanPham = new Product();
        sanPham.setId(1);

        Sale khuyenMai1 = new Sale();
        khuyenMai1.setStart_date(layNgayTruoc(10));
        khuyenMai1.setEnd_date(layNgayTruoc(1));
        ProductSale ps1 = new ProductSale();
        ps1.setId(1);
        ps1.setProduct(sanPham);
        ps1.setSale(khuyenMai1);
        ps1.setActive(true);

        Sale khuyenMai2 = new Sale();
        khuyenMai2.setStart_date(layNgayTruoc(2));
        khuyenMai2.setEnd_date(layNgaySau(5));
        ProductSale ps2 = new ProductSale();
        ps2.setId(2);
        ps2.setProduct(sanPham);
        ps2.setSale(khuyenMai2);
        ps2.setActive(false);

        danhSachProductSale.add(ps1);
        danhSachProductSale.add(ps2);

        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Log trạng thái trước
        logger.info("Trạng thái ps1 trước: {}", ps1.getActive());
        logger.info("Trạng thái ps2 trước: {}", ps2.getActive());

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Log trạng thái sau
        logger.info("Trạng thái ps1 sau: {}", ps1.getActive());
        logger.info("Trạng thái ps2 sau: {}", ps2.getActive());

        // Kiểm tra
        assertFalse(ps1.getActive(), "Discount hết hạn phải là inactive");
        assertTrue(ps2.getActive(), "Discount hợp lệ phải là active");
        logger.info("Kết quả TC_setDiscountActive_03: Chỉ discount hợp lệ được kích hoạt.");
    }

    // TC_setDiscountActive_04: Tất cả discount đã hết hạn
    @Test
    public void testSetDiscountActiveWithAllExpired() {
        logger.info("Bắt đầu TC_setDiscountActive_04: Kiểm tra discount hết hạn.");
        // Chuẩn bị: Một product sale đã hết hạn
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setStart_date(layNgayTruoc(10));
        khuyenMai.setEnd_date(layNgayTruoc(1));
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        when(productSaleRepo.save(any(ProductSale.class))).thenReturn(ps);

        // Log trạng thái trước
        logger.info("Trạng thái active trước: {}", ps.getActive());

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Log trạng thái sau
        logger.info("Trạng thái active sau: {}", ps.getActive());

        // Kiểm tra
        assertFalse(ps.getActive(), "Discount hết hạn phải là inactive");
        logger.info("Kết quả TC_setDiscountActive_04: Discount hết hạn bị vô hiệu hóa.");
    }

    // TC_setDiscountActive_05: Tất cả discount chưa bắt đầu
    @Test
    public void testSetDiscountActiveWithFutureDiscounts() {
        logger.info("Bắt đầu TC_setDiscountActive_05: Kiểm tra discount tương lai.");
        // Chuẩn bị: Một product sale trong tương lai
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setStart_date(layNgaySau(1));
        khuyenMai.setEnd_date(layNgaySau(10));
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        when(productSaleRepo.save(any(ProductSale.class))).thenReturn(ps);

        // Log trạng thái trước
        logger.info("Trạng thái active trước: {}", ps.getActive());

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Log trạng thái sau
        logger.info("Trạng thái active sau: {}", ps.getActive());

        // Kiểm tra
        assertFalse(ps.getActive(), "Discount tương lai phải là inactive");
        logger.info("Kết quả TC_setDiscountActive_05: Discount tương lai bị vô hiệu hóa.");
    }

    // TC_setDiscountActive_06: Nhiều sản phẩm với discount
    @Test
    public void testSetDiscountActiveWithMultipleProducts() {
        logger.info("Bắt đầu TC_setDiscountActive_06: Kiểm tra nhiều sản phẩm.");
        // Chuẩn bị: Hai sản phẩm, mỗi sản phẩm có một discount
        Product sp1 = new Product();
        sp1.setId(1);
        Sale km1 = new Sale();
        km1.setStart_date(layNgayTruoc(2));
        km1.setEnd_date(layNgaySau(2));
        ProductSale ps1 = new ProductSale();
        ps1.setId(1);
        ps1.setProduct(sp1);
        ps1.setSale(km1);
        ps1.setActive(false);

        Product sp2 = new Product();
        sp2.setId(2);
        Sale km2 = new Sale();
        km2.setStart_date(layNgayTruoc(1));
        km2.setEnd_date(layNgaySau(3));
        ProductSale ps2 = new ProductSale();
        ps2.setId(2);
        ps2.setProduct(sp2);
        ps2.setSale(km2);
        ps2.setActive(false);

        danhSachProductSale.add(ps1);
        danhSachProductSale.add(ps2);

        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Log trạng thái trước
        logger.info("Trạng thái ps1 trước: {}", ps1.getActive());
        logger.info("Trạng thái ps2 trước: {}", ps2.getActive());

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Log trạng thái sau
        logger.info("Trạng thái ps1 sau: {}", ps1.getActive());
        logger.info("Trạng thái ps2 sau: {}", ps2.getActive());

        // Kiểm tra
        assertTrue(ps1.getActive(), "Discount cho sản phẩm 1 phải là active");
        assertTrue(ps2.getActive(), "Discount cho sản phẩm 2 phải là active");
        logger.info("Kết quả TC_setDiscountActive_06: Cả hai discount được kích hoạt.");
    }

    // TC_setDiscountActive_07: Discount bắt đầu hôm nay
    @Test
    public void testSetDiscountActiveWithStartToday() {
        logger.info("Bắt đầu TC_setDiscountActive_07: Kiểm tra discount bắt đầu hôm nay.");
        // Chuẩn bị: Discount bắt đầu hôm nay
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setStart_date(new Date());
        khuyenMai.setEnd_date(layNgaySau(5));
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setActive(false);
        danhSachProductSale.add(ps);

        when(productSaleRepo.save(any(ProductSale.class))).thenReturn(ps);

        // Log trạng thái trước
        logger.info("Trạng thái active trước: {}", ps.getActive());

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Log trạng thái sau
        logger.info("Trạng thái active sau: {}", ps.getActive());

        // Kiểm tra
        assertTrue(ps.getActive(), "Discount bắt đầu hôm nay phải là active");
        logger.info("Kết quả TC_setDiscountActive_07: Discount bắt đầu hôm nay được kích hoạt.");
    }

    // TC_setDiscountActive_08: Discount kết thúc hôm nay
    @Test
    public void testSetDiscountActiveWithEndToday() {
        logger.info("Bắt đầu TC_setDiscountActive_08: Kiểm tra discount kết thúc hôm nay.");
        // Chuẩn bị: Discount kết thúc hôm nay
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setStart_date(layNgayTruoc(5));
        khuyenMai.setEnd_date(new Date());
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setActive(false);
        danhSachProductSale.add(ps);

        when(productSaleRepo.save(any(ProductSale.class))).thenReturn(ps);

        // Log trạng thái trước
        logger.info("Trạng thái active trước: {}", ps.getActive());

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Log trạng thái sau
        logger.info("Trạng thái active sau: {}", ps.getActive());

        // Kiểm tra
        assertTrue(ps.getActive(), "Discount kết thúc hôm nay vẫn phải là active");
        logger.info("Kết quả TC_setDiscountActive_08: Discount kết thúc hôm nay vẫn được kích hoạt.");
    }

    // TC_setDiscountActive_09: Discount chồng lấp cho cùng sản phẩm
    @Test
    public void testSetDiscountActiveWithOverlappingDiscounts() {
        logger.info("Bắt đầu TC_setDiscountActive_09: Kiểm tra discount chồng lấp.");
        // Chuẩn bị: Hai discount chồng lấp cho cùng sản phẩm
        Product sanPham = new Product();
        sanPham.setId(1);

        Sale km1 = new Sale();
        km1.setStart_date(layNgayTruoc(10));
        km1.setEnd_date(layNgaySau(5));
        ProductSale ps1 = new ProductSale();
        ps1.setId(1);
        ps1.setProduct(sanPham);
        ps1.setSale(km1);
        ps1.setActive(false);

        Sale km2 = new Sale();
        km2.setStart_date(layNgayTruoc(2));
        km2.setEnd_date(layNgaySau(3));
        ProductSale ps2 = new ProductSale();
        ps2.setId(2);
        ps2.setProduct(sanPham);
        ps2.setSale(km2);
        ps2.setActive(false);

        danhSachProductSale.add(ps1);
        danhSachProductSale.add(ps2);

        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Log trạng thái trước
        logger.info("Trạng thái ps1 trước: {}", ps1.getActive());
        logger.info("Trạng thái ps2 trước: {}", ps2.getActive());

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Log trạng thái sau
        logger.info("Trạng thái ps1 sau: {}", ps1.getActive());
        logger.info("Trạng thái ps2 sau: {}", ps2.getActive());

        // Kiểm tra
        assertFalse(ps1.getActive(), "Discount cũ hơn phải là inactive");
        assertTrue(ps2.getActive(), "Discount mới hơn phải là active");
        logger.info("Kết quả TC_setDiscountActive_09: Discount mới hơn được kích hoạt.");
    }

    // TC_setDiscountActive_10: Lỗi khi lưu vào DB
    @Test
    public void testSetDiscountActiveWithSaveDBError() {
        logger.info("Bắt đầu TC_setDiscountActive_10: Kiểm tra lỗi lưu DB.");
        // Chuẩn bị: Một product sale hợp lệ, nhưng lưu DB thất bại
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setStart_date(layNgayTruoc(2));
        khuyenMai.setEnd_date(layNgaySau(2));
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        ps.setActive(false);
        danhSachProductSale.add(ps);

        when(productSaleRepo.save(any(ProductSale.class))).thenThrow(new RuntimeException("Lỗi cơ sở dữ liệu"));

        // Log trạng thái trước
        logger.info("Trạng thái active trước: {}", ps.getActive());

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(RuntimeException.class, () -> {
            productSaleService.setDiscountActive();
        }, "Phải ném ngoại lệ khi lưu thất bại");

        // Log kết quả (thường không đến đây do ngoại lệ)
        logger.info("Kết quả TC_setDiscountActive_10: Ngoại lệ được ném khi lưu thất bại.");
    }

    // TC_setDiscountActive_11: Discount với ngày null
    @Test
    public void testSetDiscountActiveWithNullDates() {
        logger.info("Bắt đầu TC_setDiscountActive_11: Kiểm tra discount với ngày null.");
        // Chuẩn bị: Một ProductSale với start_date và end_date là null
        Product product = new Product();
        product.setId(1);
        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(null);
        sale.setEnd_date(null);
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(product);
        ps.setSale(sale);
        ps.setActive(false);
        danhSachProductSale.add(ps);

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(NullPointerException.class, () -> {
            productSaleService.setDiscountActive();
        }, "Phải ném NullPointerException khi ngày là null");

        // Log
        logger.info("Kết quả TC_setDiscountActive_11: Ném NullPointerException như mong đợi.");
    }

    // TC_setDiscountActive_12: 2 discount có cùng start_date
    @Test
    public void testSetDiscountActiveWithSameStartDate() {
        logger.info("Bắt đầu TC_setDiscountActive_12: Kiểm tra 2 discount có cùng start_date.");
        // Chuẩn bị: 2 ProductSale với cùng start_date, end_date khác nhau
        Date now = new Date();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date startDate = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 5);
        Date endDate1 = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 10);
        Date endDate2 = cal.getTime();

        Product product = new Product();
        product.setId(1);
        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setStart_date(startDate);
        sale1.setEnd_date(endDate1);
        Sale sale2 = new Sale();
        sale2.setId(2);
        sale2.setStart_date(startDate);
        sale2.setEnd_date(endDate2);
        ProductSale ps1 = new ProductSale();
        ps1.setId(1);
        ps1.setProduct(product);
        ps1.setSale(sale1);
        ps1.setActive(false);
        ProductSale ps2 = new ProductSale();
        ps2.setId(2);
        ps2.setProduct(product);
        ps2.setSale(sale2);
        ps2.setActive(false);
        danhSachProductSale.add(ps1);
        danhSachProductSale.add(ps2);

        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Kiểm tra và log
        assertFalse(ps1.getActive(), "ps1 phải không active");
        assertTrue(ps2.getActive(), "ps2 phải active vì end_date xa hơn");
        verify(productSaleRepo, times(2)).save(any(ProductSale.class));
        logger.info("Kết quả TC_setDiscountActive_12: Discount có end_date xa hơn được kích hoạt.");
    }

    // TC_setDiscountActive_13: ProductSale với product null
    @Test
    public void testSetDiscountActiveWithNullProduct() {
        logger.info("Bắt đầu TC_setDiscountActive_13: Kiểm tra ProductSale với product null.");
        // Chuẩn bị: Một ProductSale với product = null
        Sale sale = new Sale();
        sale.setId(1);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -5);
        sale.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 10);
        sale.setEnd_date(cal.getTime());
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(null);
        ps.setSale(sale);
        ps.setActive(false);
        danhSachProductSale.add(ps);

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(NullPointerException.class, () -> {
            productSaleService.setDiscountActive();
        }, "Phải ném NullPointerException khi product là null");

        // Log
        logger.info("Kết quả TC_setDiscountActive_13: Ném NullPointerException như mong đợi.");
    }

    // TC_setDiscountActive_14: Discount với ngày không hợp lệ (start_date sau end_date)
    @Test
    public void testSetDiscountActiveWithInvalidDates() {
        logger.info("Bắt đầu TC_setDiscountActive_14: Kiểm tra discount với ngày không hợp lệ.");
        // Chuẩn bị: Một ProductSale với start_date sau end_date
        Product product = new Product();
        product.setId(1);
        Sale sale = new Sale();
        sale.setId(1);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 5);
        sale.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -10);
        sale.setEnd_date(cal.getTime());
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(product);
        ps.setSale(sale);
        ps.setActive(true);
        danhSachProductSale.add(ps);

        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Kiểm tra và log
        assertFalse(ps.getActive(), "Discount không hợp lệ phải bị vô hiệu hóa");
        verify(productSaleRepo, times(1)).save(ps);
        logger.info("Kết quả TC_setDiscountActive_14: Discount không hợp lệ bị vô hiệu hóa.");
    }

    // TC_setDiscountActive_15: Nhiều discount hết hạn cho cùng productId
    @Test
    public void testSetDiscountActiveWithMultipleExpired() {
        logger.info("Bắt đầu TC_setDiscountActive_15: Kiểm tra nhiều discount hết hạn.");
        // Chuẩn bị: 3 ProductSale hết hạn
        Product product = new Product();
        product.setId(1);
        Sale sale1 = new Sale();
        sale1.setId(1);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -10);
        sale1.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 2);
        sale1.setEnd_date(cal.getTime());
        Sale sale2 = new Sale();
        sale2.setId(2);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -8);
        sale2.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 2);
        sale2.setEnd_date(cal.getTime());
        Sale sale3 = new Sale();
        sale3.setId(3);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -6);
        sale3.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 2);
        sale3.setEnd_date(cal.getTime());
        ProductSale ps1 = new ProductSale();
        ps1.setId(1);
        ps1.setProduct(product);
        ps1.setSale(sale1);
        ps1.setActive(true);
        ProductSale ps2 = new ProductSale();
        ps2.setId(2);
        ps2.setProduct(product);
        ps2.setSale(sale2);
        ps2.setActive(true);
        ProductSale ps3 = new ProductSale();
        ps3.setId(3);
        ps3.setProduct(product);
        ps3.setSale(sale3);
        ps3.setActive(true);
        danhSachProductSale.add(ps1);
        danhSachProductSale.add(ps2);
        danhSachProductSale.add(ps3);

        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Kiểm tra và log
        assertFalse(ps1.getActive(), "ps1 phải không active");
        assertFalse(ps2.getActive(), "ps2 phải không active");
        assertFalse(ps3.getActive(), "ps3 phải không active");
        logger.info("Kết quả TC_setDiscountActive_15: Tất cả discount bị vô hiệu hóa.");
    }

    // TC_setDiscountActive_16: Nhiều discount chưa bắt đầu cho cùng productId
    @Test
    public void testSetDiscountActiveWithMultipleFuture() {
        logger.info("Bắt đầu TC_setDiscountActive_16: Kiểm tra nhiều discount chưa bắt đầu.");
        // Chuẩn bị: 3 ProductSale chưa bắt đầu
        Product product = new Product();
        product.setId(1);
        Sale sale1 = new Sale();
        sale1.setId(1);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        sale1.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 5);
        sale1.setEnd_date(cal.getTime());
        Sale sale2 = new Sale();
        sale2.setId(2);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 2);
        sale2.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 5);
        sale2.setEnd_date(cal.getTime());
        Sale sale3 = new Sale();
        sale3.setId(3);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 3);
        sale3.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 5);
        sale3.setEnd_date(cal.getTime());
        ProductSale ps1 = new ProductSale();
        ps1.setId(1);
        ps1.setProduct(product);
        ps1.setSale(sale1);
        ps1.setActive(true);
        ProductSale ps2 = new ProductSale();
        ps2.setId(2);
        ps2.setProduct(product);
        ps2.setSale(sale2);
        ps2.setActive(true);
        ProductSale ps3 = new ProductSale();
        ps3.setId(3);
        ps3.setProduct(product);
        ps3.setSale(sale3);
        ps3.setActive(true);
        danhSachProductSale.add(ps1);
        danhSachProductSale.add(ps2);
        danhSachProductSale.add(ps3);

        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Kiểm tra và log
        assertFalse(ps1.getActive(), "ps1 phải không active");
        assertFalse(ps2.getActive(), "ps2 phải không active");
        assertFalse(ps3.getActive(), "ps3 phải không active");
        logger.info("Kết quả TC_setDiscountActive_16: Tất cả discount bị vô hiệu hóa.");
    }

    // TC_setDiscountActive_17: Hỗn hợp discount hợp lệ, hết hạn, và chưa bắt đầu
    @Test
    public void testSetDiscountActiveWithMixedDiscounts() {
        logger.info("Bắt đầu TC_setDiscountActive_17: Kiểm tra hỗn hợp discount.");
        // Chuẩn bị: 3 ProductSale: 1 hợp lệ, 1 hết hạn, 1 chưa bắt đầu
        Product product = new Product();
        product.setId(1);
        Sale sale1 = new Sale(); // Hết hạn
        sale1.setId(1);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -10);
        sale1.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 2);
        sale1.setEnd_date(cal.getTime());
        Sale sale2 = new Sale(); // Hợp lệ
        sale2.setId(2);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -5);
        sale2.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 10);
        sale2.setEnd_date(cal.getTime());
        Sale sale3 = new Sale(); // Chưa bắt đầu
        sale3.setId(3);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        sale3.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 5);
        sale3.setEnd_date(cal.getTime());
        ProductSale ps1 = new ProductSale();
        ps1.setId(1);
        ps1.setProduct(product);
        ps1.setSale(sale1);
        ps1.setActive(true);
        ProductSale ps2 = new ProductSale();
        ps2.setId(2);
        ps2.setProduct(product);
        ps2.setSale(sale2);
        ps2.setActive(false);
        ProductSale ps3 = new ProductSale();
        ps3.setId(3);
        ps3.setProduct(product);
        ps3.setSale(sale3);
        ps3.setActive(true);
        danhSachProductSale.add(ps1);
        danhSachProductSale.add(ps2);
        danhSachProductSale.add(ps3);

        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Kiểm tra và log
        assertFalse(ps1.getActive(), "ps1 phải không active");
        assertTrue(ps2.getActive(), "ps2 phải active");
        assertFalse(ps3.getActive(), "ps3 phải không active");
        logger.info("Kết quả TC_setDiscountActive_17: Chỉ discount hợp lệ được kích hoạt.");
    }

    // TC_setDiscountActive_18: Nhiều sản phẩm với discount chồng lấp
    @Test
    public void testSetDiscountActiveWithMultipleProductsAndOverlapping() {
        logger.info("Bắt đầu TC_setDiscountActive_18: Kiểm tra nhiều sản phẩm với discount chồng lấp.");
        // Chuẩn bị: 3 ProductSale: 2 cho productId 1 (chồng lấp), 1 cho productId 2
        Product product1 = new Product();
        product1.setId(1);
        Product product2 = new Product();
        product2.setId(2);
        Sale sale1 = new Sale();
        sale1.setId(1);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -5);
        sale1.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 10);
        sale1.setEnd_date(cal.getTime());
        Sale sale2 = new Sale();
        sale2.setId(2);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -3);
        sale2.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 10);
        sale2.setEnd_date(cal.getTime());
        Sale sale3 = new Sale();
        sale3.setId(3);
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -5);
        sale3.setStart_date(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 10);
        sale3.setEnd_date(cal.getTime());
        ProductSale ps1 = new ProductSale();
        ps1.setId(1);
        ps1.setProduct(product1);
        ps1.setSale(sale1);
        ps1.setActive(false);
        ProductSale ps2 = new ProductSale();
        ps2.setId(2);
        ps2.setProduct(product1);
        ps2.setSale(sale2);
        ps2.setActive(false);
        ProductSale ps3 = new ProductSale();
        ps3.setId(3);
        ps3.setProduct(product2);
        ps3.setSale(sale3);
        ps3.setActive(false);
        danhSachProductSale.add(ps1);
        danhSachProductSale.add(ps2);
        danhSachProductSale.add(ps3);

        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Kiểm tra và log
        assertFalse(ps1.getActive(), "ps1 phải không active");
        assertTrue(ps2.getActive(), "ps2 phải active vì start_date gần hơn");
        assertTrue(ps3.getActive(), "ps3 phải active");
        logger.info("Kết quả TC_setDiscountActive_18: Discount hợp lệ được kích hoạt.");
    }

    // TC_setDiscountActive_19: Số lượng lớn ProductSale
    @Test
    public void testSetDiscountActiveWithLargeDataSet() {
        logger.info("Bắt đầu TC_setDiscountActive_19: Kiểm tra với số lượng lớn ProductSale.");
        // Chuẩn bị: 100 ProductSale cho 10 productId
        for (int i = 1; i <= 100; i++) {
            Product product = new Product();
            product.setId((i % 10) + 1); // 10 productId khác nhau
            Sale sale = new Sale();
            sale.setId(i);
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -5);
            sale.setStart_date(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 10);
            sale.setEnd_date(cal.getTime());
            ProductSale ps = new ProductSale();
            ps.setId(i);
            ps.setProduct(product);
            ps.setSale(sale);
            ps.setActive(false);
            danhSachProductSale.add(ps);
        }

        when(productSaleRepo.save(any(ProductSale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Thực hiện: Gọi phương thức
        productSaleService.setDiscountActive();

        // Kiểm tra và log
        int activeCount = 0;
        for (ProductSale ps : danhSachProductSale) {
            if (ps.getActive()) {
                activeCount++;
            }
        }
        logger.info("activeCount: " + activeCount);
        assertEquals(10, activeCount, "Chỉ 10 discount (1 cho mỗi productId) được kích hoạt");
        logger.info("Kết quả TC_setDiscountActive_19: Xử lý đúng với dữ liệu lớn.");
    }

    // TC_setDiscountActive_20: Lỗi truy vấn cơ sở dữ liệu
    @Test
    public void testSetDiscountActiveWithDatabaseError() {
        logger.info("Bắt đầu TC_setDiscountActive_20: Kiểm tra lỗi truy vấn cơ sở dữ liệu.");
        // Chuẩn bị: Mock getProductSale() ném DataAccessException
        when(entityManager.createNativeQuery(anyString(), eq(ProductSale.class)))
                .thenThrow(new RuntimeException("Lỗi cơ sở dữ liệu"));

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(RuntimeException.class, () -> {
            productSaleService.setDiscountActive();
        }, "Phải ném ngoại lệ khi truy vấn thất bại");

        // Kiểm tra tương tác và log
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(ProductSale.class));
        logger.info("Kết quả TC_setDiscountActive_20: Ném ngoại lệ như mong đợi.");
    }

    /**
     * Phương thức hỗ trợ: Lấy ngày trước đó X ngày.
     */
    private Date layNgayTruoc(int soNgay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -soNgay);
        return calendar.getTime();
    }

    /**
     * Phương thức hỗ trợ: Lấy ngày sau đó X ngày.
     */
    private Date layNgaySau(int soNgay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, soNgay);
        return calendar.getTime();
    }
}