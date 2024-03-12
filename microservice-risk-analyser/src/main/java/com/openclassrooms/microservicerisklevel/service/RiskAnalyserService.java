package com.openclassrooms.microservicerisklevel.service;

import com.openclassrooms.microservicepatient.model.Patient;
import com.openclassrooms.microservicereport.model.Report;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RiskAnalyserService {
    private final List<String> criteria = List.of(
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fumeuse",
            "Fumeur",
            "Anormal",
            "Cholestérol",
            "Vertiges",
            "Rechute",
            "Réaction",
            "Anticorps");

    public String analyse(Patient patient, List<Report> reports) {
        int age = getAge(patient.getBirthdate());
        int critera = criteriaCounter(reports);
        if(critera < 2) {
            return "None";
        }
        if(critera < 6 && age > 30) {
            return "Borderline";
        }
        if(patient.getGender().equals("F")) {
            if(age < 30) {
                if(critera > 6) {
                    return "Early onset";
                }
                if(critera > 3) {
                    return "In Danger";
                }
            } else {
                if(critera > 7) {
                    return "Early onset";
                }
                if(critera > 5) {
                    return "In Danger";
                }
            }
        } else {
            if(age < 30) {
                if(critera > 4) {
                    return "Early onset";
                }
                if(critera > 2) {
                    return "In Danger";
                }
            } else {
                if(critera > 7) {
                    return "Early onset";
                }
                if(critera > 6) {
                    return "In Danger";
                }
            }
        }
        return "Error : None Match";
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
                if(r.getContent().contains(criterion)) {
                    criteriaCount.put(criterion, criteriaCount.get(criterion) + 1);
                    total.getAndIncrement();
                }
            }
        });
        return total.get();
    }
}
