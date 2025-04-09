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
public class UserServiceSearchUserTest {

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

    // TC_US_01 - testSearchUser_EmptyList: No customers in the database
    @Test
    public void testSearchUser_EmptyList() {
        logger.info("Bắt đầu TC_US_01 - testSearchUser_EmptyList: Kiểm tra khi không có khách hàng nào trong DB.");
        Customer user = new Customer();
        List<Customer> customers = new ArrayList<>();
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: user = {}, query trả về danh sách rỗng");

        List<Customer> result = userService.searchUser(user);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách khách hàng phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_01 - testSearchUser_EmptyList: Kết thúc test case.");
    }

    // TC_US_02 - testSearchUser_SingleCustomer: One customer in the database
    @Test
    public void testSearchUser_SingleCustomer() {
        logger.info("Bắt đầu TC_US_02 - testSearchUser_SingleCustomer: Kiểm tra với 1 khách hàng trong DB.");
        Customer user = new Customer();
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);
        customer.setPhone("1234567890");
        customers.add(customer);
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: user = {}, query trả về 1 khách hàng");

        List<Customer> result = userService.searchUser(user);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 khách hàng");
        assertEquals("1234567890", result.get(0).getPhone(), "Số điện thoại phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_02 - testSearchUser_SingleCustomer: Kết thúc test case.");
    }

    // TC_US_03 - testSearchUser_MultipleCustomers: Multiple customers in the database
    @Test
    public void testSearchUser_MultipleCustomers() {
        logger.info("Bắt đầu TC_US_03 - testSearchUser_MultipleCustomers: Kiểm tra với nhiều khách hàng trong DB.");
        Customer user = new Customer();
        List<Customer> customers = new ArrayList<>();
        Customer c1 = new Customer();
        c1.setId(1);
        c1.setPhone("1234567890");
        Customer c2 = new Customer();
        c2.setId(2);
        c2.setPhone("0987654321");
        customers.add(c1);
        customers.add(c2);
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: user = {}, query trả về 2 khách hàng");

        List<Customer> result = userService.searchUser(user);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 khách hàng");
        assertEquals("1234567890", result.get(0).getPhone(), "Số điện thoại khách hàng 1 phải đúng");
        assertEquals("0987654321", result.get(1).getPhone(), "Số điện thoại khách hàng 2 phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_03 - testSearchUser_MultipleCustomers: Kết thúc test case.");
    }

    // TC_US_04 - testSearchUser_NullUser: User parameter is null
    @Test
    public void testSearchUser_NullUser() {
        logger.info("Bắt đầu TC_US_04 - testSearchUser_NullUser: Kiểm tra khi user là null.");
        Customer user = null;
        List<Customer> customers = new ArrayList<>();
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: user = null, query trả về danh sách rỗng");

        List<Customer> result = userService.searchUser(user);

        assertNotNull(result, "Kết quả không được null");
        assertTrue(result.isEmpty(), "Danh sách khách hàng phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_04 - testSearchUser_NullUser: Kết thúc test case.");
    }

    // TC_US_05 - testSearchUser_QueryThrowsException: Query throws an exception
    @Test
    public void testSearchUser_QueryThrowsException() {
        logger.info("Bắt đầu TC_US_05 - testSearchUser_QueryThrowsException: Kiểm tra khi query ném ngoại lệ.");
        Customer user = new Customer();
        when(query.getResultList()).thenThrow(new RuntimeException("Database error"));
        logger.info("Dữ liệu chuẩn bị: user = {}, query ném ngoại lệ");

        assertThrows(RuntimeException.class, () -> userService.searchUser(user),
                "Phải ném RuntimeException khi query thất bại");

        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_05 - testSearchUser_QueryThrowsException: Kết thúc test case.");
    }

    // TC_US_06 - testSearchUser_LargeNumberOfCustomers: Large number of customers
    @Test
    public void testSearchUser_LargeNumberOfCustomers() {
        logger.info("Bắt đầu TC_US_06 - testSearchUser_LargeNumberOfCustomers: Kiểm tra với số lượng lớn khách hàng.");
        Customer user = new Customer();
        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            Customer c = new Customer();
            c.setId(i);
            c.setPhone("123456789" + i);
            customers.add(c);
        }
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: user = {}, query trả về 1000 khách hàng");

        List<Customer> result = userService.searchUser(user);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1000, result.size(), "Phải trả về 1000 khách hàng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_06 - testSearchUser_LargeNumberOfCustomers: Kết thúc test case.");
    }

    // TC_US_07 - testSearchUser_NullPhone: Customer with null phone
    @Test
    public void testSearchUser_NullPhone() {
        logger.info("Bắt đầu TC_US_07 - testSearchUser_NullPhone: Kiểm tra với khách hàng có phone là null.");
        Customer user = new Customer();
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);
        customer.setPhone(null);
        customers.add(customer);
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: user = {}, query trả về 1 khách hàng (phone=null)");

        List<Customer> result = userService.searchUser(user);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 khách hàng");
        assertNull(result.get(0).getPhone(), "Số điện thoại phải là null");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_07 - testSearchUser_NullPhone: Kết thúc test case.");
    }

    // TC_US_08 - testSearchUser_EmptyPhone: Customer with empty phone
    @Test
    public void testSearchUser_EmptyPhone() {
        logger.info("Bắt đầu TC_US_08 - testSearchUser_EmptyPhone: Kiểm tra với khách hàng có phone rỗng.");
        Customer user = new Customer();
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);
        customer.setPhone("");
        customers.add(customer);
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: user = {}, query trả về 1 khách hàng (phone='')");

        List<Customer> result = userService.searchUser(user);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(1, result.size(), "Phải trả về 1 khách hàng");
        assertEquals("", result.get(0).getPhone(), "Số điện thoại phải rỗng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_08 - testSearchUser_EmptyPhone: Kết thúc test case.");
    }

    // TC_US_09 - testSearchUser_DuplicatePhones: Customers with duplicate phone numbers
    @Test
    public void testSearchUser_DuplicatePhones() {
        logger.info("Bắt đầu TC_US_09 - testSearchUser_DuplicatePhones: Kiểm tra với khách hàng có số điện thoại trùng lặp.");
        Customer user = new Customer();
        List<Customer> customers = new ArrayList<>();
        Customer c1 = new Customer();
        c1.setId(1);
        c1.setPhone("1234567890");
        Customer c2 = new Customer();
        c2.setId(2);
        c2.setPhone("1234567890");
        customers.add(c1);
        customers.add(c2);
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: user = {}, query trả về 2 khách hàng (phone trùng lặp)");

        List<Customer> result = userService.searchUser(user);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 khách hàng");
        assertEquals("1234567890", result.get(0).getPhone(), "Số điện thoại khách hàng 1 phải đúng");
        assertEquals("1234567890", result.get(1).getPhone(), "Số điện thoại khách hàng 2 phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_09 - testSearchUser_DuplicatePhones: Kết thúc test case.");
    }

    // TC_US_10 - testSearchUser_SpecificPhoneInUser
    @Test
    public void testSearchUser_SpecificPhoneInUser() {
        logger.info("Bắt đầu TC_US_10 - testSearchUser_SpecificPhoneInUser: Kiểm tra với user có số điện thoại cụ thể.");
        Customer user = new Customer();
        user.setPhone("5555555555"); // Set a specific phone number
        List<Customer> customers = new ArrayList<>();
        Customer c1 = new Customer();
        c1.setId(1);
        c1.setPhone("1234567890");
        Customer c2 = new Customer();
        c2.setId(2);
        c2.setPhone("0987654321");
        customers.add(c1);
        customers.add(c2);
        when(query.getResultList()).thenReturn(customers);
        logger.info("Dữ liệu chuẩn bị: user = {phone=5555555555}, query trả về 2 khách hàng");

        List<Customer> result = userService.searchUser(user);

        assertNotNull(result, "Kết quả không được null");
        assertEquals(2, result.size(), "Phải trả về 2 khách hàng");
        assertEquals("1234567890", result.get(0).getPhone(), "Số điện thoại khách hàng 1 phải đúng");
        assertEquals("0987654321", result.get(1).getPhone(), "Số điện thoại khách hàng 2 phải đúng");
        verify(entityManager, times(1)).createNativeQuery(anyString(), eq(Customer.class));
        verify(query, times(1)).getResultList();
        logger.info("Kết quả TC_US_10 - testSearchUser_SpecificPhoneInUser: Kết thúc test case.");
    }
}
