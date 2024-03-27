package com.openclassrooms.microservice_ui.service;

import com.openclassrooms.microservice_ui.repository.RiskAnalyserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class RiskAnalyserServiceTest {
    @MockBean
    private RiskAnalyserRepository riskAnalyserRepository;
    @Autowired
    private RiskAnalyserService riskAnalyserService;
    @Test
    public void testGet() {
        given(riskAnalyserRepository.getRisk(1L)).willReturn("InDanger");
        assertNotNull(riskAnalyserService.get(1L));
    }
}
