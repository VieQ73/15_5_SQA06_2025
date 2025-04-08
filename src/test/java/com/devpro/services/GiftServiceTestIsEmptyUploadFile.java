package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class GiftServiceTestIsEmptyUploadFile {

    @Autowired
    private GiftService giftService;

    private Method isEmptyUploadFileMethod;

    /**
     * Thiết lập trước mỗi test case.
     * Sử dụng reflection để lấy phương thức private isEmptyUploadFile.
     */
    @BeforeEach
    void setUp() throws NoSuchMethodException {
        // Lấy phương thức private thông qua reflection
        isEmptyUploadFileMethod = GiftService.class.getDeclaredMethod("isEmptyUploadFile", MultipartFile[].class);
        isEmptyUploadFileMethod.setAccessible(true); // Cho phép truy cập phương thức private
    }

    /**
     * Kiểm tra với mảng images là null.
     * Dự kiến trả về true vì mảng không tồn tại.
     */
    @Test
    void testIsEmptyUploadFileWithNullArray() throws Exception {
        // Chuẩn bị
        MultipartFile[] images = null;

        // Thực thi
        boolean result = (boolean) isEmptyUploadFileMethod.invoke(giftService, (Object) null);

        // Kiểm tra
        assertTrue(result);
    }

    /**
     * Kiểm tra với mảng images rỗng.
     * Dự kiến trả về true vì không có phần tử nào trong mảng.
     */
    @Test
    void testIsEmptyUploadFileWithEmptyArray() throws Exception {
        // Chuẩn bị
        MultipartFile[] images = new MultipartFile[0];

        // Thực thi
        boolean result = (boolean) isEmptyUploadFileMethod.invoke(giftService, (Object) images);

        // Kiểm tra
        assertTrue(result);
    }

    /**
     * Kiểm tra với mảng chứa một file có tên rỗng.
     * Dự kiến trả về true vì file không hợp lệ.
     */
    @Test
    void testIsEmptyUploadFileWithSingleEmptyFile() throws Exception {
        // Chuẩn bị
        MultipartFile emptyFile = new MockMultipartFile("file", "", "text/plain", new byte[0]);
        MultipartFile[] images = new MultipartFile[]{emptyFile};

        // Thực thi
        boolean result = (boolean) isEmptyUploadFileMethod.invoke(giftService, (Object) images);

        // Kiểm tra
        assertTrue(result);
    }

    /**
     * Kiểm tra với mảng chứa một file hợp lệ.
     * Dự kiến trả về false vì file có tên hợp lệ.
     */
    @Test
    void testIsEmptyUploadFileWithSingleValidFile() throws Exception {
        // Chuẩn bị
        MultipartFile validFile = new MockMultipartFile("file", "test.txt", "text/plain", "content".getBytes());
        MultipartFile[] images = new MultipartFile[]{validFile};

        // Thực thi
        boolean result = (boolean) isEmptyUploadFileMethod.invoke(giftService, (Object) images);

        // Kiểm tra
        assertFalse(result);
    }

    /**
     * Kiểm tra với mảng chứa nhiều file hợp lệ.
     * Dự kiến trả về false vì có ít nhất một file hợp lệ.
     */
    @Test
    void testIsEmptyUploadFileWithMultipleValidFiles() throws Exception {
        // Chuẩn bị
        MultipartFile file1 = new MockMultipartFile("file1", "test1.txt", "text/plain", "content1".getBytes());
        MultipartFile file2 = new MockMultipartFile("file2", "test2.txt", "text/plain", "content2".getBytes());
        MultipartFile[] images = new MultipartFile[]{file1, file2};

        // Thực thi
        boolean result = (boolean) isEmptyUploadFileMethod.invoke(giftService, (Object) images);

        // Kiểm tra
        assertFalse(result);
    }

    /**
     * Kiểm tra với mảng chứa file hợp lệ và file rỗng.
     * Dự kiến trả về false vì có ít nhất một file hợp lệ.
     */
    @Test
    void testIsEmptyUploadFileWithMixedFiles() throws Exception {
        // Chuẩn bị
        MultipartFile emptyFile = new MockMultipartFile("file", "", "text/plain", new byte[0]);
        MultipartFile validFile = new MockMultipartFile("file", "test.txt", "text/plain", "content".getBytes());
        MultipartFile[] images = new MultipartFile[]{emptyFile, validFile};

        // Thực thi
        boolean result = (boolean) isEmptyUploadFileMethod.invoke(giftService, (Object) images);

        // Kiểm tra
        assertFalse(result);
    }
}