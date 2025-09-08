package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.supermarketmanagement.api.Model.Custom.CommonListResponse;
import com.supermarketmanagement.api.Model.Custom.ResponseData;
import com.supermarketmanagement.api.Model.Custom.ResponseMessage;
import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.InactiveProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Product.ProductMessageDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Service.ProductService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.ProductDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public Object updateProduct(ProductModel updatedProduct) {

		ProductModel existingProduct = productDao.findByProductId(updatedProduct.getProductId());
		ResponseMessage responseMessage = new ResponseMessage();
		if (existingProduct == null) {
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setMessage(WebServiceUtil.PRODUCT_ID_NOT_FOUND);
			return responseMessage;
		}

		else {

			if (existingProduct.getProductEffectiveDate().isAfter(LocalDateTime.now())) {
				if (updatedProduct.getProductEffectiveDate() != null)
					existingProduct.setProductEffectiveDate(updatedProduct.getProductEffectiveDate());

				existingProduct.setProductName(updatedProduct.getProductName());
				existingProduct.setProductPackageType(updatedProduct.getProductPackageType());
				existingProduct.setProductPackQuantity(updatedProduct.getProductPackQuantity());
				existingProduct.setProductPackageUnitOfMeasure(updatedProduct.getProductPackageUnitOfMeasure());
				existingProduct.setProductPrice(updatedProduct.getProductPrice());
				existingProduct.setProductCreatedDate(LocalDateTime.now());
				existingProduct.setProductCurrentStockPackageCount(updatedProduct.getProductCurrentStockPackageCount());
				existingProduct.setProductUpdatedtedDate(LocalDateTime.now());
				responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
				responseMessage.setMessage(WebServiceUtil.PRODUCT_UPDATED);
				return responseMessage;
			} else {
				existingProduct.setProductLastEffectiveDate(LocalDateTime.now());

				ProductModel newProduct = new ProductModel();
				newProduct.setProductName(updatedProduct.getProductName());
				newProduct.setProductPackageType(updatedProduct.getProductPackageType());
				newProduct.setProductPackQuantity(updatedProduct.getProductPackQuantity());
				newProduct.setProductPackageUnitOfMeasure(updatedProduct.getProductPackageUnitOfMeasure());
				newProduct.setProductPrice(updatedProduct.getProductPrice());
				newProduct.setProductCurrentStockPackageCount(updatedProduct.getProductCurrentStockPackageCount());
				newProduct.setProductEffectiveDate(updatedProduct.getProductEffectiveDate());
				newProduct.setProductLastEffectiveDate(updatedProduct.getProductLastEffectiveDate());
				newProduct.setOldProductId(existingProduct.getProductId());
				newProduct.setProductCreatedDate(LocalDateTime.now());
				newProduct.setProductUpdatedtedDate(LocalDateTime.now());
				productDao.saveProduct(newProduct);
				responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
				responseMessage.setMessage(WebServiceUtil.PRODUCT_UPDATED);
				return responseMessage;
			}
		}
	}

	@Override
	public Object getProductDetailsById(int id) {
		ProductModel model = productDao.getProductDetailsById(id);
		ResponseData responseData = new ResponseData();
		if (model == null) {
			responseData.setStatus(WebServiceUtil.FAILED_STATUS);
			responseData.setData(null);
			return responseData;
		} else {
			responseData.setData(model);
			responseData.setStatus(WebServiceUtil.SUCCESS_STATUS);
			return responseData;
		}
	}

	@Override
	public List<ActiveProductsListDto> getActiveProductDetails(LocalDateTime date) {

		return productDao.getActiveProductDetails(date);
	}

	@Override
	public List<InactiveProductListDto> getInActiveProductDetails(LocalDateTime date) {

		return productDao.getInActiveProductDetails(date);
	}

	@Override
	public Object addProductDetails(ProductModel productModel) {

		ProductModel dto = new ProductModel();
		ResponseMessage responseMessage = new ResponseMessage();
		dto.setProductCreatedDate(LocalDateTime.now());
		dto.setProductCurrentStockPackageCount(productModel.getProductCurrentStockPackageCount());
		dto.setProductEffectiveDate(productModel.getProductEffectiveDate());
		dto.setProductLastEffectiveDate(productModel.getProductLastEffectiveDate());
		dto.setProductName(productModel.getProductName());
		dto.setProductPackageType(productModel.getProductPackageType());
		dto.setProductPackQuantity(productModel.getProductPackQuantity());
		dto.setProductPackageUnitOfMeasure(productModel.getProductPackageUnitOfMeasure());
		dto.setProductPrice(productModel.getProductPrice());
		productDao.addProductDetails(dto);

		responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
		responseMessage.setMessage(WebServiceUtil.NEW_PRODUCT_ADDED);
		return responseMessage;
	}

	@Override
	public Object deleteProductById(Long id) {

		ProductModel existingProduct = productDao.findByProductId(id);
		ResponseMessage responseMessage = new ResponseMessage();
		if (existingProduct == null) {
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setMessage(WebServiceUtil.PRODUCT_ID_NOT_FOUND);
			return responseMessage;
		}
		existingProduct.setIsDeleted(true);
		responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
		responseMessage.setMessage(WebServiceUtil.PRODUCT_DELETED);
		return responseMessage;
	}

	@Override
	public CommonListResponse<?> getProductPriceHistoryById(Long activeId) {
	    List<ProductPriceHistoryDto> result = new ArrayList<>();
	    Long currentId = activeId;

	    while (currentId != null) {
	        ProductModel product = productDao.findByProductId(currentId);

	        if (product == null) {
	            CommonListResponse<String> failResponse = new CommonListResponse<>();
	            failResponse.setStatus(WebServiceUtil.FAILED_STATUS);
	            failResponse.setData(WebServiceUtil.PRODUCT_ID_NOT_FOUND);
	            return failResponse;
	        }

	        result.add(new ProductPriceHistoryDto(
	                product.getProductId(),
	                product.getProductName(),
	                product.getProductPrice(),
	                product.getProductEffectiveDate()
	        ));

	        currentId = product.getOldProductId();
	    }

	    CommonListResponse<List<ProductPriceHistoryDto>> successResponse = new CommonListResponse<>();
	    successResponse.setStatus(WebServiceUtil.SUCCESS_STATUS);
	    successResponse.setData(result);
	    return successResponse;
	}


	@Override
	public Map<String, Object> getAllProductDetails(ProductListRequestModel request) {
		return productDao.getAllProductDetails(request);
	}

}
