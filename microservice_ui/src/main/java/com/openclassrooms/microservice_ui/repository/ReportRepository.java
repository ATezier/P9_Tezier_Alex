package com.openclassrooms.microservice_ui.repository;

import com.openclassrooms.microservice_ui.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ReportRepository {
    private String reportUrl = "http://localhost:8081/report";
    @Autowired
    private RestTemplate restTemplate;

    public Report[] findByPid(Long pid) {
        return restTemplate.getForObject(reportUrl + "/getByPid/" + pid, Report[].class);
    }

    public Report get(String rid) {
        return restTemplate.getForObject(reportUrl + "/get/" + rid, Report.class);
    }

    public void add(Report report) {
        restTemplate.postForObject(reportUrl + "/create", report, Report.class);
    }

    public void update(String rid, Report report) {
        restTemplate.put(reportUrl + "/update/" + rid, report);
    }

    public void delete(String rid) {
        restTemplate.delete(reportUrl + "/delete/" + rid);
    }
}
