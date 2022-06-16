package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int product_id;
	private String product_name;
	private String product_desc;
	private String product_image;
	private double actual_price;
	private double discount_price;
	private String product_offer;
	private String delivery_type;
	private String product_deal;
	private String product_category;
	private String product_brand;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "products")
	private List<UserModel> users=new ArrayList<UserModel>();

}
