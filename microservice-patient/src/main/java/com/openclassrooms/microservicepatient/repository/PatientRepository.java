package com.openclassrooms.microservicepatient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassrooms.microservicepatient.model.Patient;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}
