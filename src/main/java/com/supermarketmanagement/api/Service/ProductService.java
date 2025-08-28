package com.supermarketmanagement.api.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatusCode;

import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;

public interface ProductService {

	List<ProductListDto> getAllProductDetails();

	ProductModel updateProduct(ProductModel updatedProduct);

	ProductModel getProductDetailsById(int id);

	List<ActiveProductsListDto> getActiveProductDetails(LocalDate date);

	List<ActiveProductsListDto> getInActiveProductDetails(LocalDate date);

	ProductModel addProductDetails(ProductModel productModel);

	Object deleteProductById(Long id);

	List<ProductPriceHistoryDto> getProductPriceHistoryById(Long id);
	
}
