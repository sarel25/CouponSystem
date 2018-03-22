/**
 * 
 */
package com.example.demo.DBDAO;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.CompanyRepo;
import com.example.demo.CouponRepo;
import com.example.demo.DAO.CouponDAO;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.enums.CouponType;

/**
 * @author grimbergs
 *
 */
@Service
public class CouponDBDAO implements CouponDAO {
	
	@Autowired
	private CouponRepo coupRepo ;
	
	@Autowired
	private CompanyRepo compRepo;
	
	/**
	 * Create new Coupon
	 */
	@Override
	public void createCoupon(Coupon coupon, int companyId) throws CouponExistException, CompanyNotFoundException {
	
		Company father = compRepo.findOne(companyId);
		
		coupon.setCompany(father);
		
		coupRepo.save(coupon);
		
		
		
	}

	/**
	 * Remove Coupon
	 */
	@Override
	public void removeCoupon(int couponId, int companyId) throws CouponNotFoundException, CompanyNotFoundException {
		coupRepo.removeCoupon(companyId, couponId);
	}
	
	/***
	 * Removing all Company Coupons
	 * @param companyId
	 */
	public void removeCompanyCoupons(int companyId)
	{
		coupRepo.removeAllCompanyCoupons(companyId);
	}
	
	
	
	/**
	 * Update Coupon
	 */
	@Override
	public void updateCoupon(Date endDate, double price, int couponId, int companyId)
			throws CouponNotFoundException, CompanyNotFoundException {
		coupRepo.updateCoupon(endDate, price, couponId, companyId);
	}

	/**
	 * Get Coupon
	 */
	@Override
	public Coupon getCoupon(int couponId) throws CouponNotFoundException {
		return coupRepo.findOne(couponId);
		
	}
	/***
	 * Get Coupon by title
	 * @param title
	 * @return Coupon
	 */
	public Coupon getCouponByTitle(String title) {
		return coupRepo.findCouponByTitle(title);
	}
	
	/***
	 * Get Company's Coupon
	 * @param coupId
	 * @param compId
	 * @return Company's Coupon
	 * @throws CouponNotFoundException
	 * @throws CompanyNotFoundException
	 */
	public Coupon getCompanyCoupon(int coupId , int compId) throws CouponNotFoundException , CompanyNotFoundException
	{
		return coupRepo.getCompanyCoupon(coupId, compId);
	}

	/***
	 * Get All Coupons
	 */
	@Override
	public ArrayList<Coupon> getAllCoupons() throws CouponsNotFoundException {
		ArrayList<Coupon> allCoupons = (ArrayList<Coupon>) coupRepo.findAll();
		return allCoupons;
	}

	/***
	 * Get all Coupons by type
	 */
	@Override
	public ArrayList<Coupon> getCouponsByType(CouponType type) throws CouponsNotFoundException {
		ArrayList<Coupon> byType = coupRepo.findCouponByType(type);
		return byType;
	}
	
	/***
	 * Get All Company's Coupon by type
	 * @param compId
	 * @param type
	 * @return
	 * @throws CouponsNotFoundException
	 * @throws CompanyNotFoundException
	 */
	public ArrayList<Coupon> getCompanyCouponByType(int compId , CouponType type)throws CouponsNotFoundException  , CompanyNotFoundException
	{
		ArrayList<Coupon> byType = coupRepo.getCompanyCouponByType(compId, type);
		return byType;
	}

}
