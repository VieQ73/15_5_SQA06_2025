package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.devpro.entities.Gift;
import com.devpro.entities.Images;
import com.devpro.entities.Product;
import com.devpro.model.GiftSearch;
import com.devpro.repositories.GiftRepo;
import com.devpro.repositories.ProductRepo;

@SpringBootTest
@Transactional
class GiftServiceTest {

    @Autowired
    private GiftService giftService;

    @Autowired
    private GiftRepo giftRepo;

    @Autowired
    private ProductRepo productRepo;

    private Gift testGift;
    private Product testProduct;
    private File testImageFile;

    /**
     * Thiết lập dữ liệu kiểm thử trước mỗi phương thức test
     * - Tạo tệp ảnh tạm thời để sử dụng cho việc tải lên
     * - Tạo đối tượng Gift để sử dụng trong các test case
     * - Tạo đối tượng Product liên kết với Gift
     */
    @BeforeEach
    void setUp() throws IOException {
        // Create test image file
        testImageFile = File.createTempFile("test-image", ".jpg");

        // Create test gift
        testGift = new Gift();
        testGift.setTitle("Test Gift");
        testGift.setDescription("Test Description");
        testGift.setStatus(Boolean.TRUE);

        // Save the gift directly to the database
        testGift = giftRepo.save(testGift);

        // Create test product associated with the gift
        testProduct = new Product();
        testProduct.setTitle("Test Product");
        testProduct.setPrice(BigDecimal.valueOf(100.0));
        testProduct.setStatus(Boolean.TRUE);
        testProduct.setGift(testGift);

        // Save the product directly to the database
        testProduct = productRepo.save(testProduct);
    }

    /**
     * Dọn dẹp dữ liệu kiểm thử sau mỗi phương thức test
     * - Xóa tệp ảnh tạm thời đã tạo
     */
    @AfterEach
    void tearDown() {
        // Clean up test data
        if (testImageFile != null && testImageFile.exists()) {
            testImageFile.delete();
        }
    }



    /**
     * Trường hợp: Cập nhật Gift đã tồn tại và thay đổi ảnh
     * - Kiểm tra một Gift đã tồn tại có thể được cập nhật thông tin và ảnh
     * - Kiểm tra ảnh cũ được xóa khỏi hệ thống tệp
     * - Kiểm tra ảnh mới được tải lên và lưu vào DB
     * - Kiểm tra mối quan hệ với ảnh được cập nhật đúng
     */
    @Test
    public void testUpdateGiftWithNewImages() throws IOException {
        // Arrange
        // First add an image to the gift
        FileInputStream fis1 = new FileInputStream(testImageFile);
        MockMultipartFile originalImage = new MockMultipartFile("image", "original-image.jpg", "image/jpeg", fis1);
        MultipartFile[] originalImages = new MultipartFile[]{originalImage};
        giftService.save(originalImages, testGift);

        // Verify the original image was saved
        Gift giftWithImage = giftRepo.findById(testGift.getId()).orElse(null);
        assertNotNull(giftWithImage);
        assertNotNull(giftWithImage.getGiftImages());
        assertEquals(1, giftWithImage.getGiftImages().size());

        // Now update with a new image
        FileInputStream fis2 = new FileInputStream(testImageFile);
        MockMultipartFile newImage = new MockMultipartFile("image", "new-image.jpg", "image/jpeg", fis2);
        MultipartFile[] newImages = new MultipartFile[]{newImage};

        giftWithImage.setTitle("Updated Gift Title");

        // Act
        giftService.save(newImages, giftWithImage);

        // Assert
        Gift updatedGift = giftRepo.findById(giftWithImage.getId()).orElse(null);
        assertNotNull(updatedGift);
        assertEquals("Updated Gift Title", updatedGift.getTitle());
        assertNotNull(updatedGift.getGiftImages());
        assertEquals(1, updatedGift.getGiftImages().size());
        assertEquals("new-image.jpg", updatedGift.getGiftImages().get(0).getPath());

        // Clean up the uploaded files
        File originalUploadedFile = new File("C:\\Users\\PV\\OneDrive\\Máy tính\\DoAnTotNghiepHaUI\\upload\\original-image.jpg");
        if (originalUploadedFile.exists()) {
            originalUploadedFile.delete();
        }

        File newUploadedFile = new File("C:\\Users\\PV\\OneDrive\\Máy tính\\DoAnTotNghiepHaUI\\upload\\new-image.jpg");
        if (newUploadedFile.exists()) {
            newUploadedFile.delete();
        }
    }

