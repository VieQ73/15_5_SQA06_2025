package com.devpro.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.devpro.entities.Images;
import com.devpro.entities.Product;
import com.devpro.repositories.ProductRepo;

/**
 * Lớp kiểm thử đơn vị cho ProductService, tập trung vào phương thức save().
 * Sử dụng Mockito để mock các phụ thuộc như ProductRepo, MultipartFile.
 * Log trạng thái dữ liệu và kết quả bằng SLF4J.
 */
public class ProductServiceSaveTest {

    // Khởi tạo logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceSaveTest.class);

    @InjectMocks
    private ProductService productService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private ProductSaleService productSaleService;

    @Mock
    private Query mockQuery;

    @Mock
    private MultipartFile mockFile;

    @Mock
    private File mockPhysicalFile;

    @BeforeEach
    public void thietLap() {
        // Khởi tạo mock
        MockitoAnnotations.initMocks(this);
        logger.info("Thiết lập dữ liệu kiểm thử hoàn tất.");
    }

    @AfterEach
    public void hoanTac() {
        // Reset mock
        reset(mockQuery, entityManager, productRepo, productSaleService, mockFile, mockPhysicalFile);
        logger.info("Hoàn tác dữ liệu kiểm thử hoàn tất.");
    }

    // TC_P_09 - testSave_NewProductNoImages: Lưu sản phẩm mới không có ảnh
    @Test
    public void testSave_NewProductNoImages() throws IOException {
        logger.info("Bắt đầu TC_P_09 - testSave_NewProductNoImages: Kiểm tra lưu sản phẩm mới không có ảnh.");
        // Chuẩn bị: Sản phẩm mới, không có ảnh
        Product product = new Product();
        product.setId(null);
        product.setAmount(10);
        MultipartFile[] productImages = null;
        logger.info("Dữ liệu chuẩn bị: productImages = null, product = {}", product);

        // Thực hiện: Gọi phương thức
        productService.save(productImages, product);

        // Kiểm tra và log
        verify(productRepo, times(1)).save(product);
        verify(mockFile, never()).transferTo(any(File.class));
        assertNotNull(product.getUpdatedDate(), "UpdatedDate phải được thiết lập");
        logger.info("Kết quả TC_P_09 - testSave_NewProductNoImages: Kết thúc test case.");
    }

    // TC_P_10 - testSave_NewProductWithEmptyImages: Lưu sản phẩm mới với mảng ảnh rỗng
    @Test
    public void testSave_NewProductWithEmptyImages() throws IOException {
        logger.info("Bắt đầu TC_P_10 - testSave_NewProductWithEmptyImages: Kiểm tra lưu sản phẩm mới với mảng ảnh rỗng.");
        // Chuẩn bị: Sản phẩm mới, mảng ảnh rỗng
        Product product = new Product();
        product.setId(null);
        product.setAmount(10);
        MultipartFile[] productImages = new MultipartFile[]{};
        logger.info("Dữ liệu chuẩn bị: productImages = {}, product = {}", product);

        // Thực hiện: Gọi phương thức
        productService.save(productImages, product);

        // Kiểm tra và log
        verify(productRepo, times(1)).save(product);
        verify(mockFile, never()).transferTo(any(File.class));
        assertNotNull(product.getUpdatedDate(), "UpdatedDate phải được thiết lập");
        logger.info("Kết quả TC_P_10 - testSave_NewProductWithEmptyImages: Kết thúc test case.");
    }

    // TC_P_11 - testSave_NewProductWithSingleImage: Lưu sản phẩm mới với 1 ảnh
    @Test
    public void testSave_NewProductWithSingleImage() throws IOException {
        logger.info("Bắt đầu TC_P_11 - testSave_NewProductWithSingleImage: Kiểm tra lưu sản phẩm mới với 1 ảnh.");
        // Chuẩn bị: Sản phẩm mới, 1 ảnh
        Product product = new Product();
        product.setId(null);
        product.setAmount(10);
        when(mockFile.getOriginalFilename()).thenReturn("image.jpg");
        MultipartFile[] productImages = new MultipartFile[]{mockFile};
        logger.info("Dữ liệu chuẩn bị: productImages = [MultipartFile với originalFilename = \"image.jpg\"], product = {}", product);

        // Thực hiện: Gọi phương thức
        productService.save(productImages, product);

        // Kiểm tra và log
        verify(productRepo, times(1)).save(product);
        verify(mockFile, times(1)).transferTo(any(File.class));
        assertEquals(1, product.getProductImages().size(), "Phải thêm 1 ảnh vào sản phẩm");
        assertEquals("image.jpg", product.getProductImages().get(0).getPath(), "Đường dẫn ảnh phải đúng");
        assertNotNull(product.getUpdatedDate(), "UpdatedDate phải được thiết lập");
        logger.info("Kết quả TC_P_11 - testSave_NewProductWithSingleImage: Kết thúc test case.");
    }

