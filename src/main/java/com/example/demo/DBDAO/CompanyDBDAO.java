package com.example.demo.DBDAO;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.CompanyRepo;
import com.example.demo.CouponRepo;
import com.example.demo.DAO.CompanyDAO;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CompaniesNotFoundException;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.enums.CouponType;

/**
 * 
 */

/**
 * Company DBDAO connecting to DB
 * 
 * @author grimbergs
 *
 */
@Service
public class CompanyDBDAO implements CompanyDAO {

	// Object's Attributes
	@Autowired
	private CompanyRepo compRepo;
	
	@Autowired
	private CouponRepo coupRepo;

	/***
	 * Creating new Company on DB
	 */
	@Override
	public void createCompany(Company company) throws CompanyExistException {
		compRepo.save(company);

	}

	/***
	 * Removing Company by the ID
	 */
	@Override
	public void removeCompany(int companyId) throws CompanyNotFoundException {
		compRepo.delete(companyId);

	}

	/***
	 * Updating Company set only password & email values
	 */
	@Override
	public void updateCompany(String password, String email, int companyId) throws CompanyNotFoundException {
		compRepo.updateCompany(password, email, companyId);
	}

	/***
	 * Getting Company by id
	 */
	@Override
	public Company getCompany(int companyId) throws CompanyNotFoundException {
		return compRepo.findOne(companyId);
	}
	
	/***
	 * Getting Company by its name
	 * @param compName
	 * @return Company
	 */
	public Company getCompanyByName(String compName)
	{
		return compRepo.findCompanyByCompName(compName);
	}
	
	/***
	 * Getting Company by its name and password
	 * @param compName
	 * @return Company
	 */
	public Company getCompanyByNameAndPassword(String compName , String password)
	{
		return compRepo.findCompanyByCompNameAndPassword(compName, password);
	}


	/***
	 * Get All Companies
	 */
	@Override
	public ArrayList<Company> getAllCompanies() throws CompaniesNotFoundException {
		ArrayList<Company> allCompanies = (ArrayList<Company>) compRepo.findAll();
		return 	allCompanies;
	}

	/***
	 * Get Company's Coupons
	 */
	@Override
	public ArrayList<Coupon> getCoupons(int companyId) throws CouponsNotFoundException, CompanyNotFoundException {
		ArrayList<Coupon> companyCoupon = coupRepo.getCompanyCoupons(companyId);
		return companyCoupon;
	}
	
	/***
	 * Get Company Coupons by type
	 * @param companyId
	 * @param type
	 * @return ArrayList<Coupon>
	 * @throws CompanyNotFoundException
	 * @throws CouponsNotFoundException
	 */
	public ArrayList<Coupon> getCouponsByType(int companyId , CouponType type)throws CompanyNotFoundException , CouponsNotFoundException
	{
		return coupRepo.findByCompanyIdAndType(companyId, type);
	}

	/***
	 * Login method
	 */
	@Override
	public boolean login(String compName, String password) {
		Company check = compRepo.findCompanyByCompNameAndPassword(compName, password);
		if(check == null)
		{
			return false;
		}
		return true;
	}

}
