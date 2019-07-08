package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.entity.User;
import com.ecommerce.exception.UserManagementException;
import com.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UserRepository userRepo;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public String registerUser(UserDTO userDTO) throws UserManagementException {

		try {
			User userEntity = modelMapper.map(userDTO, User.class);

			if(userRepo.findByUserName(userDTO.getUserName()) != null)
				throw new UserManagementException("User name already registered");
			if(userRepo.findByuserEmail(userDTO.getUserEmail()) != null)
				throw new UserManagementException("User email already registered");
			if(userRepo.findByContact(userDTO.getContact()) != null)
				throw new UserManagementException("Contact already Registered");
			
			if (userRepo.save(userEntity) != null)
				return "User Registration Successful";
			else
				throw new UserManagementException("Something went wrong!! Please contact support team");// internal server error
		
		  } catch(Exception e) {
			 if(e.getMessage().contains("userEmail")) 
				 throw new UserManagementException("Email should be entered in xyz@xyz.com format");
			 else 
				 throw new UserManagementException(e.getMessage());
		  }
		 
	}

	public String updateUserProfile(UserDTO userDTO) throws UserManagementException {

		Optional<User> op = userRepo.findById(userDTO.getUserId());

		if (op.isPresent()) {
			
			User userEntity = op.get();
			userEntity.setUserName((userDTO.getUserName() != null && !userDTO.getUserName().isEmpty()) ? userDTO.getUserName():userEntity.getUserName());
			userEntity.setAddress((userDTO.getAddress() != null && !userDTO.getAddress().isEmpty()) ? userDTO.getAddress(): userEntity.getAddress());
			userEntity.setContact((userDTO.getContact() != null && !userDTO.getContact().isEmpty()) ? userDTO.getContact(): userEntity.getContact());
			userEntity.setPassword((userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) ? userDTO.getPassword(): userEntity.getPassword());
			userEntity.setUserCategory((userDTO.getUserCategory() != null && !userDTO.getUserCategory().isEmpty())? userDTO.getUserCategory(): userEntity.getUserCategory());
			userEntity.setUserEmail((userDTO.getUserEmail() != null && !userDTO.getUserEmail().isEmpty()) ? userDTO.getUserEmail(): userEntity.getUserEmail());
			
			if (userRepo.save(userEntity) != null)
				return "User Details updated successfully";
			else
				throw new UserManagementException("Something went wrong!! Please contact support team"); // internal server error
		} else {
			// log here
			throw new UserManagementException("User with ID "+userDTO.getUserId()+" doesn't exist");
		}
	}

	public String deleteUserProfile(long userId) throws UserManagementException {

		Optional<User> op = userRepo.findById(userId);

		if (op.isPresent()) {
			userRepo.deleteById(userId);
			return "User Details deleted successfully";

		} else {
			// log here
			 throw new UserManagementException("User with ID "+userId+" doesn't exist");
		}
	}

	public Object getUserListByCategory(String userCategory) {

		List<User> usersList = userRepo.findAllByUserCategoryIgnoreCase(userCategory);
		List<UserDTO> usersListDTO = new ArrayList<>();

		if (usersList != null && !usersList.isEmpty()) {
			usersList.forEach(user -> {usersListDTO.add(modelMapper.map(user, UserDTO.class));});
			return usersListDTO;
		} else {
			return "Users are not available";
		}
	}

	public String login(String userName, String password) throws UserManagementException {
		if (userRepo.findByUserNameAndPassword(userName, password) != null)
			return "Login successful";
		else
			throw new UserManagementException("Enter valid credentials");
	}

}
