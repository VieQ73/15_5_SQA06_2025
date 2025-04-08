package com.devpro.services;

import com.devpro.entities.Category;
import com.devpro.model.CategorySearch;
import com.devpro.repositories.CategoryRepo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lớp kiểm thử đơn vị cho phương thức searchAdmin của CategoryService.
 */
@SpringBootTest
class CategoryServiceSearchAdminTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepo categoryRepo;


    /**
     * TC_CS_05: Tìm kiếm với tất cả trường rỗng/null → trả về null.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchAdmin_AllNull() {
        CategorySearch search = new CategorySearch();
        List<Category> result = categoryService.searchAdmin(search);
        assertTrue(result.isEmpty());
    }

    /**
     * TC_CS_06: Tìm kiếm với chỉ trường name → lọc theo name chứa "Test".
     */
    @Test
    @Transactional
    @Rollback
    void testSearchAdmin_NameOnly() {
        CategorySearch search = new CategorySearch();
        search.setName("Test");
        List<Category> result = categoryService.searchAdmin(search);
        assertFalse(result.isEmpty());
        result.forEach(category -> assertTrue(category.getName().contains("Test")));
    }

    /**
     * TC_CS_07: Tìm kiếm với chỉ trường descrition → lọc theo mô tả chứa "desc".
     */
    @Test
    @Transactional
    @Rollback
    void testSearchAdmin_DescriptionOnly() {
        CategorySearch search = new CategorySearch();
        search.setDescrition("tion");
        List<Category> result = categoryService.searchAdmin(search);
        assertFalse(result.isEmpty());
        result.forEach(category -> assertTrue(category.getDescription().contains("tion")));
    }

    /**
     * TC_CS_08: Tìm kiếm với chỉ trường seo → lọc theo SEO chứa "seo".
     */
    @Test
    @Transactional
    @Rollback
    void testSearchAdmin_SeoOnly() {
        CategorySearch search = new CategorySearch();
        search.setSeo("seo");
        List<Category> result = categoryService.searchAdmin(search);
        assertFalse(result.isEmpty());
        result.forEach(category -> assertTrue(result.get(0).getSeo().contains("seo")));

    }

    /**
     * TC_CS_09: Tìm kiếm với cả name và descrition → lọc theo cả 2 điều kiện.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchAdmin_NameAndDescription() {
        CategorySearch search = new CategorySearch();
        search.setName("Test");
        search.setDescrition("desc");
        List<Category> result = categoryService.searchAdmin(search);
        assertFalse(result.isEmpty());
        result.forEach(category -> {
            assertTrue(category.getName().contains("Test"));
            assertTrue(category.getDescription().contains("desc"));
        });
    }

    /**
     * TC_CS_10: Tìm kiếm với cả name và seo → lọc theo cả 2 điều kiện.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchAdmin_NameAndSeo() {
        CategorySearch search = new CategorySearch();
        search.setName("Test");
        search.setSeo("seo");
        List<Category> result = categoryService.searchAdmin(search);
        assertFalse(result.isEmpty());
        result.forEach(category -> {
            assertTrue(category.getName().contains("Test"));
            assertTrue(category.getSeo().contains("seo"));
        });
    }

    /**
     * TC_CS_11: Tìm kiếm với cả descrition và seo → lọc theo cả 2 điều kiện.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchAdmin_DescriptionAndSeo() {
        CategorySearch search = new CategorySearch();
        search.setDescrition("desc");
        search.setSeo("seo");
        List<Category> result = categoryService.searchAdmin(search);
        assertFalse(result.isEmpty());
        result.forEach(category -> {
            assertTrue(category.getDescription().contains("desc"));
            assertTrue(category.getSeo().contains("seo"));
        });
    }

    /**
     * TC_CS_12: Tìm kiếm với đầy đủ name, descrition và seo → lọc chính xác cả 3 điều kiện.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchAdmin_AllFieldsFilled() {
        CategorySearch search = new CategorySearch();
        search.setName("Test");
        search.setDescrition("desc");
        search.setSeo("seo");
        List<Category> result = categoryService.searchAdmin(search);
        assertFalse(result.isEmpty());
        result.forEach(category -> {
            assertTrue(category.getName().contains("Test"));
            assertTrue(category.getDescription().contains("desc"));
            assertTrue(category.getSeo().contains("seo"));
        });
    }


    /**
     * TC_CS_13: Tìm kiếm với name chứa ký tự SQL đặc biệt → hệ thống vẫn xử lý an toàn.
     * Dữ liệu chỉ được lưu tạm và sẽ rollback sau test.
     */
    @Test
    @Transactional
    @Rollback
    void testSearchAdmin_NameWithSQLSpecialCharacters() {
        // Lưu category có tên chứa ký tự SQL đặc biệt
        Category sqlInjectionCategory = new Category();
        sqlInjectionCategory.setName(" and 1=1;DROP TABLE tbl_category; --");
        sqlInjectionCategory.setDescription("desc");
        sqlInjectionCategory.setSeo("seo");
        categoryRepo.save(sqlInjectionCategory);

        // Tìm kiếm với một phần chuỗi đặc biệt trong name
        CategorySearch search = new CategorySearch();
        search.setName("DROP TABLE");

        List<Category> result = categoryService.searchAdmin(search);

        // Kiểm tra kết quả tìm kiếm
        assertFalse(result.isEmpty());  // Phải có kết quả trả về
        assertTrue(result.get(0).getName().contains("DROP TABLE"));
    }
}
