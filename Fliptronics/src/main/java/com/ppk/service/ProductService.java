package com.ppk.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppk.Repository.ProductRepository;
import com.ppk.model.Product;
@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	public List<Product> getAllProduct()
	{
		return productRepository.findAll();
	}
	public void addProduct(Product product)
	{
		productRepository.save(product);
	}

	public void removeProductById(Long id)
	{
		productRepository.deleteById(id);
	}
	public Optional<Product> getProductById(Long id)
	{
		return productRepository.findById(id);
		
	}
	public List<Product> getAllProductsByCategoryId(int id)
	{
		return productRepository.findAllBycategory_Id(id);
	}
	
}
