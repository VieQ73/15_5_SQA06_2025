package com.devpro.services;

import com.devpro.entities.Images;
import com.devpro.entities.News;
import com.devpro.repositories.NewsRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Đảm bảo rollback sau mỗi lần test
class NewsServiceTestSave {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepo newsRepo;

    private final String IMAGE_PATH = "D:\\IntelliJ\\DoAnTotNghiepHaUI\\src\\test\\resources\\";

    /**
     * Chuẩn bị dữ liệu trước mỗi bài test
     */
    @BeforeEach
    public void setup() {
        // Xóa tất cả các file ảnh tạm trong thư mục test trước mỗi bài test
        File testDir = new File(IMAGE_PATH);
        File[] testFiles = testDir.listFiles((dir, name) -> name.startsWith("test_"));
        if (testFiles != null) {
            for (File file : testFiles) {
                file.delete();
            }
        }
    }

    /**
     * Dọn dẹp sau mỗi bài test
     */
    @AfterEach
    public void cleanup() {
        // Xóa tất cả các file ảnh tạm trong thư mục test sau mỗi bài test
        File testDir = new File(IMAGE_PATH);
        File[] testFiles = testDir.listFiles((dir, name) -> name.startsWith("test_"));
        if (testFiles != null) {
            for (File file : testFiles) {
                file.delete();
            }
        }
    }

    /**
     * Test chức năng thêm mới một tin tức với ảnh
     * Kiểm tra việc tạo mới tin tức và lưu ảnh vào thư mục
     */
    @Test
    public void testLuuTinTucMoiCoAnh() throws IOException {
        // Tạo một đối tượng tin tức mới
        News news = new News();
        news.setTitle("Tin tức test");
        news.setSeo("Nội dung tin tức test");
        news.setStatus(true);

        // Tạo file ảnh mẫu để test
        MockMultipartFile image = new MockMultipartFile(
                "image", "test_new_image.jpg", "image/jpeg", "test image content".getBytes()
        );
        MultipartFile[] images = new MultipartFile[]{image};

        // Lưu tin tức mới
        newsService.save(images, news);

        // Kiểm tra tin tức đã được lưu vào cơ sở dữ liệu
        assertNotNull(news.getId(), "Tin tức phải được lưu với ID");

        // Lấy tin tức từ cơ sở dữ liệu để kiểm tra
        News savedNews = newsRepo.findById(news.getId()).orElse(null);
        assertNotNull(savedNews, "Tin tức phải tồn tại trong cơ sở dữ liệu");
        assertEquals("Tin tức test", savedNews.getTitle(), "Tiêu đề phải khớp");

        // Kiểm tra ảnh đã được lưu
        assertNotNull(savedNews.getNewsImages(), "Danh sách ảnh không được null");
        assertFalse(savedNews.getNewsImages().isEmpty(), "Danh sách ảnh không được rỗng");
        assertEquals("test_new_image.jpg", savedNews.getNewsImages().get(0).getPath(), "Tên file ảnh phải khớp");

        // Kiểm tra file vật lý đã tồn tại
        File physicalFile = new File(IMAGE_PATH + "test_new_image.jpg");
        assertTrue(physicalFile.exists(), "File ảnh phải tồn tại trên hệ thống");
    }

