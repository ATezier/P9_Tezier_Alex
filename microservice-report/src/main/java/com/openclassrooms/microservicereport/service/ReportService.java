package com.openclassrooms.microservicereport.service;

import com.openclassrooms.microservicereport.model.Report;
import com.openclassrooms.microservicereport.repository.ReportRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public boolean isValid(Report report) throws Exception {
        String errorMessage = "";
        if(report.getContent() == null || report.getContent().isEmpty()) {
            errorMessage += "The content is required.\n";
        }
        if(report.getPid() == null) {
            errorMessage += "The pid is required.\n";
        }
        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage);
        }
        return true;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getReportById(String id) {
        return reportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Report not found"));
    }

    public List<Report> getReportByPid(Long pid) {
        return reportRepository.findByPid(pid);
    }

    public Report create(Report report) {
        try {
            isValid(report);
            getReportById(report.getRid());
            throw new IllegalArgumentException("Report already exists");
        } catch (IllegalArgumentException e) {
            return reportRepository.save(report);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Report update(String id, Report report) {
        try {
            isValid(report);
            Report old = getReportById(id);
            if (old.getPid() != report.getPid()) {
                throw new Exception("The pid cannot be changed");
            }
            if(old.getContent() == report.getContent()) {
                throw new Exception("Nothing to update");
            }
            report.setRid(old.getRid());
            return reportRepository.save(report);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + ", fail to update");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void delete(String id) {
        try {
            Report report = getReportById(id);
            reportRepository.delete(report);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + ", fail to delete");
        }
    }
}
