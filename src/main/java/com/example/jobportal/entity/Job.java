package com.example.jobportal.entity;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.jobportal.enums.JobStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Component
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int jobId;

	private String jobTitle;
	private String jobLocation;
	private String jobDescription;
	private JobStatus jobStatus;
	private LocalDate openingDate;
	private LocalDate closingDate;
	
	@ManyToOne
	private Company company;

	public LocalDate getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public LocalDate getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
