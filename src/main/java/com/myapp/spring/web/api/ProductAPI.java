package com.myapp.spring.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.spring.model.Product;
import com.myapp.spring.repository.ProductRepository;

//This is a class which exposes REST API's
@RestController
@RequestMapping("/api/v1/products")
public class ProductAPI {
	
	//Dependency Injection
	@Autowired
	private ProductRepository repository;

	//http://localhost:8888/api/v1/products
	@GetMapping
	public ResponseEntity<List<Product>> findAll(){
		return new ResponseEntity<List<Product>>(repository.findAll(),HttpStatus.OK);
		
	}
	
	//http://localhost:8888/api/v1/products
	@PostMapping
	public ResponseEntity<Product> saveNewProduct(@RequestBody Product product){
		return new ResponseEntity<Product>(repository.save(product),HttpStatus.CREATED);
		
	}
	
	//http://localhost:8888/api/v1/products/id
	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable("id") Integer id){
		return new ResponseEntity<Product>(repository.findById(id).get(),HttpStatus.OK);
			
	}
	
	//http://localhost:8888/api/v1/products
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id,
			@RequestBody Product product){
		
		Product existingProduct = repository.findById(id).get();	
		
		BeanUtils.copyProperties(product, existingProduct);
		
		return new ResponseEntity<Product>(repository.save(existingProduct),
				HttpStatus.CREATED);
		
	}
	
	//http://localhost:8888/api/v1/products
	@PostMapping("/bulk")
	public ResponseEntity<List<Product>> bulkProductInsert(@RequestBody List<Product> products){
		return new ResponseEntity<List<Product>>(repository.saveAll(products),HttpStatus.CREATED);
		
	}
	
	//http://localhost:8888/api/v1/products/id
	@DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Integer id)
      throws ResourceNotFoundException {
        Product product = repository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException
                   ("Product not found for this id :: " + id));
        repository.delete(product);
         Map<String, Boolean> response = new HashMap<>();
         response.put("deleted", Boolean.TRUE);
         return response;
    }
		
	//http://localhost:8888/api/v1/products
	@GetMapping("/find/gt/{price}")
	public ResponseEntity<List<Product>> findProductsByPriceGt
	(@PathVariable Double price){
		return new ResponseEntity<List<Product>>(
				repository.findByPriceGreaterThan(price).get(),HttpStatus.OK);
		
	}
	
	//http://localhost:8888/api/v1/products
	@GetMapping("/find/gteq/{price}")
	public ResponseEntity<List<Product>> findProductsByPriceGtEq(
			@PathVariable Double price){
		return new ResponseEntity<List<Product>>
		(repository.findByPriceGreaterThanEqual(price).get(),HttpStatus.OK);
		
	}
	

	//http://localhost:8888/api/v1/products/find/ByNameOrPrice/?productName=Vivo
	@GetMapping("/find/ByNameOrPrice/")
	public ResponseEntity<List<Product>> findProductsByPriceOrName
	    (@RequestParam("productName") Optional<String> productName,
	    		@RequestParam("price") Optional<Double> price){
	        
	 return new ResponseEntity<List<Product>>
	    (repository.findByProductNameOrPrice(productName.orElse(""), price.orElse(0.0)).get(), HttpStatus.OK);
	}
	
//	//http://localhost:8888/api/v1/products/find/ProductNameByLike/{pattern}
//	//http://localhost:8888/api/v1/products/find/ProductNameByLike/{%u%}
//	@GetMapping("/find/ProductNameByLike/{productname}")
//	public ResponseEntity<List<Product>> findProductNameByLike
//	    (@RequestParam("productName") String productName){
//	        
//	 return new ResponseEntity<List<Product>>
//	    (repository.findByProductNameLike(productName).get(),HttpStatus.OK);
//	}	
//	
	
}