    // TC_P_12 - testSave_NewProductWithMultipleImages: Lưu sản phẩm mới với nhiều ảnh
    @Test
    public void testSave_NewProductWithMultipleImages() throws IOException {
        logger.info("Bắt đầu TC_P_12 - testSave_NewProductWithMultipleImages: Kiểm tra lưu sản phẩm mới với nhiều ảnh.");
        // Chuẩn bị: Sản phẩm mới, 2 ảnh
        Product product = new Product();
        product.setId(null);
        product.setAmount(10);
        MultipartFile mockFile1 = mock(MultipartFile.class);
        MultipartFile mockFile2 = mock(MultipartFile.class);
        when(mockFile1.getOriginalFilename()).thenReturn("image1.jpg");
        when(mockFile2.getOriginalFilename()).thenReturn("image2.jpg");
        MultipartFile[] productImages = new MultipartFile[]{mockFile1, mockFile2};
        logger.info("Dữ liệu chuẩn bị: productImages = [MultipartFile với originalFilename = \"image1.jpg\", MultipartFile với originalFilename = \"image2.jpg\"], product = {}", product);

        // Thực hiện: Gọi phương thức
        productService.save(productImages, product);

        // Kiểm tra và log
        verify(productRepo, times(1)).save(product);
        verify(mockFile1, times(1)).transferTo(any(File.class));
        verify(mockFile2, times(1)).transferTo(any(File.class));
        assertEquals(2, product.getProductImages().size(), "Phải thêm 2 ảnh vào sản phẩm");
        assertEquals("image1.jpg", product.getProductImages().get(0).getPath(), "Đường dẫn ảnh 1 phải đúng");
        assertEquals("image2.jpg", product.getProductImages().get(1).getPath(), "Đường dẫn ảnh 2 phải đúng");
        assertNotNull(product.getUpdatedDate(), "UpdatedDate phải được thiết lập");
        logger.info("Kết quả TC_P_12 - testSave_NewProductWithMultipleImages: Kết thúc test case.");
    }

    // TC_P_13 - testSave_ExistingProductNoImages: Lưu sản phẩm đã tồn tại, không có ảnh
    @Test
    public void testSave_ExistingProductNoImages() throws IOException {
        logger.info("Bắt đầu TC_P_13 - testSave_ExistingProductNoImages: Kiểm tra lưu sản phẩm đã tồn tại, không có ảnh.");
        // Chuẩn bị: Sản phẩm đã tồn tại, không có ảnh mới
        Product product = new Product();
        product.setId(1);
        product.setAmount(5);
        Product oldProduct = new Product();
        oldProduct.setId(1);
        oldProduct.setAmount(10);
        oldProduct.setCreatedDate(new Date());
        List<Images> oldImages = new ArrayList<>();
        Images oldImage = new Images();
        oldImage.setPath("old.jpg");
        oldImages.add(oldImage);
        oldProduct.setProductImages(oldImages);
        when(productRepo.findById(1)).thenReturn(Optional.of(oldProduct));
        MultipartFile[] productImages = null;
        logger.info("Dữ liệu chuẩn bị: productImages = null, product = {}, oldProduct = {}", product, oldProduct);

        // Thực hiện: Gọi phương thức
        productService.save(productImages, product);

        // Kiểm tra và log
        verify(productRepo, times(1)).save(product);
        verify(mockFile, never()).transferTo(any(File.class));
        assertEquals(15, product.getAmount(), "Amount phải được cộng từ oldProduct");
        assertEquals(oldImages, product.getProductImages(), "Ảnh cũ phải được giữ nguyên");
        assertEquals(oldProduct.getCreatedDate(), product.getCreatedDate(), "CreatedDate phải được giữ nguyên");
        assertNotNull(product.getUpdatedDate(), "UpdatedDate phải được thiết lập");
        logger.info("Kết quả TC_P_13 - testSave_ExistingProductNoImages: Kết thúc test case.");
    }

