package com.example.jobportal.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jobportal.entity.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
	
	@Query("select trunc(months_between(e.endDate,e.startingDate)/12) from Experience e")
  public Double findWorkExperience(LocalDate endDate,LocalDate startingDate);
}
