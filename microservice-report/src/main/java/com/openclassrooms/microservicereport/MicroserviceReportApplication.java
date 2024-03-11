package com.openclassrooms.microservicereport;

import com.openclassrooms.microservicereport.model.Report;
import com.openclassrooms.microservicereport.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceReportApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceReportApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
