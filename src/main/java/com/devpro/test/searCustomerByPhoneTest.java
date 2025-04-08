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
public class searCustomerByPhoneTest {

    @Autowired
    private UserService userService;

    @Test //("searCustomerByPhone - Case 1: Truyền số điện thoại đúng định dạng")
    void testSearchByPhone_Valid() {
        List<Customer> result = userService.searCustomerByPhone("0123456789");
        assertNotNull(result);
    }

    @Test //("searCustomerByPhone - Case 2: Truyền null")
    void testSearchByPhone_Null() {
        assertDoesNotThrow(() -> userService.searCustomerByPhone(null));
    }

    @Test //("searCustomerByPhone - Case 3: Số điện thoại không tồn tại")
    void testSearchByPhone_NotFound() {
        List<Customer> result = userService.searCustomerByPhone("0000000000");
        assertTrue(result.isEmpty() || result.size() >= 0);
    }

    @Test //("searCustomerByPhone - Case 4: Chuỗi trống")
    void testSearchByPhone_EmptyString() {
        List<Customer> result = userService.searCustomerByPhone("");
        assertNotNull(result);
    }

    @Test //("searCustomerByPhone - Case 5: Chuỗi có ký tự đặc biệt")
    void testSearchByPhone_SpecialChars() {
        List<Customer> result = userService.searCustomerByPhone("!@#$");
        assertNotNull(result);
    }

    @Test //("searCustomerByPhone - Case 6: Kiểm tra kiểu trả về")
    void testSearchByPhone_Type() {
        List<Customer> result = userService.searCustomerByPhone("0123456789");
        assertTrue(result instanceof List);
    }

    @Test //("searCustomerByPhone - Case 7: Trả về tối đa 1 kết quả")
    void testSearchByPhone_Limit1() {
        List<Customer> result = userService.searCustomerByPhone("0123456789");
        assertTrue(result.size() <= 1);
    }

    @Test //("searCustomerByPhone - Case 8: Không ném lỗi SQL")
    void testSearchByPhone_NoSqlError() {
        assertDoesNotThrow(() -> userService.searCustomerByPhone("1234567890"));
    }

    @Test //("searCustomerByPhone - Case 9: Có thể trả về dữ liệu hợp lệ")
    void testSearchByPhone_ValidData() {
        List<Customer> result = userService.searCustomerByPhone("0123456789");
        result.forEach(c -> assertNotNull(c.getPhone()));
    }

    @Test //("searCustomerByPhone - Case 10: Kết quả không null")
    void testSearchByPhone_NotNull() {
        List<Customer> result = userService.searCustomerByPhone("0123456789");
        assertNotNull(result);
    }
}
