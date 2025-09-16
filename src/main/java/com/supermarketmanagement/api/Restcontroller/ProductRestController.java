package com.supermarketmanagement.api.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.supermarketmanagement.api.Model.Custom.Product.ProductFilterRequest;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Service.ProductService;

@RestController
@RequestMapping(value = "/product")
public class ProductRestController {
	
	@Autowired
	private ProductService productService;
	
//	@GetMapping("/list")
//	public ResponseEntity<List<ProductListDto>>  getAllProductDetails(){
//		
//		return new ResponseEntity<List<ProductListDto>>(productService.getAllProductDetails(),HttpStatus.OK);
//	}
		
	@GetMapping("/list")
	public ResponseEntity<?> getProducts(@RequestBody ProductFilterRequest  request) {
	    return ResponseEntity.ok(productService.getAllProductDetails(request));
	}

	
	@PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductModel updatedProduct) {
		
        return ResponseEntity.ok(productService.updateProduct(updatedProduct));
    }
	
//	@PostMapping("/add")
//	public ResponseEntity<?> addSingleProduct(@RequestBody ProductListDto productModel){
//		
//		return ResponseEntity.ok(productService.addProductDetails(productModel));
//	}
	
	@PostMapping("/delete/id/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable Long id){
		
		return ResponseEntity.ok(productService.deleteProductById(id));
	}
	
	
	@GetMapping("/pricehistory/id/{id}")
	public ResponseEntity<?> getProductPriceHistoryById(@PathVariable Long id){
		return ResponseEntity.ok(productService.getProductPriceHistoryById(id));
	
	}
	
}
