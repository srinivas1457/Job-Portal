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
import com.example.jobportal.entity.User;
import com.example.jobportal.enums.BusinessType;
import com.example.jobportal.enums.UserRole;
import com.example.jobportal.exceptionhandling.CompanyNotFoundByIdException;
import com.example.jobportal.exceptionhandling.CompanyNotFoundByNameException;
import com.example.jobportal.exceptionhandling.CompanyNotFoundException;
import com.example.jobportal.exceptionhandling.IllegalAccessException;
import com.example.jobportal.exceptionhandling.UserNotFoundException;
import com.example.jobportal.repository.CompanyRepository;
import com.example.jobportal.repository.UserRepository;
import com.example.jobportal.requestdto.CompanyRequest;
import com.example.jobportal.responsedto.CompanyResponse;
import com.example.jobportal.service.CompanyService;
import com.example.jobportal.utility.ResponseStructure;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CompanyRepository companyRepo;

	private Company convertToCompany(CompanyRequest compRq, Company comp) {
		comp.setBusinessType(compRq.getBusinessType());
		comp.setCompanyName(compRq.getCompanyName());
		comp.setContactEmail(compRq.getContactEmail());
		comp.setContactPhno(compRq.getContactPhno());
		comp.setDescription(compRq.getDescription());
		comp.setFoundedDate(compRq.getFoundedDate());
		comp.setWebsite(compRq.getWebsite());

		return comp;
	}

	private CompanyResponse convertToCompResponse(Company comp) {
		CompanyResponse respDto = new CompanyResponse();
		respDto.setBusinessType(comp.getBusinessType());
		respDto.setCompanyId(comp.getCompanyId());
		respDto.setCompanyName(comp.getCompanyName());
		respDto.setContactEmail(comp.getContactEmail());
		respDto.setContactPhno(comp.getContactPhno());
		respDto.setDescription(comp.getDescription());
		respDto.setFoundedDate(comp.getFoundedDate());
		respDto.setWebsite(comp.getWebsite());
		return respDto;

	}

	public ResponseEntity<ResponseStructure<CompanyResponse>> insertCompany(CompanyRequest companyRequest,
			BusinessType bussType, int userId) {
		Optional<User> optUser = userRepo.findById(userId);

		if (optUser.isPresent()) {
			User user = optUser.get();
			if (user.getUserRole() == UserRole.EMPLOYER) {

				Company company = convertToCompany(companyRequest, new Company());
				company.setBusinessType(bussType);

				Company company2 = companyRepo.save(company);

				CompanyResponse companyResponse = convertToCompResponse(company2);

				ResponseStructure<CompanyResponse> respStruc = new ResponseStructure<>();
				respStruc.setStatusCode(HttpStatus.CREATED.value());
				respStruc.setMessage(" Company data saved successfully");
				respStruc.setData(companyResponse);

				return new ResponseEntity<ResponseStructure<CompanyResponse>>(respStruc, HttpStatus.CREATED);
			}

			else
				throw new IllegalAccessException(" This user not Autharised to add Companies");

		} else
			throw new UserNotFoundException("user with mentioned Id , not found");
	}

	@Override
	public ResponseEntity<ResponseStructure<CompanyResponse>> findCompanyById(int companyId) {
		Optional<Company> optional = companyRepo.findById(companyId);
		if (!optional.isEmpty()) {
			Company company = optional.get();

			CompanyResponse companyResponse = convertToCompResponse(company);

			ResponseStructure<CompanyResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("Company data is found");
			responseStructure.setData(companyResponse);

			return new ResponseEntity<ResponseStructure<CompanyResponse>>(responseStructure, HttpStatus.FOUND);

		} else {
			throw new CompanyNotFoundByIdException("Company data is not present");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<CompanyResponse>>> findCompanyByName(String companyName) {
		List<Company> companyList = companyRepo.findByName(companyName);
		if (!companyList.isEmpty()) {
			List<CompanyResponse> list = new ArrayList<>();
			for (Company company : companyList) {

				CompanyResponse companyResponse = convertToCompResponse(company);
				Map<String, String> o = new HashMap<>();
//					o.put("reviews", "/" + movie.getMovieId() + "/reviews");
//					movieResponse.setOptions(o);

				list.add(companyResponse);
			}
			ResponseStructure<List<CompanyResponse>> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("Movie Records Found");
			responseStructure.setData(list);

			return new ResponseEntity<ResponseStructure<List<CompanyResponse>>>(responseStructure, HttpStatus.FOUND);

		} else {
			throw new CompanyNotFoundByNameException("Company data is not present");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<CompanyResponse>>> findByBusinessType(String businessType) {
		BusinessType businessType2 = BusinessType.valueOf(businessType);
		List<Company> companies = companyRepo.findCompanyByBusinessType(businessType2);
		if (!companies.isEmpty()) {
			List<CompanyResponse> list = new ArrayList<>();
			for (Company company : companies) {

				CompanyResponse companyResponse = convertToCompResponse(company);
				Map<String, String> o = new HashMap<>();
//				o.put("reviews", "/" + movie.getMovieId() + "/reviews");
//				movieResponse.setOptions(o);

				list.add(companyResponse);
			}

			ResponseStructure<List<CompanyResponse>> structure = new ResponseStructure<>();
			structure.setStatusCode(HttpStatus.FOUND.value());
			structure.setMessage("Movie Records Found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<CompanyResponse>>>(structure, HttpStatus.FOUND);
		} else {
			throw new CompanyNotFoundException("Company data not found based on :" + businessType2);
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<CompanyResponse>> updateCompanyById(CompanyRequest companyRequest,
			int companyId) {
		Optional<Company> optional = companyRepo.findById(companyId);
		if (optional.isPresent()) {
			Company existingCompany = optional.get();

			Company company = convertToCompany(companyRequest, existingCompany);
			Company company2 = companyRepo.save(company);

			CompanyResponse response = convertToCompResponse(company2);

			ResponseStructure<CompanyResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
			responseStructure.setMessage("Company Data Updated Successfully");
			responseStructure.setData(response);

			return new ResponseEntity<ResponseStructure<CompanyResponse>>(responseStructure, HttpStatus.ACCEPTED);

		} else {
			throw new CompanyNotFoundByIdException("Company data is not present");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<CompanyResponse>> deleteBycompanyId(int companyId) {
		Optional<Company> optional = companyRepo.findById(companyId);
		if (!optional.isEmpty()) {
			Company company = optional.get();
			companyRepo.delete(company);
			CompanyResponse companyResponse = convertToCompResponse(company);

			ResponseStructure<CompanyResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			responseStructure.setMessage("Company data is found");
			responseStructure.setData(companyResponse);

			return new ResponseEntity<ResponseStructure<CompanyResponse>>(responseStructure, HttpStatus.FOUND);

		} else {
			throw new CompanyNotFoundByIdException("Company data is not present");
		}
	}

}
