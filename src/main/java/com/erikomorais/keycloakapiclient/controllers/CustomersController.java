package com.erikomorais.keycloakapiclient.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erikomorais.keycloakapiclient.domain.entities.Customer;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {
	

	@GetMapping(produces  =MediaType.APPLICATION_JSON_VALUE)
	@RolesAllowed("ROLE_USER")
	public List<Customer> customers(){
			List<Customer> customers = new ArrayList<>();
			customers.add(new Customer(1L,"John Doe", "jdoe@eriko.com"));
			customers.add(new Customer(2L,"Jane Doe", "j_doe@eriko.com"));
			customers.add(new Customer(3L,"Doe P Man", "dopeman@eriko.com"));
		return customers;
	}
	
	

}
