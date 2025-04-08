package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.devpro.entities.BaseEntity;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class GiftServiceTestSearchProductGift {

    @Autowired
    private GiftService giftService;

    @Autowired
    private GiftRepo giftRepo;

    @Autowired
    private ProductRepo productRepo;

    @PersistenceContext
    private EntityManager entityManager;

    private Gift testGift;

    /**
     * Kiểm tra tìm kiếm sản phẩm với gift_id hợp lệ.
     * Dự kiến trả về tối đa 3 sản phẩm có trạng thái true.
     */
    @Test
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
     * Kiểm tra tìm kiếm sản phẩm với gift_id không tồn tại.
     * Dự kiến trả về danh sách rỗng.
     */
    @Test
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
     * Kiểm tra tìm kiếm sản phẩm với gift_id là null.
     * Dự kiến ném ngoại lệ do id không hợp lệ.
     */
    @Test
    void testSearchProductGiftWithNullId() {
        // Kiểm tra ngoại lệ khi truyền null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            giftService.searchProductGift(null);
        });

        // Kiểm tra thông báo lỗi (tùy thuộc vào xử lý thực tế trong phương thức)
        assertEquals("Gift ID cannot be null", exception.getMessage());
    }

}