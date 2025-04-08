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
 * Lớp kiểm thử đơn vị cho ProductSaleService, tập trung vào phương thức checkP().
 * Sử dụng Mockito để mock các phụ thuộc như ProductSaleRepo.
 * Log trạng thái dữ liệu và kết quả bằng SLF4J.
 */
public class ProductSaleServiceCheckPTest {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductSaleServiceCheckPTest.class);

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
        // Khởi tạo mock và danh sách ProductSale
        MockitoAnnotations.initMocks(this);
        danhSachProductSale = new ArrayList<>();
        // Mock productSaleRepo.findAll() để trả về danh sách ProductSale
        when(productSaleRepo.findAll()).thenReturn(danhSachProductSale);
        logger.info("Thiết lập dữ liệu kiểm thử hoàn tất. Danh sách ProductSale ban đầu: {}", danhSachProductSale);
    }

    @AfterEach
    public void hoanTac() {
        // Xóa danh sách và reset mock
        danhSachProductSale.clear();
        reset(mockQuery, entityManager, productSaleRepo, saleRepo);
        logger.info("Hoàn tác dữ liệu kiểm thử hoàn tất. Danh sách ProductSale sau test: {}", danhSachProductSale);
    }

    // TC_PS_129 - testCheckP_WithMatchingProductSale: Tồn tại ProductSale khớp với idP và idS
    @Test
    public void testCheckP_WithMatchingProductSale() {
        logger.info("Bắt đầu TC_PS_129: Kiểm tra tồn tại ProductSale khớp với idP và idS.");
        // Chuẩn bị: Tạo một ProductSale với product_id = 1, sale_id = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setId(1);
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        danhSachProductSale.add(ps);
        logger.info("Dữ liệu chuẩn bị: idP = 1, idS = 1, danh sách ProductSale: {}", danhSachProductSale);

        // Thực hiện: Gọi phương thức
        boolean result = productSaleService.checkP(1, 1);

        // Kiểm tra và log
        assertTrue(result, "Phải trả về true vì tồn tại ProductSale khớp");
        verify(productSaleRepo, times(1)).findAll();
        logger.info("Kết quả TC_PS_129:  Kết thúc test case. Danh sách ProductSale sau test: {}", danhSachProductSale);
    }

    // TC_PS_130 - testCheckP_WithNoMatchingProductSale: Không có ProductSale nào khớp với idP và idS
    @Test
    public void testCheckP_WithNoMatchingProductSale() {
        logger.info("Bắt đầu TC_PS_130: Kiểm tra không có ProductSale khớp với idP và idS.");
        // Chuẩn bị: Tạo một ProductSale với product_id = 1, sale_id = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setId(1);
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        danhSachProductSale.add(ps);
        logger.info("Dữ liệu chuẩn bị: idP = 2, idS = 2, danh sách ProductSale: {}", danhSachProductSale);

        // Thực hiện: Gọi phương thức với idP và idS không khớp
        boolean result = productSaleService.checkP(2, 2);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì không có ProductSale khớp");
        verify(productSaleRepo, times(1)).findAll();
        logger.info("Kết quả TC_PS_130:  Kết thúc test case. Danh sách ProductSale sau test: {}", danhSachProductSale);
    }

    // TC_PS_131 - testCheckP_WithMultipleProductSalesOneMatch: Nhiều ProductSale, chỉ một cái khớp
    @Test
    public void testCheckP_WithMultipleProductSalesOneMatch() {
        logger.info("Bắt đầu TC_PS_131: Kiểm tra nhiều ProductSale, chỉ một cái khớp.");
        // Chuẩn bị: Tạo 3 ProductSale, chỉ một cái khớp với idP = 2, idS = 2
        for (int i = 1; i <= 3; i++) {
            Product sanPham = new Product();
            sanPham.setId(i);
            Sale khuyenMai = new Sale();
            khuyenMai.setId(i);
            ProductSale ps = new ProductSale();
            ps.setId(i);
            ps.setProduct(sanPham);
            ps.setSale(khuyenMai);
            danhSachProductSale.add(ps);
        }
        logger.info("Dữ liệu chuẩn bị: idP = 2, idS = 2, danh sách ProductSale: {}", danhSachProductSale);

        // Thực hiện: Gọi phương thức
        boolean result = productSaleService.checkP(2, 2);

        // Kiểm tra và log
        assertTrue(result, "Phải trả về true vì tồn tại một ProductSale khớp");
        verify(productSaleRepo, times(1)).findAll();
        logger.info("Kết quả TC_PS_131:  Kết thúc test case. Danh sách ProductSale sau test: {}", danhSachProductSale);
    }

    // TC_PS_132 - testCheckP_WithNullIdP: idP là null
    @Test
    public void testCheckP_WithNullIdP() {
        logger.info("Bắt đầu TC_PS_132: Kiểm tra idP là null.");
        // Chuẩn bị: Tạo một ProductSale với product_id = 1, sale_id = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setId(1);
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        danhSachProductSale.add(ps);
        logger.info("Dữ liệu chuẩn bị: idP = null, idS = 1, danh sách ProductSale: {}", danhSachProductSale);

        // Thực hiện: Gọi phương thức với idP = null
        boolean result = productSaleService.checkP(null, 1);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì idP là null");
        verify(productSaleRepo, times(1)).findAll();
        logger.info("Kết quả TC_PS_132:  Kết thúc test case. Danh sách ProductSale sau test: {}", danhSachProductSale);
    }

    // TC_PS_133 - testCheckP_WithNullIdS: idS là null
    @Test
    public void testCheckP_WithNullIdS() {
        logger.info("Bắt đầu TC_PS_133: Kiểm tra idS là null.");
        // Chuẩn bị: Tạo một ProductSale với product_id = 1, sale_id = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setId(1);
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        danhSachProductSale.add(ps);
        logger.info("Dữ liệu chuẩn bị: idP = 1, idS = null, danh sách ProductSale: {}", danhSachProductSale);

        // Thực hiện: Gọi phương thức với idS = null
        boolean result = productSaleService.checkP(1, null);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì idS là null");
        verify(productSaleRepo, times(1)).findAll();
        logger.info("Kết quả TC_PS_133: Kết thúc test case. Danh sách ProductSale sau test: {}", danhSachProductSale);
    }

    // TC_PS_134 - testCheckP_WithBothNullIds: Cả idP và idS đều là null
    @Test
    public void testCheckP_WithBothNullIds() {
        logger.info("Bắt đầu TC_PS_134: Kiểm tra cả idP và idS đều là null.");
        // Chuẩn bị: Tạo một ProductSale với product_id = 1, sale_id = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setId(1);
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        danhSachProductSale.add(ps);
        logger.info("Dữ liệu chuẩn bị: idP = null, idS = null, danh sách ProductSale: {}", danhSachProductSale);

        // Thực hiện: Gọi phương thức với cả idP và idS là null
        boolean result = productSaleService.checkP(null, null);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì cả idP và idS đều là null");
        verify(productSaleRepo, times(1)).findAll();
        logger.info("Kết quả TC_PS_134:  Kết thúc test case. Danh sách ProductSale sau test: {}", danhSachProductSale);
    }

    // TC_PS_135 - testCheckP_WithNegativeIds: idP hoặc idS âm
    @Test
    public void testCheckP_WithNegativeIds() {
        logger.info("Bắt đầu TC_PS_135: Kiểm tra idP hoặc idS âm.");
        // Chuẩn bị: Tạo một ProductSale với product_id = 1, sale_id = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setId(1);
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        danhSachProductSale.add(ps);
        logger.info("Dữ liệu chuẩn bị: idP = -1, idS = -1, danh sách ProductSale: {}", danhSachProductSale);

        // Thực hiện: Gọi phương thức với idP và idS âm
        boolean result = productSaleService.checkP(-1, -1);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì idP và idS âm không khớp");
        verify(productSaleRepo, times(1)).findAll();
        logger.info("Kết quả TC_PS_135:  Kết thúc test case. Danh sách ProductSale sau test: {}", danhSachProductSale);
    }

    // TC_PS_136 - testCheckP_WithZeroIds: idP hoặc idS là 0
    @Test
    public void testCheckP_WithZeroIds() {
        logger.info("Bắt đầu TC_PS_136: Kiểm tra idP hoặc idS là 0.");
        // Chuẩn bị: Tạo một ProductSale với product_id = 1, sale_id = 1
        Product sanPham = new Product();
        sanPham.setId(1);
        Sale khuyenMai = new Sale();
        khuyenMai.setId(1);
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(sanPham);
        ps.setSale(khuyenMai);
        danhSachProductSale.add(ps);
        logger.info("Dữ liệu chuẩn bị: idP = 0, idS = 0, danh sách ProductSale: {}", danhSachProductSale);

        // Thực hiện: Gọi phương thức với idP và idS là 0
        boolean result = productSaleService.checkP(0, 0);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì idP và idS là 0 không khớp");
        verify(productSaleRepo, times(1)).findAll();
        logger.info("Kết quả TC_PS_136:  Kết thúc test case. Danh sách ProductSale sau test: {}", danhSachProductSale);
    }

    // TC_PS_137 - testCheckP_WithNullFields: ProductSale có product hoặc sale là null
    @Test
    public void testCheckP_WithNullFields() {
        logger.info("Bắt đầu TC_PS_137: Kiểm tra ProductSale có product hoặc sale là null.");
        // Chuẩn bị: Tạo một ProductSale với product = null, sale = null
        ProductSale ps = new ProductSale();
        ps.setId(1);
        ps.setProduct(null);
        ps.setSale(null);
        danhSachProductSale.add(ps);
        logger.info("Dữ liệu chuẩn bị: idP = 1, idS = 1, danh sách ProductSale: {}", danhSachProductSale);

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(NullPointerException.class, () -> {
            productSaleService.checkP(1, 1);
        }, "Phải ném NullPointerException khi product hoặc sale là null");

        // Kiểm tra tương tác và log
        verify(productSaleRepo, times(1)).findAll();
        logger.info("Kết quả TC_PS_137:  Kết thúc test case. Danh sách ProductSale sau test: {}", danhSachProductSale);
    }

    // TC_PS_138 - testCheckP_WithEmptyList: Danh sách ProductSale rỗng
    @Test
    public void testCheckP_WithEmptyList() {
        logger.info("Bắt đầu TC_PS_138: Kiểm tra danh sách ProductSale rỗng.");
        // Chuẩn bị: Danh sách ProductSale rỗng
        logger.info("Dữ liệu chuẩn bị: idP = 1, idS = 1, danh sách ProductSale: {}", danhSachProductSale);

        // Thực hiện: Gọi phương thức
        boolean result = productSaleService.checkP(1, 1);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì danh sách ProductSale rỗng");
        verify(productSaleRepo, times(1)).findAll();
        logger.info("Kết quả TC_PS_138:  Kết thúc test case. Danh sách ProductSale sau test: {}", danhSachProductSale);
    }
}