/**
 * 
 */
package com.example.demo.DBDAO;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.CouponRepo;
import com.example.demo.CustomerRepo;
import com.example.demo.DAO.CustomerDAO;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.demo.exceptions.CustomerExistException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.CustomersNotFoundException;
import com.example.enums.CouponType;

/**
 * Customer DBDAO connecting to DB
 * 
 * @author grimbergs
 *
 */
@Service
public class CustomerDBDAO implements CustomerDAO {

	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private CouponRepo coupRepo;

	/***
	 * Creating new Customer
	 */
	@Override
	public void createCustomer(Customer customer) throws CustomerExistException {
		custRepo.save(customer);

	}

	/**
	 * Removing Customer by ID
	 */
	@Override
	public void removeCustomer(int customerId) throws CustomerNotFoundException {
		custRepo.delete(customerId);
	}
	
	

	/**
	 * Updating Customer set only password
	 */
	@Override
	public void updateCustomer(String password, int customerId) throws CustomerNotFoundException {
		custRepo.updateCustomer(password, customerId);

	}
	
	/***
	 * Purchasing Coupon by Customer
	 * @param customerId
	 * @param couponId
	 */
	public void purchaseCoupon(int customerId , int couponId)throws CustomerNotFoundException , CouponNotFoundException
	{
		// Purchasing Coupon
		custRepo.purchaseCoupon(customerId, couponId);
		// Updating amount
		custRepo.updatingAmountForPurchase(couponId);
	}

	/**
	 * Get Customer by Id
	 */
	@Override
	public Customer getCustomer(int customerId) throws CustomerNotFoundException {
		return custRepo.findOne(customerId);
	}
	
	/***
	 * Get Customer Coupon
	 * @param couponId
	 * @param customerId
	 * @return Coupon
	 */
	public Coupon getCustomerCoupon(int couponId , int customerId)
	{
		return coupRepo.findByidAndCustomersId(couponId, customerId);
	}

	/**
	 * Get Customer By Name
	 * 
	 * @param custName
	 * @return customer
	 */
	public Customer getCustomerByName(String custName) {
		return custRepo.findCustomerByCustName(custName); 

	}

	/**
	 * Get Customer By Name & Password
	 * 
	 * @param custName
	 * @return customer
	 */
	public Customer getCustomerByNameAndPassword(String custName , String password) {
		return custRepo.findCustomerBycustNameAndPassword(custName, password);
		
	}

	/**
	 * Get all Customers
	 * 
	 * @return customers
	 */
	@Override
	public ArrayList<Customer> getAllCustoemr() throws CustomersNotFoundException {
		ArrayList<Customer> allCustomers = (ArrayList<Customer>) custRepo.findAll();
		return allCustomers;
	}

	/***
	 * Get Customer's Coupons
	 */
	@Override
	public ArrayList<Coupon> getCoupons(int customerId) throws CouponsNotFoundException, CustomerNotFoundException {
		ArrayList<Coupon> customerCoupon = coupRepo.getCustomerCoupons(customerId);
		return customerCoupon;
	}
	
	/***
	 * Get Customer Coupons by type
	 * @param customerId
	 * @param type
	 * @return ArrayList<Coupon>
	 * @throws CustomerNotFoundException
	 * @throws CouponsNotFoundException
	 */
	public ArrayList<Coupon> getCouponsByType(int customerId , CouponType type)throws CustomerNotFoundException , CouponsNotFoundException
	{
		return coupRepo.getCustomerCouponsByType(type, customerId);
	}
	
	/***
	 * Get Customer Coupons by price
	 * @param customerId
	 * @param price
	 * @return ArrayList<Coupon>
	 * @throws CustomerNotFoundException
	 * @throws CouponsNotFoundException
	 */
	public ArrayList<Coupon> getCouponsByPrice(int customerId , double price)throws CustomerNotFoundException , CouponsNotFoundException
	{
		return coupRepo.getCustomerCouponsByPrice(price, customerId);
	}

	/** login method
	 * 
	 */
	@Override
	public boolean login(String custName, String password) {
		Customer check = custRepo.findCustomerBycustNameAndPassword(custName, password);

		if (check == null) {
			return false;
		}
		return true;
	}

}
