package com.example.demo.DAO;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CompaniesNotFoundException;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;

@Service   
public interface CompanyDAO {

	void createCompany(Company company) throws CompanyExistException;
	
	void removeCompany(int companyId) throws CompanyNotFoundException;
	
	void updateCompany(String password , String email , int companyId ) throws CompanyNotFoundException;
	
	Company getCompany(int companyId) throws CompanyNotFoundException;
	
	ArrayList<Company> getAllCompanies() throws CompaniesNotFoundException;
	
	ArrayList<Coupon> getCoupons(int companyId) throws CouponsNotFoundException , CompanyNotFoundException ;
	
	boolean login(String compName , String password );
	
	
	
	

	

}

