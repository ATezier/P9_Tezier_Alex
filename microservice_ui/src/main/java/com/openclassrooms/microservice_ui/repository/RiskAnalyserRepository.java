package com.openclassrooms.microservice_ui.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class RiskAnalyserRepository {
    private String riskAnalyserUrl = "http://localhost:8081/risk-analyser";
    @Autowired
    RestTemplate restTemplate;
    public String getRisk(Long pid) {
        return restTemplate.getForObject(riskAnalyserUrl + "/" + pid, String.class);
    }
}
