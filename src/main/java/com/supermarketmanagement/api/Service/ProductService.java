package com.supermarketmanagement.api.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatusCode;

import com.supermarketmanagement.api.Model.Custom.CommonListResponse;
import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.InactiveProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;

public interface ProductService {

	Object updateProduct(ProductModel updatedProduct);

	Object getProductDetailsById(int id);

	List<ActiveProductsListDto> getActiveProductDetails(LocalDateTime date);

	List<InactiveProductListDto> getInActiveProductDetails(LocalDateTime date);

	Object addProductDetails(ProductModel productModel);

	Object deleteProductById(Long id);

	CommonListResponse<?> getProductPriceHistoryById(Long id);

	Map<String, Object> getAllProductDetails(ProductListRequestModel request);
	
}
