package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ProductManagementException;
import com.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository prodRepo;

	@Autowired
	ModelMapper modelMapper;

	@Bean
	public ModelMapper modelMapperForProducts() {
		return new ModelMapper();
	}

	public String registerProduct(ProductDTO productDTO) throws ProductManagementException {

		Product product = modelMapper.map(productDTO, Product.class);

		if (prodRepo.save(product) != null)
			return "Product registered successfully";
		else
			throw new ProductManagementException("Something went wrong!! please contact support team");
	}

	public Object getProductList(String productCategory, String productName) throws ProductManagementException {

		List<ProductDTO> prodDTOList = new ArrayList<>();
		List<Product> prodList;

		if (productCategory != null && !productCategory.isEmpty() && productName != null && !productName.isEmpty())
			throw new ProductManagementException("Product Category and Product Name cannot be same");
		else if (productName != null && !productName.isEmpty())
			prodList = prodRepo.findByProductName(productName);
		else if (productCategory != null && !productCategory.isEmpty())
			prodList = prodRepo.findByProductCategoryIgnoreCase(productCategory);
		else
			prodList = prodRepo.findAll();

		if (prodList != null && !prodList.isEmpty()) {
			prodList.forEach(product -> {
				prodDTOList.add(modelMapper.map(product, ProductDTO.class));
			});
			return prodDTOList;
		} else {
			throw new ProductManagementException("Product list is empty");
			//logger
		}
	}

	/*
	 * public Object getProdctsByCategory(String productCategory) {
	 * 
	 * List<ProductDTO> prodDTOList = new ArrayList<>(); List<Product> prodList =
	 * prodRepo.findByProductCategoryIgnoreCase(productCategory);
	 * 
	 * if (prodList != null && !prodList.isEmpty()) { prodList.forEach(product -> {
	 * prodDTOList.add(modelMapper.map(product, ProductDTO.class)); }); return
	 * prodDTOList; } else { return "Product list is empty"; } }
	 */
}
