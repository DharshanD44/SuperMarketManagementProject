package com.supermarketmanagement.api.Restcontroller;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.supermarketmanagement.api.Model.Custom.CommonListResponse;
import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.InactiveProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Product.ProductMessageDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
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
	public ResponseEntity<?> getProducts(@RequestBody ProductListRequestModel request) {
	    return ResponseEntity.ok(productService.getAllProductDetails(request));
	}

	
	@PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductModel updatedProduct) {
		
        return ResponseEntity.ok(productService.updateProduct(updatedProduct));
    }
	
	@GetMapping("/view/id/{id}")
	public ResponseEntity<?> getProductDetailsById(@PathVariable int id){
	    return ResponseEntity.ok(productService.getProductDetailsById(id));
	}
	
	@GetMapping("/view/active")
	public ResponseEntity<List<ActiveProductsListDto>> getActiveProductDetails(@RequestParam("date") LocalDateTime date)
	{
		return new ResponseEntity<List<ActiveProductsListDto>>(productService.getActiveProductDetails(date),HttpStatus.OK);
	}
	
	@GetMapping("/view/Inactive")
	public ResponseEntity<List<InactiveProductListDto>> getInActiveProductDetails(@RequestParam("date") LocalDateTime date)
	{
		return new ResponseEntity<List<InactiveProductListDto>>(productService.getInActiveProductDetails(date),HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addSingleProduct(@RequestBody ProductModel productModel){
		
		return ResponseEntity.ok(productService.addProductDetails(productModel));
	}
	
	@PostMapping("/delete/id/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable Long id){
		
		return ResponseEntity.ok(productService.deleteProductById(id));
	}
	
	
	@GetMapping("/pricehistory/id/{id}")
	public ResponseEntity<CommonListResponse<?>> getProductPriceHistoryById(@PathVariable Long id){
		
		return new ResponseEntity<CommonListResponse<?>>(productService.getProductPriceHistoryById(id),HttpStatus.OK);	
	}
	
	
	
	
}
