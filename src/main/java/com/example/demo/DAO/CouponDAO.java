package com.example.demo.DAO;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.enums.CouponType;

@Service
public interface CouponDAO {
	
	
	void createCoupon(Coupon coupon , int companyId) throws CouponExistException , CompanyNotFoundException;
	
	void removeCoupon(int couponId , int companyId) throws CouponNotFoundException ,  CompanyNotFoundException; 
	
	void updateCoupon(Date endDate , double price , int couponId , int companyId ) throws CouponNotFoundException , CompanyNotFoundException;
	
	Coupon getCoupon(int couponId) throws CouponNotFoundException;
	
	ArrayList<Coupon> getAllCoupons() throws CouponsNotFoundException;

	ArrayList<Coupon> getCouponsByType(CouponType type) throws CouponsNotFoundException;
	
	
	

}
