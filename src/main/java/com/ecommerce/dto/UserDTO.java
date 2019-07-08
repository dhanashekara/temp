package com.ecommerce.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	long userId;
	@NotEmpty(message = "User Name Should not be empty or Null")
	String userName;
	@NotEmpty(message = "User Email Should not be empty or Null")
	String userEmail;
	@NotEmpty(message = "Password Should not be empty or Null")
	String password;
	@NotEmpty(message = "User Category Should not be empty or Null")
	String userCategory;
	String contact;
	String address;

}
