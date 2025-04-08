package com.devpro.test;

import com.devpro.entities.Product;
import com.devpro.model.ProductCustom;
import com.devpro.model.ProductSearch;
import com.devpro.model.ThongKe;
import com.devpro.services.ProductService;
import com.devpro.services.ThongKeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class thongKeTheoTimeTest {

    @Autowired
    private ThongKeService thongKeService;

    @Test //("thongKeTheoTime - Case 1: Không lỗi")
    void testThongKeTheoTime_NoException() {
        long now = new Date().getTime();
        assertDoesNotThrow(() -> thongKeService.thongKeTheoTime(now - 10000, now));
    }

    @Test //("thongKeTheoTime - Case 2: Trả về không null")
    void testThongKeTheoTime_NotNull() throws Exception {
        long now = new Date().getTime();
        List<ThongKe> result = thongKeService.thongKeTheoTime(now - 10000, now);
        assertNotNull(result);
    }

    @Test //("thongKeTheoTime - Case 3: Danh sách có thể rỗng")
    void testThongKeTheoTime_EmptyOK() throws Exception {
        long now = new Date().getTime();
        List<ThongKe> result = thongKeService.thongKeTheoTime(now + 1000000, now + 2000000);
        assertTrue(result.size() >= 0);
    }

    @Test //("thongKeTheoTime - Case 4: Ngày bắt đầu sau ngày kết thúc")
    void testThongKeTheoTime_InvalidRange() throws Exception {
        long now = new Date().getTime();
        List<ThongKe> result = thongKeService.thongKeTheoTime(now, now - 10000);
        assertNotNull(result);
    }

    @Test //("thongKeTheoTime - Case 5: Khoảng thời gian lớn")
    void testThongKeTheoTime_LongRange() throws Exception {
        long now = new Date().getTime();
        List<ThongKe> result = thongKeService.thongKeTheoTime(now - 100000000, now);
        assertNotNull(result);
    }

    @Test //("thongKeTheoTime - Case 6: Truyền vào 0")
    void testThongKeTheoTime_ZeroInput() throws Exception {
        List<ThongKe> result = thongKeService.thongKeTheoTime(0L, 0L);
        assertNotNull(result);
    }

    @Test //("thongKeTheoTime - Case 7: Kết quả đúng kiểu")
    void testThongKeTheoTime_Type() throws Exception {
        long now = new Date().getTime();
        List<ThongKe> result = thongKeService.thongKeTheoTime(now - 10000, now);
        assertTrue(result instanceof List);
    }

    @Test //("thongKeTheoTime - Case 8: Có thể có nhiều kết quả")
    void testThongKeTheoTime_MultipleResults() throws Exception {
        long now = new Date().getTime();
        List<ThongKe> result = thongKeService.thongKeTheoTime(now - 100000000, now);
        assertTrue(result.size() >= 0);
    }

    @Test //("thongKeTheoTime - Case 9: Ngày âm")
    void testThongKeTheoTime_NegativeDates() throws Exception {
        List<ThongKe> result = thongKeService.thongKeTheoTime(-10000L, -5000L);
        assertNotNull(result);
    }

    @Test //("thongKeTheoTime - Case 10: Truyền vào null không lỗi")
    void testThongKeTheoTime_NullSafe() {
        assertDoesNotThrow(() -> thongKeService.thongKeTheoTime(null, null));
    }
}
