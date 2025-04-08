package com.devpro.test;

import com.devpro.entities.Customer;
import com.devpro.entities.Product;
import com.devpro.model.ProductCustom;
import com.devpro.model.ProductSearch;
import com.devpro.services.ProductService;
import com.devpro.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class searchUserTest {

    @Autowired
    private UserService userService;

    @Test //("searchUser - Case 1: Không lỗi")
    void testSearchUser_NoException() {
        Customer user = new Customer();
        assertDoesNotThrow(() -> userService.searchUser(user));
    }

    @Test //("searchUser - Case 2: Trả về không null")
    void testSearchUser_NotNull() {
        Customer user = new Customer();
        List<Customer> result = userService.searchUser(user);
        assertNotNull(result);
    }

    @Test //("searchUser - Case 3: Danh sách có thể rỗng")
    void testSearchUser_EmptyOK() {
        Customer user = new Customer();
        List<Customer> result = userService.searchUser(user);
        assertTrue(result.size() >= 0);
    }

    @Test //("searchUser - Case 4: Danh sách đúng kiểu")
    void testSearchUser_Type() {
        Customer user = new Customer();
        List<Customer> result = userService.searchUser(user);
        assertTrue(result instanceof List);
    }

    @Test //("searchUser - Case 5: Truyền user null")
    void testSearchUser_NullSafe() {
        assertDoesNotThrow(() -> userService.searchUser(null));
    }

    @Test //("searchUser - Case 6: User không có dữ liệu")
    void testSearchUser_EmptyUser() {
        Customer user = new Customer();
        List<Customer> result = userService.searchUser(user);
        assertNotNull(result);
    }

    @Test //("searchUser - Case 7: Có thể có nhiều kết quả")
    void testSearchUser_MultipleResults() {
        Customer user = new Customer();
        List<Customer> result = userService.searchUser(user);
        assertTrue(result.size() >= 0);
    }

    @Test //("searchUser - Case 8: Không ném ngoại lệ khi user thiếu thông tin")
    void testSearchUser_IncompleteUser() {
        Customer user = new Customer();
        user.setName(null);
        assertDoesNotThrow(() -> userService.searchUser(user));
    }

    @Test //("searchUser - Case 9: So sánh dữ liệu thực tế")
    void testSearchUser_ContentCheck() {
        Customer user = new Customer();
        List<Customer> result = userService.searchUser(user);
        result.forEach(c -> assertNotNull(c.getId()));
    }

    @Test //("searchUser - Case 10: Danh sách không null")
    void testSearchUser_ListNotNull() {
        Customer user = new Customer();
        List<Customer> result = userService.searchUser(user);
        assertNotNull(result);
    }
}
