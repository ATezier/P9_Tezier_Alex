package com.openclassrooms.medilabo.controller;

import com.openclassrooms.medilabo.model.Patient;
import com.openclassrooms.medilabo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return new ResponseEntity<>(patientService.findAllPatients(), HttpStatus.OK);
    }

    @GetMapping("/patient")
    public ResponseEntity<Patient> getPatientByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        ResponseEntity<Patient> res = null;
        try {
            res = new ResponseEntity<>(patientService.findPatientByFirstNameAndLastName(firstName, lastName), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @PostMapping("/patient")
    public ResponseEntity<?> createPerson(@RequestBody Patient patient) {
        ResponseEntity<?> res = null;
        try {
            patientService.createPatient(patient);
            res = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            res = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @PutMapping("/patient")
    public ResponseEntity<?> updatePerson(@RequestBody String firstName, @RequestBody String lastName, @RequestBody Patient patient) {
        ResponseEntity<?> res = null;
        try {
            patientService.updatePatient(firstName, lastName, patient);
            res = new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            if(e.getMessage().contains("Patient not found")) {
                res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                res = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return res;
    }

    @DeleteMapping("/patient")
    public ResponseEntity<?> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        ResponseEntity<?> res = null;
        try {
            patientService.deletePatient(firstName, lastName);
            res = new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return res;
    }
}
