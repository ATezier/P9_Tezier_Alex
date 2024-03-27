package com.openclassrooms.microservice_ui.controller;

import com.openclassrooms.microservice_ui.model.Patient;
import com.openclassrooms.microservice_ui.service.PatientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PatientControllerTest {
    private Patient newTestPatient;
    private MockMvc mockMvc;

    @Autowired
    private PatientService patientService;
    @Autowired
    private WebApplicationContext context;

    @BeforeAll
    public void setUp() {
        newTestPatient = new Patient();
        newTestPatient.setFirstName("Tester");
        newTestPatient.setLastName("Test");
        newTestPatient.setBirthdate("01/01/2000");
        newTestPatient.setGender("M");
        newTestPatient.setPhone("");
        newTestPatient.setAddress("");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getPatients() throws Exception {
        mockMvc.perform(get("/patient/list")).andExpect(status().isOk());
    }
    @Test
    public void getCreateForm() throws Exception {
        mockMvc.perform(get("/patient/add")).andExpect(status().isOk());
    }
    @Test
    public void crudTests() throws Exception {
        //Create
        mockMvc.perform(post("/patient/add").flashAttr("patient", newTestPatient)).andExpect(status().is3xxRedirection());
        newTestPatient = patientService.get("Tester", "Test");
        //Update
        newTestPatient.setPhone("1234567890");
        mockMvc.perform(get("/patient/update/{id}", newTestPatient.getPid())).andExpect(status().isOk());
        mockMvc.perform(post("/patient/update/{id}", newTestPatient.getPid()).flashAttr("patient", newTestPatient)).andExpect(status().is3xxRedirection());
        //Read Patient, Report and Risk
        mockMvc.perform(get("/patient/get/{id}", newTestPatient.getPid())).andExpect(status().isOk());
        //Delete
        mockMvc.perform(get("/patient/delete/{id}", newTestPatient.getPid())).andExpect(status().is3xxRedirection());
    }
}
