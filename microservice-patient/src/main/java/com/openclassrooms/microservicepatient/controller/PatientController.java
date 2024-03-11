package com.openclassrooms.microservicepatient.controller;

import com.openclassrooms.microservicepatient.model.Patient;
import com.openclassrooms.microservicepatient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("patient/list")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return new ResponseEntity<>(patientService.findAllPatients(), HttpStatus.OK);
    }

    @GetMapping("patient/get")
    public ResponseEntity<Patient> getPatientByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        ResponseEntity<Patient> res = null;
        try {
            res = new ResponseEntity<>(patientService.findPatientByFirstNameAndLastName(firstName, lastName), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @GetMapping("patient/get/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        ResponseEntity<Patient> res = null;
        try {
            res = new ResponseEntity<>(patientService.findPatientById(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @PostMapping("patient/create")
    public ResponseEntity<?> createPerson(@RequestBody Patient patient) {
        ResponseEntity<?> res = null;
        try {
            patientService.createPatient(patient);
            res = ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return res;
    }

    @PutMapping("patient/update/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable Long id, @RequestBody Patient patient) {
        ResponseEntity<?> res = null;
        try {
            patientService.updatePatient(id, patient);
            res = new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            if(e.getMessage().contains("Patient not found")) {
                res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return res;
    }

    @DeleteMapping("patient/delete/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        ResponseEntity<?> res = null;
        try {
            patientService.deletePatient(id);
            res = new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return res;
    }
}