    // TC_P_14 - testSave_ExistingProductWithEmptyImages: Lưu sản phẩm đã tồn tại, mảng ảnh rỗng
    @Test
    public void testSave_ExistingProductWithEmptyImages() throws IOException {
        logger.info("Bắt đầu TC_P_14 - testSave_ExistingProductWithEmptyImages: Kiểm tra lưu sản phẩm đã tồn tại, mảng ảnh rỗng.");
        // Chuẩn bị: Sản phẩm đã tồn tại, mảng ảnh rỗng
        Product product = new Product();
        product.setId(1);
        product.setAmount(5);
        Product oldProduct = new Product();
        oldProduct.setId(1);
        oldProduct.setAmount(10);
        oldProduct.setCreatedDate(new Date());
        List<Images> oldImages = new ArrayList<>();
        Images oldImage = new Images();
        oldImage.setPath("old.jpg");
        oldImages.add(oldImage);
        oldProduct.setProductImages(oldImages);
        when(productRepo.findById(1)).thenReturn(Optional.of(oldProduct));
        MultipartFile[] productImages = new MultipartFile[]{};
        logger.info("Dữ liệu chuẩn bị: productImages = {}, product = {}, oldProduct = {}", product, oldProduct);

        // Thực hiện: Gọi phương thức
        productService.save(productImages, product);

        // Kiểm tra và log
        verify(productRepo, times(1)).save(product);
        verify(mockFile, never()).transferTo(any(File.class));
        assertEquals(15, product.getAmount(), "Amount phải được cộng từ oldProduct");
        assertEquals(oldImages, product.getProductImages(), "Ảnh cũ phải được giữ nguyên");
        assertEquals(oldProduct.getCreatedDate(), product.getCreatedDate(), "CreatedDate phải được giữ nguyên");
        assertNotNull(product.getUpdatedDate(), "UpdatedDate phải được thiết lập");
        logger.info("Kết quả TC_P_14 - testSave_ExistingProductWithEmptyImages: Kết thúc test case.");
    }

    // WIP: I'll continue with the remaining test cases below and ensure numbering continues correctly.

    // TC_P_15 - testSave_ExistingProductWithNewImages: Lưu sản phẩm đã tồn tại, có ảnh mới
    @Test
    public void testSave_ExistingProductWithNewImages() throws IOException {
        logger.info("Bắt đầu TC_P_15 - testSave_ExistingProductWithNewImages: Kiểm tra lưu sản phẩm đã tồn tại, có ảnh mới.");
        // Chuẩn bị: Sản phẩm đã tồn tại, có ảnh mới
        Product product = new Product();
        product.setId(1);
        product.setAmount(5);
        Product oldProduct = new Product();
        oldProduct.setId(1);
        oldProduct.setAmount(10);
        oldProduct.setCreatedDate(new Date());
        List<Images> oldImages = new ArrayList<>();
        Images oldImage = new Images();
        oldImage.setPath("old.jpg");
        oldImages.add(oldImage);
        oldProduct.setProductImages(oldImages);
        when(productRepo.findById(1)).thenReturn(Optional.of(oldProduct));
        when(mockFile.getOriginalFilename()).thenReturn("new.jpg");
        when(mockPhysicalFile.delete()).thenReturn(true);
        MultipartFile[] productImages = new MultipartFile[]{mockFile};
        logger.info("Dữ liệu chuẩn bị: productImages = [MultipartFile với originalFilename = \"new.jpg\"], product = {}, oldProduct = {}", product, oldProduct);

        // Thực hiện: Gọi phương thức
        productService.save(productImages, product);

        // Kiểm tra và log
        verify(productRepo, times(1)).save(product);
        verify(mockFile, times(1)).transferTo(any(File.class));
        verify(mockPhysicalFile, times(1)).delete();
        assertEquals(15, product.getAmount(), "Amount phải được cộng từ oldProduct");
        assertEquals(1, product.getProductImages().size(), "Phải có 1 ảnh mới");
        assertEquals("new.jpg", product.getProductImages().get(0).getPath(), "Đường dẫn ảnh mới phải đúng");
        assertNotNull(product.getUpdatedDate(), "UpdatedDate phải được thiết lập");
        logger.info("Kết quả TC_P_15 - testSave_ExistingProductWithNewImages: Kết thúc test case.");
    }

