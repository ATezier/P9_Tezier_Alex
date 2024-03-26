package com.openclassrooms.microservicerisklevel.service;

import com.openclassrooms.microservicerisklevel.model.Patient;
import com.openclassrooms.microservicerisklevel.model.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RiskAnalyserServiceTest {
    @Autowired
    private RiskAnalyserService riskAnalyserService;
    @Test
    public void getAgeTest() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String birthdate = date.minusYears(10).format(formatter);
        assert(riskAnalyserService.getAge(birthdate) == 10);
    }
    @Test
    public void criteriaCounter() {
        List<Report> reports = new ArrayList<>();
        String reportContent = "For another test ";
        for(String criterion : riskAnalyserService.getCriteria()) {
            Report report = new Report();
            report.setContent(criterion);
            reports.add(report);
            reportContent += criterion + " ";
        }
        assert(riskAnalyserService.criteriaCounter(reports) == reports.size());
        Report specialReport = new Report();
        specialReport.setContent(reportContent);
        assert(riskAnalyserService.criteriaCounter(List.of(specialReport)) == reports.size());
    }

    @Test
    public void analyseTest() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Patient patient = new Patient();
        patient.setGender("M");
        patient.setBirthdate(date.minusYears(38).format(formatter));

        List<Report> reports = new ArrayList<>();
        //None risk level
        assertEquals("None", riskAnalyserService.analyse(patient, reports));
        List<String> criteria = riskAnalyserService.getCriteria();
        //Borderline risk level
        reportManager(reports, 2);
        assertEquals("Borderline", riskAnalyserService.analyse(patient, reports));
        //For a man older than 30
        //On danger risk level
        reportManager(reports, 7);
        assertEquals("In Danger", riskAnalyserService.analyse(patient, reports));
        //Early onset risk level
        reportManager(reports, 8);
        assertEquals("Early onset", riskAnalyserService.analyse(patient, reports));
        //For a man younger than 30
        patient.setBirthdate(date.minusYears(28).format(formatter));
        //On danger risk level
        reportManager(reports, 4);
        assertEquals("In Danger", riskAnalyserService.analyse(patient, reports));
        //Early onset risk level
        reportManager(reports, 5);
        assertEquals("Early onset", riskAnalyserService.analyse(patient, reports));
        //For a woman younger than 30
        patient.setGender("F");
        //On danger risk level
        reportManager(reports, 4);
        assertEquals("In Danger", riskAnalyserService.analyse(patient, reports));
        //Early onset risk level
        reportManager(reports, 7);
        assertEquals("Early onset", riskAnalyserService.analyse(patient, reports));
        //For a woman older than 30
        patient.setBirthdate(date.minusYears(38).format(formatter));
        //On danger risk level
        reportManager(reports, 6);
        assertEquals("In Danger", riskAnalyserService.analyse(patient, reports));
        //Early onset risk level
        reportManager(reports, 8);
        assertEquals("Early onset", riskAnalyserService.analyse(patient, reports));
    }

    private void reportManager(List<Report> reports, int size) {
        List<String> criteria = riskAnalyserService.getCriteria();
        if(reports.size() > size) {
            while(reports.size() > size) {
                reports.remove(reports.size() - 1);
            }
        } else {
            while (reports.size() < size) {
                Report report = new Report();
                report.setContent(criteria.get(reports.size()));
                reports.add(report);
            }
        }
    }
}
