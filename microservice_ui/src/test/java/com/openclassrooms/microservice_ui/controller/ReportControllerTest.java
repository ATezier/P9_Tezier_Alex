package com.openclassrooms.microservice_ui.controller;

import com.openclassrooms.microservice_ui.model.Report;
import com.openclassrooms.microservice_ui.service.PatientService;
import com.openclassrooms.microservice_ui.service.ReportService;
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
public class ReportControllerTest {
    private Long pid;
    private MockMvc mockMvc;
    private Report newTestReport;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private WebApplicationContext context;
    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        pid = patientService.get("InDanger", "Test").getPid();
        newTestReport = new Report();
        newTestReport.setPid(pid);
        newTestReport.setContent("Test Report");
    }

    @Test
    public void getCreateForm() throws Exception {
        mockMvc.perform(get("/report/add/" + pid)).andExpect(status().isOk());
    }
    @Test
    public void crudTests() throws Exception {
        //Create
        mockMvc.perform(post("/report/add/").flashAttr("report", newTestReport)).andExpect(status().is3xxRedirection());
        newTestReport = reportService.getByPid(pid)[0];
        //Update
        newTestReport.setContent("Updated Test Report");
        mockMvc.perform(get("/report/update/" + newTestReport.getRid())).andExpect(status().isOk());
        mockMvc.perform(post("/report/update/" + newTestReport.getRid()).flashAttr("report", newTestReport)).andExpect(status().is3xxRedirection());
        //Delete
        mockMvc.perform(get("/report/delete/" + newTestReport.getPid() + "/" + newTestReport.getRid())).andExpect(status().is3xxRedirection());
    }
}
