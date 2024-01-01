package com.example.jobportal.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.jobportal.repository.ResumeRepository;
import com.example.jobportal.repository.SocialProfileRepository;
import com.example.jobportal.requestdto.SocialProfileRequest;
import com.example.jobportal.responsedto.SocialProfileResponse;
import com.example.jobportal.service.SocialProfileService;
import com.example.jobportal.utility.ResponseStructure;

@Service
public class SocialProfileServiceImpl implements SocialProfileService {
	
	@Autowired
	private SocialProfileRepository socialProfileRepo;
	
	@Autowired
	private ResumeRepository resumeRepo;

	@Override
	public ResponseEntity<ResponseStructure<SocialProfileResponse>> insert(SocialProfileRequest socialProfileRequest,
			int resumeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseStructure<SocialProfileResponse>> findById(int socialProfileId) {
		return null;
	}

	@Override
	public ResponseEntity<ResponseStructure<List<SocialProfileResponse>>> findByResumeId(int resumeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseStructure<SocialProfileResponse>> updateById(int socialProfileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseStructure<SocialProfileResponse>> deleteById(int socialProfileId) {
		// TODO Auto-generated method stub
		return null;
	}

}
