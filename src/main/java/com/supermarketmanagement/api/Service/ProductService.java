package com.supermarketmanagement.api.Service;

import java.util.Map;

import com.supermarketmanagement.api.Model.Custom.ApiResponse;
import com.supermarketmanagement.api.Model.Custom.Product.ProductFilterRequest;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;

public interface ProductService {

	Object updateProduct(ProductModel updatedProduct);

	Object getProductDetailsById(int id);

	Object addProductDetails(ProductListDto productModel);

	Object deleteProductById(Long id);

	Object	getProductPriceHistoryById(Long id);

	Map<String, Object> getAllProductDetails(ProductFilterRequest request);
		
}
