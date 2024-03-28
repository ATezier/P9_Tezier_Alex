package com.openclassrooms.microservicerisklevel.controller;

import com.openclassrooms.microservicerisklevel.service.RiskAnalyserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class RiskAnalyserController {
    @Autowired
    RiskAnalyserService riskAnalyserService;


    @GetMapping("risk-analyser/{pid}")
    public ResponseEntity<String> analyse(@PathVariable Long pid) {
        ResponseEntity<String> response;
        try {
            response = ResponseEntity.ok(riskAnalyserService.getRisk(pid));
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
        return response;
    }
}
