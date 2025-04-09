package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.devpro.entities.Gift;
import com.devpro.entities.Images;
import com.devpro.entities.Product;
import com.devpro.repositories.GiftRepo;
import com.devpro.repositories.ProductRepo;

/**
 * Lớp kiểm thử đơn vị cho phương thức save của GiftService.
 */
@SpringBootTest
class GiftServiceSaveTest {

    @Autowired
    private GiftService giftService;

    @Autowired
    private GiftRepo giftRepo;

    @Autowired
    private ProductRepo productRepo;

    private Gift testGift;
    private Product testProduct;
    private File testImageFile;

    @BeforeEach
    void setUp() {
        // Tạo tệp hình ảnh kiểm thử
        testImageFile = new File("D:\\IntelliJ\\DoAnTotNghiepHaUI\\src\\test\\resources\\22.jpg");

        // Tạo một món quà kiểm thử
        testGift = new Gift();
        testGift.setTitle("Test Gift");
        testGift.setDescription("Test Description");
        testGift.setStatus(Boolean.TRUE);

        // Lưu món quà vào cơ sở dữ liệu
        testGift = giftRepo.save(testGift);

        // Tạo sản phẩm kiểm thử liên kết với món quà
        testProduct = new Product();
        testProduct.setTitle("Test Product");
        testProduct.setPrice(BigDecimal.valueOf(100.0));
        testProduct.setStatus(Boolean.TRUE);
        testProduct.setGift(testGift);

        // Lưu sản phẩm vào cơ sở dữ liệu
        testProduct = productRepo.save(testProduct);
    }



    /**
     * TC_GS_07: Kiểm thử lưu một món quà mới mà không có hình ảnh.
     * Đảm bảo món quà được lưu đúng cách và không có hình ảnh liên kết với nó.
     */
    @Test
    @Transactional
    @Rollback
    void testSaveNewGiftWithoutImages() throws IOException {
        // Chuẩn bị
        Gift newGift = new Gift();
        newGift.setTitle("New Gift");
        newGift.setDescription("New Description");
        newGift.setStatus(Boolean.TRUE);

        // Thực hiện
        giftService.save(null, newGift);

        // Kiểm tra
        Gift savedGift = giftRepo.findById(newGift.getId()).orElse(null);
        assertNotNull(savedGift);
        assertEquals("New Gift", savedGift.getTitle());
        assertEquals("New Description", savedGift.getDescription());
        assertTrue(savedGift.getGiftImages() == null || savedGift.getGiftImages().isEmpty());
    }

    /**
     * TC_GS_08: Kiểm thử lưu một món quà mới với mảng hình ảnh rỗng.
     * Đảm bảo món quà được lưu đúng cách và không có hình ảnh nào được thêm vào.
     */
    @Test
    @Transactional
    @Rollback
    void testSaveNewGiftWithEmptyImageArray() throws IOException {
        // Chuẩn bị
        Gift newGift = new Gift();
        newGift.setTitle("New Gift");
        newGift.setDescription("New Description");
        newGift.setStatus(Boolean.TRUE);

        MultipartFile[] emptyImages = new MultipartFile[0];

        // Thực hiện
        giftService.save(emptyImages, newGift);

        // Kiểm tra
        Gift savedGift = giftRepo.findById(newGift.getId()).orElse(null);
        assertNotNull(savedGift);
        assertTrue(savedGift.getGiftImages().isEmpty());
        assertEquals("New Gift", savedGift.getTitle());
    }

    /**
     * TC_GS_09: Kiểm thử lưu một món quà mới với một tệp hình ảnh rỗng.
     * Đảm bảo rằng không có hình ảnh nào được liên kết với món quà khi tệp hình ảnh rỗng được cung cấp.
     */
    @Test
    @Transactional
    @Rollback
    void testSaveNewGiftWithOneEmptyImage() throws IOException {
        // Chuẩn bị
        Gift newGift = new Gift();
        newGift.setTitle("New Gift");
        newGift.setDescription("New Description");
        newGift.setStatus(Boolean.TRUE);

        MockMultipartFile emptyFile = new MockMultipartFile("image", "", "image/jpeg", new byte[0]);
        MultipartFile[] emptyImages = new MultipartFile[]{emptyFile};

        // Thực hiện
        giftService.save(emptyImages, newGift);

        // Kiểm tra
        Gift savedGift = giftRepo.findById(newGift.getId()).orElse(null);
        assertNotNull(savedGift);
        assertTrue(savedGift.getGiftImages().isEmpty());
    }

