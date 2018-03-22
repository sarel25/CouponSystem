package com.example.demo.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * Company table on data base
 * @author grimbergs
 *
 */
@NoArgsConstructor
@Entity
public class Company {
	
	// Object's attributes
	@Getter
	@Setter
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO) // random value for id - primary key
	private int id;
	
	@Getter
	@Setter
	@Column
	private String compName;
	
	@Getter
	@Setter
	@Column
	private String password;
	
	@Getter
	@Setter
	@Column
	private String email;
	
	@OneToMany(mappedBy = "company",  fetch = FetchType.LAZY , cascade = CascadeType.ALL)// one company with many coupons
	//fetchtype.lazy will prevent when we will make a new comapny prevernt that we get all coupons of the company.
	//cascade will give acceess to all c.r.u.d methods ----- create.read.update.delete    
	List<Coupon> coupons = new ArrayList<>();
	
	
	
	
	/***
	 * CTR with no id
	 * @param compName
	 * @param password
	 * @param email
	 */
	public Company(String compName, String password, String email) {
		super();
		this.compName = compName;
		this.password = password;
		this.email = email;
	}



	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + "]";
	}
	


}
