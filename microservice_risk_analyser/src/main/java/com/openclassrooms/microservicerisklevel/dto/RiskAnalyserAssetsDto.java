package com.openclassrooms.microservicerisklevel.dto;

import com.openclassrooms.microservicerisklevel.model.Patient;
import com.openclassrooms.microservicerisklevel.model.Report;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class RiskAnalyserAssetsDto {
    private String gender;
    private String birthdate;
    private List<Report> reports;
    public RiskAnalyserAssetsDto(Patient patient, List<Report> reports) {
        this.gender = patient.getGender();
        this.birthdate = patient.getBirthdate();
        this.reports = reports;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
