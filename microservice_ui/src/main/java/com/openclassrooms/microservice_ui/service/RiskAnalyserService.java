package com.openclassrooms.microservice_ui.service;

import com.openclassrooms.microservice_ui.dto.RiskAnalyserAssetsDto;
import com.openclassrooms.microservice_ui.repository.RiskAnalyserRepository;
import org.springframework.stereotype.Service;

@Service
public class RiskAnalyserService {
    private RiskAnalyserRepository riskAnalyserRepository;

    public RiskAnalyserService(RiskAnalyserRepository riskAnalyserRepository) {
        this.riskAnalyserRepository = riskAnalyserRepository;
    }

    public String get(RiskAnalyserAssetsDto riskAnalyserAssetsDto) {
        try {
            return riskAnalyserRepository.get(riskAnalyserAssetsDto);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
