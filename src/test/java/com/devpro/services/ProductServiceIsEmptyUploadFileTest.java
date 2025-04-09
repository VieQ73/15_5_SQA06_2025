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

    // TC_P_01 - testIsEmptyUploadFile_WithNullArray: Mảng images là null
    @Test
    public void testIsEmptyUploadFile_WithNullArray() {
        logger.info("Bắt đầu TC_P_01 - testIsEmptyUploadFile_WithNullArray: Kiểm tra mảng images là null.");
        // Chuẩn bị: Mảng images là null
        MultipartFile[] images = null;
        logger.info("Dữ liệu chuẩn bị: images = null");

        // Thực hiện: Gọi phương thức
        boolean result = productService.isEmptyUploadFile(images);

        // Kiểm tra và log
        assertTrue(result, "Phải trả về true vì mảng images là null");
        logger.info("Kết quả TC_P_01 - testIsEmptyUploadFile_WithNullArray: Kết thúc test case.");
    }

    // TC_P_02 - testIsEmptyUploadFile_WithEmptyArray: Mảng images rỗng
    @Test
    public void testIsEmptyUploadFile_WithEmptyArray() {
        logger.info("Bắt đầu TC_P_02 - testIsEmptyUploadFile_WithEmptyArray: Kiểm tra mảng images rỗng.");
        // Chuẩn bị: Mảng images rỗng
        MultipartFile[] images = new MultipartFile[]{};
        logger.info("Dữ liệu chuẩn bị: images = {}");

        // Thực hiện: Gọi phương thức
        boolean result = productService.isEmptyUploadFile(images);

        // Kiểm tra và log
        assertTrue(result, "Phải trả về true vì mảng images rỗng");
        logger.info("Kết quả TC_P_02 - testIsEmptyUploadFile_WithEmptyArray: Kết thúc test case.");
    }

    // TC_P_03 - testIsEmptyUploadFile_WithSingleEmptyFile: Mảng chứa 1 file rỗng
    @Test
    public void testIsEmptyUploadFile_WithSingleEmptyFile() {
        logger.info("Bắt đầu TC_P_03 - testIsEmptyUploadFile_WithSingleEmptyFile: Kiểm tra mảng chứa 1 file rỗng.");
        // Chuẩn bị: Mảng chứa 1 file với originalFilename rỗng
        when(mockFile.getOriginalFilename()).thenReturn("");
        MultipartFile[] images = new MultipartFile[]{mockFile};
        logger.info("Dữ liệu chuẩn bị: images = [MultipartFile với originalFilename = \"\"]");

        // Thực hiện: Gọi phương thức
        boolean result = productService.isEmptyUploadFile(images);

        // Kiểm tra và log
        assertTrue(result, "Phải trả về true vì mảng chỉ chứa 1 file rỗng");
        verify(mockFile, times(1)).getOriginalFilename();
        logger.info("Kết quả TC_P_03 - testIsEmptyUploadFile_WithSingleEmptyFile: Kết thúc test case.");
    }

    // TC_P_04 - testIsEmptyUploadFile_WithSingleNonEmptyFile: Mảng chứa 1 file không rỗng
    @Test
    public void testIsEmptyUploadFile_WithSingleNonEmptyFile() {
        logger.info("Bắt đầu TC_P_04 - testIsEmptyUploadFile_WithSingleNonEmptyFile: Kiểm tra mảng chứa 1 file không rỗng.");
        // Chuẩn bị: Mảng chứa 1 file với originalFilename không rỗng
        when(mockFile.getOriginalFilename()).thenReturn("image.jpg");
        MultipartFile[] images = new MultipartFile[]{mockFile};
        logger.info("Dữ liệu chuẩn bị: images = [MultipartFile với originalFilename = \"image.jpg\"]");

        // Thực hiện: Gọi phương thức
        boolean result = productService.isEmptyUploadFile(images);

        // Kiểm tra và log
        assertFalse(result, "Phải trả về false vì mảng chứa 1 file không rỗng");
        verify(mockFile, times(1)).getOriginalFilename();
        logger.info("Kết quả TC_P_04 - testIsEmptyUploadFile_WithSingleNonEmptyFile: Kết thúc test case.");
    }

    // TC_P_05 - testIsEmptyUploadFile_WithMultipleFilesAllEmpty: Mảng chứa nhiều file đều rỗng
    @Test
    public void testIsEmptyUploadFile_WithMultipleFilesAllEmpty() {
        logger.info("Bắt đầu TC_P_05 - testIsEmptyUploadFile_WithMultipleFilesAllEmpty: Kiểm tra mảng chứa nhiều file đều rỗng.");
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
        logger.info("Kết quả TC_P_05 - testIsEmptyUploadFile_WithMultipleFilesAllEmpty: Kết thúc test case.");
    }

    // TC_P_06 - testIsEmptyUploadFile_WithMultipleFilesSomeEmpty: Mảng chứa nhiều file, một số rỗng
    @Test
    public void testIsEmptyUploadFile_WithMultipleFilesSomeEmpty() {
        logger.info("Bắt đầu TC_P_06 - testIsEmptyUploadFile_WithMultipleFilesSomeEmpty: Kiểm tra mảng chứa nhiều file, một số rỗng.");
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
        logger.info("Kết quả TC_P_06 - testIsEmptyUploadFile_WithMultipleFilesSomeEmpty: Kết thúc test case.");
    }

    // TC_P_07 - testIsEmptyUploadFile_WithMultipleFilesAllNonEmpty: Mảng chứa nhiều file đều không rỗng
    @Test
    public void testIsEmptyUploadFile_WithMultipleFilesAllNonEmpty() {
        logger.info("Bắt đầu TC_P_07 - testIsEmptyUploadFile_WithMultipleFilesAllNonEmpty: Kiểm tra mảng chứa nhiều file đều không rỗng.");
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
        logger.info("Kết quả TC_P_07 - testIsEmptyUploadFile_WithMultipleFilesAllNonEmpty: Kết thúc test case.");
    }

    // TC_P_08 - testIsEmptyUploadFile_WithSingleFileNullName: Mảng chứa 1 file với originalFilename là null
    @Test
    public void testIsEmptyUploadFile_WithSingleFileNullName() {
        logger.info("Bắt đầu TC_P_08 - testIsEmptyUploadFile_WithSingleFileNullName: Kiểm tra mảng chứa 1 file với originalFilename là null.");
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
        logger.info("Kết quả TC_P_08 - testIsEmptyUploadFile_WithSingleFileNullName: Kết thúc test case.");
    }
}