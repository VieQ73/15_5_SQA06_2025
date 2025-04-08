package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import org.springframework.web.multipart.MultipartFile;

import com.devpro.repositories.ProductRepo;

/**
 * Lớp kiểm thử đơn vị cho ProductService, tập trung vào phương thức isEmptyUploadFile().
 * Sử dụng Mockito để mock các phụ thuộc như MultipartFile.
 * Log trạng thái dữ liệu và kết quả bằng SLF4J.
 */
public class ProductServiceIsEmptyUploadFileTest {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceIsEmptyUploadFileTest.class);

    @InjectMocks
    private ProductService productService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private ProductSaleService productSaleService;

    @Mock
    private Query mockQuery;

    @Mock
    private MultipartFile mockFile;

    @BeforeEach
    public void thietLap() {
        // Khởi tạo mock
        MockitoAnnotations.initMocks(this);
        logger.info("Thiết lập dữ liệu kiểm thử hoàn tất.");
    }

    @AfterEach
    public void hoanTac() {
        // Reset mock
        reset(mockQuery, entityManager, productRepo, productSaleService, mockFile);
        logger.info("Hoàn tác dữ liệu kiểm thử hoàn tất.");
    }

    // TC_isEmptyUploadFile_01: Mảng images là null
    @Test
    public void testIsEmptyUploadFile_WithNullArray() {
        logger.info("Bắt đầu TC_isEmptyUploadFile_01: Kiểm tra mảng images là null.");
        // Chuẩn bị: Mảng images là null
        MultipartFile[] images = null;
        logger.info("Dữ liệu chuẩn bị: images = null");

        // Thực hiện: Gọi phương thức
        boolean result = productService.isEmptyUploadFile(images);

        // Kiểm tra và log
        assertTrue(result, "Phải trả về true vì mảng images là null");
        logger.info("Kết quả TC_isEmptyUploadFile_01: Trả về true như mong đợi.");
    }

    // TC_isEmptyUploadFile_02: Mảng images rỗng
    @Test
    public void testIsEmptyUploadFile_WithEmptyArray() {
        logger.info("Bắt đầu TC_isEmptyUploadFile_02: Kiểm tra mảng images rỗng.");
        // Chuẩn bị: Mảng images rỗng
        MultipartFile[] images = new MultipartFile[]{};
        logger.info("Dữ liệu chuẩn bị: images = {}");

        // Thực hiện: Gọi phương thức
        boolean result = productService.isEmptyUploadFile(images);

        // Kiểm tra và log
        assertTrue(result, "Phải trả về true vì mảng images rỗng");
        logger.info("Kết quả TC_isEmptyUploadFile_02: Trả về true như mong đợi.");
    }

    // TC_isEmptyUploadFile_03: Mảng chứa 1 file rỗng
    @Test
    public void testIsEmptyUploadFile_WithSingleEmptyFile() {
        logger.info("Bắt đầu TC_isEmptyUploadFile_03: Kiểm tra mảng chứa 1 file rỗng.");
        // Chuẩn bị: Mảng chứa 1 file với originalFilename rỗng
        when(mockFile.getOriginalFilename()).thenReturn("");
        MultipartFile[] images = new MultipartFile[]{mockFile};
        logger.info("Dữ liệu chuẩn bị: images = [MultipartFile với originalFilename = \"\"]");

        // Thực hiện: Gọi phương thức
        boolean result = productService.isEmptyUploadFile(images);

        // Kiểm tra và log
        assertTrue(result, "Phải trả về true vì mảng chỉ chứa 1 file rỗng");
        verify(mockFile, times(1)).getOriginalFilename();
        logger.info("Kết quả TC_isEmptyUploadFile_03: Trả về true như mong đợi.");
    }

    // TC_isEmptyUploadFile_04: Mảng chứa 1 file không rỗng
    @Test
    public void testIsEmptyUploadFile_WithSingleNonEmptyFile() {
        logger.info("Bắt đầu TC_isEmptyUploadFile_04: Kiểm tra mảng chứa 1 file không rỗng.");
        // Chuẩn bị: Mảng chứa 1 file với originalFilename không rỗng
        when(mockFile.getOriginalFilename()).thenReturn("image.jpg");
        MultipartFile[] images = new MultipartFile[]{mockFile};
        logger.info("Dữ liệu chuẩn bị: images = [MultipartFile với originalFilename = \"image.jpg\"]");

        // Thực hiện: Gọi phương thức
        boolean result = productService.isEmptyUploadFile(images);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì mảng chứa 1 file không rỗng");
        verify(mockFile, times(1)).getOriginalFilename();
        logger.info("Kết quả TC_isEmptyUploadFile_04: Trả về false như mong đợi.");
    }

    // TC_isEmptyUploadFile_05: Mảng chứa nhiều file
    @Test
    public void testIsEmptyUploadFile_WithMultipleFilesAllEmpty() {
        logger.info("Bắt đầu TC_isEmptyUploadFile_05: Kiểm tra mảng chứa nhiều file.");
        // Chuẩn bị: Mảng chứa 2 file với originalFilename rỗng
        MultipartFile mockFile1 = mock(MultipartFile.class);
        MultipartFile mockFile2 = mock(MultipartFile.class);
        when(mockFile1.getOriginalFilename()).thenReturn("");
        when(mockFile2.getOriginalFilename()).thenReturn("");
        MultipartFile[] images = new MultipartFile[]{mockFile1, mockFile2};
        logger.info("Dữ liệu chuẩn bị: images = [MultipartFile với originalFilename = \"\", MultipartFile với originalFilename = \"\"]");

        // Thực hiện: Gọi phương thức
        boolean result = productService.isEmptyUploadFile(images);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì mảng chứa nhiều file");
        // Không verify getOriginalFilename() vì phương thức không gọi nó khi images.length > 1
        logger.info("Kết quả TC_isEmptyUploadFile_05: Trả về false như mong đợi.");
    }

    // TC_isEmptyUploadFile_06: Mảng chứa nhiều file
    @Test
    public void testIsEmptyUploadFile_WithMultipleFilesSomeEmpty() {
        logger.info("Bắt đầu TC_isEmptyUploadFile_06: Kiểm tra mảng chứa nhiều file.");
        // Chuẩn bị: Mảng chứa 2 file, 1 file rỗng và 1 file không rỗng
        MultipartFile mockFile1 = mock(MultipartFile.class);
        MultipartFile mockFile2 = mock(MultipartFile.class);
        when(mockFile1.getOriginalFilename()).thenReturn("");
        when(mockFile2.getOriginalFilename()).thenReturn("image.jpg");
        MultipartFile[] images = new MultipartFile[]{mockFile1, mockFile2};
        logger.info("Dữ liệu chuẩn bị: images = [MultipartFile với originalFilename = \"\", MultipartFile với originalFilename = \"image.jpg\"]");

        // Thực hiện: Gọi phương thức
        boolean result = productService.isEmptyUploadFile(images);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì mảng chứa nhiều file");
        // Không verify getOriginalFilename() vì phương thức không gọi nó khi images.length > 1
        logger.info("Kết quả TC_isEmptyUploadFile_06: Trả về false như mong đợi.");
    }

    // TC_isEmptyUploadFile_07: Mảng chứa nhiều file
    @Test
    public void testIsEmptyUploadFile_WithMultipleFilesAllNonEmpty() {
        logger.info("Bắt đầu TC_isEmptyUploadFile_07: Kiểm tra mảng chứa nhiều file.");
        // Chuẩn bị: Mảng chứa 2 file không rỗng
        MultipartFile mockFile1 = mock(MultipartFile.class);
        MultipartFile mockFile2 = mock(MultipartFile.class);
        when(mockFile1.getOriginalFilename()).thenReturn("image1.jpg");
        when(mockFile2.getOriginalFilename()).thenReturn("image2.jpg");
        MultipartFile[] images = new MultipartFile[]{mockFile1, mockFile2};
        logger.info("Dữ liệu chuẩn bị: images = [MultipartFile với originalFilename = \"image1.jpg\", MultipartFile với originalFilename = \"image2.jpg\"]");

        // Thực hiện: Gọi phương thức
        boolean result = productService.isEmptyUploadFile(images);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì mảng chứa nhiều file");
        // Không verify getOriginalFilename() vì phương thức không gọi nó khi images.length > 1
        logger.info("Kết quả TC_isEmptyUploadFile_07: Trả về false như mong đợi.");
    }

    // TC_isEmptyUploadFile_08: Mảng chứa 1 file với originalFilename là null
    @Test
    public void testIsEmptyUploadFile_WithSingleFileNullName() {
        logger.info("Bắt đầu TC_isEmptyUploadFile_08: Kiểm tra mảng chứa 1 file với originalFilename là null.");
        // Chuẩn bị: Mảng chứa 1 file với originalFilename là null
        when(mockFile.getOriginalFilename()).thenReturn(null);
        MultipartFile[] images = new MultipartFile[]{mockFile};
        logger.info("Dữ liệu chuẩn bị: images = [MultipartFile với originalFilename = null]");

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(NullPointerException.class, () -> {
            productService.isEmptyUploadFile(images);
        }, "Phải ném NullPointerException khi originalFilename là null");

        // Kiểm tra tương tác và log
        verify(mockFile, times(1)).getOriginalFilename();
        logger.info("Kết quả TC_isEmptyUploadFile_08: Ném NullPointerException như mong đợi.");
    }
}