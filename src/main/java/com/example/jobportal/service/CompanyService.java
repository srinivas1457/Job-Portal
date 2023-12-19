package com.example.jobportal.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.jobportal.enums.BusinessType;
import com.example.jobportal.requestdto.CompanyRequest;
import com.example.jobportal.responsedto.CompanyResponse;
import com.example.jobportal.utility.ResponseStructure;

public interface CompanyService {
	public ResponseEntity<ResponseStructure<CompanyResponse>> insertCompany(CompanyRequest companyRequest, BusinessType bussType,int userId);
	public ResponseEntity<ResponseStructure<CompanyResponse>> findCompanyById(int companyId);
	public ResponseEntity<ResponseStructure<List<CompanyResponse>>> findCompanyByName(String companyName);
	public ResponseEntity<ResponseStructure<List<CompanyResponse>>> findByBusinessType(String businessType);
	public ResponseEntity<ResponseStructure<CompanyResponse>> updateCompanyById(CompanyRequest companyRequest,int companyId);
	public ResponseEntity<ResponseStructure<CompanyResponse>> deleteBycompanyId(int companyId);
	
	
	

}
