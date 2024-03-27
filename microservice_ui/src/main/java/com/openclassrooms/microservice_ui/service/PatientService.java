package com.openclassrooms.microservice_ui.service;

import com.openclassrooms.microservice_ui.model.Patient;
import com.openclassrooms.microservice_ui.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient[] getAll() {
        return patientRepository.getAll();
    }

    public Patient get(Long id) {
        try {
            return patientRepository.get(id);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Patient get(String firstName, String lastName) {
        try {
            return patientRepository.get(firstName, lastName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void add(Patient patient) {
        try {
            patientRepository.create(patient);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void update(Long id, Patient patient) {
        try {
            patientRepository.update(id, patient);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            patientRepository.delete(id);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
