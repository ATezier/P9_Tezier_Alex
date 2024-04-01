package com.openclassrooms.microservicereport.controller;

import com.openclassrooms.microservicereport.model.Report;
import com.openclassrooms.microservicereport.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("report/list")
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("report/get/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable String id) {
        ResponseEntity<Report> res;
        try {
            res = ResponseEntity.ok(reportService.getReportById(id));
        } catch (IllegalArgumentException e) {
            res = ResponseEntity.notFound().build();
        }
        return res;
    }

    @GetMapping("report/getByPid/{pid}")
    public ResponseEntity<List<Report>> getReportByPid(@PathVariable Long pid) {
        ResponseEntity<List<Report>> res;
        try {
            res = ResponseEntity.ok(reportService.getReportByPid(pid));
        } catch (IllegalArgumentException e) {
            res = ResponseEntity.notFound().build();
        }
        return res;
    }

    @PostMapping("report/create")
    public ResponseEntity<?> createReport(@RequestBody Report report) {
        ResponseEntity<?> res;
        try {
            reportService.create(report);
            res = ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            res = ResponseEntity.badRequest().body(e.getMessage());
        }
        return res;
    }

    @PutMapping("report/update/{id}")
    public ResponseEntity<?> updateReport(@PathVariable String id, @RequestBody Report report) {
        ResponseEntity<?> res;
        try {
            reportService.update(id, report);
            res = ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            res = ResponseEntity.badRequest().body(e.getMessage());
        }
        return res;
    }

    @DeleteMapping("report/delete/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable String id) {
        ResponseEntity<?> res;
        try {
            reportService.delete(id);
            res = ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            res = ResponseEntity.notFound().build();
        }
        return res;
    }

}
