package com.ecommerce.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.exception.ProductManagementException;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/productmanagement")
public class ProductManagementController {

	String status;

	@Autowired
	ProductService prodService;

	@PostMapping("/product")
	public ResponseEntity<Object> registerProduct(@Valid @RequestBody ProductDTO productDTO) throws IOException, ProductManagementException{

	//	status = prodService.registerProduct(productDTO);

	//	if (status.equalsIgnoreCase("Product registered successfully"))
			return new ResponseEntity<Object>(prodService.registerProduct(productDTO), HttpStatus.CREATED);
	//	else
	//		return new ResponseEntity<Object>(status, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/products/{productcategory}/{productname}")
	public ResponseEntity<Object> getProductList(
			@RequestParam(name = "productcategory", required = false) String productCategory,
			@RequestParam(name = "productname", required = false) String productName) throws ProductManagementException {

	//	Object response = prodService.getProductList(productCategory, productName);
	//	if (!response.equals("Product list is empty"))
			return new ResponseEntity<Object>(prodService.getProductList(productCategory, productName), HttpStatus.FOUND);
	//	else
	//		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}

	/*
	 * @GetMapping("/products/{productcategory}") public ResponseEntity<Object>
	 * getProdctsByCategory(@PathVariable String productcategory) {
	 * 
	 * Object response = prodService.getProductList(productcategory); if
	 * (!response.equals("Product list is empty")) return new
	 * ResponseEntity<Object>(response, HttpStatus.FOUND); else return new
	 * ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	 * 
	 * }
	 */

	/*
	 * @GetMapping("/products/{productname}") public ResponseEntity<Object>
	 * getProdctsByCategory(@PathVariable String productname) {
	 * 
	 * Object response = prodService.getProductList(productname); if
	 * (!response.equals("Product list is empty")) return new
	 * ResponseEntity<Object>(response, HttpStatus.FOUND); else return new
	 * ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	 * 
	 * }
	 */

}
