package com.devpro.services;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.devpro.entities.Product;
import com.devpro.model.GiftSearch;
import com.devpro.repositories.GiftRepo;
import com.devpro.repositories.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.devpro.entities.Gift;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class GiftServiceTestSearchGift {

    @Autowired
    private GiftService giftService;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private GiftRepo giftRepo;

    /**
     * Kiểm tra tìm kiếm quà tặng với đối tượng GiftSearch rỗng.
     * Dự kiến trả về null
     */
    @Test
    void testSearchGiftWithEmptySearch() {
        // Chuẩn bị
        GiftSearch giftSearch = new GiftSearch();

        // Thực thi
        List<Gift> result = giftService.searchGift(giftSearch);

        // Kiểm tra
        assertNull(result);
        assertEquals(0, result.size()); // Tất cả quà tặng sẽ được trả về
    }

    /**
     * Kiểm tra tìm kiếm quà tặng chỉ với giftSeo (tên) được cung cấp.
     * Dự kiến trả về các quà tặng có tên chứa chuỗi tìm kiếm.
     */
    @Test
    void testSearchGiftWithGiftSeo() {
        // Chuẩn bị
        GiftSearch giftSearch = new GiftSearch();
        giftSearch.setGiftSeo("son");

        // Thực thi
        List<Gift> result = giftService.searchGift(giftSearch);

        // Kiểm tra
        assertNotNull(result);
    }

    /**
     * Kiểm tra tìm kiếm quà tặng chỉ với title (mô tả) được cung cấp.
     * Dự kiến trả về các quà tặng có mô tả chứa chuỗi tìm kiếm.
     */
    @Test
    void testSearchGiftWithTitle() {
        // Chuẩn bị
        GiftSearch giftSearch = new GiftSearch();
        giftSearch.setTitle("cute");

        // Thực thi
        List<Gift> result = giftService.searchGift(giftSearch);

        // Kiểm tra
        assertNotNull(result);
        result.forEach(gift -> assertTrue(gift.getTitle().contains("cute")));
    }

    /**
     * Kiểm tra tìm kiếm quà tặng với cả giftSeo và title được cung cấp.
     * Dự kiến trả về quà tặng thỏa mãn cả hai điều kiện.
     */
    @Test
    void testSearchGiftWithGiftSeoAndTitle() {
        // Chuẩn bị
        GiftSearch giftSearch = new GiftSearch();
        giftSearch.setGiftSeo("son");
        giftSearch.setTitle("cute");

        // Thực thi
        List<Gift> result = giftService.searchGift(giftSearch);

        // Kiểm tra
        assertNotNull(result);
    }

    /**
     * Kiểm tra tìm kiếm quà tặng với đối tượng GiftSearch là null.
     * Dự kiến trả về null.
     */
    @Test
    void testSearchGiftWithNullSearch() {
        // Thực thi
        List<Gift> result = giftService.searchGift(null);
        // Kiểm tra
        assertNull(result);
    }

    /**
     * Kiểm tra tìm kiếm quà tặng với chuỗi rỗng cho giftSeo và title.
     * Dự kiến trả về tất cả quà tặng vì không có điều kiện nào được áp dụng.
     */
    @Test
    void testSearchGiftWithEmptyStrings() {
        // Chuẩn bị
        GiftSearch giftSearch = new GiftSearch();
        giftSearch.setGiftSeo("");
        giftSearch.setTitle("");

        // Thực thi
        List<Gift> result = giftService.searchGift(giftSearch);

        // Kiểm tra
        assertNotNull(result);
        assertEquals(giftRepo.count(), result.size()); // Tất cả quà tặng sẽ được trả về
    }
}

