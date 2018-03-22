package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.enums.CouponType;

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
public class Coupon {
	
	// Object's members
	@Getter
	@Setter
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO) // make generate  value for id - PK
	private int id;
	
	@Getter
	@Setter
	@Column
	private String title;
	
	@Getter
	@Setter
	@Column
	private Date startDate;
	
	@Getter
	@Setter
	@Column
	private Date endDate;
	
	@Getter
	@Setter
	@Column
	private int amount;
	
	@Getter
	@Setter
	@Column
	@Enumerated(EnumType.STRING)
	private CouponType type;
	
	@Getter
	@Setter
	@Column
	private String massage;
	
	@Getter
	@Setter
	@Column
	private double price;
	
	@Getter
	@Setter
	@Column
	private String image;
	
	@Setter
	@ManyToOne  // many coupon to one company
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "Company_id" , nullable = false) // new company column inside coupon table.
	// the name of the column will company id and this column can't be without name.
	private Company company;
	
	
	@ManyToMany(mappedBy = "coupons")
	private List<Customer> customers = new ArrayList<>();
	
	
	/***
	 * CTR with no ID / Company / Customers
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param amount
	 * @param type
	 * @param massage
	 * @param price
	 * @param image
	 */
	public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String massage, double price,
			String image) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.massage = massage;
		this.price = price;
		this.image = image;
	}

	/***
	 * toString with no Customers / Company
	 */
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", starDate=" + startDate + ", endDate=" + endDate + ", amount="
				+ amount + ", type=" + type + ", massage=" + massage + ", price=" + price + ", image=" + image + "]";
	}
	

}
