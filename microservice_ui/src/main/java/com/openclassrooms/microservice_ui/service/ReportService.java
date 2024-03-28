package com.openclassrooms.microservice_ui.service;

import com.openclassrooms.microservice_ui.model.Report;
import com.openclassrooms.microservice_ui.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report[] getByPid(Long pid) {
        try {
            return reportRepository.findByPid(pid);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Report get(String rid) {
        try {
            return reportRepository.get(rid);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void add(Report report) {
        try {
            reportRepository.add(report);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void update(String rid, Report report) {
        try {
            reportRepository.update(rid, report);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void delete(String rid) {
        try {
            reportRepository.delete(rid);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
