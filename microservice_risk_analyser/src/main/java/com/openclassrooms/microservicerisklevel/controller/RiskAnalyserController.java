package com.openclassrooms.microservicerisklevel.controller;

import com.openclassrooms.microservicerisklevel.dto.RiskAnalyserAssetsDto;
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
        ResponseEntity<String> response = null;
        try {
            response = ResponseEntity.ok(riskAnalyserService.getRisk(pid));
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("risk-analyser/get")
    public ResponseEntity<String> getRisk(@RequestBody RiskAnalyserAssetsDto riskAnalyserAssetsDto) {
        ResponseEntity<String> response = null;
        try {
            response = ResponseEntity.ok(riskAnalyserService.analyse(riskAnalyserAssetsDto));
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
        return response;
    }
}
