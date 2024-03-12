package com.openclassrooms.microservicerisklevel.controller;

import com.openclassrooms.microservicepatient.model.Patient;
import com.openclassrooms.microservicereport.model.Report;
import com.openclassrooms.microservicerisklevel.service.RiskAnalyserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class RiskAnalyserController {
    private final String patientUrl = "http://localhost:8081/patient";
    private final String reportUrl = "http://localhost:8081/report";
    @Autowired
    RiskAnalyserService riskAnalyserService;

    @GetMapping("risk-analyser/{pid}")
    public ResponseEntity<String> analyse(@PathVariable Long pid) {
        ResponseEntity<String> risk = null;
        RestTemplate restTemplate = new RestTemplate();
        String getPatientUrl = patientUrl + "/get/" + pid;
        ResponseEntity<Patient> patientResponse = restTemplate.getForEntity(getPatientUrl, Patient.class);
        Patient patient = null;
        if(patientResponse.getStatusCode() == HttpStatus.OK) {
            patient = patientResponse.getBody();
            String getReportsUrl = reportUrl + "/getByPid/" + pid;
            ResponseEntity<Report[]> reportsResponse = restTemplate.getForEntity(getReportsUrl, Report[].class);
            Report[] reports = null;
            if(reportsResponse.getStatusCode() == HttpStatus.OK) {
                reports = reportsResponse.getBody();
                risk = ResponseEntity.ok(riskAnalyserService.analyse(patient, Arrays.asList(reports)));
            } else {
                risk = ResponseEntity.notFound().build();
            }
        } else {
            risk = ResponseEntity.notFound().build();
        }
        return risk;
    }

}
