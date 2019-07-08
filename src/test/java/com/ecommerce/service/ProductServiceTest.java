package com.ecommerce.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ProductManagementException;
import com.ecommerce.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {

	@InjectMocks
	ProductService productService;

	@Mock
	ProductRepository prodRepo;

	@Mock
	ModelMapper modelMapper;

	ProductDTO productDTO = new ProductDTO();
	Product productEntity;
	User user = new User();
	UserDTO userDTO = new UserDTO();
	

	@Before
	public void setUp() {
		user = new User();
		productDTO = new ProductDTO(1L,"samsung s7","mobile",userDTO);
		productEntity = new Product(1L,"samsung s7","mobile",user);
	}
	
	@Test
	public void testRegisterProduct() throws ProductManagementException {

		// positive
		when(modelMapper.map(productDTO, Product.class)).thenReturn(productEntity);
		when(prodRepo.save(productEntity)).thenReturn(productEntity);
	//	when(userRepo.findById(Mockito.anyLong())).thenReturn(Mockito.anyObject());
		String actual = productService.registerProduct(productDTO);
		assertEquals("Product registered successfully", actual);

		/*
		 * // Negative userDTO = null; userEntity = null; //
		 * when(userRepo.save(userEntity)).thenReturn(null); actual =
		 * userService.registerUser(userDTO);
		 * assertEquals("Something went wrong!! Please contact support team", actual);
		 */
	}
	
	
	@Test
	public void testGetProductList() throws ProductManagementException {
		
		List<ProductDTO> productListDTO = new ArrayList<>();
		productListDTO.add(productDTO);
		
		List<Product> productList= new ArrayList<>();
		productList.add(productEntity);
		
		when(modelMapper.map(productEntity, Product.class)).thenReturn(productEntity);
		when(prodRepo.findAll()).thenReturn(productList);
		when(prodRepo.findByProductCategoryIgnoreCase("mobile")).thenReturn(productList);
		when(prodRepo.findByProductName("samsung s7")).thenReturn(productList);
		
		List<ProductDTO> actual = (List<ProductDTO>) productService.getProductList("mobile","");
		assertEquals(productListDTO.size(), actual.size());
		
		actual = (List<ProductDTO>) productService.getProductList("","samsung s7");
		assertEquals(productListDTO.size(), actual.size());
		
		actual = (List<ProductDTO>) productService.getProductList("","");
		assertEquals(productListDTO.size(), actual.size());
		
		
		/*
		 * //Negative Case userDTO.setUserId(100L); userEntity.setUserId(10L);
		 * usersList.add(userEntity); usersListDTO.add(userDTO); Object actualNeg =
		 * userService.getUserListByCategory("seller");
		 * assertEquals("Users are not available", actualNeg);
		 */
	}
	
	
}
