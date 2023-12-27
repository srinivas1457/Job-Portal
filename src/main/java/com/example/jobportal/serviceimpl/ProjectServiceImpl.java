package com.example.jobportal.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.jobportal.entity.Project;
import com.example.jobportal.entity.Resume;
import com.example.jobportal.exceptionhandling.ResumeNotFoundByIdException;
import com.example.jobportal.repository.ProjectRepository;
import com.example.jobportal.repository.ResumeRepository;
import com.example.jobportal.requestdto.ProjectRequest;
import com.example.jobportal.responsedto.ProjectResponse;
import com.example.jobportal.service.ProjectService;
import com.example.jobportal.utility.ResponseStructure;

import jakarta.validation.Valid;

public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	ResumeRepository resumeRepository;

	private Project convertToProject(ProjectRequest projectRequest)
	{
		Project project=new Project();
		project.setProjectName(projectRequest.getProjectName());
		project.setTechStack(convertToSet(projectRequest.getTechStack()));
		project.setDescription(projectRequest.getDescription());
		project.setWebsite(projectRequest.getWebsite());
		project.setSourceCode(projectRequest.getSourceCode());
		return project;
	}

	private ProjectResponse convertToProjectResponse(Project project)
	{
		ProjectResponse projectResponse=new ProjectResponse();
		projectResponse.setProjectName(project.getProjectName());
		projectResponse.setTechStack(project.getTechStack());
		projectResponse.setDescription(project.getDescription());
		projectResponse.setWebsite(project.getWebsite());
		projectResponse.setSourceCode(project.getSourceCode());
		projectResponse.setResume(project.getResume());
		return projectResponse;
	}

	private Set<String> convertToSet(String[] techStack)
	{
		Set<String> teckSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		for(String tech:techStack)
		{
			teckSet.add(tech);
		}
		return teckSet;
	}


	@Override
	public ResponseEntity<ResponseStructure<ProjectResponse>> insertProject(@Valid ProjectRequest projectRequest,
			int resumeId) {

		Resume resume = resumeRepository.findById(resumeId).orElseThrow(()
				->new ResumeNotFoundByIdException("Resume not found with id "+resumeId));

		Project project = convertToProject(projectRequest);

		project.setResume(resume);
		projectRepository.save(project);

		List<Project> projectList=new ArrayList<>();  // creating a project list and adding the current project into it
		projectList.add(project);

		resume.setProjectList(projectList);           //setting projectList and persisting the resume object
		resumeRepository.save(resume);

		ProjectResponse projectResponse = convertToProjectResponse(project);

		ResponseStructure<ProjectResponse> responseStructure=new ResponseStructure<>();
		responseStructure.setData(projectResponse);
		responseStructure.setMessage("Project inserted successfully");
		responseStructure.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<ResponseStructure<ProjectResponse>>(responseStructure, HttpStatus.OK);
	}

}
