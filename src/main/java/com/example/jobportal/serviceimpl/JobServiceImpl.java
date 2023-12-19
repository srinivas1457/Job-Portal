package com.example.jobportal.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.jobportal.entity.Company;
import com.example.jobportal.entity.Job;
import com.example.jobportal.exceptionhandling.CompanyNotFoundByIdException;
import com.example.jobportal.exceptionhandling.JobNotFoundByIdException;
import com.example.jobportal.exceptionhandling.JobNotFoundByTitleException;
import com.example.jobportal.repository.CompanyRepository;
import com.example.jobportal.repository.JobRepository;
import com.example.jobportal.repository.UserRepository;
import com.example.jobportal.requestdto.JobRequest;
import com.example.jobportal.responsedto.JobResponse;
import com.example.jobportal.service.JobService;
import com.example.jobportal.utility.ResponseStructure;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobRepository jobRepo;

	@Autowired
	private CompanyRepository companyRepo;

	@Autowired
	private UserRepository userRepo;

	private Job convertJobRequestTOJob(JobRequest jobRequest) {
		Job job = new Job();
		job.setJobTitle(jobRequest.getJobTitle());
		job.setJobLocation(jobRequest.getJobLocation());
		job.setJobDescription(jobRequest.getJobDescription());
		job.setJobLocation(jobRequest.getJobLocation());
		job.setOpeningDate(null);

		return job;
	}

	private Job convertToJob(JobRequest jobRequest, Job existingJob) {
		existingJob.setJobTitle(jobRequest.getJobTitle());
		existingJob.setJobLocation(jobRequest.getJobLocation());
		existingJob.setJobDescription(jobRequest.getJobDescription());
		existingJob.setJobLocation(jobRequest.getJobLocation());
		existingJob.setOpeningDate(null);
		return existingJob;
	}

	private JobResponse convertJobToJobResponse(Job job) {
		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobId(job.getJobId());
		jobResponse.setJobTitle(job.getJobTitle());
		jobResponse.setJobDescription(job.getJobDescription());
		jobResponse.setJobLocation(null);
		return jobResponse;
	}

	@Override
	public ResponseEntity<ResponseStructure<JobResponse>> addJob(JobRequest jobRequest, int companyId) {
		Optional<Company> compOptional = companyRepo.findById(companyId);
		if (compOptional.isEmpty()) {
			Job job = convertJobRequestTOJob(jobRequest);
			JobResponse jobResponse = convertJobToJobResponse(job);

			ResponseStructure<JobResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("Job data is found");
			responseStructure.setData(jobResponse);

			return new ResponseEntity<ResponseStructure<JobResponse>>(responseStructure, HttpStatus.FOUND);

		} else {
			throw new CompanyNotFoundByIdException("Company not found By Id,Please add company details !!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<JobResponse>> findByJobId(int jobId) {
		Optional<Job> optional = jobRepo.findById(jobId);
		if (optional.isPresent()) {
			Job job = optional.get();
			JobResponse jobResponse = convertJobToJobResponse(job);

			ResponseStructure<JobResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("Job data is found");
			responseStructure.setData(jobResponse);

			return new ResponseEntity<ResponseStructure<JobResponse>>(responseStructure, HttpStatus.FOUND);

		} else {
			throw new JobNotFoundByIdException("Job Id not found");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<JobResponse>>> findByJobTitle(String jobTitle) {
		List<Job> jobList = jobRepo.findByJobTitle(jobTitle);
		if (!jobList.isEmpty()) {
			List<JobResponse> list = new ArrayList<>();
			for (Job job : jobList) {
				JobResponse jobResponse = convertJobToJobResponse(job);

				Map<String, String> o = new HashMap<>();
//					o.put("reviews", "/" + movie.getMovieId() + "/reviews");
//					movieResponse.setOptions(o);

				list.add(jobResponse);
			}
			ResponseStructure<List<JobResponse>> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("Job Records Found");
			responseStructure.setData(list);

			return new ResponseEntity<ResponseStructure<List<JobResponse>>>(responseStructure, HttpStatus.FOUND);

		} else {
			throw new JobNotFoundByTitleException("Job data is not present");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<JobResponse>>> findByLocation(String jobLocation) {
		List<Job> jobList = jobRepo.findByJobLocation(jobLocation);
		if (!jobList.isEmpty()) {
			List<JobResponse> list = new ArrayList<>();
			for (Job job : jobList) {
				JobResponse jobResponse = convertJobToJobResponse(job);

				Map<String, String> o = new HashMap<>();
//					o.put("reviews", "/" + movie.getMovieId() + "/reviews");
//					movieResponse.setOptions(o);

				list.add(jobResponse);
			}
			ResponseStructure<List<JobResponse>> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("Job Records Found");
			responseStructure.setData(list);

			return new ResponseEntity<ResponseStructure<List<JobResponse>>>(responseStructure, HttpStatus.FOUND);

		} else {
			throw new JobNotFoundByTitleException("Job data is not present");
		}

	}

	@Override
	public ResponseEntity<ResponseStructure<JobResponse>> deleteByJobId(int jobId) {
		Optional<Job> optional = jobRepo.findById(jobId);
		if (optional.isPresent()) {
			Job job = optional.get();
			jobRepo.delete(job);
			JobResponse jobResponse = convertJobToJobResponse(job);

			ResponseStructure<JobResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("Job data is found");
			responseStructure.setData(jobResponse);

			return new ResponseEntity<ResponseStructure<JobResponse>>(responseStructure, HttpStatus.FOUND);

		} else {
			throw new JobNotFoundByIdException("Job Id not found");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<JobResponse>> updateById(JobRequest jobRequest, int jobId) {
		Optional<Job> optional = jobRepo.findById(jobId);
		if (optional.isPresent()) {
			Job existingJob = optional.get();

			Job job = convertToJob(jobRequest, existingJob);
			Job job2 = jobRepo.save(job);

			JobResponse jobResponse = convertJobToJobResponse(job2);

			ResponseStructure<JobResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
			responseStructure.setMessage("Job Data Updated Successfully");
			responseStructure.setData(jobResponse);

			return new ResponseEntity<ResponseStructure<JobResponse>>(responseStructure, HttpStatus.ACCEPTED);

		} else {
			throw new JobNotFoundByIdException("Job data is not present");
		}
	}

}