    // TC_P_16 - testSave_ExistingProductNotFound: Lưu sản phẩm đã tồn tại nhưng không tìm thấy trong DB
    @Test
    public void testSave_ExistingProductNotFound() throws IOException {
        logger.info("Bắt đầu TC_P_16 - testSave_ExistingProductNotFound: Kiểm tra lưu sản phẩm đã tồn tại nhưng không tìm thấy trong DB.");
        // Chuẩn bị: Sản phẩm đã tồn tại, không tìm thấy trong DB
        Product product = new Product();
        product.setId(1);
        product.setAmount(5);
        when(productRepo.findById(1)).thenReturn(Optional.empty());
        MultipartFile[] productImages = null;
        logger.info("Dữ liệu chuẩn bị: productImages = null, product = {}", product);

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(NoSuchElementException.class, () -> {
            productService.save(productImages, product);
        }, "Phải ném NoSuchElementException khi không tìm thấy sản phẩm");

        // Kiểm tra và log
        verify(productRepo, never()).save(any(Product.class));
        verify(mockFile, never()).transferTo(any(File.class));
        logger.info("Kết quả TC_P_16 - testSave_ExistingProductNotFound: Kết thúc test case.");
    }

    // TC_P_17 - testSave_NullProduct: Lưu sản phẩm khi product là null
    @Test
    public void testSave_NullProduct() throws IOException {
        logger.info("Bắt đầu TC_P_17 - testSave_NullProduct: Kiểm tra lưu sản phẩm khi product là null.");
        // Chuẩn bị: product là null
        Product product = null;
        MultipartFile[] productImages = null;
        logger.info("Dữ liệu chuẩn bị: productImages = null, product = null");

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(NullPointerException.class, () -> {
            productService.save(productImages, product);
        }, "Phải ném NullPointerException khi product là null");

        // Kiểm tra và log
        verify(productRepo, never()).save(any(Product.class));
        verify(mockFile, never()).transferTo(any(File.class));
        logger.info("Kết quả TC_P_17 - testSave_NullProduct: Kết thúc test case.");
    }

    // TC_P_18 - testSave_NewProductWithImageTransferFailure: Lưu sản phẩm mới khi transferTo() thất bại
    @Test
    public void testSave_NewProductWithImageTransferFailure() throws IOException {
        logger.info("Bắt đầu TC_P_18 - testSave_NewProductWithImageTransferFailure: Kiểm tra lưu sản phẩm mới khi transferTo() thất bại.");
        // Chuẩn bị: Sản phẩm mới, transferTo() ném IOException
        Product product = new Product();
        product.setId(null);
        product.setAmount(10);
        when(mockFile.getOriginalFilename()).thenReturn("image.jpg");
        doThrow(new IOException("Transfer failed")).when(mockFile).transferTo(any(File.class));
        MultipartFile[] productImages = new MultipartFile[]{mockFile};
        logger.info("Dữ liệu chuẩn bị: productImages = [MultipartFile với originalFilename = \"image.jpg\"], product = {}", product);

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(IOException.class, () -> {
            productService.save(productImages, product);
        }, "Phải ném IOException khi transferTo() thất bại");

        // Kiểm tra và log
        verify(productRepo, never()).save(any(Product.class));
        verify(mockFile, times(1)).transferTo(any(File.class));
        logger.info("Kết quả TC_P_18 - testSave_NewProductWithImageTransferFailure: Kết thúc test case.");
    }

    // TC_P_19 - testSave_ExistingProductWithImageTransferFailure: Lưu sản phẩm đã tồn tại khi transferTo() thất bại
    @Test
    public void testSave_ExistingProductWithImageTransferFailure() throws IOException {
        logger.info("Bắt đầu TC_P_19 - testSave_ExistingProductWithImageTransferFailure: Kiểm tra lưu sản phẩm đã tồn tại khi transferTo() thất bại.");
        // Chuẩn bị: Sản phẩm đã tồn tại, transferTo() ném IOException
        Product product = new Product();
        product.setId(1);
        product.setAmount(5);
        Product oldProduct = new Product();
        oldProduct.setId(1);
        oldProduct.setAmount(10);
        oldProduct.setCreatedDate(new Date());
        List<Images> oldImages = new ArrayList<>();
        Images oldImage = new Images();
        oldImage.setPath("old.jpg");
        oldImages.add(oldImage);
        oldProduct.setProductImages(oldImages);
        when(productRepo.findById(1)).thenReturn(Optional.of(oldProduct));
        when(mockFile.getOriginalFilename()).thenReturn("new.jpg");
        when(mockPhysicalFile.delete()).thenReturn(true);
        doThrow(new IOException("Transfer failed")).when(mockFile).transferTo(any(File.class));
        MultipartFile[] productImages = new MultipartFile[]{mockFile};
        logger.info("Dữ liệu chuẩn bị: productImages = [MultipartFile với originalFilename = \"new.jpg\"], product = {}, oldProduct = {}", product, oldProduct);

        // Thực hiện & Kiểm tra: Mong đợi ngoại lệ
        assertThrows(IOException.class, () -> {
            productService.save(productImages, product);
        }, "Phải ném IOException khi transferTo() thất bại");

        // Kiểm tra và log
        verify(productRepo, never()).save(any(Product.class));
        verify(mockFile, times(1)).transferTo(any(File.class));
        verify(mockPhysicalFile, times(1)).delete();
        logger.info("Kết quả TC_P_19 - testSave_ExistingProductWithImageTransferFailure: Kết thúc test case.");
    }

