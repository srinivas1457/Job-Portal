package com.example.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jobportal.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Integer>{

//	@Query("select r from Resume where ")
//	List<Resume> findBySkillName(String skillName);

}
