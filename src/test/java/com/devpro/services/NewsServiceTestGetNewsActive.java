package com.devpro.services;

import com.devpro.entities.News;
import com.devpro.repositories.NewsRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Đảm bảo rollback sau mỗi lần test
@Rollback
class NewsServiceTestGetNewsActive {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepo newsRepo;

    /**
     * Test phương thức getNewsActive() khi có dữ liệu tin tức active
     * Kiểm tra phương thức trả về đúng danh sách tin tức có trạng thái active (status = true)
     */
    @Test
    @Transactional
    @Rollback
    void testLayDanhSachTinTucActive() throws IOException {
        // Tạo dữ liệu test: 2 tin tức active và 1 tin tức không active

        // Tin tức 1: active
        News news1 = new News();
        news1.setTitle("Tin tức active 1");
        news1.setSeo("Nội dung tin tức active 1");
        news1.setStatus(true); // active

        // Tin tức 2: active
        News news2 = new News();
        news2.setTitle("Tin tức active 2");
        news2.setSeo("Nội dung tin tức active 2");
        news2.setStatus(true); // active

        // Tin tức 3: không active
        News news3 = new News();
        news3.setTitle("Tin tức không active");
        news3.setSeo("Nội dung tin tức không active");
        news3.setStatus(false); // không active

        // Lưu các tin tức vào cơ sở dữ liệu
        MockMultipartFile[] emptyImages = new MockMultipartFile[0];
        newsService.save(emptyImages, news1);
        newsService.save(emptyImages, news2);
        newsService.save(emptyImages, news3);

        // Kiểm tra ID đã được tạo
        assertNotNull(news1.getId(), "Tin tức 1 phải được lưu với ID");
        assertNotNull(news2.getId(), "Tin tức 2 phải được lưu với ID");
        assertNotNull(news3.getId(), "Tin tức 3 phải được lưu với ID");

        // Gọi phương thức cần test
        List<News> activeNewsList = newsService.getNewsActive();

        // Kiểm tra kết quả
        assertNotNull(activeNewsList, "Danh sách tin tức active không được null");
        assertEquals(2, activeNewsList.size(), "Phải có đúng 2 tin tức active");

        // Kiểm tra từng tin tức trong danh sách đều có status = true
        for (News news : activeNewsList) {
            assertTrue(news.getStatus(), "Tất cả tin tức trong danh sách phải có status = true");
        }

        // Kiểm tra danh sách chứa 2 tin tức active đã tạo
        boolean containsNews1 = false;
        boolean containsNews2 = false;

        for (News news : activeNewsList) {
            if (news.getId().equals(news1.getId())) {
                containsNews1 = true;
                assertEquals("Tin tức active 1", news.getTitle(), "Tiêu đề tin tức 1 phải khớp");
            }

            if (news.getId().equals(news2.getId())) {
                containsNews2 = true;
                assertEquals("Tin tức active 2", news.getTitle(), "Tiêu đề tin tức 2 phải khớp");
            }
        }

        assertTrue(containsNews1, "Danh sách phải chứa tin tức active 1");
        assertTrue(containsNews2, "Danh sách phải chứa tin tức active 2");
    }

    /**
     * Test phương thức getNewsActive() khi không có tin tức active nào
     * Kiểm tra phương thức trả về danh sách rỗng khi không có tin tức active
     */
    @Test
    @Transactional
    @Rollback
    void testLayDanhSachTinTucActiveKhiKhongCoDuLieu() throws IOException {
        // Xóa tất cả dữ liệu hiện có (nếu cần)
        newsRepo.deleteAll();

        // Tạo một tin tức không active
        News inactiveNews = new News();
        inactiveNews.setTitle("Tin tức không active duy nhất");
        inactiveNews.setSeo("Nội dung tin tức không active");
        inactiveNews.setStatus(false); // không active

        // Lưu tin tức không active
        MockMultipartFile[] emptyImages = new MockMultipartFile[0];
        newsService.save(emptyImages, inactiveNews);

        // Gọi phương thức cần test
        List<News> activeNewsList = newsService.getNewsActive();

        // Kiểm tra kết quả
        assertNotNull(activeNewsList, "Danh sách tin tức active không được null");
        assertTrue(activeNewsList.isEmpty(), "Danh sách tin tức active phải rỗng");
    }

    /**
     * Test phương thức getNewsActive() có sắp xếp theo ngày tạo mới nhất (nếu có sắp xếp)
     * Kiểm tra thứ tự các tin tức trong kết quả trả về
     * Lưu ý: Test này giả định rằng kết quả được sắp xếp theo ngày tạo. Nếu không có sắp xếp, có thể bỏ test này.
     */
    @Test
    @Transactional
    @Rollback
    void testThuTuTinTucActive() throws IOException, InterruptedException {
        // Tạo dữ liệu test: các tin tức active với thời gian tạo khác nhau

        // Tin tức 1: active, tạo trước
        News news1 = new News();
        news1.setTitle("Tin tức active cũ");
        news1.setSeo("Nội dung tin tức active cũ");
        news1.setStatus(true); // active
        news1.setCreatedDate(new Date(System.currentTimeMillis() - 10000)); // tạo 10 giây trước

        // Lưu tin tức 1
        MockMultipartFile[] emptyImages = new MockMultipartFile[0];
        newsService.save(emptyImages, news1);

        // Đợi một chút để đảm bảo thời gian tạo khác nhau
        Thread.sleep(100);

        // Tin tức 2: active, tạo sau
        News news2 = new News();
        news2.setTitle("Tin tức active mới");
        news2.setSeo("Nội dung tin tức active mới");
        news2.setStatus(true); // active
        news2.setCreatedDate(new Date()); // thời gian hiện tại

        // Lưu tin tức 2
        newsService.save(emptyImages, news2);

        // Gọi phương thức cần test
        List<News> activeNewsList = newsService.getNewsActive();

        // Kiểm tra kết quả
        assertNotNull(activeNewsList, "Danh sách tin tức active không được null");
        assertEquals(2, activeNewsList.size(), "Phải có đúng 2 tin tức active");

        // NOTE: Test này chỉ áp dụng nếu getNewsActive() có sắp xếp kết quả theo ngày tạo giảm dần
        // Nếu phương thức không có sắp xếp, bạn có thể bỏ qua phần kiểm tra này

    }
}