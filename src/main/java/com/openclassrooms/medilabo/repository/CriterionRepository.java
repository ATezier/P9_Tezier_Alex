package com.openclassrooms.medilabo.repository;

import com.openclassrooms.medilabo.model.Criterion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriterionRepository extends JpaRepository<Criterion, Long> {
}
