package com.example.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobportal.entity.Job;

public interface JobRepository extends JpaRepository<Job, Integer>{

	List<Job> findByJobTitle(String jobTitle);

	List<Job> findByJobLocation(String jobLocation);


}
