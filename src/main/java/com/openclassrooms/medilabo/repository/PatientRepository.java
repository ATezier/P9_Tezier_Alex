package com.openclassrooms.medilabo.repository;

import com.openclassrooms.medilabo.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}