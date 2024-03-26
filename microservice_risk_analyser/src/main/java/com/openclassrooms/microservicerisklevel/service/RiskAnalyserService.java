package com.openclassrooms.microservicerisklevel.service;

import com.openclassrooms.microservicerisklevel.model.Patient;
import com.openclassrooms.microservicerisklevel.model.Report;
import com.openclassrooms.microservicerisklevel.repository.RiskAnalyserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RiskAnalyserService {
    @Autowired
    private RiskAnalyserRepository riskAnalyserRepository;
    private final List<String> criteria = List.of(
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fume",
            "Anormal",
            "Cholestérol",
            "Vertige",
            "Rechute",
            "Réaction",
            "Anticorps");

    public List<String> getCriteria() {
        return criteria;
    }

    public String analyse(Patient patient, List<Report> reports) {
        int age = getAge(patient.getBirthdate());
        int criteria = criteriaCounter(reports);
        if(criteria < 2) {
            return "None";
        }
        if(criteria < 6 && age > 30) {
            return "Borderline";
        }
        if(patient.getGender().equals("F")) {
            //Women section
            if(age < 30) {
                if(criteria > 6) {
                    return "Early onset";
                }
                if(criteria > 3) {
                    return "In Danger";
                }
            } else {
                if(criteria > 7) {
                    return "Early onset";
                }
                if(criteria > 5) {
                    return "In Danger";
                }
            }
        } else {
            //Men section
            if(age < 30) {
                if(criteria > 4) {
                    return "Early onset";
                }
                if(criteria > 2) {
                    return "In Danger";
                }
            } else {
                if(criteria > 7) {
                    return "Early onset";
                }
                if(criteria > 6) {
                    return "In Danger";
                }
            }
        }
        return "None Match for :\n" +
                "Age : "+age+"\n" +
                "Gender : "+patient.getGender()+"\n" +
                "Number of detected criteria : "+criteria;
    }

    public int getAge(String birthdate) {
        int age = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate past = LocalDate.parse(birthdate, formatter);
        LocalDate sysdate = LocalDate.now();
        if(past != null) {
            age = Period.between(past, sysdate).getYears();
        }
        return age;
    }

    public int criteriaCounter(List<Report> reports) {
        HashMap<String, Integer> criteriaCount = new HashMap<>();
        AtomicInteger total = new AtomicInteger();
        total.set(0);
        criteria.forEach(c -> criteriaCount.put(c, 0));
        reports.forEach(r -> {
            for (String criterion : criteriaCount.keySet()) {
                if(criteriaCount.get(criterion) > 0) {
                    continue;
                }
                if(r.getContent().toUpperCase().contains(criterion.toUpperCase())) {
                    criteriaCount.put(criterion, criteriaCount.get(criterion) + 1);
                    total.getAndIncrement();
                }
            }
        });
        return total.get();
    }

    public String getRisk(Long pid) {
        try {
            Patient patient = riskAnalyserRepository.getPatient(pid);
            List<Report> reports = Arrays.stream(riskAnalyserRepository.getReportsByPid(pid)).toList();
            return analyse(patient, reports);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
