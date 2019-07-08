package com.ecommerce.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductManagementControllerTest {

	@InjectMocks
	private ProductManagementController pmController;

	private MockMvc mockMvc;

	@Mock
	private ProductService productService;

	@Autowired
	private WebApplicationContext context;

	ProductDTO productDTO;
	UserDTO userDT = new UserDTO();

	@Before
	public void setUp() {
		productDTO = new ProductDTO(1L,"samsung s7","mobile",userDT);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	public static String asJsonString(final Object object) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(object);

	}
	
	@Test
	public void registerProductTest() throws JsonProcessingException, Exception {

		// positive case
		when(productService.registerProduct(productDTO)).thenReturn("Product registered successfully");
		ResponseEntity<Object> actual = pmController.registerProduct(productDTO);
		this.mockMvc.perform(
				post("/productmanagement/product").contentType(MediaType.APPLICATION_JSON).content(asJsonString(productDTO)));
		assertEquals(201, actual.getStatusCodeValue());

		/*
		 * // Negative Case user = null; when(userService.registerUser(user)).
		 * thenReturn("Something went wrong!! Please contact support team"); actual =
		 * umController.registerUser(user); this.mockMvc.perform(
		 * post("/usermanagement/user").contentType(MediaType.APPLICATION_JSON).content(
		 * asJsonString(user))); assertEquals(500, actual.getStatusCodeValue());
		 */

	}
	
	@Test
	public void testGetProductList() throws Exception {
		
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		
		when(productService.getProductList("mobile", "samsung s7")).thenReturn(products);
		ResponseEntity<Object> actual = pmController.getProductList("mobile", "samsung s7");
		this.mockMvc.perform(post("/productmanagement/products","mobile","samsung s7").contentType(MediaType.APPLICATION_JSON));
		assertEquals(302, actual.getStatusCodeValue());
		
		/*
		 * //negative //users = null; when(userService.getUserListByCategory("buyers")).
		 * thenReturn("Users are not available"); actual = umController.getBuyersList();
		 * assertEquals(404, actual.getStatusCodeValue());
		 */
		
	}
	
}
