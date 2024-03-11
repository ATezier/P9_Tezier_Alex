package com.openclassrooms.doctorui.controller;

import com.openclassrooms.microservicereport.model.Report;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReportController {
    private final String reportUrl = "http://localhost:8081/report";

    @GetMapping("/report/add/{pid}")
    public String createReport(@PathVariable Long pid, Model model) {
        Report report = new Report();
        report.setPid(pid);
        model.addAttribute("report", report);
        return "report/add";
    }

    @PostMapping("/report/add/")
    public String createReport(Report report, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String createReportUrl = reportUrl + "/create";
        HttpEntity<Report> request = new HttpEntity<>(report, headers);
        ResponseEntity<Report> response = restTemplate.exchange(
            createReportUrl,
            HttpMethod.POST,
            request,
            Report.class);
        if(response.getStatusCode() == HttpStatus.CREATED) {
            return "redirect:/patient/get/" + report.getPid();
        } else {
            model.addAttribute("error", response.getBody());
            return "report/add";
        }
    }

    @GetMapping("/report/update/{rid}")
    public String updateReport(@PathVariable String rid, RedirectAttributes redirectAttributes, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String getReportUrl = reportUrl + "/get/" + rid;
        ResponseEntity<Report> response = restTemplate.getForEntity(getReportUrl, Report.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("report", response.getBody());
            return "report/update";
        } else {
            redirectAttributes.addFlashAttribute("error", response.getBody());
            return "redirect:/patient/list";
        }
    }

    @PostMapping("/report/update/{rid}")
    public String updateReport(@PathVariable String rid, Report report, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String updateReportUrl = reportUrl + "/update/" + rid;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Report> request = new HttpEntity<>(report, headers);
        ResponseEntity<Report> response = restTemplate.exchange(
            updateReportUrl,
            HttpMethod.PUT,
            request,
            Report.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/patient/get/" + report.getPid();
        } else {
            model.addAttribute("error", response.getBody());
            return "report/update";
        }
    }

    @GetMapping("/report/delete/{pid}/{rid}")
    public String deleteReport( @PathVariable String pid ,@PathVariable String rid, RedirectAttributes redirectAttributes) {
        RestTemplate restTemplate = new RestTemplate();
        String deleteReportUrl = reportUrl + "/delete/" + rid;
        ResponseEntity<Report> response = restTemplate.exchange(
            deleteReportUrl,
            HttpMethod.DELETE,
            null,
            Report.class);
        if(response.getStatusCode() != HttpStatus.OK) {
            redirectAttributes.addFlashAttribute("error", response.getBody());
        }
        return "redirect:/patient/get/" + pid;
    }
}