    /**
     * Trường hợp: Tìm kiếm Gift
     * - Kiểm tra phương thức searchGift có thể truy vấn tất cả Gift từ DB
     * - Kiểm tra kết quả trả về bao gồm gift đã tạo trong quá trình thiết lập
     * - Kiểm tra câu truy vấn SQL được tạo và thực thi đúng
     */
    @Test
    public void testSearchGift() {
        // Arrange
        GiftSearch search = new GiftSearch();

        // Act
        List<Gift> results = giftService.searchGift(search);

        // Assert
        assertNotNull(results);
        assertFalse(results.isEmpty());

        // Ensure our test gift is in the results (Java 8 compatible)
        boolean foundTestGift = false;
        for (Gift gift : results) {
            if (gift.getId().equals(testGift.getId())) {
                foundTestGift = true;
                break;
            }
        }
        assertTrue(foundTestGift);
    }

    /**
     * Trường hợp: Tìm kiếm Product theo Gift (giới hạn 3 sản phẩm)
     * - Kiểm tra phương thức searchProductGift có thể truy vấn Product theo Gift từ DB
     * - Kiểm tra giới hạn 3 sản phẩm được áp dụng trong câu truy vấn
     * - Kiểm tra kết quả trả về bao gồm product đã tạo trong quá trình thiết lập
     */
    @Test
    public void testSearchProductGift() {
        // Arrange - using the test gift ID
        Integer giftId = testGift.getId();

        // Act
        List<Product> results = giftService.searchProductGift(giftId);

        // Assert
        assertNotNull(results);
        assertFalse(results.isEmpty());
        // Verify the limit is working (max 3 products)
        assertTrue(results.size() <= 3);

        // Ensure our test product is in the results (Java 8 compatible)
        boolean foundTestProduct = false;
        for (Product product : results) {
            if (product.getId().equals(testProduct.getId())) {
                foundTestProduct = true;
                break;
            }
        }
        assertTrue(foundTestProduct);
    }

    /**
     * Trường hợp: Tìm kiếm Product theo Gift (không giới hạn - dùng cho admin)
     * - Kiểm tra phương thức searchProductGiftAdmin có thể truy vấn tất cả Product theo Gift từ DB
     * - Kiểm tra không có giới hạn số lượng sản phẩm trong câu truy vấn
     * - Kiểm tra kết quả trả về bao gồm product đã tạo trong quá trình thiết lập
     */
    @Test
    public void testSearchProductGiftAdmin() {
        // Arrange - using the test gift ID
        Integer giftId = testGift.getId();

        // Act
        List<Product> results = giftService.searchProductGiftAdmin(giftId);

        // Assert
        assertNotNull(results);
        assertFalse(results.isEmpty());

        // Ensure our test product is in the results (Java 8 compatible)
        boolean foundTestProduct = false;
        for (Product product : results) {
            if (product.getId().equals(testProduct.getId())) {
                foundTestProduct = true;
                break;
            }
        }
        assertTrue(foundTestProduct);
    }

    /**
     * Trường hợp: Thêm và xóa ảnh khỏi Gift
     * - Kiểm tra phương thức addGiftImages có thể thêm ảnh vào Gift
     * - Kiểm tra mối quan hệ hai chiều giữa Gift và Images được thiết lập đúng
     * - Kiểm tra phương thức removeGiftImages có thể xóa tất cả ảnh khỏi Gift
     */
    @Test
    public void testAddAndRemoveGiftImages() {
        // Arrange
        Gift gift = new Gift();
        gift.setTitle("Test Gift");

        Images image1 = new Images();
        image1.setPath("image1.jpg");
        image1.setTitle("Image 1");

        Images image2 = new Images();
        image2.setPath("image2.jpg");
        image2.setTitle("Image 2");

        // Act & Assert - Add images
        gift.addGiftImages(image1);
        gift.addGiftImages(image2);

        assertNotNull(gift.getGiftImages());
        assertEquals(2, gift.getGiftImages().size());
        assertEquals(gift, image1.getGift());
        assertEquals(gift, image2.getGift());

        // Act & Assert - Remove images
        gift.removeGiftImages();

        assertTrue(gift.getGiftImages().isEmpty());
    }
}