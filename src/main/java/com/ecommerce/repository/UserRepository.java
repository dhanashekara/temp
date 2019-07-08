package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllByUserCategoryIgnoreCase(String userCategory);

	Object findByUserNameAndPassword(String userName, String password);

	Object findByUserName(String userName);

	Object findByuserEmail(String userEmail);

	Object findByContact(String contact);

}
