package com.example.jobportal.serviceimpl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.jobportal.entity.Education;
import com.example.jobportal.entity.Resume;
import com.example.jobportal.enums.DegreeType;
import com.example.jobportal.exceptionhandling.EducationNotFoundByIdException;
import com.example.jobportal.exceptionhandling.ResumeNotFoundByIdException;
import com.example.jobportal.repository.EducationRepository;
import com.example.jobportal.repository.ResumeRepository;
import com.example.jobportal.requestdto.EducationRequest;
import com.example.jobportal.responsedto.EducationResponse;
import com.example.jobportal.service.EducationService;
import com.example.jobportal.utility.ResponseStructure;

@Service
public class EducationServiceImpl implements EducationService {

	@Autowired
	private EducationRepository educationRepo;

	@Autowired
	private ResumeRepository resumeRepo;

	private Education convertToEducation(EducationRequest educationRequest, Education education) {
		education.setInstitutionName(educationRequest.getInstitutionName());
		education.setDegreeType(educationRequest.getDegreeType());
		education.setDegreeName(educationRequest.getDegreeName());
		education.setFieldOfStudy(educationRequest.getFieldOfStudy());
		education.setPercentage(educationRequest.getPercentage());
		education.setStartingDate(educationRequest.getStartingDate());
		education.setEndDate(educationRequest.getEndDate());
		education.setPresent(checkPresent(educationRequest.getEndDate()));

		return education;
	}

	private boolean checkPresent(LocalDate endDate) {
		if (endDate == null)
			return true;
		return false;
	}

	private EducationResponse convertToEducationResponse(Education education) {
		EducationResponse educationResponse = new EducationResponse();
		educationResponse.setEducationId(education.getEducationId());
		educationResponse.setInstitutionName(education.getInstitutionName());
		educationResponse.setDegreeType(education.getDegreeType());
		educationResponse.setDegreeName(education.getDegreeName());
		educationResponse.setFieldOfStudy(education.getFieldOfStudy());
		educationResponse.setStartingDate(education.getStartingDate());
		educationResponse.setEndDate(education.getEndDate());
		educationResponse.setPercentage(education.getPercentage());
		educationResponse.setPresent(education.isPresent());
		Map<String, String> links = new HashMap<>();
		links.put("User Resume", "/resumes/" + education.getResume().getResumeId());
		educationResponse.setOptions(links);
		return educationResponse;
	}

	@Override
	public ResponseEntity<ResponseStructure<EducationResponse>> insert(EducationRequest educationRequest,
			int resumeId) {
		Optional<Resume> optional = resumeRepo.findById(resumeId);
		if (optional.isPresent()) {
			Resume resume = optional.get();

			// Check if Metriculation or Intermediate already exists in the database
			boolean metriculationOrIntermediateExists = resume.getEducations().stream()
					.anyMatch(education -> education.getDegreeType() == DegreeType.METRICULATION
							|| education.getDegreeType() == DegreeType.INTERMEDIATE);

			// If Metriculation or Intermediate exists, do not allow adding other
			// DegreeTypes
			if (metriculationOrIntermediateExists && (educationRequest.getDegreeType() == DegreeType.METRICULATION
					|| educationRequest.getDegreeType() == DegreeType.INTERMEDIATE)) {
				throw new IllegalStateException("Metriculation or Intermediate already exists in the database.");
			}

			// Create a new education object and convert the request to education
			Education education = new Education();
			education = convertToEducation(educationRequest, education);

			// Add the new education to the list of educations in the resume
			resume.getEducations().add(education);

			// Save the updated resume back to the database
			resumeRepo.save(resume);

			EducationResponse educationResponse=convertToEducationResponse(education);
			ResponseStructure<EducationResponse>responseStructure=new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Education Added to Resume Successfully ");
			responseStructure.setData(educationResponse);
			return new ResponseEntity<ResponseStructure<EducationResponse>>(responseStructure,HttpStatus.CREATED);
			
		} else {
			throw new ResumeNotFoundByIdException("Resume Data Not Found By Given Id");
		}
	}

	

	@Override
	public ResponseEntity<ResponseStructure<EducationResponse>> findByEducationId(int educationId) {
		Optional<Education> optional = educationRepo.findById(educationId);
		if (optional.isPresent()) {
			EducationResponse educationResponse = convertToEducationResponse(optional.get());

			ResponseStructure<EducationResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("Education Details Found");
			responseStructure.setData(educationResponse);

			return new ResponseEntity<ResponseStructure<EducationResponse>>(responseStructure, HttpStatus.FOUND);

		} else {
			throw new EducationNotFoundByIdException("Education Details Not Found By Given Id");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<EducationResponse>>> findEducationByResumeId(int resumeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseStructure<EducationResponse>> updateByEducationId(EducationRequest educationRequest,
			int educationId) {
		Optional<Education> optional = educationRepo.findById(educationId);
		if (optional.isPresent()) {

			Education education = convertToEducation(educationRequest, optional.get());
			Education education2 = educationRepo.save(education);
			EducationResponse educationResponse = convertToEducationResponse(education2);

			ResponseStructure<EducationResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Education Details Deleted Successfully !!");
			responseStructure.setData(educationResponse);

			return new ResponseEntity<ResponseStructure<EducationResponse>>(responseStructure, HttpStatus.OK);

		} else {
			throw new EducationNotFoundByIdException("Education Details Not Found By Given Id");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<EducationResponse>> deleteByEducationId(int educationId) {
		Optional<Education> optional = educationRepo.findById(educationId);
		if (optional.isPresent()) {

			EducationResponse educationResponse = convertToEducationResponse(optional.get());

			educationRepo.delete(optional.get());

			ResponseStructure<EducationResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Education Details Deleted Successfully !!");
			responseStructure.setData(educationResponse);

			return new ResponseEntity<ResponseStructure<EducationResponse>>(responseStructure, HttpStatus.OK);

		} else {
			throw new EducationNotFoundByIdException("Education Details Not Found By Given Id");
		}
	}

}
