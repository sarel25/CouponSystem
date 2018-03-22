package com.example.demo.facades;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.enums.ClientType;
import com.example.enums.CouponType;

import lombok.Getter;
import lombok.ToString;
@ToString
@Component
public class CompanyFacade implements CouponClientFacade {

	// Object's members
	@Getter
	private Company loggedIn;

	@Autowired
	private CompanyDBDAO compdb;

	@Autowired
	private CouponDBDAO coupdb;

	/****
	 * Login method for Company
	 */
	@Override
	public CompanyFacade login(String name, String password, ClientType type) {

		// Checking type
		if (!type.equals(ClientType.COMPANY)) {
			return null;
		}

		// Checking name & password

		if (!compdb.login(name, password)) {
			return null;
		}

		loggedIn = compdb.getCompanyByNameAndPassword(name, password);
		return this;

	}

	/****
	 * Creating new Coupon
	 * 
	 * @param coupon
	 * @param companyId
	 * @throws CouponExistException
	 * @throws CompanyNotFoundException
	 */
	public synchronized void createCoupon(Coupon coupon) throws CouponExistException, CompanyNotFoundException {

		// Checking if Coupon exist
		Coupon couponCheck = coupdb.getCouponByTitle(coupon.getTitle());
		if (couponCheck != null) {
			throw new CouponExistException("Coupon " + coupon.getTitle() + " allready exist");
		}

		if (loggedIn == null) {
			throw new CompanyNotFoundException("Company with the ID: " + loggedIn.getId() + " doesn't exist");
		}
		// Success - creating Coupon
		coupdb.createCoupon(coupon, loggedIn.getId());
	}

	/***
	 * Removing Company's Coupon
	 * 
	 * @param couponId
	 * @throws CompanyNotFoundException
	 * @throws CouponNotFoundException
	 */
	public void removeCoupon(int couponId) throws CompanyNotFoundException, CouponNotFoundException {

		// Coupon to remove ( must be Company's Coupon)
		Coupon couponCheck = coupdb.getCompanyCoupon(couponId, loggedIn.getId());

		if (loggedIn == null) {
			throw new CompanyNotFoundException("Company doesn't login please relogin");
		}

		if (couponCheck == null) {
			throw new CouponNotFoundException("Coupon with the ID: " + couponId + " does not exist");
		}

		// Success - removing Coupon
		coupdb.removeCoupon(couponId, loggedIn.getId());

	}

	/***
	 * Update Company's Coupon
	 * 
	 * @param endDate
	 * @param price
	 * @param couponId
	 * @throws CompanyNotFoundException
	 * @throws CouponNotFoundException
	 */
	public void updateCoupon(Date endDate, double price, int couponId)
			throws CompanyNotFoundException, CouponNotFoundException {

		// Coupon to update ( must be Company's Coupon)
		Coupon couponCheck = coupdb.getCompanyCoupon(couponId, loggedIn.getId());

		if (loggedIn == null) {
			throw new CompanyNotFoundException("Company doesn't login please relogin");
		}

		if (couponCheck == null) {
			throw new CouponNotFoundException("Coupon with the ID: " + couponId + " does not exist");
		}

		// Success - updating Coupon
		coupdb.updateCoupon(endDate, price, couponId, loggedIn.getId());
	}

	/***
	 * Get Company Coupon
	 * 
	 * @param couponId
	 * @return Coupon
	 * @throws CompanyNotFoundException
	 * @throws CouponNotFoundException
	 */
	public Coupon getCompanyCoupon(int couponId) throws CompanyNotFoundException, CouponNotFoundException {

		// Coupon to update ( must be Company's Coupon)
		Coupon couponCheck = coupdb.getCompanyCoupon(couponId, loggedIn.getId());

		if (loggedIn == null) {
			throw new CompanyNotFoundException("Company doesn't login please relogin");
		}

		if (couponCheck == null) {
			throw new CouponNotFoundException("Coupon with the ID: " + couponId + " does not exist");
		}

		// Success - providing Coupon
		return couponCheck;
	}

	/***
	 * Getting Company's Coupons
	 * 
	 * @return
	 * @throws CouponsNotFoundException
	 * @throws CompanyNotFoundException
	 */
	public ArrayList<Coupon> getCompanyCoupons() throws CouponsNotFoundException, CompanyNotFoundException {
		// Company Coupons
		ArrayList<Coupon> companyCoupons = compdb.getCoupons(loggedIn.getId());

		// Checking Company
		if (loggedIn == null) {
			throw new CompanyNotFoundException("Company doesn't login please relogin");
		}
		// Checking Array List of Coupons
		if (companyCoupons == null) {
			throw new CouponsNotFoundException("There is no Coupons yet");
		}
		// Success - providing Company Coupons
		return companyCoupons;
	}

	/***
	 * Get Company Coupons by type
	 * @param type
	 * @return ArrayList<Coupon>
	 * @throws CouponsNotFoundException
	 * @throws CompanyNotFoundException
	 */
	public ArrayList<Coupon> getCompanyCouponsByType(CouponType type) throws CouponsNotFoundException, CompanyNotFoundException {
		// Company Coupons
		ArrayList<Coupon> companyCoupons = compdb.getCouponsByType(loggedIn.getId(), type);

		// Checking Company
		if (loggedIn == null) {
			throw new CompanyNotFoundException("Company doesn't login please relogin");
		}
		// Checking Array List of Coupons
		if (companyCoupons == null) {
			throw new CouponsNotFoundException("There is no Coupons yet");
		}
		
		// Success - providing Coupons by type
		return companyCoupons;
	}

}
