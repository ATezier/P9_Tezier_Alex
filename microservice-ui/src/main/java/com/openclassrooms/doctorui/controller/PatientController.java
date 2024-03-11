package com.openclassrooms.doctorui.controller;

import com.openclassrooms.microservicepatient.model.Patient;
import com.openclassrooms.microservicereport.model.Report;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.http.HttpStatus.*;

@Controller
public class PatientController {
    private final String patientUrl = "http://localhost:8081/patient";
    private final String reportUrl = "http://localhost:8081/report";
    private final String classificatorUrl = "http://localhost:8081/classificator";
    @GetMapping("/patient/list")
    public String getPatients(Model model, RedirectAttributes redirectAttributes) {
        RestTemplate restTemplate = new RestTemplate();
        String getPatientsUrl = patientUrl + "/list";
        ResponseEntity<Patient[]> response = restTemplate.getForEntity(getPatientsUrl, Patient[].class);
        if(response.getStatusCode() == OK) {
            model.addAttribute("patients", response.getBody());
        }
        if(redirectAttributes.getFlashAttributes().containsKey("error")) {
            model.addAttribute("error", redirectAttributes.getFlashAttributes().get("error"));
        }
        return "patient/list";
    }
    @GetMapping("/patient/get/{id}")
    public String getPatient(@PathVariable Long id, Model model) {
        Patient patient = null;
        RestTemplate restTemplate = new RestTemplate();
        String getPatientUrl = patientUrl + "/get/" + id;
        ResponseEntity<Patient> response = restTemplate.getForEntity(getPatientUrl, Patient.class);
        if(response.getStatusCode() == OK) {
            model.addAttribute("patient", response.getBody());
            patient = response.getBody();
            String getReportsUrl = reportUrl + "/getByPid/" + patient.getPid();
            ResponseEntity<Report[]> responseReports = restTemplate.getForEntity(getReportsUrl, Report[].class);
            if(responseReports.getStatusCode() == OK) {
                model.addAttribute("reports", responseReports.getBody());
            } else {
                model.addAttribute("error", responseReports.getBody());
            }
        } else {
            model.addAttribute("error", response.getBody());
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
        RestTemplate restTemplate = new RestTemplate();
        String createPatientUrl = patientUrl + "/create";
        HttpEntity<Patient> request = new HttpEntity<Patient>(patient, headers);
        ResponseEntity<Patient> response = restTemplate.exchange(
            createPatientUrl,
            HttpMethod.POST,
            request,
            Patient.class);
        if(response.getStatusCode() == CREATED) {
            return "redirect:/patient/list";
        } else {
            model.addAttribute("error", response.getBody());
            return "patient/add";
        }
    }

    @GetMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        RestTemplate restTemplate = new RestTemplate();
        String getPatientUrl = patientUrl + "/get/" + id;
        ResponseEntity<Patient> response = restTemplate.getForEntity(getPatientUrl, Patient.class);
        if(response.getStatusCode() == OK) {
            model.addAttribute("patient", response.getBody());
        } else {
            redirectAttributes.addFlashAttribute("error", response.getBody());
            return "redirect:/patient/list";
        }
        return "patient/update";
    }
    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable Long id, Patient patient, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String updatePatientUrl = patientUrl + "/update/" + id;
        HttpEntity<Patient> request = new HttpEntity<Patient>(patient);
        ResponseEntity<Patient> response = restTemplate.exchange(
            updatePatientUrl,
            HttpMethod.PUT,
            request,
            Patient.class);
        if(response.getStatusCode() == OK) {
            return "redirect:/patient/list";
        } else {
            model.addAttribute("error", response.getBody());
            return "patient/update";
        }
    }

    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        RestTemplate restTemplate = new RestTemplate();
        String deletePatientUrl = patientUrl + "/delete/" + id;
        ResponseEntity<Patient> response = restTemplate.exchange(
            deletePatientUrl,
            HttpMethod.DELETE,
            null,
            Patient.class);
        if(response.getStatusCode() != OK) {
            redirectAttributes.addFlashAttribute("error", response.getBody());
        }
        return "redirect:/patient/list";
    }
}
