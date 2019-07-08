package com.ecommerce.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.entity.User;
import com.ecommerce.exception.UserManagementException;
import com.ecommerce.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepo;

	@Mock
	ModelMapper modelMapper;

	UserDTO userDTO;
	User userEntity;

	@Before
	public void setUp() {
		userDTO = new UserDTO(1l, "manish", "me@gmail.com", "manish@123", "seller", "8884959368",
				"#12,Jayanagar,Bengaluru");
		userEntity = new User(1l, "manish", "me@gmail.com", "manish@123", "seller", "8884959368",
				"#12,Jayanagar,Bengaluru",null);
	}

	@Test
	public void testRegisterUser() throws UserManagementException {

		// positive
		when(modelMapper.map(userDTO, User.class)).thenReturn(userEntity);
		when(userRepo.save(userEntity)).thenReturn(userEntity);
	//	when(userRepo.findById(Mockito.anyLong())).thenReturn(Mockito.anyObject());
		String actual = userService.registerUser(userDTO);
		assertEquals("User Registration Successful", actual);

		/*
		 * // Negative userDTO = null; userEntity = null; //
		 * when(userRepo.save(userEntity)).thenReturn(null); actual =
		 * userService.registerUser(userDTO);
		 * assertEquals("Something went wrong!! Please contact support team", actual);
		 */

	}
	
	/*
	 * @Test(expected = UserManagementException.class) public void
	 * testRegisterUserNegative() throws UserManagementException {
	 * when(userRepo.save(null)).thenReturn(UserManagementException.class);
	 * userService.registerUser(null); }
	 */
	

	@Test
	public void testUpdateUserProfile() throws UserManagementException {

		// positive
		when(userRepo.findById(userDTO.getUserId())).thenReturn(Optional.of(userEntity));
		when(userRepo.save(userEntity)).thenReturn(userEntity);
		String actual = userService.updateUserProfile(userDTO);
		assertEquals("User Details updated successfully", actual);

		/*
		 * // Negative userDTO.setUserId(2l); userEntity = null; //
		 * when(userRepo.save(userEntity)).thenReturn(null); actual =
		 * userService.updateUserProfile(userDTO);
		 * assertEquals("User with ID "+userDTO.getUserId()+" doesn't exist", actual);
		 */

	}
	
	@Test
	public void testDeleteUserProfile() throws UserManagementException {
		
		when(userRepo.findById(1L)).thenReturn(Optional.of(userEntity));
		String actual = userService.deleteUserProfile(1L);
		assertEquals("User Details deleted successfully", actual);
		
	}
	
	@Test
	public void testGetUserListByCategory() {
		
		List<UserDTO> usersListDTO = new ArrayList<>();
		usersListDTO.add(userDTO);
		
		List<User> usersList= new ArrayList<>();
		usersList.add(userEntity);
		
		when(modelMapper.map(userEntity, UserDTO.class)).thenReturn(userDTO);
		when(userRepo.findAllByUserCategoryIgnoreCase("buyers")).thenReturn(usersList);
		List<UserDTO> actual = (List<UserDTO>) userService.getUserListByCategory("buyers");
		assertEquals(usersListDTO.size(), actual.size());
		
		/*
		 * //Negative Case userDTO.setUserId(100L); userEntity.setUserId(10L);
		 * usersList.add(userEntity); usersListDTO.add(userDTO); Object actualNeg =
		 * userService.getUserListByCategory("seller");
		 * assertEquals("Users are not available", actualNeg);
		 */
	}
	
	@Test
	public void testLogin() throws UserManagementException {
		String userName = "anhi";
		String password = "zjzcz";
		
		when(userRepo.findByUserNameAndPassword(userName, password)).thenReturn(userEntity);
		String actual = userService.login(userName, password);
		assertEquals("Login successful", actual);
	}
	
}
