package com.myapp.spring.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.spring.model.Product;
import com.myapp.spring.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private MockMvc mockMvc;

	private static File DATA_JSON= Paths.get("src","test","resources","products.json").toFile();

	@BeforeEach
	public void setup() throws JsonParseException, JsonMappingException, IOException{

		Product products []=new ObjectMapper().readValue(DATA_JSON,Product[].class);

		Arrays.stream(products).forEach(repository::save);
	
	}

	@AfterEach
	public void cleanUp() {
	
		repository.deleteAll();
	
	}

	
	@Test
	@DisplayName("Test Find Product By Id - GET /api/v1/products")
	public void testGetProductsByiId() throws Exception{

//		Product product = new Product("Vivo","VivoPro12",37560.54,3.9);
//		product.setProductId(1);
//		
//		doReturn(Optional.of(product)).when(repository).findById(product.getProductId());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}",1))
		
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		.andExpect(jsonPath("$.productId", is(1)))
		.andExpect(jsonPath("$.productName", is("Vivo")))
		.andExpect(jsonPath("$.description", is("VivoPro12")))
		.andExpect(jsonPath("$.price", is(37560.54)))
		.andExpect(jsonPath("$.starRating", is(3.9)));
		
	}
	
	@Test
	@DisplayName("Test Get All Product- GET /api/v1/products")
	public void testGetAllProducts() throws Exception{

//		Product product1 = new Product("Vivo","VivoPro12",37560.54,3.9);
//		product1.setProductId(1);
//		
//		Product product2 = new Product("OnePlus","OnePlus8Pro",40000.00,4.7);
//		product2.setProductId(2);
//		
//		List<Product> products = new ArrayList<>();
//		products.add(product1);
//		products.add(product2);
//		
//		doReturn(products).when(repository).findAll();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/"))
		//Validate status should be 
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		//Validate Response Body
		.andExpect(jsonPath("$[0].productId", is(1)))
		.andExpect(jsonPath("$[0].productName", is("Vivo")))
		.andExpect(jsonPath("$[0].description", is("VivoPro12")))
		.andExpect(jsonPath("$[0].price", is(37560.54)))
		.andExpect(jsonPath("$[0].starRating", is(3.9)))
		
		.andExpect(jsonPath("$[1].productId", is(2)))
		.andExpect(jsonPath("$[1].productName", is("OnePlus")))
		.andExpect(jsonPath("$[1].description", is("OnePlus8Pro")))
		.andExpect(jsonPath("$[1].price", is(40000.00)))
		.andExpect(jsonPath("$[1].starRating", is(4.7)));
		
	}
	
	
	@Test
	@DisplayName("Test Add New Product - POST /api/v1/products/")
	public void testAddNewProduct() throws Exception{

		Product newProduct = new Product("Nokia","Nokia6.1+",77560.54,3.9);
		newProduct.setProductId(5);
		
//		Product mockProduct = new Product("Nokia","Nokia6.1+",77560.54,3.9);
//		mockProduct.setProductId(5);
	
//		doReturn(mockProduct).when(repository).save(ArgumentMatchers.any());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/")
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(new ObjectMapper().writeValueAsString(newProduct)))
		
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		.andExpect(jsonPath("$.productId", is(5)))
		.andExpect(jsonPath("$.productName", is("Nokia")))
		.andExpect(jsonPath("$.description", is("Nokia6.1+")))
		.andExpect(jsonPath("$.price", is(77560.54)))
		.andExpect(jsonPath("$.starRating", is(3.9)));
		
	}

	@Test
	@DisplayName("Test Find Product By Price Greater Than- GET /api/v1/products/find/gt/{price}")
	public void testFindPriceGreaterThan() throws Exception{

//		Product product1 = new Product("Vivo","VivoPro12",37560.54,3.9);
//		product1.setProductId(1);
//		
//		Product product2 = new Product("OnePlus","OnePlus8Pro",40000.00,4.7);
//		product2.setProductId(2);
//		
//		Product product3 = new Product("Sasmung","GalaxyNote12",50000.00,4.1);
//		product3.setProductId(3);
//		
//		Product product4 = new Product("OnePlus","OnePlus9Pro",60000.00,4.5);
//		product4.setProductId(4);
//		
//		List<Product> products = new ArrayList<>();
//		products.add(product1);
//		products.add(product2);
//		products.add(product3);
//		products.add(product4);

		double price = 50000.00;
		
//		doReturn(Optional.of(products)).when(repository).findByPriceGreaterThan(price);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/find/gt/{price}",price))
		
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		.andExpect(jsonPath("$[0].productId", is(4)))
		.andExpect(jsonPath("$[0].productName", is("OnePlus")))
		.andExpect(jsonPath("$[0].description", is("OnePlus9Pro")))
		.andExpect(jsonPath("$[0].price", is(60000.00)))
		.andExpect(jsonPath("$[0].starRating", is(4.5)));
		
	}

	@Test
	@DisplayName("Test Find Product By Price Greater Than Equal- GET /api/v1/products/find/gteq/{price}")
	public void testFindPriceGreaterThanEqual() throws Exception{

//		Product product1 = new Product("Vivo","VivoPro12",37560.54,3.9);
//		product1.setProductId(1);
//		
//		Product product2 = new Product("OnePlus","OnePlus8Pro",40000.00,4.7);
//		product2.setProductId(2);
//		
//		Product product3 = new Product("Sasmung","GalaxyNote12",50000.00,4.1);
//		product3.setProductId(3);
//		
//		Product product4 = new Product("OnePlus","Oneplus9Pro",60000.00,4.5);
//		product4.setProductId(4);
//		
//		List<Product> products = new ArrayList<>();
//		products.add(product1);
//		products.add(product2);
//		products.add(product3);
//		products.add(product4);
//		
		double price = 50000.00;
		
//		doReturn(Optional.of(products)).when(repository).findByPriceGreaterThanEqual(price);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/find/gteq/{price}",price))
		
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		.andExpect(jsonPath("$[0].productId", is(3)))
		.andExpect(jsonPath("$[0].productName", is("Sasmung")))
		.andExpect(jsonPath("$[0].description", is("GalaxyNote12")))
		.andExpect(jsonPath("$[0].price", is(50000.00)))
		.andExpect(jsonPath("$[0].starRating", is(4.1)))
		
		.andExpect(jsonPath("$[1].productId", is(4)))
		.andExpect(jsonPath("$[1].productName", is("OnePlus")))
		.andExpect(jsonPath("$[1].description", is("OnePlus9Pro")))
		.andExpect(jsonPath("$[1].price", is(60000.00)))
		.andExpect(jsonPath("$[1].starRating", is(4.5)));
		
	}

	@Test
	@DisplayName("Test Find Product By Name Or Price - GET /api/v1/products/find/ByNameOrPrice/")
	public void testFindProductNameOrPrice() throws Exception{

//		Product product1 = new Product("Vivo","VivoPro12",37560.54,3.9);
//		product1.setProductId(1);
//		
//		Product product2 = new Product("OnePlus","OnePlus8Pro",40000.00,4.7);
//		product2.setProductId(2);
//		
//		Product product3 = new Product("Sasmung","GalaxyNote12",50000.00,4.1);
//		product3.setProductId(3);
//		
//		Product product4 = new Product("OnePlus","OnePlus9Pro",60000.00,4.5);
//		product4.setProductId(4);
//		
//		List<Product> products = new ArrayList<>();
//		products.add(product1);
//		products.add(product2);
//		products.add(product3);
//		products.add(product4);
//		
//		String productName = "OnePlus";
		Double price = 50000.00;
//		
//		doReturn(Optional.of(products)).when(repository).findByProductNameOrPrice(productName, price);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/find/ByNameOrPrice/")
//				.queryParam("productName",productName))
				.queryParam("price", price.toString()))
		
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
//		.andExpect(jsonPath("$[0].productId", is(2)))
//		.andExpect(jsonPath("$[0].productName", is("OnePlus")))
//		.andExpect(jsonPath("$[0].description", is("OnePlus8Pro")))
//		.andExpect(jsonPath("$[0].price", is(40000.00)))
//		.andExpect(jsonPath("$[0].starRating", is(4.7)))
//
//		.andExpect(jsonPath("$[1].productId", is(4)))
//		.andExpect(jsonPath("$[1].productName", is("OnePlus")))
//		.andExpect(jsonPath("$[1].description", is("OnePlus9Pro")))
//		.andExpect(jsonPath("$[1].price", is(60000.00)))
//		.andExpect(jsonPath("$[1].starRating", is(4.5)));
		
		.andExpect(jsonPath("$[0].productId", is(3)))
		.andExpect(jsonPath("$[0].productName", is("Sasmung")))
		.andExpect(jsonPath("$[0].description", is("GalaxyNote12")))
		.andExpect(jsonPath("$[0].price", is(50000.00)))
		.andExpect(jsonPath("$[0].starRating", is(4.1)));
		
	}	

	

}
