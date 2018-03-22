package com.example.demo.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.facades.AdminFacade;

@RestController
public class Example {
	
	@Autowired
	AdminFacade af;

	@RequestMapping(value = "/getCompany/{id}" , method = RequestMethod.GET)
	public String getMethod(@PathVariable("id") int id)
	{
		try {
			return af.getCompany(id).toString();
		} catch (CompanyNotFoundException e) {
			return e.getMessage();
		}
	}
	
	@RequestMapping(value = "/post/{number}" , method = RequestMethod.POST)
	public String postMethod(@PathVariable("number")int number)
	{
		return "POST " + number;
	}
	
	@RequestMapping(value = "/put" , method = RequestMethod.PUT)
	public String putMethod()
	{
		return "PUT";
	}
	
	@RequestMapping(value = "/delete" , method = RequestMethod.DELETE)
	public String deleteMethod()
	{
		return "DELETE";
	}
}
