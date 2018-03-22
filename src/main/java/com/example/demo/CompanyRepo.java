package com.example.demo;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Company;


@Repository
public interface CompanyRepo extends CrudRepository<Company, Integer> {
	
	
	/***
	 * Updating Company set only password & email
	 * @param password
	 * @param email
	 * @param id
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Company comp SET comp.password = :password , comp.email = :email WHERE comp.id = :id")
	void updateCompany(@Param("password") String password , @Param("email") String email , @Param("id") int id);
	
	/***
	 * Getting Company by its name
	 * @param compName
	 * @return
	 */
	Company findCompanyByCompName(String compName);

	/***
	 * Getting Company by its name & password
	 * @param compName
	 * @return
	 */
	Company findCompanyByCompNameAndPassword(String compName , String password);
}
