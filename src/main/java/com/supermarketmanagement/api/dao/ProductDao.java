package com.supermarketmanagement.api.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;

public interface ProductDao {

	List<ProductListDto> getAllProductDetails();

	ProductModel getProductDetailsById(int id);

	List<ActiveProductsListDto> getActiveProductDetails(LocalDate date);

	List<ActiveProductsListDto> getInActiveProductDetails(LocalDate date);

	Object addProductDetails(ProductModel productModel);

	Optional<ProductModel> findByProductId(Long productId);

	Object saveProduct(ProductModel newProduct);

}
