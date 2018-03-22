package com.example.demo.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.couponsystem.CouponSystem;
import com.example.demo.entities.Company;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.facades.AdminFacade;
import com.example.enums.ClientType;

/***
 * Web Service for Admin
 * @author grimbergs
 *
 */
@RestController // -> WebService 
@RequestMapping("Admin") // -? the URL path
public class AdminWebService {
	
	// Core
	@Autowired
	private AdminFacade af;
	@Autowired
	private CouponSystem cs;
	
	/***
	 * Fake Login for testing
	 * @return AdminFacade
	 */
	private AdminFacade facade()
	{
		return (AdminFacade) cs.login("admin", "1234", ClientType.ADMIN);
	}
	
	/****
	 * Creating new Company
	 * @param company
	 * @return Response entity
	 */
	@RequestMapping(value = "/createCompany" , method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity createCompany(@RequestBody Company company)
	{
		// Fake login
		af = facade();
		
		try {
			
			af.createCompany(company);
			// Success
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body("Company " + company.getCompName() + " was created successfully.");
		} catch (CompanyExistException e) {
			// Fail
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		}
	}
	
	/***
	 * Get Company by id
	 * @param id
	 * @return Response Entity
	 */
	@RequestMapping(value = "getCompany/{id}" , method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getCompany(@PathVariable("id") int id)
	{
		// Fake login
		af = facade();
		
		try {
			Company company = af.getCompany(id);
			// Suuccess 
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(company);
		} catch (CompanyNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		}
	}
	
}
