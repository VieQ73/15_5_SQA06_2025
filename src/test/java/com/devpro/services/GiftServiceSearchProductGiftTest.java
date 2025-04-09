package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.devpro.entities.BaseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.devpro.entities.Product;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lớp kiểm thử đơn vị cho phương thức searchProductGift của GiftService.
 */
@SpringBootTest
@Transactional
class GiftServiceSearchProductGiftTest {

    @Autowired
    private GiftService giftService;

    /**
     * TC_GS_22: Kiểm tra tìm kiếm sản phẩm với gift_id hợp lệ.
     * Dự kiến trả về tối đa 3 sản phẩm có trạng thái true.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchProductGiftWithValidId() {
        // Chuẩn bị
        Integer giftId = 13;

        // Thực thi
        List<Product> result = giftService.searchProductGift(giftId);

        // Kiểm tra
        assertNotNull(result);
        assertEquals(3, result.size()); // Chỉ trả về 3 sản phẩm do limit
        assertTrue(result.stream().allMatch(BaseEntity::getStatus)); // Tất cả đều có status = true
        assertTrue(result.stream().allMatch(p -> p.getGift().getId().equals(13)));
    }

    /**
     * TC_GS_23: Kiểm tra tìm kiếm sản phẩm với gift_id không tồn tại.
     * Dự kiến trả về danh sách rỗng.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchProductGiftWithNonExistentId() {
        // Chuẩn bị
        Integer nonExistentId = 9999;

        // Thực thi
        List<Product> result = giftService.searchProductGift(nonExistentId);

        // Kiểm tra
        assertNotNull(result);
        assertTrue(result.isEmpty()); // Không có sản phẩm nào thỏa mãn
    }

    /**
     * TC_GS_24: Kiểm tra tìm kiếm sản phẩm với gift_id là null.
     * Dự kiến ném ngoại lệ do id không hợp lệ.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchProductGiftWithNullId() {
        // Kiểm tra ngoại lệ khi truyền null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            giftService.searchProductGift(null);
        });

        // Kiểm tra thông báo lỗi (tùy thuộc vào xử lý thực tế trong phương thức)
        assertEquals("Gift ID cannot be null", exception.getMessage());
    }
}