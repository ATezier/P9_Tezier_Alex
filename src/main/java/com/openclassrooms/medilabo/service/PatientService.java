package com.openclassrooms.medilabo.service;

import com.openclassrooms.medilabo.model.Patient;
import com.openclassrooms.medilabo.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public boolean isValid(Patient patient) {
        boolean isValid = true;
        String errorMessage = "";
        if(patient.getFirstName() == null || patient.getFirstName().isEmpty()) {
            isValid = false;
            errorMessage += "First name is required. ";
        }
        if(patient.getLastName() == null || patient.getLastName().isEmpty()) {
            isValid = false;
            errorMessage += "Last name is required. ";
        }
        if(patient.getBirthdate() == null) {
            isValid = false;
            errorMessage += "Birth date is required. ";
        }
        if(patient.getGender() == null) {
            isValid = false;
            errorMessage += "Gender is required. ";
        }
        if(!isValid) {
            throw new IllegalArgumentException(errorMessage);
        }
        return true;
    }
    public Patient findPatient(String firstName, String lastName) {
        return patientRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
    }
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }
    public Patient createPatient(Patient patient) {
        if(isValid(patient)) {
            try {
                findPatient(patient.getFirstName(), patient.getLastName());
                throw new IllegalArgumentException("Patient already exists, failed to create new patient");
            } catch (IllegalArgumentException e) {
                return patientRepository.save(patient);
            }
        } else {
            throw new IllegalArgumentException("Patient is not valid");
        }
    }
    public Patient updatePatient(Patient patient) {
        if(isValid(patient)) {
            try {
                findPatient(patient.getFirstName(), patient.getLastName());
                return patientRepository.save(patient);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Patient does not exist, failed to update");
            }
        } else {
            throw new IllegalArgumentException("Patient is not valid");
        }
    }
    public void deletePatient(String firstName, String lastName) {
        try {
            Patient patient = findPatient(firstName, lastName);
            patientRepository.delete(patient);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Patient does not exist, failed to delete");
        }
    }
}