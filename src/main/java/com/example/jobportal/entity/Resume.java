package com.example.jobportal.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Component
@Entity
public class Resume {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int resumeId;
	private String objective;
	
	@OneToOne
	private User user;
	
	public int getResumeId() {
		return resumeId;
	}
	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}

}
