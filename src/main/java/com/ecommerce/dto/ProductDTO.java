package com.ecommerce.dto;

import javax.validation.constraints.NotEmpty;

import com.ecommerce.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	long productId;
	
	@NotEmpty(message = "Product Name Should not be empty or Null")
	String productName;
	
	@NotEmpty(message = "Product Category Should not be empty or Null")
	String productCategory;
	
	UserDTO user;
}
