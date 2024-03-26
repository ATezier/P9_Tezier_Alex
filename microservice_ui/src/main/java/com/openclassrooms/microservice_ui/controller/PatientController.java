package com.openclassrooms.microservice_ui.controller;

import com.openclassrooms.microservice_ui.model.Patient;
import com.openclassrooms.microservice_ui.model.Report;
import com.openclassrooms.microservice_ui.service.PatientService;
import com.openclassrooms.microservice_ui.service.ReportService;
import com.openclassrooms.microservice_ui.service.RiskAnalyserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;


@Controller
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private RiskAnalyserService riskAnalyserService;

    @GetMapping("/patient/list")
    public String getPatients(Model model, RedirectAttributes redirectAttributes) {
        if(redirectAttributes.getFlashAttributes().containsKey("error")) {
            model.addAttribute("error", redirectAttributes.getFlashAttributes().get("error"));
        }
        Patient[] patients = null;
        try {
            patients = patientService.getAll();
            model.addAttribute("patients", patients);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patient/list";
        }
        return "patient/list";
    }


    @GetMapping("/patient/get/{id}")
    public String getPatient(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Patient patient = null;
        Report[] reports = null;
        String risk = null;
        try {
            patient = patientService.get(id);
            reports = reportService.getByPid(patient.getPid());
            risk = riskAnalyserService.get(id);
            model.addAttribute("patient", patient);
            model.addAttribute("reports", reports);
            model.addAttribute("risk", risk);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patient/list";
        }
        return "patient/get";
    }

    @GetMapping("/patient/add")
    public String createPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient/add";
    }
    @PostMapping("/patient/add")
    public String createPatient(Patient patient, Model model) {
        try {
            patientService.add(patient);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "patient/add";
        }
        return "redirect:/patient/list";
    }

    @GetMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Patient patient = patientService.get(id);
            model.addAttribute("patient", patient);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patient/list";
        }
        return "patient/update";
    }
    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable Long id, Patient patient, Model model) {
        try {
            patientService.update(id, patient);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "patient/update";
        }
        return "redirect:/patient/list";
    }

    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            patientService.delete(id);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/patient/list";
    }
}
