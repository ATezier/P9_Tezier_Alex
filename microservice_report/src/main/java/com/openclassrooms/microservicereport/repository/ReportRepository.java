package com.openclassrooms.microservicereport.repository;

import com.openclassrooms.microservicereport.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, String>{
    public List<Report> findByPid(Long pid);
}
