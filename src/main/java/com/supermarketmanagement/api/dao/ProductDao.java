package com.supermarketmanagement.api.dao;


import java.util.List;
import java.util.Map;

import com.supermarketmanagement.api.Model.Custom.Product.ProductFilterRequest;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;

public interface ProductDao {

	ProductModel getProductDetailsById(int id);

	ProductModel findByProductId(Long productId);

	Object saveProduct(ProductModel newProduct);

	Map<String, Object> getAllProductDetails(ProductFilterRequest request);

}
