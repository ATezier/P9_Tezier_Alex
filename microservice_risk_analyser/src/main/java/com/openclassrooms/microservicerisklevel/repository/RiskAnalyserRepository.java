package com.openclassrooms.microservicerisklevel.repository;

import com.openclassrooms.microservicerisklevel.model.Patient;
import com.openclassrooms.microservicerisklevel.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class RiskAnalyserRepository {
    private final String patientUrl = "http://localhost:8081/patient";
    private final String reportUrl = "http://localhost:8081/report";
    @Autowired
    RestTemplate restTemplate;

    public Patient getPatient(Long id) {
        return restTemplate.getForObject(patientUrl + "/get/" + id, Patient.class);
    }

    public Report[] getReportsByPid(Long pid) {
        return restTemplate.getForObject(reportUrl + "/getByPid/" + pid, Report[].class);
    }
}
