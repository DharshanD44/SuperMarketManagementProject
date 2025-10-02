package com.supermarketmanagement.api.Restcontroller;

import java.io.IOException;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.supermarketmanagement.api.Model.Custom.Product.ProductFilterRequest;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Service.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/products")
public class ProductRestController {
	
	@Autowired
	private ProductService productService;
		
	/**
	 * Retrieves list of products based on filter/search criteria.
	 *
	 * @param request request body
	 * @return ResponseEntity with list of products
	 */
	@PostMapping("/list/all")
	public ResponseEntity<?> getProducts(@RequestBody ProductFilterRequest productFilterRequest) {
	    return ResponseEntity.ok(productService.getAllProductDetails(productFilterRequest));
	}
	
	
	/**
	 * Adds a new product.
	 *
	 * @param productModel request body
	 * @return ResponseEntity with success message after adding new product
	 */
	@PostMapping("/save")
	public ResponseEntity<?> addSingleProduct(@RequestBody ProductListDto productModel){
		return ResponseEntity.ok(productService.addProductDetails(productModel));
	}

	
	/**
	 * Updates a product.
	 *
	 * @param updatedProduct request body
	 * @return ResponseEntity with success message after updation
	 */
	@PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductListDto updatedProduct) {
        return ResponseEntity.ok(productService.updateProduct(updatedProduct));
    }
	
	
	/**
	 * Deactivates a product.
	 *
	 * @param productid path variable
	 * @param expiry_date path variable
	 * @return ResponseEntity with success message after deactivating product
	 */
	@PostMapping("/deactivate")
	public ResponseEntity<?> deactivateProduct(@RequestParam Long productid, @RequestParam @DateTimeFormat(pattern = "MM-dd-yyyy")LocalDate expirydate){
		return ResponseEntity.ok(productService.deactivateProduct(productid, expirydate));
	}
	
	
	/**
	 * Deletes a product by ID.
	 *
	 * @param productid path variable
	 * @return ResponseEntity with success message after deletion
	 */
	@PostMapping("/delete/productid/{productid}")
	public ResponseEntity<?> deleteProductById(@PathVariable Long productid){
		return ResponseEntity.ok(productService.deleteProductById(productid));
	}

	
	@GetMapping("/get/productid/{productid}")
	public ResponseEntity<?> getProductDetailsById(@PathVariable Long productid){
		return ResponseEntity.ok(productService.getProductDetailsById(productid));
	}
	
			
			
			
	/**
	 * Retrieves product price history by ID.
	 *
	 * @param productid path variable
	 * @return ResponseEntity with list of products based on price change
	 */
	@GetMapping("/pricehistory/productid/{productid}")
	public ResponseEntity<?> getProductPriceHistoryById(@PathVariable Long productid){
		return ResponseEntity.ok(productService.getProductPriceHistoryById(productid));
	}
	
	
	/**
	 * Downloads the product list as an Excel file.
	 *
	 * @param response - HTTP response to write the file
	 * @param request - filter criteria for product list
	 * @throws IOException if writing the file fails
	 */
	@PostMapping("/list/download")
	public void downloadProducts(HttpServletResponse response,
	                             @RequestBody ProductFilterRequest request) throws IOException {
	    

	    productService.exportProductsToExcel(response, request);
	}

}