    /**
     * Test chức năng cập nhật tin tức đã tồn tại với ảnh mới
     * Kiểm tra việc xóa ảnh cũ và thêm ảnh mới
     */
    @Test
    public void testCapNhatTinTucVoiAnhMoi() throws IOException, InterruptedException {
        // Tạo một đối tượng tin tức mới để test
        News news = new News();
        news.setTitle("Tin tức ban đầu");
        news.setSeo("Nội dung tin tức ban đầu");
        news.setStatus(true);
        news.setCreatedDate(new Date());

        // Tạo và lưu ảnh ban đầu
        MockMultipartFile originalImage = new MockMultipartFile(
                "image", "test_original_image.jpg", "image/jpeg", "original image content".getBytes()
        );
        MultipartFile[] originalImages = new MultipartFile[]{originalImage};

        // Lưu tin tức ban đầu
        newsService.save(originalImages, news);

        // Đảm bảo file vật lý tồn tại để test việc xóa
        File originalPhysicalFile = new File(IMAGE_PATH + "test_original_image.jpg");
        assertTrue(originalPhysicalFile.exists(), "File ảnh ban đầu phải tồn tại trên hệ thống");

        // Lấy tin tức đã lưu từ cơ sở dữ liệu
        News existingNews = newsRepo.findById(news.getId()).orElseThrow(() -> new RuntimeException("Không tìm thấy tin tức test"));

        // Lưu thông tin ảnh cũ để kiểm tra sau
        List<String> oldImagePaths = new ArrayList<>();
        for (Images img : existingNews.getNewsImages()) {
            oldImagePaths.add(img.getPath());
        }

        // Chuẩn bị thông tin cập nhật
        News updatedNews = new News();
        updatedNews.setId(existingNews.getId());
        updatedNews.setTitle("Tiêu đề đã cập nhật");
        updatedNews.setSeo("Nội dung đã cập nhật");
        updatedNews.setStatus(existingNews.getStatus());

        // Tạo file ảnh mới
        MockMultipartFile newImage = new MockMultipartFile(
                "image", "test_updated_image.jpg", "image/jpeg", "updated image content".getBytes()
        );
        MultipartFile[] newImages = new MultipartFile[]{newImage};

        // Lưu cập nhật
        Date beforeUpdate = new Date();
        Thread.sleep(100); // đợi một chút để đảm bảo thời gian cập nhật khác
        newsService.save(newImages, updatedNews);

        // Lấy tin tức đã cập nhật từ cơ sở dữ liệu
        News savedNews = newsRepo.findById(existingNews.getId()).orElse(null);
        assertNotNull(savedNews, "Tin tức phải tồn tại sau khi cập nhật");

        // Kiểm tra thông tin đã được cập nhật
        assertEquals("Tiêu đề đã cập nhật", savedNews.getTitle(), "Tiêu đề phải được cập nhật");
        assertEquals("Nội dung đã cập nhật", savedNews.getSeo(), "Nội dung phải được cập nhật");
        assertNotNull(savedNews.getUpdatedDate(), "Ngày cập nhật không được null");
        assertTrue(savedNews.getUpdatedDate().after(beforeUpdate), "Ngày cập nhật phải sau thời điểm trước khi cập nhật");

        // Kiểm tra ảnh cũ đã bị xóa trên hệ thống
        for (String oldPath : oldImagePaths) {
            File oldFile = new File(IMAGE_PATH + oldPath);
            assertFalse(oldFile.exists(), "File ảnh cũ phải bị xóa");
        }

        // Kiểm tra ảnh mới đã được lưu
        assertNotNull(savedNews.getNewsImages(), "Danh sách ảnh không được null");
        assertFalse(savedNews.getNewsImages().isEmpty(), "Danh sách ảnh không được rỗng");
        assertEquals("test_updated_image.jpg", savedNews.getNewsImages().get(0).getPath(), "Tên file ảnh mới phải khớp");

        // Kiểm tra file ảnh mới vật lý đã tồn tại
        File newPhysicalFile = new File(IMAGE_PATH + "test_updated_image.jpg");
        assertTrue(newPhysicalFile.exists(), "File ảnh mới phải tồn tại trên hệ thống");
    }

