package com.openclassrooms.microservicereport.service;

import com.openclassrooms.microservicereport.model.Report;
import com.openclassrooms.microservicereport.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class ReportServiceTest {
    @MockBean
    ReportRepository reportRepository;
    @Autowired
    ReportService reportService;
    @Test
    public void crudTest() {
        Report report = new Report();
        report.setRid("reportID");
        report.setPid(1L);
        report.setContent("Test");
        Report updatedReport = new Report();
        updatedReport.setRid(report.getRid());
        updatedReport.setPid(report.getPid());
        updatedReport.setContent("Updated Test");
        //Valid
        try {
            assertTrue(reportService.isValid(report));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Create
        given(reportRepository.save(report)).willReturn(report);
        assertNotNull(reportService.create(report));
        //Read
        given(reportRepository.findByPid(report.getPid())).willReturn(List.of(report));
        assertFalse(reportService.getReportByPid(report.getPid()).isEmpty());
        given(reportRepository.findById(report.getRid())).willReturn(Optional.of(report));
        assertNotNull(reportService.getReportById(report.getRid()));
        //Update
        given(reportRepository.save(updatedReport)).willReturn(updatedReport);
        assertNotNull(reportService.update(report.getRid(), updatedReport));
        //Delete
        assertDoesNotThrow( () -> reportService.delete(report.getRid()));
    }
}
