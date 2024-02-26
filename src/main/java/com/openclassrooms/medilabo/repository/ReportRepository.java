package com.openclassrooms.medilabo.repository;

import com.openclassrooms.medilabo.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
