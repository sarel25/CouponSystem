package com.example.demo.facades;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.ExpiredDateException;
import com.example.demo.exceptions.IllegalAmountException;
import com.example.enums.ClientType;
import com.example.enums.CouponType;

import lombok.Getter;
import lombok.ToString;

@ToString
@Component
public class CustomerFacade implements CouponClientFacade {

	// Object's members
	@Getter
	private Customer loggedIn;
	@Autowired
	private CustomerDBDAO custDb;
	@Autowired
	private CouponDBDAO coupDb;

	/***
	 * Login method for Customer
	 */
	@Override
	public CustomerFacade login(String name, String password, ClientType type) {
		// Checking type first
		if (!type.equals(ClientType.CUSTOMER)) {
			return null;
		}
		// Checking name and password

		if (!custDb.login(name, password)) {
			return null;
		}

		// Success - providing facade
		loggedIn = custDb.getCustomerByNameAndPassword(name, password);
		return this;

	}

	/***
	 * Purchasing Coupon
	 * 
	 * @param couponId
	 * @throws CouponNotFoundException
	 * @throws IllegalAmountException
	 * @throws ExpiredDateException
	 * @throws CustomerNotFoundException
	 * @throws CouponExistException
	 */
	public synchronized void purchaseCouopn(int couponId) throws CouponNotFoundException, IllegalAmountException,
			ExpiredDateException, CustomerNotFoundException, CouponExistException {
		// Checking Customer
		if (loggedIn == null) {
			throw new CustomerNotFoundException("Customer does not login please relogin");
		}

		Coupon couponCheck = coupDb.getCoupon(couponId);

		// Checking if exist
		if (couponCheck == null) {
			throw new CouponNotFoundException("Coupon with the ID:" + couponId + " does not exist");
		}

		// Checking if Customer holds it
		Coupon customerCoupon = custDb.getCustomerCoupon(couponId, loggedIn.getId());
		if (customerCoupon != null) {
			throw new CouponExistException("Customer already holds this Coupon.");
		}

		// Checking amount
		if (couponCheck.getAmount() <= 0) {
			throw new IllegalAmountException("Cant purchase Coupon - Amount lower then 0");
		}
		// Checking expired date

		if (couponCheck.getEndDate().before(new Date(System.currentTimeMillis()))) {
			throw new ExpiredDateException("Coupon's end date expired");
		}

		// Success - purchasing Coupon
		custDb.purchaseCoupon(loggedIn.getId(), couponId);
	}

	/**
	 * Getting a single Coupon that Customer holds
	 * @param couponId
	 * @return Coupon
	 * @throws CustomerNotFoundException
	 * @throws CouponNotFoundException
	 */
	public Coupon getPurchasedCouopn(int couponId) throws CustomerNotFoundException, CouponNotFoundException {

		// Checking Customer
		if (loggedIn == null) {
			throw new CustomerNotFoundException("Customer does not login please relogin");
		}
		
		// Checking Coupon
		Coupon coupon = custDb.getCustomerCoupon(couponId, loggedIn.getId());
		if(coupon == null) {
			throw new CouponNotFoundException("There is no purchased Coupon with the ID:" + couponId);
		}
		// Success - providing Coupon
		return coupon;

	}

	/***
	 * Get All purchased Coupons
	 * 
	 * @return ArrayList<Coupon>
	 * @throws CustomerNotFoundException
	 * @throws CouponsNotFoundException
	 */
	public ArrayList<Coupon> getAllPurchasedCoupons() throws CustomerNotFoundException, CouponsNotFoundException {

		// Checking Customer
		if (loggedIn == null) {
			throw new CustomerNotFoundException("Customer does not login please relogin");
		}

		ArrayList<Coupon> customerCoupons = custDb.getCoupons(loggedIn.getId());
		// Checking array list
		if (customerCoupons == null) {
			throw new CouponsNotFoundException("Coupons does not exist yet");
		}
		// Success providing Coupons
		return customerCoupons;
	}

	/***
	 * Get Coupons by type
	 * 
	 * @param type
	 * @return ArrayList<Coupon>
	 * @throws CustomerNotFoundException
	 * @throws CouponsNotFoundException
	 */
	public ArrayList<Coupon> getCouponsByType(CouponType type)
			throws CustomerNotFoundException, CouponsNotFoundException {
		// Checking Customer
		if (loggedIn == null) {
			throw new CustomerNotFoundException("Customer does not login please relogin");
		}

		ArrayList<Coupon> customerCoupons = custDb.getCouponsByType(loggedIn.getId(), type);
		// Checking array list
		if (customerCoupons == null) {
			throw new CouponsNotFoundException("Coupons does not exist yet");
		}
		// Success providing Coupons
		return customerCoupons;
	}

	/***
	 * Get Coupons by price
	 * 
	 * @param type
	 * @return ArrayList<Coupon>
	 * @throws CustomerNotFoundException
	 * @throws CouponsNotFoundException
	 */
	public ArrayList<Coupon> getCouponsByPrice(double price)
			throws CustomerNotFoundException, CouponsNotFoundException {
		// Checking Customer
		if (loggedIn == null) {
			throw new CustomerNotFoundException("Customer does not login please relogin");
		}

		ArrayList<Coupon> customerCoupons = custDb.getCouponsByPrice(loggedIn.getId(), price);
		// Checking array list
		if (customerCoupons == null) {
			throw new CouponsNotFoundException("Coupons does not exist yet");
		}
		// Success providing Coupons
		return customerCoupons;
	}
}
