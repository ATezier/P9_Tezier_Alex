package com.openclassrooms.microservicepatient.service;

import com.openclassrooms.microservicepatient.model.Patient;
import com.openclassrooms.microservicepatient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public boolean isValid(Patient patient) throws Exception {
        boolean isValid = true;
        String errorMessage = "";
        if(patient.getFirstName() == null || patient.getFirstName().isEmpty()) {
            isValid = false;
            errorMessage += "First name is required.\n";
        }
        if(patient.getLastName() == null || patient.getLastName().isEmpty()) {
            isValid = false;
            errorMessage += "Last name is required.\n";
        }
        if(patient.getBirthdate() == null) {
            isValid = false;
            errorMessage += "Birth date is required.\n";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            formatter.parse(patient.getBirthdate());
        } catch (DateTimeParseException e) {
            isValid = false;
            errorMessage += e.getMessage() + "\n";
        }
        if(patient.getGender() == null) {
            switch (patient.getGender()) {
                case "M":
                case "F":
                case "O":
                    break;
                default:
                    isValid = false;
                    errorMessage += "Gender is required.\n";
            }
        }
        if(!isValid) {
            throw new Exception(errorMessage);
        }
        return true;
    }
    public Patient findPatientByFirstNameAndLastName(String firstName, String lastName) {
        return patientRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
    }

    public Patient findPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
    }
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }
    public Patient createPatient(Patient patient) {
        try {
            isValid(patient);
            findPatientByFirstNameAndLastName(patient.getFirstName(), patient.getLastName());
            throw new Exception("Patient already exists, failed to create new patient");
        } catch (IllegalArgumentException e) {
            return patientRepository.save(patient);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Patient updatePatient(Long id, Patient patient) {
        Patient old;
        try {
            isValid(patient);
            old = findPatientById(id);
            patient.setPid(old.getPid());
            if (old.getFirstName().equals(patient.getFirstName())
                    && old.getLastName().equals(patient.getLastName())
                    && old.getBirthdate().equals(patient.getBirthdate())
                    && old.getGender().equals(patient.getGender())
                    && old.getAddress().equals(patient.getAddress())
                    && old.getPhone().equals(patient.getPhone())) {
                throw new Exception("Nothing to update, failed to update");
            }
            try {
                Patient duplicate = findPatientByFirstNameAndLastName(patient.getFirstName(), patient.getLastName());
                if(duplicate.getPid() != old.getPid()) {
                    throw new Exception("Duplication attempt, failed to update");
                }
            } catch (IllegalArgumentException e) {
                // do nothing
            }
            return patientRepository.save(patient);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + ", failed to update");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public void deletePatient(Long id) {
        try {
            Patient patient = findPatientById(id);
            patientRepository.delete(patient);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + ", failed to delete");
        }
    }
}