    /**
     * Test chức năng cập nhật tin tức đã tồn tại nhưng giữ nguyên ảnh cũ
     * Kiểm tra việc giữ nguyên ảnh cũ khi không có ảnh mới được tải lên
     */
    @Test
    public void testCapNhatTinTucGiuNguyenAnh() throws IOException {
        // Tạo một đối tượng tin tức mới để test
        News news = new News();
        news.setTitle("Tin tức ban đầu - giữ ảnh");
        news.setSeo("Nội dung tin tức ban đầu - giữ ảnh");
        news.setStatus(true);
        Date createdDate = new Date();
        news.setCreatedDate(createdDate);

        // Tạo và lưu ảnh ban đầu
        MockMultipartFile originalImage = new MockMultipartFile(
                "image", "test_keep_image.jpg", "image/jpeg", "keep image content".getBytes()
        );
        MultipartFile[] originalImages = new MultipartFile[]{originalImage};

        // Lưu tin tức ban đầu
        newsService.save(originalImages, news);

        // Lấy tin tức đã lưu từ cơ sở dữ liệu
        News existingNews = newsRepo.findById(news.getId()).orElseThrow(() -> new RuntimeException("Không tìm thấy tin tức test"));

        // Lưu thông tin ảnh cũ để kiểm tra sau
        List<String> oldImagePaths = new ArrayList<>();
        for (Images img : existingNews.getNewsImages()) {
            oldImagePaths.add(img.getPath());
        }

        // Chuẩn bị thông tin cập nhật
        News updatedNews = new News();
        updatedNews.setId(existingNews.getId());
        updatedNews.setTitle("Tiêu đề đã cập nhật - giữ ảnh");
        updatedNews.setSeo("Nội dung đã cập nhật - giữ ảnh");
        updatedNews.setStatus(existingNews.getStatus());

        // Không có ảnh mới
        MultipartFile[] emptyImages = new MultipartFile[0];

        // Lưu cập nhật
        newsService.save(emptyImages, updatedNews);

        // Lấy tin tức đã cập nhật từ cơ sở dữ liệu
        News savedNews = newsRepo.findById(existingNews.getId()).orElse(null);
        assertNotNull(savedNews, "Tin tức phải tồn tại sau khi cập nhật");

        // Kiểm tra thông tin đã được cập nhật
        assertEquals("Tiêu đề đã cập nhật - giữ ảnh", savedNews.getTitle(), "Tiêu đề phải được cập nhật");
        assertEquals("Nội dung đã cập nhật - giữ ảnh", savedNews.getSeo(), "Nội dung phải được cập nhật");

        // Kiểm tra ngày tạo được giữ nguyên
        assertEquals(createdDate, savedNews.getCreatedDate(), "Ngày tạo phải được giữ nguyên");

        // Kiểm tra ảnh vẫn được giữ nguyên
        assertNotNull(savedNews.getNewsImages(), "Danh sách ảnh không được null");
        assertEquals(oldImagePaths.size(), savedNews.getNewsImages().size(), "Số lượng ảnh phải giữ nguyên");

        // Kiểm tra từng ảnh
        List<String> currentImagePaths = new ArrayList<>();
        for (Images img : savedNews.getNewsImages()) {
            currentImagePaths.add(img.getPath());
        }

        // So sánh danh sách đường dẫn ảnh
        for (String oldPath : oldImagePaths) {
            assertTrue(currentImagePaths.contains(oldPath), "Danh sách ảnh phải chứa path ảnh cũ: " + oldPath);
        }

        // Kiểm tra file vật lý vẫn tồn tại
        File keepImageFile = new File(IMAGE_PATH + "test_keep_image.jpg");
        assertTrue(keepImageFile.exists(), "File ảnh ban đầu phải vẫn tồn tại trên hệ thống");
    }

    /**
     * Test chức năng lưu tin tức với mảng ảnh null
     * Kiểm tra xử lý khi không có ảnh được cung cấp
     */
    @Test
    public void testLuuTinTucVoiAnhNull() throws IOException {
        // Tạo một đối tượng tin tức mới
        News news = new News();
        news.setTitle("Tin tức không có ảnh");
        news.setSeo("Nội dung tin tức không có ảnh");
        news.setStatus(true);

        // Lưu tin tức với mảng ảnh null
        newsService.save(null, news);

        // Kiểm tra tin tức đã được lưu vào cơ sở dữ liệu
        assertNotNull(news.getId(), "Tin tức phải được lưu với ID");

        // Lấy tin tức từ cơ sở dữ liệu để kiểm tra
        News savedNews = newsRepo.findById(news.getId()).orElse(null);
        assertNotNull(savedNews, "Tin tức phải tồn tại trong cơ sở dữ liệu");
        assertEquals("Tin tức không có ảnh", savedNews.getTitle(), "Tiêu đề phải khớp");

        // Kiểm tra danh sách ảnh trống hoặc null
        if (savedNews.getNewsImages() != null) {
            assertTrue(savedNews.getNewsImages().isEmpty(), "Danh sách ảnh phải rỗng");
        }
    }

