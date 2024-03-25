package com.openclassrooms.microservice_ui.repository;

import com.openclassrooms.microservice_ui.dto.RiskAnalyserAssetsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

    public String get(RiskAnalyserAssetsDto riskAnalyserAssetsDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RiskAnalyserAssetsDto> request = new HttpEntity<>(riskAnalyserAssetsDto, headers);
        return restTemplate.exchange(riskAnalyserUrl + "/get", HttpMethod.POST, request, String.class).getBody();
    }

}
