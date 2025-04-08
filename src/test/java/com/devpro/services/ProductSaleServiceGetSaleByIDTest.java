package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devpro.entities.Sale;
import com.devpro.repositories.ProductSaleRepo;
import com.devpro.repositories.SaleRepo;

/**
 * Lớp kiểm thử đơn vị cho ProductSaleService, tập trung vào phương thức getSaleByID().
 * Sử dụng Mockito để mock các phụ thuộc như SaleRepo.
 * Log kết quả minh chứng bằng SLF4J.
 */
public class ProductSaleServiceGetSaleByIDTest {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductSaleServiceGetSaleByIDTest.class);

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

    @BeforeEach
    public void thietLap() {
        MockitoAnnotations.initMocks(this);
        logger.info("Thiết lập dữ liệu kiểm thử hoàn tất.");
    }

    @AfterEach
    public void hoanTac() {
        reset(mockQuery, entityManager, productSaleRepo, saleRepo);
        logger.info("Hoàn tác dữ liệu kiểm thử hoàn tất.");
    }

    // TC_getSaleByID_01: Tồn tại Sale với id hợp lệ
    @Test
    public void testGetSaleByID_WithValidId() {
        logger.info("Bắt đầu TC_getSaleByID_01: Kiểm tra id hợp lệ và tồn tại.");
        // Chuẩn bị: Mock saleRepo.getOne(1) trả về một Sale
        Sale sale = new Sale();
        sale.setId(1);
        when(saleRepo.getOne(1)).thenReturn(sale);

        // Thực hiện: Gọi phương thức
        Sale result = productSaleService.getSaleByID(1);

        // Kiểm tra và log
        assertNotNull(result, "Sale không được null");
        assertEquals(1, result.getId(), "ID của Sale phải là 1");
        verify(saleRepo, times(1)).getOne(1);
        logger.info("Kết quả TC_getSaleByID_01: Trả về Sale với id = 1 như mong đợi.");
    }

    // TC_getSaleByID_02: id không tồn tại
    @Test
    public void testGetSaleByID_WithNonExistentId() {
        logger.info("Bắt đầu TC_getSaleByID_02: Kiểm tra id không tồn tại.");
        // Chuẩn bị: Mock saleRepo.getOne(2) ném EntityNotFoundException
        when(saleRepo.getOne(2)).thenThrow(new EntityNotFoundException("Sale không tồn tại với id = 2"));

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(EntityNotFoundException.class, () -> {
            productSaleService.getSaleByID(2);
        }, "Phải ném EntityNotFoundException khi id không tồn tại");

        // Kiểm tra tương tác và log
        verify(saleRepo, times(1)).getOne(2);
        logger.info("Kết quả TC_getSaleByID_02: Ném EntityNotFoundException như mong đợi.");
    }

    // TC_getSaleByID_03: id là null
    @Test
    public void testGetSaleByID_WithNullId() {
        logger.info("Bắt đầu TC_getSaleByID_03: Kiểm tra id là null.");
        // Chuẩn bị: Gọi với id = null
        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(IllegalArgumentException.class, () -> {
            productSaleService.getSaleByID(null);
        }, "Phải ném IllegalArgumentException khi id là null");

        // Kiểm tra tương tác và log
        verify(saleRepo, never()).getOne(anyInt());
        logger.info("Kết quả TC_getSaleByID_03: Ném IllegalArgumentException như mong đợi.");
    }

    // TC_getSaleByID_04: id âm
    @Test
    public void testGetSaleByID_WithNegativeId() {
        logger.info("Bắt đầu TC_getSaleByID_04: Kiểm tra id âm.");
        // Chuẩn bị: Mock saleRepo.getOne(-1) ném EntityNotFoundException
        when(saleRepo.getOne(-1)).thenThrow(new EntityNotFoundException("Sale không tồn tại với id = -1"));

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(EntityNotFoundException.class, () -> {
            productSaleService.getSaleByID(-1);
        }, "Phải ném EntityNotFoundException khi id âm");

        // Kiểm tra tương tác và log
        verify(saleRepo, times(1)).getOne(-1);
        logger.info("Kết quả TC_getSaleByID_04: Ném EntityNotFoundException như mong đợi.");
    }

    // TC_getSaleByID_05: id = 0
    @Test
    public void testGetSaleByID_WithZeroId() {
        logger.info("Bắt đầu TC_getSaleByID_05: Kiểm tra id = 0.");
        // Chuẩn bị: Mock saleRepo.getOne(0) ném EntityNotFoundException
        when(saleRepo.getOne(0)).thenThrow(new EntityNotFoundException("Sale không tồn tại với id = 0"));

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(EntityNotFoundException.class, () -> {
            productSaleService.getSaleByID(0);
        }, "Phải ném EntityNotFoundException khi id = 0");

        // Kiểm tra tương tác và log
        verify(saleRepo, times(1)).getOne(0);
        logger.info("Kết quả TC_getSaleByID_05: Ném EntityNotFoundException như mong đợi.");
    }

    // TC_getSaleByID_06: Lỗi cơ sở dữ liệu
    @Test
    public void testGetSaleByID_WithDatabaseError() {
        logger.info("Bắt đầu TC_getSaleByID_06: Kiểm tra lỗi cơ sở dữ liệu.");
        // Chuẩn bị: Mock saleRepo.getOne(1) ném RuntimeException
        when(saleRepo.getOne(1)).thenThrow(new RuntimeException("Lỗi cơ sở dữ liệu"));

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(RuntimeException.class, () -> {
            productSaleService.getSaleByID(1);
        }, "Phải ném RuntimeException khi có lỗi cơ sở dữ liệu");

        // Kiểm tra tương tác và log
        verify(saleRepo, times(1)).getOne(1);
        logger.info("Kết quả TC_getSaleByID_06: Ném RuntimeException như mong đợi.");
    }

    // TC_getSaleByID_07: Sale có các thuộc tính null
    @Test
    public void testGetSaleByID_WithNullFields() {
        logger.info("Bắt đầu TC_getSaleByID_07: Kiểm tra Sale có các thuộc tính null.");
        // Chuẩn bị: Mock saleRepo.getOne(1) trả về Sale với các thuộc tính null
        Sale sale = new Sale();
        sale.setId(1);
        sale.setStart_date(null);
        sale.setEnd_date(null);
        when(saleRepo.getOne(1)).thenReturn(sale);

        // Thực hiện: Gọi phương thức
        Sale result = productSaleService.getSaleByID(1);

        // Kiểm tra và log
        assertNotNull(result, "Sale không được null");
        assertEquals(1, result.getId(), "ID của Sale phải là 1");
        assertNull(result.getStart_date(), "start_date phải là null");
        assertNull(result.getEnd_date(), "end_date phải là null");
        verify(saleRepo, times(1)).getOne(1);
        logger.info("Kết quả TC_getSaleByID_07: Trả về Sale với các thuộc tính null như mong đợi.");
    }

    // TC_getSaleByID_08: Gọi nhiều lần với cùng id
    @Test
    public void testGetSaleByID_MultipleCalls() {
        logger.info("Bắt đầu TC_getSaleByID_08: Kiểm tra gọi nhiều lần với cùng id.");
        // Chuẩn bị: Mock saleRepo.getOne(1) trả về một Sale
        Sale sale = new Sale();
        sale.setId(1);
        when(saleRepo.getOne(1)).thenReturn(sale);

        // Thực hiện: Gọi phương thức nhiều lần
        Sale result1 = productSaleService.getSaleByID(1);
        Sale result2 = productSaleService.getSaleByID(1);
        Sale result3 = productSaleService.getSaleByID(1);

        // Kiểm tra và log
        assertNotNull(result1, "Sale lần 1 không được null");
        assertNotNull(result2, "Sale lần 2 không được null");
        assertNotNull(result3, "Sale lần 3 không được null");
        assertEquals(1, result1.getId(), "ID của Sale lần 1 phải là 1");
        assertEquals(1, result2.getId(), "ID của Sale lần 2 phải là 1");
        assertEquals(1, result3.getId(), "ID của Sale lần 3 phải là 1");
        assertSame(result1, result2, "Sale lần 1 và lần 2 phải là cùng một đối tượng");
        assertSame(result2, result3, "Sale lần 2 và lần 3 phải là cùng một đối tượng");
        verify(saleRepo, times(3)).getOne(1);
        logger.info("Kết quả TC_getSaleByID_08: Trả về cùng một Sale mỗi lần gọi như mong đợi.");
    }
}