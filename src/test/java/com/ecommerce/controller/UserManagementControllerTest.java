
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

import com.ecommerce.dto.UserDTO;
import com.ecommerce.exception.UserManagementException;
import com.ecommerce.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserManagementControllerTest {

	@InjectMocks
	private UserManagementController umController;

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@Autowired
	private WebApplicationContext context;

	UserDTO user;

	@Before
	public void setUp() {
		user = new UserDTO(1l, "manish", "me@gmail.com", "manish@123", "seller", "8884959368",
				"#12,Jayanagar,Bengaluru");
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	public static String asJsonString(final Object object) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(object);

	}

	@Test
	public void registerUserTest() throws JsonProcessingException, Exception {

		// positive case
		when(userService.registerUser(user)).thenReturn("User Registration Successful");
		ResponseEntity<String> actual = umController.registerUser(user);
		this.mockMvc.perform(
				post("/usermanagement/user").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)));
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
	public void testUpdateUserProfile() throws JsonProcessingException, Exception {

		// positive case
		when(userService.updateUserProfile(user)).thenReturn("User Details updated successfully");
		ResponseEntity<String> actual = umController.updateUserProfile(user);
		this.mockMvc.perform(
				post("/usermanagement/user").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)));
		assertEquals(200, actual.getStatusCodeValue());

		/*
		 * // Negative Case1 user.setUserId(2L);
		 * when(userService.updateUserProfile(user)).thenReturn("User with ID "+user.
		 * getUserId()+" doesn't exist"); actual = umController.updateUserProfile(user);
		 * this.mockMvc.perform(
		 * post("/usermanagement/user").contentType(MediaType.APPLICATION_JSON).content(
		 * asJsonString(user))); assertEquals(404, actual.getStatusCodeValue());
		 */

	}

	@Test
	public void testLogin() throws Exception {

		String userName = "anhi";
		String password = "zjzcz";
		when(userService.login(userName, password)).thenReturn("Login successful");
		ResponseEntity<String> actual = umController.login(userName, password);
		this.mockMvc.perform(post("/usermanagement/login").contentType(MediaType.APPLICATION_JSON));
		assertEquals(302, actual.getStatusCodeValue());
	}

	@Test
	public void testDeleteUserProfile() throws Exception {
		
		when(userService.deleteUserProfile(21)).thenReturn("User Details deleted successfully");
		ResponseEntity<String> actual = umController.deleteUserProfile(21);
		this.mockMvc.perform(post("/usermanagement/users/",21).contentType(MediaType.APPLICATION_JSON));
		assertEquals(200, actual.getStatusCodeValue());
	}
	
	@Test
	public void testGetBuyersList() throws Exception {
		
		List<UserDTO> users = new ArrayList<UserDTO>();
		
		when(userService.getUserListByCategory("buyer")).thenReturn(users);
		ResponseEntity<Object> actual = umController.getBuyersList();
		this.mockMvc.perform(post("/usermanagement/users/buyers").contentType(MediaType.APPLICATION_JSON));
		assertEquals(200, actual.getStatusCodeValue());
		
		//negative
		//users = null;
		when(userService.getUserListByCategory("buyer")).thenReturn("Users are not available");
		actual = umController.getBuyersList();
		assertEquals(404, actual.getStatusCodeValue());
		
	}
	
	@Test
	public void testGetSellersList() throws Exception {
		
		List<UserDTO> users = new ArrayList<UserDTO>();
		
		when(userService.getUserListByCategory("seller")).thenReturn(users);
		ResponseEntity<Object> actual = umController.getSellersList();
		this.mockMvc.perform(post("/usermanagement/users/sellers").contentType(MediaType.APPLICATION_JSON));
		assertEquals(200, actual.getStatusCodeValue());
		
		//negative
		when(userService.getUserListByCategory("seller")).thenReturn("Users are not available");
		actual = umController.getSellersList();
		assertEquals(404, actual.getStatusCodeValue());
		
	}

	
}
