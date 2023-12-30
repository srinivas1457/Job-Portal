package com.example.jobportal.responsedto;

import java.time.LocalDate;
import java.util.Map;

public class ExperienceResponse {

	private int experienceId;
	private String companyName;
	private String Designation;
	private LocalDate startingDate;
	private LocalDate endDate;
	private boolean present;
	private String Description;
	private  Double workExperience;

	public Double getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(Double workExperience) {
		this.workExperience = workExperience;
	}

	Map<String, String> options;

	public int getExperienceId() {
		return experienceId;
	}

	public void setExperienceId(int experienceId) {
		this.experienceId = experienceId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDesignation() {
		return Designation;
	}

	public void setDesignation(String designation) {
		Designation = designation;
	}

	public LocalDate getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(LocalDate startingDate) {
		this.startingDate = startingDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

}
