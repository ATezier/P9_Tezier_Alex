package com.openclassrooms.microservicereport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.openclassrooms.microservicereport.model.Report;
import com.openclassrooms.microservicereport.service.ReportService;
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
public class ReportControllerTest {
    @Autowired
    private ReportService reportService;
    @Autowired
    private WebApplicationContext context;

    @Test
    public void crudTests() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        Report report = new Report();
        report.setPid(2L);
        report.setContent("Test");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(report);

        //Create
        mockMvc.perform(post("/report/create").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isCreated());
        report = reportService.getReportByPid(report.getPid()).get(0);
        //Read
        mockMvc.perform(get("/report/get/{id}", report.getRid())).andExpect(status().isOk());
        mockMvc.perform(get("/report/getByPid/{pid}", report.getPid())).andExpect(status().isOk());
        mockMvc.perform(get("/report/list")).andExpect(status().isOk());
        //Update
        report.setContent("Test2");
        requestJson=ow.writeValueAsString(report);
        mockMvc.perform(put("/report/update/{id}", report.getRid()).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isOk());
        //Delete
        mockMvc.perform(delete("/report/delete/{id}", report.getRid())).andExpect(status().isOk());
    }
}
