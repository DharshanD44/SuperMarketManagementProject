package com.supermarketmanagement.api.Restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductMessageDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Service.ProductService;

@RestController
@RequestMapping(value = "/product")
public class ProductRestController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/list")
	public ResponseEntity<List<ProductListDto>>  getAllProductDetails(){
		
		return new ResponseEntity<List<ProductListDto>>(productService.getAllProductDetails(),HttpStatus.OK);
	}
	
	@PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductModel updatedProduct) {
		productService.updateProduct(updatedProduct);
        return ResponseEntity.ok(ProductMessageDto.PRODUCT_UPDATED);
    }
	
	@GetMapping("/view/id/{id}")
	public ResponseEntity<ProductModel> getProductDetailsById(@PathVariable int id){
	    return new ResponseEntity<ProductModel>(productService.getProductDetailsById(id), HttpStatus.OK);
	}
	
	@GetMapping("/view/active")
	public ResponseEntity<List<ActiveProductsListDto>> getActiveProductDetails(@RequestParam("date") LocalDate date)
	{
		return new ResponseEntity<List<ActiveProductsListDto>>(productService.getActiveProductDetails(date),HttpStatus.OK);
	}
	
	@GetMapping("/view/Inactive")
	public ResponseEntity<List<ActiveProductsListDto>> getInActiveProductDetails(@RequestParam("date") LocalDate date)
	{
		return new ResponseEntity<List<ActiveProductsListDto>>(productService.getInActiveProductDetails(date),HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addSingleProduct(@RequestBody ProductModel productModel){
		productService.addProductDetails(productModel);
		return ResponseEntity.ok(ProductMessageDto.NEW_PRODUCT_ADDED);
	}
	
	@PostMapping("/delete/id/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable Long id){
		productService.deleteProductById(id);
		return ResponseEntity.ok(ProductMessageDto.PRODUCT_DELETED);
	}
	
	
	@GetMapping("/pricehistory/id/{id}")
	public ResponseEntity<List<ProductPriceHistoryDto>> getProductPriceHistoryById(@PathVariable Long id){
		return new ResponseEntity<List<ProductPriceHistoryDto>>(productService.getProductPriceHistoryById(id),HttpStatus.OK);	
	}
	
	
	
	
}