    /**
     * TC_GS_10: Kiểm thử lưu một món quà mới với hình ảnh hợp lệ.
     * Đảm bảo món quà được lưu đúng cách và hình ảnh được liên kết với nó.
     */
    @Test
    @Transactional
    @Rollback
    void testSaveNewGiftWithImages() throws IOException {
        // Chuẩn bị
        Gift newGift = new Gift();
        newGift.setTitle("New Gift With Image");
        newGift.setDescription("New Description");
        newGift.setStatus(Boolean.TRUE);

        // Tạo một tệp hình ảnh thực tế cho kiểm thử
        FileInputStream fis = new FileInputStream(testImageFile);
        MockMultipartFile testImage = new MockMultipartFile("image", "test-image.jpg", "image/jpeg", fis);
        MultipartFile[] images = new MultipartFile[]{testImage};

        // Thực hiện
        giftService.save(images, newGift);

        // Kiểm tra
        Gift savedGift = giftRepo.findById(newGift.getId()).orElse(null);
        assertNotNull(savedGift);
        assertNotNull(savedGift.getGiftImages());
        assertFalse(savedGift.getGiftImages().isEmpty());
        assertEquals(1, savedGift.getGiftImages().size());
        assertEquals("test-image.jpg", savedGift.getGiftImages().get(0).getPath());

        // Dọn dẹp tệp đã tải lên
        File uploadedFile = new File("D:\\IntelliJ\\DoAnTotNghiepHaUI\\src\\main\\resources\\META-INF\\images\\upload" + testImage.getOriginalFilename());
        if (uploadedFile.exists()) {
            uploadedFile.delete();
        }
    }

    /**
     * TC_GS_11: Kiểm thử cập nhật một món quà hiện tại mà không thay đổi hình ảnh của nó.
     * Đảm bảo món quà được cập nhật đúng cách mà không thay đổi hình ảnh của nó.
     */
    @Test
    @Transactional
    @Rollback
    void testUpdateGiftWithoutImages() throws IOException {
        // Chuẩn bị
        Gift existingGift = testGift;
        existingGift.setTitle("Original Title");
        existingGift = giftRepo.save(existingGift);

        // Cập nhật món quà
        existingGift.setTitle("Updated Title");
        existingGift.setDescription("Updated Description");

        // Thực hiện
        giftService.save(null, existingGift);

        // Kiểm tra
        Gift updatedGift = giftRepo.findById(existingGift.getId()).orElse(null);
        assertNotNull(updatedGift);
        assertEquals("Updated Title", updatedGift.getTitle());
        assertEquals("Updated Description", updatedGift.getDescription());
    }

    /**
     * TC_GS_12: Kiểm thử cập nhật một món quà hiện tại và thay thế hình ảnh cũ bằng hình ảnh mới.
     * Đảm bảo rằng hình ảnh cũ được thay thế bằng hình ảnh mới khi cập nhật món quà.
     */
    @Test
    @Transactional
    @Rollback
    void testUpdateGiftWithNewImages() throws IOException {
        // Chuẩn bị
        Gift existingGift = testGift;
        existingGift.setTitle("Gift With Old Image");

        // Thêm một hình ảnh cũ giả
        Images oldImage = new Images();
        oldImage.setPath("old-image.jpg");
        oldImage.setTitle("old-image.jpg");
        existingGift.addGiftImages(oldImage);
        giftRepo.save(existingGift);

        // Lấy lại gift từ DB để cập nhật, tránh dùng object cũ đang chứa collection
        Gift giftToUpdate = giftRepo.findById(existingGift.getId()).get();

        // Tạo một hình ảnh mới để thay thế hình ảnh cũ
        FileInputStream fis = new FileInputStream(testImageFile);
        MockMultipartFile newImage = new MockMultipartFile("image", "new-image.jpg", "image/jpeg", fis);
        MultipartFile[] newImages = new MultipartFile[]{newImage};

        // Thực hiện
        giftService.save(newImages, giftToUpdate);
        fis.close();

        // Kiểm tra
        Gift updatedGift = giftRepo.findById(existingGift.getId()).orElse(null);
        assertNotNull(updatedGift);
        assertEquals(1, updatedGift.getGiftImages().size());
        assertEquals("new-image.jpg", updatedGift.getGiftImages().get(0).getPath());

        // Dọn dẹp tệp đã tải lên
        File uploadedFile = new File("D:\\IntelliJ\\DoAnTotNghiepHaUI\\src\\main\\resources\\META-INF\\images\\upload" + newImage.getOriginalFilename());
        if (uploadedFile.exists()) {
            uploadedFile.delete();
        }
    }

