
package com.example.demo.facades;

import org.springframework.stereotype.Component;

import com.example.enums.ClientType;



@Component
public interface CouponClientFacade {
	/**
	 * login method for different types.
	 * @param name
	 * @param password
	 * @param type
	 * @return
	 */
	CouponClientFacade login(String name , String password , ClientType type);
}
