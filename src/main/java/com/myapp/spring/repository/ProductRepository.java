package com.myapp.spring.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.spring.model.Product;

//Annotation is to identify that this is a spring managed bean
//This is a Data Repository Class
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	//select * from springboot_products where price>{price}
	Optional<List<Product>> findByPriceGreaterThan(Double price);
	
	//select * from springboot_products where price>={price}
	Optional<List<Product>> findByPriceGreaterThanEqual(Double price);
    
	//select * from springboot_products where productName={productName} OR price={price}
    Optional<List<Product>> findByProductNameOrPrice(String productName,Double price);
    
    //select * from springboot_products where productName LIKE {pattern}
    // Eg: //select * from springboot_products where productName LIKE {%u%}
    Optional<List<Product>> findByProductNameLike(String productName);
    
    //select * from springboot_products where price IN ({price1},{price2},{price3}.....) 
    Optional<List<Product>> findByPriceIn(Collection<Double> prices);
    
    //Case Insensitive
    Optional<List<Product>> findByProductNameIgnoreCase(String productName);
	
	

}