    /**
     * Test chức năng lưu tin tức với ảnh có tên trống
     * Kiểm tra xử lý với file ảnh không hợp lệ
     */
    @Test
    public void testLuuTinTucVoiAnhTenTrong() throws IOException {
        // Tạo một đối tượng tin tức mới
        News news = new News();
        news.setTitle("Tin tức với ảnh tên trống");
        news.setSeo("Nội dung tin tức với ảnh tên trống");
        news.setStatus(true);

        // Tạo file ảnh với tên trống
        MockMultipartFile emptyImage = new MockMultipartFile(
                "image", "", "image/jpeg", "test image content".getBytes()
        );
        MultipartFile[] images = new MultipartFile[]{emptyImage};

        // Lưu tin tức
        newsService.save(images, news);

        // Kiểm tra tin tức đã được lưu vào cơ sở dữ liệu
        assertNotNull(news.getId(), "Tin tức phải được lưu với ID");

        // Lấy tin tức từ cơ sở dữ liệu để kiểm tra
        News savedNews = newsRepo.findById(news.getId()).orElse(null);
        assertNotNull(savedNews, "Tin tức phải tồn tại trong cơ sở dữ liệu");

        // Kiểm tra danh sách ảnh trống hoặc null
        if (savedNews.getNewsImages() != null) {
            assertTrue(savedNews.getNewsImages().isEmpty(), "Danh sách ảnh phải rỗng");
        }
    }

    /**
     * Test phương thức isEmptyUploadFile với mảng null
     * Kiểm tra xử lý với tham số null
     */
    @Test
    public void testKiemTraAnhRongVoiMangNull() {
        // Gọi phương thức private thông qua reflection
        java.lang.reflect.Method method;
        try {
            method = NewsService.class.getDeclaredMethod("isEmptyUploadFile", MultipartFile[].class);
            method.setAccessible(true);
            boolean result = (boolean) method.invoke(newsService, new Object[]{null});
            assertTrue(result, "Phương thức phải trả về true với mảng null");
        } catch (Exception e) {
            fail("Không thể gọi phương thức isEmptyUploadFile: " + e.getMessage());
        }
    }

    /**
     * Test phương thức isEmptyUploadFile với mảng rỗng
     * Kiểm tra xử lý với mảng không có phần tử
     */
    @Test
    public void testKiemTraAnhRongVoiMangRong() {
        // Gọi phương thức private thông qua reflection
        java.lang.reflect.Method method;
        try {
            method = NewsService.class.getDeclaredMethod("isEmptyUploadFile", MultipartFile[].class);
            method.setAccessible(true);
            boolean result = (boolean) method.invoke(newsService, new Object[]{new MultipartFile[0]});
            assertTrue(result, "Phương thức phải trả về true với mảng rỗng");
        } catch (Exception e) {
            fail("Không thể gọi phương thức isEmptyUploadFile: " + e.getMessage());
        }
    }

    /**
     * Test phương thức isEmptyUploadFile với mảng chứa file tên rỗng
     * Kiểm tra xử lý với file không hợp lệ
     */
    @Test
    public void testKiemTraAnhRongVoiTenFileRong() {
        // Tạo file ảnh với tên trống
        MockMultipartFile emptyImage = new MockMultipartFile(
                "image", "", "image/jpeg", "test image content".getBytes()
        );
        MultipartFile[] images = new MultipartFile[]{emptyImage};

        // Gọi phương thức private thông qua reflection
        java.lang.reflect.Method method;
        try {
            method = NewsService.class.getDeclaredMethod("isEmptyUploadFile", MultipartFile[].class);
            method.setAccessible(true);
            boolean result = (boolean) method.invoke(newsService, (Object) images);
            assertTrue(result, "Phương thức phải trả về true với file tên rỗng");
        } catch (Exception e) {
            fail("Không thể gọi phương thức isEmptyUploadFile: " + e.getMessage());
        }
    }

    /**
     * Test phương thức isEmptyUploadFile với file hợp lệ
     * Kiểm tra xử lý với file hợp lệ
     */
    @Test
    public void testKiemTraAnhRongVoiFileHopLe() {
        // Tạo file ảnh hợp lệ
        MockMultipartFile validImage = new MockMultipartFile(
                "image", "test_valid.jpg", "image/jpeg", "test image content".getBytes()
        );
        MultipartFile[] images = new MultipartFile[]{validImage};

        // Gọi phương thức private thông qua reflection
        java.lang.reflect.Method method;
        try {
            method = NewsService.class.getDeclaredMethod("isEmptyUploadFile", MultipartFile[].class);
            method.setAccessible(true);
            boolean result = (boolean) method.invoke(newsService, (Object) images);
            assertFalse(result, "Phương thức phải trả về false với file hợp lệ");
        } catch (Exception e) {
            fail("Không thể gọi phương thức isEmptyUploadFile: " + e.getMessage());
        }
    }
}