    /**
     * TC_GS_13:Kiểm thử cập nhật một món quà hiện tại với hình ảnh rỗng, đảm bảo rằng hình ảnh cũ được giữ lại.
     * Đảm bảo rằng nếu hình ảnh rỗng được tải lên, hình ảnh trước đó sẽ không bị thay đổi.
     */
    @Test
    @Transactional
    @Rollback
    void testUpdateGiftWithOneEmptyImageToKeepOld() throws IOException {
        // Chuẩn bị
        Gift existingGift = testGift;

        Images existingImage = new Images();
        existingImage.setPath("existing-image.jpg");
        existingImage.setTitle("existing-image.jpg");
        existingGift.addGiftImages(existingImage);
        giftRepo.save(existingGift);

        MockMultipartFile emptyFile = new MockMultipartFile("image", "", "image/jpeg", new byte[0]);
        MultipartFile[] images = new MultipartFile[]{emptyFile};

        // Thực hiện
        giftService.save(images, existingGift);
        // Kiểm tra
        Gift updatedGift = giftRepo.findById(existingGift.getId()).orElse(null);
        assertNotNull(updatedGift);
        assertEquals(1, updatedGift.getGiftImages().size());
        assertEquals("existing-image.jpg", updatedGift.getGiftImages().get(0).getPath());
    }

    /**
     * TC_GS_14: Kiểm thử lưu một món quà mới với cả hình ảnh hợp lệ và hình ảnh rỗng.
     * Đảm bảo chỉ những hình ảnh hợp lệ được liên kết với món quà, và hình ảnh rỗng sẽ bị bỏ qua.
     */
    @Test
    @Transactional
    @Rollback
    void testSaveGiftWithMixedValidAndEmptyImages() throws IOException {
        // Chuẩn bị
        Gift newGift = new Gift();
        newGift.setTitle("Gift with Mixed Images");
        newGift.setDescription("Gift Description");
        newGift.setStatus(Boolean.TRUE);

        // Tạo một hình ảnh hợp lệ
        FileInputStream fis = new FileInputStream(testImageFile);
        MockMultipartFile validImage = new MockMultipartFile("image", "valid.jpg", "image/jpeg", fis);

        // Tạo một hình ảnh rỗng
        MockMultipartFile emptyImage = new MockMultipartFile("image", "", "image/jpeg", new byte[0]);

        MultipartFile[] mixedImages = new MultipartFile[]{emptyImage, validImage};

        // Thực hiện
        giftService.save(mixedImages, newGift);
        fis.close();
        // Kiểm tra
        Gift savedGift = giftRepo.findById(newGift.getId()).orElse(null);
        assertNotNull(savedGift);
        assertNotNull(savedGift.getGiftImages());
        assertEquals(1, savedGift.getGiftImages().size());
        assertEquals("valid.jpg", savedGift.getGiftImages().get(0).getPath());

        // Dọn dẹp tệp đã tải lên
        File uploadedFile = new File("D:\\IntelliJ\\DoAnTotNghiepHaUI\\src\\main\\resources\\META-INF\\images\\upload" + validImage.getOriginalFilename());
        if (uploadedFile.exists()) {
            uploadedFile.delete();
        }
    }

    /**
     * TC_GS_15: Kiểm thử lưu một món quà với mảng hình ảnh null.
     * Đảm bảo rằng không có hình ảnh nào được liên kết với món quà nếu mảng hình ảnh là null.
     */
    @Test
    @Transactional
    @Rollback
    void testSaveGiftWithNullImageArray() throws IOException {
        // Chuẩn bị
        Gift newGift = new Gift();
        newGift.setTitle("Gift with Null Image Array");
        newGift.setDescription("Gift with null image array");

        // Thực hiện
        giftService.save(null, newGift);

        // Kiểm tra
        Gift savedGift = giftRepo.findById(newGift.getId()).orElse(null);
        assertNotNull(savedGift);
        assertTrue(savedGift.getGiftImages().isEmpty());
    }
}
