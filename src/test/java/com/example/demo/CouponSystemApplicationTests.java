package com.example.demo;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.validator.constraints.EAN.Type;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.couponsystem.CouponSystem;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CompaniesNotFoundException;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponsNotFoundException;
import com.example.demo.exceptions.CustomerExistException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.CustomersNotFoundException;
import com.example.demo.exceptions.ExpiredDateException;
import com.example.demo.exceptions.IllegalAmountException;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.facades.CustomerFacade;
import com.example.enums.ClientType;
import com.example.enums.CouponType;

import sidekicks.DateMaker;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponSystemApplicationTests {

	// Attributes
	// Coupon System
	@Autowired
	private CouponSystem cs;
	// Facades
	// Admin Facade
	@Autowired
	private AdminFacade adminFacade;
	// Company Facade
	@Autowired
	private CompanyFacade companyFacade;

	// Customer Facade
	@Autowired
	private CustomerFacade customerFacade;

	@AfterClass
	public static void afterEverything() {
		System.out.println("######################");
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void CouponSystemTest() {

		// !!!!!!!!!!-------ADMIN----------!!!!!!!!!!!

		// Login as admin
		adminFacade = (AdminFacade) cs.login("admin", "1234", ClientType.ADMIN);

		// Assert AdminFacade not null
		Assert.notNull(adminFacade, "Element is null");

		// Creating a Company
		try {
			adminFacade.createCompany(new Company("Aroma", "1234", "aroma@gmail.com"));
			adminFacade.createCompany(new Company("IBM", "1234", "ibm@gmail.com"));
			adminFacade.createCompany(new Company("ForTraveller", "1234", "ft@gmail.com"));
			adminFacade.createCompany(new Company("NBA", "1234", "nba@gmail.com"));
		} catch (CompanyExistException e) {
			System.out.println(e.getMessage());
		}

		// Checking company exist
		try {
			Assert.notNull(adminFacade.getCompany(1), "Element not exist");
		} catch (CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Updating Company
		try {
			adminFacade.updateCompany("123", "aromes@gmail.com", 1);
		} catch (CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Checking company updated

		try {
			Assert.isTrue(adminFacade.getCompany(1).getPassword().equals("123"), "Element not updated");
		} catch (CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Removing Company
		try {
			adminFacade.removeCompany(3);
		} catch (CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Checking company not exist
		try {
			Assert.isNull(adminFacade.getCompany(3), "Element exist");
		} catch (CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Get all Companies
		try {
			ArrayList<Company> allCompanies = adminFacade.getAllCompnaies();

			// Checking allCompanies not empty
			Assert.notEmpty(allCompanies, "All Companies empty");
		} catch (CompaniesNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Get Company
		try {
			Company company = adminFacade.getCompany(1);
			// Checking company not null
			Assert.notNull(company, "Element is null");

		} catch (CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Create Customer
		try {
			adminFacade.createCustomer(new Customer("Avner", "1234"));
			adminFacade.createCustomer(new Customer("Liat", "1234"));
			adminFacade.createCustomer(new Customer("Itay", "1234"));
			adminFacade.createCustomer(new Customer("Ziv", "1234"));
		} catch (CustomerExistException e) {
			System.out.println(e.getMessage());
		}

		// Checking customer exist
		try {
			Assert.notNull(adminFacade.getCustomer(5), "Element not exist");
		} catch (CustomerNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Update Customer
		try {
			adminFacade.updateCustomer("123", 1);
		} catch (CustomerNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Checking customer updated

		try {
			Assert.isTrue(adminFacade.getCustomer(1).getPassword().equals("123"), "Element not updated");
		} catch (CustomerNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Removing Customer
		try {
			adminFacade.removeCustomer(3);
		} catch (CustomerNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Checking company not exist
		try {
			Assert.isNull(adminFacade.getCustomer(3), "Element exist");
		} catch (CustomerNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Get all Customers
		try {
			ArrayList<Customer> allCustomers = adminFacade.getAllCustomers();
			// Checking allCompanies not empty
			Assert.notEmpty(allCustomers, "All Companies empty");
		} catch (CustomersNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Get Customer
		try {
			Customer customer = adminFacade.getCustomer(5);
			// Checking customer not null
			Assert.notNull(customer, "Element is null");
		} catch (CustomerNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// !!!!!!!!!!-------COMPANY----------!!!!!!!!!!!
		companyFacade = (CompanyFacade) cs.login("Aroma", "123", ClientType.COMPANY);

		// Assert CompanyFacade not null
		Assert.notNull(companyFacade, "Element not null");

		// Creating Coupon
		Coupon coffe = new Coupon("Coffe Break", DateMaker.dateFix(2018, 1, 1), DateMaker.dateFix(2019, 1, 1), 100,
				CouponType.RESTURANS, "A decent Coffe break", 19, "aroma.com/c.jpg");
		Coupon breakFast = new Coupon("Break-Fast", DateMaker.dateFix(2018, 1, 1), DateMaker.dateFix(2019, 1, 1), 100,
				CouponType.RESTURANS, "A decent Morning meal", 32, "aroma.com/m.jpg");
		try {
			companyFacade.createCoupon(coffe);
			companyFacade.createCoupon(breakFast);
		} catch (CouponExistException | CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Checking coupon created

		try {
			Assert.notNull(companyFacade.getCompanyCoupon(1), "Element not exist");
		} catch (CompanyNotFoundException | CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Updating Coupon
		try {
			companyFacade.updateCoupon(DateMaker.dateFix(2024, 1, 1), 89, 1);
		} catch (CompanyNotFoundException | CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Checking coupon updated

		try {
			Assert.isTrue(companyFacade.getCompanyCoupon(1).getPrice() == 89, "Coupon not updated");
		} catch (CompanyNotFoundException | CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Removing Coupon
		try {
			companyFacade.removeCoupon(2);
		} catch (CompanyNotFoundException | CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Checking Coupon removed

		try {
			Assert.isNull(companyFacade.getCompanyCoupon(2), "Cuopon not null");
		} catch (CompanyNotFoundException | CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Get Coupon
		try {
			Coupon coupon = companyFacade.getCompanyCoupon(1);
			// Checking coupon not null
			Assert.notNull(coupon, "Coupon is null");
		} catch (CompanyNotFoundException | CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Get all Coupons
		try {
			ArrayList<Coupon> allCoupons = companyFacade.getCompanyCoupons();
			// Checking not empty
			Assert.notEmpty(allCoupons, "Empty Array list of Coupons");
		} catch (CouponsNotFoundException | CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Get all Coupons by type
		try {
			ArrayList<Coupon> allCoupons = companyFacade.getCompanyCouponsByType(CouponType.RESTURANS);
			// Checking not empty
			Assert.notEmpty(allCoupons, "Empty Array list of Coupons");
		} catch (CouponsNotFoundException | CompanyNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// !!!!!!!!!!-------CUSTOMER----------!!!!!!!!!!!
		customerFacade = (CustomerFacade) cs.login("Avner", "123", ClientType.CUSTOMER);

		// Assert CustomerFacade not null
		Assert.notNull(customerFacade, "Element not null");

		// Purchase Coupon
		try {
			customerFacade.purchaseCouopn(1);
		} catch (CouponNotFoundException | IllegalAmountException | ExpiredDateException | CustomerNotFoundException
				| CouponExistException e) {
			System.out.println(e.getMessage());
		}

		// Checking Customer holds Coupon
		try {
			Assert.notNull(customerFacade.getPurchasedCouopn(1), "Coupon is null");
		} catch (CustomerNotFoundException | CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Getting all purchased Coupons
		try {
			ArrayList<Coupon> allCustomerCoupons = customerFacade.getAllPurchasedCoupons();
			Assert.notEmpty(allCustomerCoupons, "Array List of Coupons is empty 1");
		} catch (CustomerNotFoundException | CouponsNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Getting all purchased Coupons by type
		try {
			ArrayList<Coupon> allCustomerCoupons = customerFacade.getCouponsByType(CouponType.RESTURANS);
			Assert.notEmpty(allCustomerCoupons, "Array List of Coupons is empty - no fit type");
		} catch (CustomerNotFoundException | CouponsNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// Getting all purchased Coupons by price
		try {
			ArrayList<Coupon> allCustomerCoupons = customerFacade.getCouponsByPrice(999999);
			Assert.notEmpty(allCustomerCoupons, "Array List of Coupons is empty - no lower price");
		} catch (CustomerNotFoundException | CouponsNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

}
