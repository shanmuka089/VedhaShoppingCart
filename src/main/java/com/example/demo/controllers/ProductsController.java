package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ProductModel;
import com.example.demo.service.ProductService;

@RestController
public class ProductsController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping("/product")
	public ResponseEntity<List<ProductModel>> showProducts() {
		List<ProductModel> product_list=service.showProducts();
		return new ResponseEntity<List<ProductModel>>(product_list,HttpStatus.OK);
	}
	
	@PostMapping("/product")
	public ResponseEntity<ProductModel> addProduct(@RequestBody ProductModel model){
		ProductModel product=service.addProduct(model);
		return new ResponseEntity<ProductModel>(product,HttpStatus.CREATED);
	}

}
