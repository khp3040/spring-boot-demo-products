package com.myapp.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="springboot_products")
public class Product {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="Product_Id")
	private Integer productId;
	
	@Column(name="Product_Name")
	private String productName;
	
	@Column(name="Product_Description")
	private String description;
	
	@Column(name="Product_Price")
	private Double price;
	
	@Column(name="Product_Rating")
	private Double starRating;
	
	public Product() {
		// TODO Auto-generated constructor stub
			
	}

	public Product(String productName, String description, Double price, Double starRating) {
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.starRating = starRating;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getStarRating() {
		return starRating;
	}

	public void setStarRating(Double starRating) {
		this.starRating = starRating;
	}
	
	

}
