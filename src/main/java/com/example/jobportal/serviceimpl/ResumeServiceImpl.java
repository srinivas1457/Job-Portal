package com.example.jobportal.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.jobportal.entity.Company;
import com.example.jobportal.entity.Resume;
import com.example.jobportal.entity.User;
import com.example.jobportal.enums.UserRole;
import com.example.jobportal.exceptionhandling.CompanyNotFoundException;
import com.example.jobportal.exceptionhandling.ResumeNotFoundByIdException;
import com.example.jobportal.exceptionhandling.ResumeNotFoundException;
import com.example.jobportal.exceptionhandling.UserNotFoundByIdException;
import com.example.jobportal.exceptionhandling.UserNotFoundByUserRole;
import com.example.jobportal.repository.ResumeRepository;
import com.example.jobportal.repository.UserRepository;
import com.example.jobportal.requestdto.ResumeRequest;
import com.example.jobportal.responsedto.CompanyResponse;
import com.example.jobportal.responsedto.ResumeResponse;
import com.example.jobportal.service.ResumeService;
import com.example.jobportal.utility.ResponseStructure;

@Service
public class ResumeServiceImpl implements ResumeService {

	@Autowired
	private ResumeRepository resumeRepo;

	@Autowired
	private UserRepository userRepo;

	private Resume convertResumeReqToResume(ResumeRequest resumeRequest, Resume resume) {
		resume.setObjective(resumeRequest.getObjective());
		return resume;
	}

	private ResumeResponse convertResumeToResumeResponse(Resume resume) {
		ResumeResponse resumeResponse = new ResumeResponse();
		resumeResponse.setResumeId(resume.getResumeId());
		resumeResponse.setObjective(resume.getObjective());
		return resumeResponse;

	}

	@Override
	public ResponseEntity<ResponseStructure<ResumeResponse>> addResume(ResumeRequest resumeRequest, int userId) {
		Optional<User> optional = userRepo.findById(userId);
		if (optional.isPresent()) {
			User user = optional.get();
			if (user.getUserRole() == UserRole.APPLICANT) {
				Resume resume = convertResumeReqToResume(resumeRequest, new Resume());
				Resume resume2 = resumeRepo.save(resume);

				ResumeResponse resumeResponse = convertResumeToResumeResponse(resume2);

				ResponseStructure<ResumeResponse> responseStructure = new ResponseStructure<>();
				responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
				responseStructure.setData(resumeResponse);
				responseStructure.setMessage("Resume Data Added Successfully!!");

				return new ResponseEntity<ResponseStructure<ResumeResponse>>(responseStructure, HttpStatus.ACCEPTED);

			} else {
				throw new UserNotFoundByUserRole("User role is not present");
			}

		} else {
			throw new UserNotFoundByIdException("user with the given  Id not present");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<ResumeResponse>> findByResumeId(int resumeId) {

		Optional<Resume> optional = resumeRepo.findById(resumeId);

		if (optional.isPresent()) {
			ResumeResponse resumeResponse = convertResumeToResumeResponse(optional.get());

			ResponseStructure<ResumeResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("user found successfully");
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setData(resumeResponse);

			return new ResponseEntity<ResponseStructure<ResumeResponse>>(responseStructure, HttpStatus.FOUND);

		}

		else
			throw new ResumeNotFoundByIdException("Resume with the given  Id not present");

	}

	@Override
	public ResponseEntity<ResponseStructure<ResumeResponse>> updateByResumeId(ResumeRequest resumeRequest,
			int resumeId) {
		Optional<Resume> optional = resumeRepo.findById(resumeId);

		if (optional.isPresent()) {
			ResumeResponse resumeResponse = convertResumeToResumeResponse(optional.get());

			ResponseStructure<ResumeResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("user found successfully");
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setData(resumeResponse);

			return new ResponseEntity<ResponseStructure<ResumeResponse>>(responseStructure, HttpStatus.FOUND);

		}

		else
			throw new ResumeNotFoundByIdException("Resume with the given  Id not present");
	}

	@Override
	public ResponseEntity<ResponseStructure<ResumeResponse>> deleteByResumeId(int resumeId) {
		Optional<Resume> optional = resumeRepo.findById(resumeId);

		if (optional.isPresent()) {
			resumeRepo.deleteById(resumeId);
			ResumeResponse resumeResponse = convertResumeToResumeResponse(optional.get());

			ResponseStructure<ResumeResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("Resume deleted successfully");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setData(resumeResponse);

			return new ResponseEntity<ResponseStructure<ResumeResponse>>(responseStructure, HttpStatus.OK);

		}

		else
			throw new ResumeNotFoundByIdException("Resume with the given  Id not present");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<ResumeResponse>>> findAll() {
		List<Resume> resumes = resumeRepo.findAll();
		if (!resumes.isEmpty()) {
			List<ResumeResponse> list = new ArrayList<>();
			for (Resume  resume : resumes) {
				ResumeResponse  resumeResponse=convertResumeToResumeResponse(resume);
				list.add(resumeResponse);
			}

			ResponseStructure<List<ResumeResponse>> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Resume Records Found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<ResumeResponse>>>(structure, HttpStatus.FOUND);
		} else {
			throw new ResumeNotFoundException("Resume Data Not Present!!");
		}

	}

	@Override
	public ResponseEntity<ResponseStructure<List<ResumeResponse>>> findResumeBySkillName(String skillName) {
		// TODO Auto-generated method stub
		return null;
	}

}
