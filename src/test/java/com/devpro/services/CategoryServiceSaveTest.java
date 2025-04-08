package com.devpro.services;

import com.devpro.entities.Category;
import com.devpro.model.CategorySearch;
import com.devpro.repositories.CategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class CategoryServiceSaveTest {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceSaveTest.class);
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepo categoryRepo;

    @PersistenceContext
    private EntityManager entityManager;

    private Category category;

    @BeforeEach
    void setUp() {
        // Tạo đối tượng Category mẫu và lưu vào cơ sở dữ liệu trước mỗi test
        category = new Category();
        category.setName("Test Category");
        category.setDescription("Test description");
        category.setSeo("test-seo");
        categoryRepo.save(category);  // Insert vào DB trước mỗi lần kiểm thử
    }

    /**
     * Trường hợp: Lưu Category mới (id = null) → lưu vào DB
     */
    @Test
    void testSaveCategory() {
        Category newCategory = new Category();
        newCategory.setName("New Category");
        newCategory.setDescription("New Description");
        newCategory.setSeo("new-seo");
        newCategory.setCreatedDate(new Date());

        categoryService.save(newCategory);
        log.info("Saved Category: {}", newCategory.getId());
        // Kiểm tra xem Category mới đã được lưu vào cơ sở dữ liệu hay chưa
        assertNotNull(newCategory.getId());

        Category savedCategory = categoryRepo.findById(newCategory.getId()).orElse(null);
        assertNotNull(savedCategory);  // Kiểm tra xem Category có tồn tại trong DB không
        assertEquals("New Category", savedCategory.getName());  // Kiểm tra tên của Category đã lưu
    }

    /**
     * Trường hợp: Lưu Category đã có ID (id không null tồn tại trong csdl), không thay đổi dữ liệu
     */
    @Test
    void testSaveCategoryWithExistingId() {
        category.setId(45);
        category.setName("Updated Category");
        categoryService.save(category);  // Cập nhật Category đã tồn tại

        Category updatedCategory = categoryRepo.findById(category.getId()).orElse(null);
        assertNotNull(updatedCategory);  // Kiểm tra Category vẫn tồn tại trong DB
        assertEquals("Test Category", updatedCategory.getName());  // Kiểm tra tên không thay đổi
    }

    /**
     * Trường hợp: Lưu Category đã có ID (id không null) id chưa tồn tại trong csdl, thay đổi dữ liệu
     */
    @Test
    void testSaveCategoryWithExistingIdIs() {
        category.setId(55);
        category.setName("Updating Category");
        long count1 = categoryRepo.count();
        categoryService.save(category);
        long count2 = categoryRepo.count();
        Category updatedCategory = categoryRepo.findById(category.getId()).orElse(null);
        assertNotNull(updatedCategory);  // Kiểm tra Category vẫn tồn tại trong DB
        assertEquals(count1 + 1 , count2);  // Kiểm tra số lượng bảng ghi
    }

    /**
     * Trường hợp: Lưu Category với thông tin thiếu (ví dụ: thiếu tên)
     */
    @Test
    void testSaveCategoryWithMissingName() {
        Category newCategory = new Category();
        newCategory.setDescription("Description without name");
        newCategory.setSeo("missing-name-seo");

        // Gọi save, kiểm tra xem sẽ không lưu được vào DB
        categoryService.save(newCategory);

        assertNull(newCategory.getId());  // ID vẫn null vì không thể lưu nếu thiếu thông tin quan trọng như tên
    }
}
