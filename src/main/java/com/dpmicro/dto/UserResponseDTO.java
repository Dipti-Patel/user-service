package com.dpmicro.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
	
	private Long id;

	private String name;
	
	private String mobileNo;

	private String Email;
	
	private int userType;
	
	private String token;
	
	private String password;

}
