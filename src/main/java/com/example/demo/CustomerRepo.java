package com.example.demo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Customer;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, Integer> {
	
	/***
	 * Creating Customer Coupon
	 * @param couponId
	 * @param couponId
	 */
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO customer_coupons (customers_id , coupons_id) VALUES(:customerId , :couponId)" , nativeQuery = true)
	void purchaseCoupon(@Param("customerId") int customerId , @Param("couponId") int couponId);
	
	/***
	 * Updating amount -1
	 * @param couponId
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Coupon coup SET coup.amount=amount-1 WHERE coup.id =:couponId")
	void updatingAmountForPurchase(@Param("couponId") int couponId);
	
	/***
	 * Updating Customer set only password
	 * @param password
	 * @param customerId
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Customer cust SET cust.password = :password WHERE cust.id = :customerId")
	void updateCustomer(@Param("password") String password ,@Param("customerId")  int customerId);
	
	/**
	 * Find Customer By Name
	 * @param custName
	 * @return
	 */
	Customer findCustomerByCustName(String custName);
	
	
	
	/**
	 * Find Customer By Name and Password
	 * @param custName
	 * @param password
	 * @return
	 */
	Customer findCustomerBycustNameAndPassword(String custName , String password);
	
	
	
	
	

}
