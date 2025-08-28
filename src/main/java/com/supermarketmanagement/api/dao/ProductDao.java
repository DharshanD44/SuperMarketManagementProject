package com.supermarketmanagement.api.dao;

import java.time.LocalDate;
import java.util.List;

import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;

public interface ProductDao {

	List<ProductListDto> getAllProductDetails();

	ProductModel updateProduct(ProductModel updatedProduct);

	ProductModel getProductDetailsById(int id);

	List<ActiveProductsListDto> getActiveProductDetails(LocalDate date);

	List<ActiveProductsListDto> getInActiveProductDetails(LocalDate date);

	ProductModel addProductDetails(ProductModel productModel);

	Object deleteProductById(Long id);

	List<ProductPriceHistoryDto> getProductPriceHistoryById(Long id);

}
