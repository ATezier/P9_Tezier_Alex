package com.openclassrooms.microservice_ui.controller;

import com.openclassrooms.microservice_ui.model.Report;
import com.openclassrooms.microservice_ui.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;
    @GetMapping("/report/add/{pid}")
    public String createReport(@PathVariable Long pid, Model model) {
        Report report = new Report();
        report.setPid(pid);
        model.addAttribute("report", report);
        return "report/add";
    }

    @PostMapping("/report/add/")
    public String createReport(Report report, Model model) {
        try {
            reportService.add(report);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "report/add";
        }
        return "redirect:/patient/get/" + report.getPid();
    }

    @GetMapping("/report/update/{rid}")
    public String updateReport(@PathVariable String rid, RedirectAttributes redirectAttributes, Model model) {
        try {
            Report report = reportService.get(rid);
            model.addAttribute("report", report);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patient/list";
        }
        return "report/update";
    }

    @PostMapping("/report/update/{rid}")
    public String updateReport(@PathVariable String rid, Report report, Model model) {
        try {
            reportService.update(rid, report);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "report/update";
        }
        return "redirect:/patient/get/" + report.getPid();
    }

    @GetMapping("/report/delete/{pid}/{rid}")
    public String deleteReport( @PathVariable String pid ,@PathVariable String rid, RedirectAttributes redirectAttributes) {
        try {
            reportService.delete(rid);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/patient/get/" + pid;
    }
}
