package com.openclassrooms.microservicepatient.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.openclassrooms.microservicepatient.model.Patient;
import com.openclassrooms.microservicepatient.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PatientControllerTest {
    @Autowired
    private PatientService patientService;
    @Autowired
    private WebApplicationContext context;

    @Test
    public void crudTests() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        Patient patient = new Patient();
        patient.setFirstName("Tester");
        patient.setLastName("Test");
        patient.setBirthdate("01/01/2000");
        patient.setGender("M");
        patient.setPhone("");
        patient.setAddress("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(patient);

        //Create
        mockMvc.perform(post("/patient/create").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isCreated());
        patient = patientService.findByFirstNameAndLastName(patient.getFirstName(), patient.getLastName());
        //Read
        mockMvc.perform(get("/patient/get/{id}", patient.getPid())).andExpect(status().isOk());
        mockMvc.perform(get("/patient/get")
                        .param("firstName", patient.getFirstName())
                        .param("lastName", patient.getLastName())).andExpect(status().isOk());
        mockMvc.perform(get("/patient/list")).andExpect(status().isOk());
        //Update
        patient.setPhone("1234567890");
        requestJson=ow.writeValueAsString(patient);
        mockMvc.perform(put("/patient/update/{id}", patient.getPid()).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isOk());
        //Delete
        mockMvc.perform(delete("/patient/delete/{id}", patient.getPid())).andExpect(status().isOk());
    }
}
