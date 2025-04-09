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
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lớp kiểm thử đơn vị cho phương thức getNewsIndex của NewsService.
 */
@SpringBootTest
@Transactional
@Rollback// Đảm bảo rollback sau mỗi lần test
class NewsServiceGetNewsIndexTest {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepo newsRepo;

    /**
     * TC_NS_01: Test phương thức getNewsIndex() khi có dữ liệu tin tức
     * Kiểm tra phương thức trả về đúng số lượng tin tức (4 tin) và tất cả có trạng thái active (status = true)
     */
    @Test
    @Transactional
    @Rollback
    void testGetNewsIndex() throws IOException {
        // Tạo dữ liệu test: 5 tin tức active và 2 tin tức không active

        // Xóa tất cả dữ liệu hiện có
        newsRepo.deleteAll();

        // Tin tức active
        for (int i = 1; i <= 5; i++) {
            News news = new News();
            news.setTitle("Tin tức active " + i);
            news.setSeo("Nội dung tin tức active " + i);
            news.setStatus(true); // active

            // Lưu tin tức vào cơ sở dữ liệu
            MockMultipartFile[] emptyImages = new MockMultipartFile[0];
            newsService.save(emptyImages, news);

            // Kiểm tra ID đã được tạo
            assertNotNull(news.getId(), "Tin tức active " + i + " phải được lưu với ID");
        }

        // Tin tức không active
        for (int i = 1; i <= 2; i++) {
            News news = new News();
            news.setTitle("Tin tức không active " + i);
            news.setSeo("Nội dung tin tức không active " + i);
            news.setStatus(false); // không active

            // Lưu tin tức vào cơ sở dữ liệu
            MockMultipartFile[] emptyImages = new MockMultipartFile[0];
            newsService.save(emptyImages, news);

            // Kiểm tra ID đã được tạo
            assertNotNull(news.getId(), "Tin tức không active " + i + " phải được lưu với ID");
        }

        // Gọi phương thức cần test
        List<News> newsIndexList = newsService.getNewsIndex();

        // Kiểm tra kết quả
        assertNotNull(newsIndexList, "Danh sách tin tức không được null");
        assertEquals(4, newsIndexList.size(), "Phải có đúng 4 tin tức trong kết quả");

        // Kiểm tra từng tin tức trong danh sách đều có status = true
        for (News news : newsIndexList) {
            assertTrue(news.getStatus(), "Tất cả tin tức trong danh sách phải có status = true");
        }
    }

    /**
     * TC_NS_02: Test phương thức getNewsIndex() khi không có tin tức active nào
     * Kiểm tra phương thức trả về danh sách rỗng khi không có tin tức active
     */
    @Test
    @Transactional
    @Rollback
    void testGetNewsIndexKhiKhongCoTinTucActive() throws IOException {
        // Xóa tất cả dữ liệu hiện có
        newsRepo.deleteAll();

        // Tạo một số tin tức không active
        for (int i = 1; i <= 3; i++) {
            News inactiveNews = new News();
            inactiveNews.setTitle("Tin tức không active " + i);
            inactiveNews.setSeo("Nội dung tin tức không active " + i);
            inactiveNews.setStatus(false); // không active

            // Lưu tin tức không active
            MockMultipartFile[] emptyImages = new MockMultipartFile[0];
            newsService.save(emptyImages, inactiveNews);
        }

        // Gọi phương thức cần test
        List<News> newsIndexList = newsService.getNewsIndex();

        // Kiểm tra kết quả
        assertNotNull(newsIndexList, "Danh sách tin tức không được null");
        assertTrue(newsIndexList.isEmpty(), "Danh sách tin tức phải rỗng khi không có tin tức active");
    }

