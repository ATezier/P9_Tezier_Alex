package com.openclassrooms.microservicepatient.service;

import com.openclassrooms.microservicepatient.model.Patient;
import com.openclassrooms.microservicepatient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
            switch (patient.getGender()) {
                case "M":
                case "F":
                case "O":
                    break;
                default:
                    isValid = false;
                    errorMessage += "Gender is required. ";
            }
        }
        if(!isValid) {
            throw new IllegalArgumentException(errorMessage);
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
        if(isValid(patient)) {
            try {
                findPatientByFirstNameAndLastName(patient.getFirstName(), patient.getLastName());
                throw new Exception("Patient already exists, failed to create new patient");
            } catch (IllegalArgumentException e) {
                return patientRepository.save(patient);
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Patient is not valid");
        }
    }
    public Patient updatePatient(String firstName, String lastName, Patient patient) {
        Patient old;
        if(isValid(patient)) {
            try {
                old = findPatientByFirstNameAndLastName(firstName, lastName);
                patient.setPid(old.getPid());
                if (old.getFirstName().equals(patient.getFirstName())
                        && old.getLastName().equals(patient.getLastName())
                        && old.getBirthdate().equals(patient.getBirthdate())
                        && old.getGender().equals(patient.getGender())
                        && old.getAddress().equals(patient.getAddress())
                        && old.getPhone().equals(patient.getPhone())) {
                    throw new Exception("Nothing to update, failed to update");
                }
                return patientRepository.save(patient);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage() + ", failed to update");
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Patient is not valid");
        }
    }
    public void deletePatient(String firstName, String lastName) {
        try {
            Patient patient = findPatientByFirstNameAndLastName(firstName, lastName);
            patientRepository.delete(patient);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + ", failed to delete");
        }
    }
}
