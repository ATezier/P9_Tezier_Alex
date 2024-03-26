package com.openclassrooms.microservice_ui.service;

import com.openclassrooms.microservice_ui.repository.RiskAnalyserRepository;
import org.springframework.stereotype.Service;

@Service
public class RiskAnalyserService {
    private RiskAnalyserRepository riskAnalyserRepository;

    public RiskAnalyserService(RiskAnalyserRepository riskAnalyserRepository) {
        this.riskAnalyserRepository = riskAnalyserRepository;
    }

    public String get(Long pid) {
        try {
            return riskAnalyserRepository.getRisk(pid);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
