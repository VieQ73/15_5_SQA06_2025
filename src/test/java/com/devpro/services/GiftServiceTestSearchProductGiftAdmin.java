package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.devpro.entities.Gift;
import com.devpro.entities.Product;
import com.devpro.repositories.GiftRepo;
import com.devpro.repositories.ProductRepo;

@SpringBootTest
class GiftServiceTestSearchProductGiftAdmin {

    @Autowired
    private GiftService giftService;

    @Autowired
    private ProductRepo productRepo;


    /**
     * Kiểm tra tìm kiếm sản phẩm với gift_id hợp lệ.
     * Dự kiến trả về tất cả sản phẩm có trạng thái true liên kết với gift_id.
     */
    @Test
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
     * Kiểm tra tìm kiếm sản phẩm với gift_id không tồn tại.
     * Dự kiến trả về danh sách rỗng.
     */
    @Test
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
     * Kiểm tra tìm kiếm sản phẩm với gift_id là null.
     * Dự kiến ném ngoại lệ do id không hợp lệ.
     */
    @Test
    void testSearchProductGiftAdminWithNullId() {
        // Kiểm tra ngoại lệ khi truyền null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            giftService.searchProductGiftAdmin(null);
        });

        // Kiểm tra thông báo lỗi
        assertEquals("Gift ID cannot be null", exception.getMessage());
    }

}