    /**
     * TC_NS_03: Test tính ngẫu nhiên của phương thức getNewsIndex()
     * Kiểm tra xem kết quả của nhiều lần gọi có khác nhau không do ORDER BY RAND()
     */
    @Test
    @Transactional
    @Rollback
    void testTinhNgauNhienCuaGetNewsIndex() throws IOException {
        // Xóa tất cả dữ liệu hiện có
        newsRepo.deleteAll();

        // Tạo nhiều tin tức active để tăng khả năng randomness
        for (int i = 1; i <= 10; i++) {
            News news = new News();
            news.setTitle("Tin tức test " + i);
            news.setSeo("Nội dung tin tức test " + i);
            news.setStatus(true); // active

            // Lưu tin tức vào cơ sở dữ liệu
            MockMultipartFile[] emptyImages = new MockMultipartFile[0];
            newsService.save(emptyImages, news);
        }

        // Gọi phương thức getNewsIndex nhiều lần và lưu kết quả
        List<News> result1 = newsService.getNewsIndex();
        List<News> result2 = newsService.getNewsIndex();
        List<News> result3 = newsService.getNewsIndex();

        // Kiểm tra số lượng kết quả
        assertEquals(4, result1.size(), "Kết quả 1 phải có đúng 4 tin tức");
        assertEquals(4, result2.size(), "Kết quả 2 phải có đúng 4 tin tức");
        assertEquals(4, result3.size(), "Kết quả 3 phải có đúng 4 tin tức");

        // Tạo các tập hợp ID từ các kết quả
        Set<Integer> idSet1 = new HashSet<>();
        Set<Integer> idSet2 = new HashSet<>();
        Set<Integer> idSet3 = new HashSet<>();

        for (News news : result1) {
            idSet1.add(news.getId());
        }

        for (News news : result2) {
            idSet2.add(news.getId());
        }

        for (News news : result3) {
            idSet3.add(news.getId());
        }

        // Kiểm tra xem có ít nhất một sự khác biệt giữa các kết quả
        // Lưu ý: Test này có thể thỉnh thoảng fail nếu ngẫu nhiên các kết quả giống nhau
        boolean allResultsIdentical = idSet1.equals(idSet2) && idSet2.equals(idSet3);

        // Nếu tất cả 3 kết quả giống nhau hoàn toàn, điều này rất không có khả năng với ORDER BY RAND()
        assertFalse(allResultsIdentical, "Các kết quả của nhiều lần gọi getNewsIndex() không nên hoàn toàn giống nhau do ORDER BY RAND()");
    }

    /**
     * TC_NS_04: Test phương thức getNewsIndex() khi số lượng tin tức active ít hơn giới hạn 4
     * Kiểm tra phương thức trả về tất cả tin tức active có sẵn
     */
    @Test
    @Transactional
    @Rollback
    void testGetNewsIndexKhiSoLuongTinTucItHonGioiHan() throws IOException {
        // Xóa tất cả dữ liệu hiện có
        newsRepo.deleteAll();

        // Tạo 2 tin tức active (ít hơn giới hạn 4)
        for (int i = 1; i <= 2; i++) {
            News news = new News();
            news.setTitle("Tin tức active " + i);
            news.setSeo("Nội dung tin tức active " + i);
            news.setStatus(true); // active

            // Lưu tin tức vào cơ sở dữ liệu
            MockMultipartFile[] emptyImages = new MockMultipartFile[0];
            newsService.save(emptyImages, news);
        }

        // Tạo 1 tin tức không active
        News inactiveNews = new News();
        inactiveNews.setTitle("Tin tức không active");
        inactiveNews.setSeo("Nội dung tin tức không active");
        inactiveNews.setStatus(false); // không active

        // Lưu tin tức không active
        MockMultipartFile[] emptyImages = new MockMultipartFile[0];
        newsService.save(emptyImages, inactiveNews);

        // Gọi phương thức cần test
        List<News> newsIndexList = newsService.getNewsIndex();

        // Kiểm tra kết quả
        assertNotNull(newsIndexList, "Danh sách tin tức không được null");
        assertEquals(2, newsIndexList.size(), "Phải có đúng 2 tin tức active trong kết quả");

        // Kiểm tra từng tin tức trong danh sách đều có status = true
        for (News news : newsIndexList) {
            assertTrue(news.getStatus(), "Tất cả tin tức trong danh sách phải có status = true");
        }
    }
}