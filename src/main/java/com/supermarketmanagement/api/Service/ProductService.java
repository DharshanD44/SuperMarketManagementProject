package com.supermarketmanagement.api.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;
import com.supermarketmanagement.api.Model.Custom.Product.ProductFilterRequest;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;

import jakarta.servlet.http.HttpServletResponse;

public interface ProductService {

	Object updateProduct(ProductListDto updatedProduct);

	Object addProductDetails(ProductListDto productModel);

	Object deleteProductById(Long id);

	Map<String, Object>	getProductPriceHistoryById(Long id);

	Object getAllProductDetails(ProductFilterRequest request);

	Object deactivateProduct(Long productid, LocalDate expirydate);

	void exportProductsToExcel(HttpServletResponse response, ProductFilterRequest request) throws IOException;

	CommonMessageResponse getProductDetailsById(Long productid);
		
}
