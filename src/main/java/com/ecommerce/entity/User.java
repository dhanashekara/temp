package com.ecommerce.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true)
	long userId;

	@Column(name = "user_name", unique = true)
	String userName;

	@Email(message = "invalid format")
	@Column(name = "user_email", unique = true)
	String userEmail;

	@Column(name = "password")
	String password;

	@Column(name = "user_category")
	String userCategory;

	@Column(name = "contact", unique = true)
	String contact;

	@Column(name = "address")
	String address;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> product;

}
