package com.devpro.services;

import com.devpro.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class UserServiceSearCustomerByPhoneTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceSearchUserTest.class);

    @InjectMocks
    private UserService userService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @BeforeEach
    public void setUp() {
        when(entityManager.createNativeQuery(anyString(), eq(Customer.class))).thenReturn(query);
    }

    // TC_US_11 - testSearCustomerByPhone_EmptyResult: No customer with the given phone
    @Test
    public void testSearCustomerByPhone_EmptyResult() {
        logger.info("Bắt đầu TC_US_11 - testSearCustomerByPhone_EmptyResult: Kiểm tra khi không có khách hàng với số điện thoại đã cho.");
        String phone = "1234567890";
        List<Customer> customers = new ArrayList<>();
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: phone = 1234567890, query trả về danh sách rỗng");

        List<Customer> result = userService.searCustomerByPhone(phone);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách khách hàng phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_11 - testSearCustomerByPhone_EmptyResult: Kết thúc test case.");
    }

    // TC_US_12 - testSearCustomerByPhone_SingleMatch: One customer with the given phone
    @Test
    public void testSearCustomerByPhone_SingleMatch() {
        logger.info("Bắt đầu TC_US_12 - testSearCustomerByPhone_SingleMatch: Kiểm tra với 1 khách hàng khớp số điện thoại.");
        String phone = "1234567890";
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);
        customer.setPhone("1234567890");
        customers.add(customer);
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: phone = 1234567890, query trả về 1 khách hàng");

        List<Customer> result = userService.searCustomerByPhone(phone);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 khách hàng");
        assertEquals("1234567890", result.get(0).getPhone(), "Số điện thoại phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_12 - testSearCustomerByPhone_SingleMatch: Kết thúc test case.");
    }

    // TC_US_13 - testSearCustomerByPhone_NullPhone: Phone parameter is null
    @Test
    public void testSearCustomerByPhone_NullPhone() {
        logger.info("Bắt đầu TC_US_13 - testSearCustomerByPhone_NullPhone: Kiểm tra khi phone là null.");
        String phone = null;
        List<Customer> customers = new ArrayList<>();
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: phone = null, query trả về danh sách rỗng");

        List<Customer> result = userService.searCustomerByPhone(phone);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách khách hàng phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_13 - testSearCustomerByPhone_NullPhone: Kết thúc test case.");
    }

    // TC_US_14 - testSearCustomerByPhone_EmptyPhone: Phone parameter is empty
    @Test
    public void testSearCustomerByPhone_EmptyPhone() {
        logger.info("Bắt đầu TC_US_14 - testSearCustomerByPhone_EmptyPhone: Kiểm tra khi phone rỗng.");
        String phone = "";
        List<Customer> customers = new ArrayList<>();
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: phone = '', query trả về danh sách rỗng");

        List<Customer> result = userService.searCustomerByPhone(phone);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách khách hàng phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_14 - testSearCustomerByPhone_EmptyPhone: Kết thúc test case.");
    }

    // TC_US_15 - testSearCustomerByPhone_QueryThrowsException: Query throws an exception
    @Test
    public void testSearCustomerByPhone_QueryThrowsException() {
        logger.info("Bắt đầu TC_US_15 - testSearCustomerByPhone_QueryThrowsException: Kiểm tra khi query ném ngoại lệ.");
        String phone = "1234567890";
        when(query.getResultList()).thenThrow(new RuntimeException("Database error"));
        logger.info("Dữ liệu chuẩn bị: phone = 1234567890, query ném ngoại lệ");

        assertThrows(RuntimeException.class, () -> userService.searCustomerByPhone(phone),
                "Phải ném RuntimeException khi query thất bại");

        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_15 - testSearCustomerByPhone_QueryThrowsException: Kết thúc test case.");
    }

    // TC_US_16 - testSearCustomerByPhone_SpecialCharactersInPhone: Phone with special characters
    @Test
    public void testSearCustomerByPhone_SpecialCharactersInPhone() {
        logger.info("Bắt đầu TC_US_16 - testSearCustomerByPhone_SpecialCharactersInPhone: Kiểm tra với số điện thoại chứa ký tự đặc biệt.");
        String phone = "123-456-7890";
        List<Customer> customers = new ArrayList<>();
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: phone = 123-456-7890, query trả về danh sách rỗng");

        List<Customer> result = userService.searCustomerByPhone(phone);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách khách hàng phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_16 - testSearCustomerByPhone_SpecialCharactersInPhone: Kết thúc test case.");
    }

    // TC_US_17 - testSearCustomerByPhone_LongPhoneNumber: Very long phone number
    @Test
    public void testSearCustomerByPhone_LongPhoneNumber() {
        logger.info("Bắt đầu TC_US_17 - testSearCustomerByPhone_LongPhoneNumber: Kiểm tra với số điện thoại rất dài.");
        String phone = "12345678901234567890";
        List<Customer> customers = new ArrayList<>();
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: phone = 12345678901234567890, query trả về danh sách rỗng");

        List<Customer> result = userService.searCustomerByPhone(phone);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách khách hàng phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_17 - testSearCustomerByPhone_LongPhoneNumber: Kết thúc test case.");
    }

    // TC_US_18 - testSearCustomerByPhone_NullPhoneInDB: Customer in DB with null phone
    @Test
    public void testSearCustomerByPhone_NullPhoneInDB() {
        logger.info("Bắt đầu TC_US_18 - testSearCustomerByPhone_NullPhoneInDB: Kiểm tra với khách hàng trong DB có phone là null.");
        String phone = "1234567890";
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);
        customer.setPhone(null);
        customers.add(customer);
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: phone = 1234567890, query trả về 1 khách hàng (phone=null)");

        List<Customer> result = userService.searCustomerByPhone(phone);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách khách hàng phải rỗng vì phone không khớp");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_18 - testSearCustomerByPhone_NullPhoneInDB: Kết thúc test case.");
    }

    // TC_US_19 - testSearCustomerByPhone_SQLInjectionAttempt: Phone parameter with SQL injection attempt
    @Test
    public void testSearCustomerByPhone_SQLInjectionAttempt() {
        logger.info("Bắt đầu TC_US_19 - testSearCustomerByPhone_SQLInjectionAttempt: Kiểm tra với số điện thoại chứa mã SQL injection.");
        String phone = "1234567890' OR '1'='1";
        List<Customer> customers = new ArrayList<>();
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: phone = 1234567890' OR '1'='1, query trả về danh sách rỗng");

        List<Customer> result = userService.searCustomerByPhone(phone);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách khách hàng phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_19 - testSearCustomerByPhone_SQLInjectionAttempt: Kết thúc test case.");
    }

    // TC_US_20 - testSearCustomerByPhone_MultipleMatches
    @Test
    public void testSearCustomerByPhone_MultipleMatches() {
        logger.info("Bắt đầu TC_US_20 - testSearCustomerByPhone_MultipleMatches: Kiểm tra với số điện thoại khớp nhiều khách hàng.");
        String phone = "1234567890";
        List<Customer> customers = new ArrayList<>();
        Customer c1 = new Customer();
        c1.setId(1);
        c1.setPhone("1234567890");
        Customer c2 = new Customer();
        c2.setId(2);
        c2.setPhone("1234567890"); // Same phone number as c1
        customers.add(c1); // Only the first customer is returned due to "limit 1"
        // Note: In a real DB query, c2 would also match, but "limit 1" ensures only c1 is returned
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: phone = 1234567890, query trả về 1 khách hàng (mặc dù có 2 khách hàng khớp)");

        List<Customer> result = userService.searCustomerByPhone(phone);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 khách hàng (do limit 1)");
        assertEquals(1, result.get(0).getId(), "Phải trả về khách hàng đầu tiên (id=1)");
        assertEquals("1234567890", result.get(0).getPhone(), "Số điện thoại phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_20 - testSearCustomerByPhone_MultipleMatches: Kết thúc test case.");
    }
}