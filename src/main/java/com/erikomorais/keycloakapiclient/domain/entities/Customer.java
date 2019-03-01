package com.erikomorais.keycloakapiclient.domain.entities;


import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Customer {

	private Long id;
	private String name;
	private String email;
	
	

}
