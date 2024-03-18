package com.openclassrooms.doctorui.controller;

import com.openclassrooms.microservicepatient.model.Patient;
import com.openclassrooms.microservicereport.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.http.HttpStatus.*;

@Controller
public class PatientController {
    @Autowired
    private RestTemplate restTemplate;

    private final String patientUrl = "http://localhost:8081/patient";
    private final String reportUrl = "http://localhost:8081/report";
    private final String riskAnalyserUrl = "http://localhost:8081/risk-analyser";
    @GetMapping("/patient/list")
    public String getPatients(Model model, RedirectAttributes redirectAttributes) {
        if(redirectAttributes.getFlashAttributes().containsKey("error")) {
            model.addAttribute("error", redirectAttributes.getFlashAttributes().get("error"));
        }
        String getPatientsUrl = patientUrl + "/list";
        try {
            ResponseEntity<Patient[]> response = restTemplate.getForEntity(getPatientsUrl, Patient[].class);
            model.addAttribute("patients", response.getBody());
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
        String getPatientUrl = patientUrl + "/get/" + id;
        try {
            ResponseEntity<Patient> responsePatient = restTemplate.getForEntity(getPatientUrl, Patient.class);
            patient = responsePatient.getBody();
            String getReportsUrl = reportUrl + "/getByPid/" + patient.getPid();
            String analyseRiskUrl = riskAnalyserUrl + "/" + patient.getPid();
            model.addAttribute("patient", patient);
            ResponseEntity<Report[]> responseReports = restTemplate.getForEntity(getReportsUrl, Report[].class);
            reports = responseReports.getBody();
            model.addAttribute("reports", reports);
            ResponseEntity<String> responseRisk = restTemplate.getForEntity(analyseRiskUrl, String.class);
            model.addAttribute("risk", responseRisk.getBody());

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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String createPatientUrl = patientUrl + "/create";
        HttpEntity<Patient> request = new HttpEntity<Patient>(patient, headers);
        try {
            ResponseEntity<Patient> response = restTemplate.postForEntity(createPatientUrl, request, Patient.class);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "patient/add";
        }
        return "redirect:/patient/list";
    }

    @GetMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        String getPatientUrl = patientUrl + "/get/" + id;
        try {
            ResponseEntity<Patient> response = restTemplate.getForEntity(getPatientUrl, Patient.class);
            model.addAttribute("patient", response.getBody());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patient/list";
        }
        return "patient/update";
    }
    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable Long id, Patient patient, Model model) {
        String updatePatientUrl = patientUrl + "/update/" + id;
        HttpEntity<Patient> request = new HttpEntity<Patient>(patient);
        try {
            ResponseEntity<Patient> response = restTemplate.exchange(
                    updatePatientUrl,
                    HttpMethod.PUT,
                    request,
                    Patient.class);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "patient/update";
        }
        return "redirect:/patient/list";
    }

    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String deletePatientUrl = patientUrl + "/delete/" + id;
        try {
            ResponseEntity<Patient> response = restTemplate.exchange(
                    deletePatientUrl,
                    HttpMethod.DELETE,
                    null,
                    Patient.class);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/patient/list";
    }
}
