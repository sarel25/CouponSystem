package com.example.demo.DAO;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.demo.exceptions.CustomerExistException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.CustomersNotFoundException;

@Service 
public interface CustomerDAO {
	
	
	void createCustomer(Customer customer) throws CustomerExistException;
	
	void removeCustomer(int customerId) throws CustomerNotFoundException; 
	
	void updateCustomer(String password , int customerId) throws CustomerNotFoundException;
	
	Customer getCustomer(int customerId) throws CustomerNotFoundException; 
	
	ArrayList<Customer> getAllCustoemr() throws CustomersNotFoundException; 
	
	ArrayList<Coupon> getCoupons(int customerId) throws CouponsNotFoundException , CustomerNotFoundException ;
	
	boolean login(String custName , String password);
	
	
	
	
	
	

}
