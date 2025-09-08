package com.supermarketmanagement.api.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.InactiveProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;

public interface ProductDao {

//	List<ProductListDto> getAllProductDetails();

	ProductModel getProductDetailsById(int id);

	List<ActiveProductsListDto> getActiveProductDetails(LocalDateTime date);

	List<InactiveProductListDto> getInActiveProductDetails(LocalDateTime date);

	Object addProductDetails(ProductModel productModel);

	ProductModel findByProductId(Long productId);

	Object saveProduct(ProductModel newProduct);

	Map<String, Object> getAllProductDetails(ProductListRequestModel request);

}
