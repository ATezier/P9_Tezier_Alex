package com.openclassrooms.microservice_ui.service;

import com.openclassrooms.microservice_ui.model.Patient;
import com.openclassrooms.microservice_ui.repository.PatientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PatientServiceTest {
    private Patient patient;
    @MockBean
    private PatientRepository patientRepository;
    @Autowired
    private PatientService patientService;
    @BeforeAll
    public void setUp() {
        patient = new Patient();
        patient.setPid(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setBirthdate("01/01/1970");
        patient.setGender("M");
    }
    @Test
    public void testGetAll() {
        given(patientRepository.getAll()).willReturn(new Patient[] {patient});
        assertTrue(patientService.getAll().length > 0);
    }
    @Test
    public void testGet() {
        given(patientRepository.get(1L)).willReturn(patient);
        assertTrue(patientService.get(1L) != null);
    }
    @Test
    public void testGetByFirstNameAndLastName() {
        given(patientRepository.get("John", "Doe")).willReturn(patient);
        assertTrue(patientService.get("John", "Doe") != null);
    }
    @Test
    public void testAdd() {
        assertDoesNotThrow(() -> patientService.add(patient));
    }
    @Test
    public void testUpdate() {
        assertDoesNotThrow(() -> patientService.update(1L, patient));
    }
    @Test
    public void testDelete() {
        assertDoesNotThrow(() -> patientService.delete(1L));
    }
}
