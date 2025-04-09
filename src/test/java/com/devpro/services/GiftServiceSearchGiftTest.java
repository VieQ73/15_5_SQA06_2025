package com.devpro.services;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.devpro.model.GiftSearch;
import com.devpro.repositories.GiftRepo;
import com.devpro.repositories.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.devpro.entities.Gift;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lớp kiểm thử đơn vị cho phương thức searchGift của GiftService.
 */
@SpringBootTest
@Transactional
class GiftServiceSearchGiftTest {

    @Autowired
    private GiftService giftService;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private GiftRepo giftRepo;

    /**
     * TC_GS_16:Kiểm tra tìm kiếm quà tặng với đối tượng GiftSearch rỗng.
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
     * TC_GS_17: Kiểm tra tìm kiếm quà tặng chỉ với giftSeo (tên) được cung cấp.
     * Dự kiến trả về các quà tặng có tên chứa chuỗi tìm kiếm.
     */
    @Test
    @Transactional
    @Rollback
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
     * TC_GS_18: Kiểm tra tìm kiếm quà tặng chỉ với title (mô tả) được cung cấp.
     * Dự kiến trả về các quà tặng có mô tả chứa chuỗi tìm kiếm.
     */
    @Test
    @Transactional
    @Rollback
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
     * TC_GS_19: Kiểm tra tìm kiếm quà tặng với cả giftSeo và title được cung cấp.
     * Dự kiến trả về quà tặng thỏa mãn cả hai điều kiện.
     */
    @Test
    @Transactional
    @Rollback
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
     * TC_GS_20: Kiểm tra tìm kiếm quà tặng với đối tượng GiftSearch là rỗng.
     * Dự kiến trả về null.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchGiftWithNullSearch() {
        // Thực thi
        GiftSearch giftSearch = new GiftSearch();
        List<Gift> result = giftService.searchGift(giftSearch);
        // Kiểm tra
        assertNull(result);
    }

    /**
     * TC_GS_21: Kiểm tra tìm kiếm quà tặng với chuỗi rỗng cho giftSeo và title.
     * Dự kiến trả về tất cả quà tặng vì không có điều kiện nào được áp dụng.
     */
    @Test
    @Transactional
    @Rollback
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

