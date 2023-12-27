package com.example.jobportal.service;

import org.springframework.http.ResponseEntity;

import com.example.jobportal.requestdto.ProjectRequest;
import com.example.jobportal.responsedto.ProjectResponse;
import com.example.jobportal.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface ProjectService {

	ResponseEntity<ResponseStructure<ProjectResponse>> insertProject(@Valid ProjectRequest projectRequest,
			int resumeId);

}
