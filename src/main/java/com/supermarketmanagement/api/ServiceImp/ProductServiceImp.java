package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.CommonResponse;
import com.supermarketmanagement.api.Model.Custom.Product.ProductFilterRequest;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Service.ProductService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.ProductDao;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SuperMarketCodeDao superMarketCodeDao;

	@Override
	public Object updateProduct(ProductModel updatedProduct) {

		ProductModel existingProduct = productDao.findByProductId(updatedProduct.getProductId());
		CommonResponse responseMessage = new CommonResponse();
		if (existingProduct == null) {
			responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
			responseMessage.setMessage(WebServiceUtil.PRODUCT_ID_NOT_FOUND);
			return responseMessage;
		}

		else {

			if (existingProduct.getProductEffectiveDate().isAfter(LocalDate.now())) {
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
			} else {
				SuperMarketCode inactiveCode = superMarketCodeDao.findByCode("IA");
				existingProduct.setProductLastEffectiveDate(LocalDate.now());
				existingProduct.setProductUpdatedtedDate(LocalDateTime.now());
				existingProduct.setProductStatus(inactiveCode);

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
			}
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setMessage(WebServiceUtil.PRODUCT_UPDATED);
			return responseMessage;
		}
	}

	@Override
	public Object getProductDetailsById(int id) {
		ProductModel model = productDao.getProductDetailsById(id);
		CommonResponse commonResponse = new CommonResponse();
		if (model == null) {
			commonResponse.setStatus(WebServiceUtil.FAILED_STATUS);
			commonResponse.setData(null);
			return commonResponse;
		} else {
			commonResponse.setData(model);
			commonResponse.setStatus(WebServiceUtil.SUCCESS_STATUS);
			return commonResponse;
		}
	}

	@Override
	public Object addProductDetails(ProductListDto productModel) {

		ProductModel dto = new ProductModel();
		CommonResponse responseMessage = new CommonResponse();
		dto.setProductCreatedDate(LocalDateTime.now());
		dto.setProductCurrentStockPackageCount(productModel.getProductCurrentStockPackageCount());
		dto.setProductEffectiveDate(productModel.getProductEffectiveDate());
		dto.setProductLastEffectiveDate(productModel.getProductLastEffectiveDate());
		dto.setProductName(productModel.getProductName());
		dto.setProductPackageType(productModel.getProductPackageType());
		dto.setProductPackQuantity(productModel.getProductPackQuantity());
		dto.setProductPackageUnitOfMeasure(productModel.getProductPackageUnitOfMeasure());
		dto.setProductPrice(productModel.getProductPrice());
	    SuperMarketCode status = superMarketCodeDao.findByDescription(productModel.getProductStatus());
	    dto.setProductStatus(status);

		productDao.saveProduct(dto);

		responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
		responseMessage.setMessage(WebServiceUtil.NEW_PRODUCT_ADDED);
		return responseMessage;
	}

	@Override
	public Object deleteProductById(Long id) {

		ProductModel existingProduct = productDao.findByProductId(id);
		CommonResponse responseMessage = new CommonResponse();
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
	public Object getProductPriceHistoryById(Long activeId) {
	    List<ProductPriceHistoryDto> result = new ArrayList<>();
	    Long currentId = activeId;

	    while (currentId != null) {
	        ProductModel product = productDao.findByProductId(currentId);

	        if (product == null) {
	            
	        }

	        result.add(new ProductPriceHistoryDto(
	                product.getProductId(),
	                product.getProductName(),
	                product.getProductPrice(),
	                product.getProductEffectiveDate()
	        ));

	        currentId = product.getOldProductId();
	    }
	    CommonResponse response = new CommonResponse();
	    response.setData(result);
	    response.setStatus(WebServiceUtil.SUCCESS_STATUS);
	    return response;
	}

	@Override
	public Map<String, Object> getAllProductDetails(ProductFilterRequest request) {
		return productDao.getAllProductDetails(request);
	}
}
