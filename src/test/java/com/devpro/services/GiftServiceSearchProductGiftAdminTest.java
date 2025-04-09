package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.devpro.entities.Product;
import com.devpro.repositories.ProductRepo;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lớp kiểm thử đơn vị cho phương thức searchProductGiftAdmin của GiftService.
 */
@SpringBootTest
class GiftServiceSearchProductGiftAdminTest {

    @Autowired
    private GiftService giftService;

    @Autowired
    private ProductRepo productRepo;


    /**
     * TC_GS_25: Kiểm tra tìm kiếm sản phẩm với gift_id hợp lệ.
     * Dự kiến trả về tất cả sản phẩm có trạng thái true liên kết với gift_id.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchProductGiftAdminWithValidId() {
        // Chuẩn bị
        Integer giftId = 15;

        // Thực thi
        List<Product> result = giftService.searchProductGiftAdmin(giftId);

        // Kiểm tra
        assertNotNull(result);
        assertTrue(result.stream().allMatch(p -> p.getStatus())); // Tất cả đều có status = true
        assertTrue(result.stream().allMatch(p -> p.getGift().getId().equals(15)));
    }

    /**
     * TC_GS_26: Kiểm tra tìm kiếm sản phẩm với gift_id không tồn tại.
     * Dự kiến trả về danh sách rỗng.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchProductGiftAdminWithNonExistentId() {
        // Chuẩn bị
        Integer nonExistentId = 9999;

        // Thực thi
        List<Product> result = giftService.searchProductGiftAdmin(nonExistentId);

        // Kiểm tra
        assertNotNull(result);
        assertTrue(result.isEmpty()); // Không có sản phẩm nào thỏa mãn
    }

    /**
     * TC_GS_27: Kiểm tra tìm kiếm sản phẩm với gift_id là null.
     * Dự kiến ném ngoại lệ do id không hợp lệ.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchProductGiftAdminWithNullId() {
        // Kiểm tra ngoại lệ khi truyền null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            giftService.searchProductGiftAdmin(null);
        });

        // Kiểm tra thông báo lỗi
        assertEquals("Gift ID cannot be null", exception.getMessage());
    }

}