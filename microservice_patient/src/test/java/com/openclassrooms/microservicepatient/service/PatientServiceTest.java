package com.openclassrooms.microservicepatient.service;

import com.openclassrooms.microservicepatient.model.Patient;
import com.openclassrooms.microservicepatient.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PatientServiceTest {

    @MockBean
    private PatientRepository patientRepository;
    @Autowired
    PatientService patientService;

    @Test
    public void crudTest() {
        Patient updatedPatient = new Patient();
        Patient patient = new Patient();
        patient.setPid(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setBirthdate("01/01/1970");
        patient.setGender("M");
        patient.setAddress("");
        patient.setPhone("");
        updatedPatient.setPid(1L);
        updatedPatient.setFirstName("John");
        updatedPatient.setLastName("Doe");
        updatedPatient.setBirthdate("01/01/1970");
        updatedPatient.setGender("M");
        updatedPatient.setAddress("123 Main St");
        updatedPatient.setPhone("");
        //Valid
        try {
            assertTrue(patientService.isValid(patient));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Create
        given(patientRepository.save(patient)).willReturn(patient);
        assertNotNull(patientService.create(patient));
        //Read
        given(patientRepository.findById(patient.getPid())).willReturn(Optional.of(patient));
        given(patientRepository.findByFirstNameAndLastName(patient.getFirstName(), patient.getLastName())).willReturn(java.util.Optional.of(patient));
        assertNotNull(patientService.findByFirstNameAndLastName(patient.getFirstName(), patient.getLastName()));
        //Update
        given(patientRepository.save(updatedPatient)).willReturn(updatedPatient);
        assertNotNull(patientService.update(patient.getPid(), updatedPatient));
        //Delete
        assertDoesNotThrow(() -> patientService.delete(patient.getPid()));
    }
}
