package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.ProductModel;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	public ProductModel addProduct(ProductModel model) {
		return repository.save(model);
	}
	
	public List<ProductModel> showProducts(){
		return repository.findAll();
	}
	

}
