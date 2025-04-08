package com.devpro.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Lớp kiểm thử đơn vị cho phương thức isEmptyUploadFile của NewsService.
 */
@SpringBootTest
class NewsServiceIsEmptyUploadFileTest {

    @Autowired
    private NewsService newsService;
    private Method isEmptyUploadFileMethod;

    /**
     * Thiết lập trước mỗi lần chạy test:
     * Lấy method isEmptyUploadFile (private) từ lớp NewsService thông qua Reflection.
     */
    @BeforeEach
    void setUp() throws Exception {
        isEmptyUploadFileMethod = NewsService.class.getDeclaredMethod("isEmptyUploadFile", MultipartFile[].class);
        isEmptyUploadFileMethod.setAccessible(true); // Cho phép truy cập method private
    }

    /**
     * Hàm tiện ích để gọi phương thức isEmptyUploadFile qua Reflection.
     *
     * @param files Mảng MultipartFile cần kiểm tra
     * @return true nếu mảng file rỗng hoặc không hợp lệ, ngược lại false
     */
    private boolean invokeIsEmptyUploadFile(MultipartFile[] files) throws Exception {
        return (boolean) isEmptyUploadFileMethod.invoke(newsService, (Object) files);
    }

    /**
     * TC_NS_05: Kiểm tra với mảng images là null.
     * Dự kiến trả về true vì mảng không tồn tại.
     */
    @Test
    void testNullArray() throws Exception {
        assertTrue(invokeIsEmptyUploadFile(null));
    }

    /**
     * TC_NS_06: Kiểm tra với mảng images có độ dài bằng 0.
     * Dự kiến trả về true vì không có file nào được upload.
     */
    @Test
    void testEmptyArray() throws Exception {
        MultipartFile[] files = new MultipartFile[0];
        assertTrue(invokeIsEmptyUploadFile(files));
    }

    /**
     * TC_NS_07: Kiểm tra với một file duy nhất có tên file rỗng.
     * Dự kiến trả về true vì file không hợp lệ.
     */
    @Test
    void testSingleFileEmptyFilename() throws Exception {
        MultipartFile[] files = {
                new MockMultipartFile("file", "", "text/plain", new byte[0])
        };
        assertTrue(invokeIsEmptyUploadFile(files));
    }

    /**
     * TC_NS_08: Kiểm tra với một file duy nhất có tên file hợp lệ.
     * Dự kiến trả về false vì có file hợp lệ được upload.
     */
    @Test
    void testSingleFileValid() throws Exception {
        MultipartFile[] files = {
                new MockMultipartFile("file", "test.txt", "text/plain", new byte[]{1, 2})
        };
        assertFalse(invokeIsEmptyUploadFile(files));
    }

    /**
     * TC_NS_09: Kiểm tra với nhiều file (lớn hơn 1).
     * Dự kiến trả về false vì có nhiều file được upload.
     */
    @Test
    void testMultipleFiles() throws Exception {
        MultipartFile[] files = {
                new MockMultipartFile("file1", "a.txt", "text/plain", new byte[]{1}),
                new MockMultipartFile("file2", "b.txt", "text/plain", new byte[]{2})
        };
        assertFalse(invokeIsEmptyUploadFile(files));
    }
}
