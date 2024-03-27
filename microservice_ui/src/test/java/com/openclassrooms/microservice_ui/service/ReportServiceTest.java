package com.openclassrooms.microservice_ui.service;

import com.openclassrooms.microservice_ui.model.Report;
import com.openclassrooms.microservice_ui.repository.ReportRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReportServiceTest {
    private Report report;
    @MockBean
    private ReportRepository reportRepository;
    @Autowired
    private ReportService reportService;
    @BeforeAll
    public void setUp() {
        report = new Report();
        report.setPid(1L);
        report.setRid("1");
        report.setContent("content");
    }
    @Test
    public void testGetByPid() {
        given(reportRepository.findByPid(1L)).willReturn(new Report[] {report});
        assertTrue(reportService.getByPid(1L).length > 0);
    }
    @Test
    public void testGet() {
        given(reportRepository.get("1")).willReturn(report);
        assertTrue(reportService.get("1") != null);
    }
    @Test
    public void testAdd() {
        assertDoesNotThrow(() -> reportService.add(report));
    }
    @Test
    public void testUpdate() {
        assertDoesNotThrow(() -> reportService.update("1", report));
    }
    @Test
    public void testDelete() {
        assertDoesNotThrow(() -> reportService.delete("1"));
    }
}
