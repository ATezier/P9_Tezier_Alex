package com.openclassrooms.microservice_ui.repository;

import com.openclassrooms.microservice_ui.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class PatientRepository {
    private String patientUrl = "http://localhost:8081/patient";
    @Autowired
    private RestTemplate restTemplate;

    public Patient[] getAll() {
        return restTemplate.getForObject(patientUrl + "/list", Patient[].class);
    }

    public Patient get(Long id) {
        return restTemplate.getForObject(patientUrl + "/get/" + id, Patient.class);
    }

    public Patient get(String firstName, String lastName) {
        return restTemplate.getForObject(patientUrl + "/get?firstName=" + firstName + "&lastName=" + lastName, Patient.class);
    }

    public void create(Patient patient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Patient> request = new HttpEntity<>(patient, headers);
        restTemplate.postForObject(patientUrl + "/create", request, Patient.class);
    }

    public void update(Long id, Patient patient) {
        restTemplate.put(patientUrl + "/update/" + id, patient);
    }

    public void delete(Long id) {
        restTemplate.delete(patientUrl + "/delete/" + id);
    }

}