    // TC_P_20 - testSave_ExistingProductWithNullOldImages: Lưu sản phẩm đã tồn tại, ảnh cũ là null
    @Test
    public void testSave_ExistingProductWithNullOldImages() throws IOException {
        logger.info("Bắt đầu TC_P_20 - testSave_ExistingProductWithNullOldImages: Kiểm tra lưu sản phẩm đã tồn tại, ảnh cũ là null.");
        // Chuẩn bị: Sản phẩm đã tồn tại, ảnh cũ là null
        Product product = new Product();
        product.setId(1);
        product.setAmount(5);
        Product oldProduct = new Product();
        oldProduct.setId(1);
        oldProduct.setAmount(10);
        oldProduct.setCreatedDate(new Date());
        oldProduct.setProductImages(null);
        when(productRepo.findById(1)).thenReturn(Optional.of(oldProduct));
        MultipartFile[] productImages = null;
        logger.info("Dữ liệu chuẩn bị: productImages = null, product = {}, oldProduct = {}", product, oldProduct);

        // Thực hiện: Gọi phương thức
        productService.save(productImages, product);

        // Kiểm tra và log
        verify(productRepo, times(1)).save(product);
        verify(mockFile, never()).transferTo(any(File.class));
        assertEquals(15, product.getAmount(), "Amount phải được cộng từ oldProduct");
        assertNull(product.getProductImages(), "Ảnh cũ là null, không thay đổi");
        assertEquals(oldProduct.getCreatedDate(), product.getCreatedDate(), "CreatedDate phải được giữ nguyên");
        assertNotNull(product.getUpdatedDate(), "UpdatedDate phải được thiết lập");
        logger.info("Kết quả TC_P_20 - testSave_ExistingProductWithNullOldImages: Kết thúc test case.");
    }

    // TC_P_21 - testSave_ExistingProductWithEmptyOldImages: Lưu sản phẩm đã tồn tại, danh sách ảnh cũ rỗng
    @Test
    public void testSave_ExistingProductWithEmptyOldImages() throws IOException {
        logger.info("Bắt đầu TC_P_21 - testSave_ExistingProductWithEmptyOldImages: Kiểm tra lưu sản phẩm đã tồn tại, danh sách ảnh cũ rỗng.");
        // Chuẩn bị: Sản phẩm đã tồn tại, danh sách ảnh cũ rỗng
        Product product = new Product();
        product.setId(1);
        product.setAmount(5);
        Product oldProduct = new Product();
        oldProduct.setId(1);
        oldProduct.setAmount(10);
        oldProduct.setCreatedDate(new Date());
        oldProduct.setProductImages(new ArrayList<>());
        when(productRepo.findById(1)).thenReturn(Optional.of(oldProduct));
        MultipartFile[] productImages = null;
        logger.info("Dữ liệu chuẩn bị: productImages = null, product = {}, oldProduct = {}", product, oldProduct);

        // Thực hiện: Gọi phương thức
        productService.save(productImages, product);

        // Kiểm tra và log
        verify(productRepo, times(1)).save(product);
        verify(mockFile, never()).transferTo(any(File.class));
        assertEquals(15, product.getAmount(), "Amount phải được cộng từ oldProduct");
        assertTrue(product.getProductImages().isEmpty(), "Danh sách ảnh cũ rỗng, không thay đổi");
        assertEquals(oldProduct.getCreatedDate(), product.getCreatedDate(), "CreatedDate phải được giữ nguyên");
        assertNotNull(product.getUpdatedDate(), "UpdatedDate phải được thiết lập");
        logger.info("Kết quả TC_P_21 - testSave_ExistingProductWithEmptyOldImages: Kết thúc test case.");
    }

    // TC_P_22 - testSave_ExistingProductWithNewImagesAndDeleteFailure: Lưu sản phẩm đã tồn tại, xóa ảnh cũ thất bại
    @Test
    public void testSave_ExistingProductWithNewImagesAndDeleteFailure() throws IOException {
        logger.info("Bắt đầu TC_P_22 - testSave_ExistingProductWithNewImagesAndDeleteFailure: Kiểm tra lưu sản phẩm đã tồn tại, xóa ảnh cũ thất bại.");
        // Chuẩn bị: Sản phẩm đã tồn tại, xóa ảnh cũ thất bại
        Product product = new Product();
        product.setId(1);
        product.setAmount(5);
        Product oldProduct = new Product();
        oldProduct.setId(1);
        oldProduct.setAmount(10);
        oldProduct.setCreatedDate(new Date());
        List<Images> oldImages = new ArrayList<>();
        Images oldImage = new Images();
        oldImage.setPath("old.jpg");
        oldImages.add(oldImage);
        oldProduct.setProductImages(oldImages);
        when(productRepo.findById(1)).thenReturn(Optional.of(oldProduct));
        when(mockFile.getOriginalFilename()).thenReturn("new.jpg");
        when(mockPhysicalFile.delete()).thenReturn(false); // Xóa thất bại
        MultipartFile[] productImages = new MultipartFile[]{mockFile};
        logger.info("Dữ liệu chuẩn bị: productImages = [MultipartFile với originalFilename = \"new.jpg\"], product = {}, oldProduct = {}", product, oldProduct);

        // Thực hiện: Gọi phương thức
        productService.save(productImages, product);

        // Kiểm tra và log
        verify(productRepo, times(1)).save(product);
        verify(mockFile, times(1)).transferTo(any(File.class));
        verify(mockPhysicalFile, times(1)).delete();
        assertEquals(15, product.getAmount(), "Amount phải được cộng từ oldProduct");
        assertEquals(1, product.getProductImages().size(), "Phải có 1 ảnh mới");
        assertEquals("new.jpg", product.getProductImages().get(0).getPath(), "Đường dẫn ảnh mới phải đúng");
        assertNotNull(product.getUpdatedDate(), "UpdatedDate phải được thiết lập");
        logger.info("Kết quả TC_P_22 - testSave_ExistingProductWithNewImagesAndDeleteFailure: Kết thúc test case.");
    }

    // TC_P_23 - testSave_ExistingProductWithZeroAmount: Lưu sản phẩm đã tồn tại, amount = 0
    @Test
    public void testSave_ExistingProductWithZeroAmount() throws IOException {
        logger.info("Bắt đầu TC_P_23 - testSave_ExistingProductWithZeroAmount: Kiểm tra lưu sản phẩm đã tồn tại, amount = 0.");
        // Chuẩn bị: Sản phẩm đã tồn tại, amount = 0
        Product product = new Product();
        product.setId(1);
        product.setAmount(0);
        Product oldProduct = new Product();
        oldProduct.setId(1);
        oldProduct.setAmount(10);
        oldProduct.setCreatedDate(new Date());
        List<Images> oldImages = new ArrayList<>();
        Images oldImage = new Images();
        oldImage.setPath("old.jpg");
        oldImages.add(oldImage);
        oldProduct.setProductImages(oldImages);
        when(productRepo.findById(1)).thenReturn(Optional.of(oldProduct));
        MultipartFile[] productImages = null;
        logger.info("Dữ liệu chuẩn bị: productImages = null, product = {}, oldProduct = {}", product, oldProduct);

        // Thực hiện: Gọi phương thức
        productService.save(productImages, product);

        // Kiểm tra và log
        verify(productRepo, times(1)).save(product);
        verify(mockFile, never()).transferTo(any(File.class));
        assertEquals(10, product.getAmount(), "Amount phải được cộng từ oldProduct");
        assertEquals(oldImages, product.getProductImages(), "Ảnh cũ phải được giữ nguyên");
        assertEquals(oldProduct.getCreatedDate(), product.getCreatedDate(), "CreatedDate phải được giữ nguyên");
        assertNotNull(product.getUpdatedDate(), "UpdatedDate phải được thiết lập");
        logger.info("Kết quả TC_P_23 - testSave_ExistingProductWithZeroAmount: Kết thúc test case.");
    }
}