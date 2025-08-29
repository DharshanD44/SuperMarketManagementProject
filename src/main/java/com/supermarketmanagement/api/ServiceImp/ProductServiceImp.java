package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductMessageDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Service.ProductService;
import com.supermarketmanagement.api.dao.ProductDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImp implements ProductService{
	
	@Autowired
	private ProductDao productDao;

	@Override
	public List<ProductListDto> getAllProductDetails() {

		return productDao.getAllProductDetails();
	}

	@Override
	public Object updateProduct(ProductModel updatedProduct) {

		Optional<ProductModel> optionalProduct = productDao.findByProductId(updatedProduct.getProductId());
		
		ProductModel existingProduct = optionalProduct.get();

		if (optionalProduct.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not Found!");
		}

		else {

			if (existingProduct.getProductEffectiveDate().isAfter(LocalDate.now())) 
			{
				if (updatedProduct.getProductEffectiveDate() != null)
					existingProduct.setProductEffectiveDate(updatedProduct.getProductEffectiveDate());

				existingProduct.setProductName(updatedProduct.getProductName());
				existingProduct.setProductPackageType(updatedProduct.getProductPackageType());
				existingProduct.setProductPackQuantity(updatedProduct.getProductPackQuantity());
				existingProduct.setProductPackageUnitOfMeasure(updatedProduct.getProductPackageUnitOfMeasure());
				existingProduct.setProductPrice(updatedProduct.getProductPrice());
				existingProduct.setProductCreatedDate(LocalDate.now());
				existingProduct.setProductCurrentStockPackageCount(updatedProduct.getProductCurrentStockPackageCount());
				existingProduct.setProductUpdatedtedDate(LocalDate.now());

				return existingProduct;
			} 
			else {
					existingProduct.setProductLastEffectiveDate(LocalDate.now());

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
					newProduct.setProductCreatedDate(LocalDate.now());
					newProduct.setProductUpdatedtedDate(LocalDate.now());
					return newProduct;
				}
		}
		 
	}

	@Override
	public ProductModel getProductDetailsById(int id) {
		
		return productDao.getProductDetailsById(id);
	}

	@Override
	public List<ActiveProductsListDto> getActiveProductDetails(LocalDate date) {
		
		return productDao.getActiveProductDetails(date);
	}

	@Override
	public List<ActiveProductsListDto> getInActiveProductDetails(LocalDate date) {
		
		return productDao.getInActiveProductDetails(date);
	}

	@Override
	public Object addProductDetails(ProductModel productModel) {
		
		ProductModel dto = new ProductModel();

		dto.setProductCreatedDate(LocalDate.now());
		dto.setProductCurrentStockPackageCount(productModel.getProductCurrentStockPackageCount());
		dto.setProductEffectiveDate(productModel.getProductEffectiveDate());
		dto.setProductLastEffectiveDate(productModel.getProductLastEffectiveDate());
		dto.setProductName(productModel.getProductName());
		dto.setProductPackageType(productModel.getProductPackageType());
		dto.setProductPackQuantity(productModel.getProductPackQuantity());
		dto.setProductPackageUnitOfMeasure(productModel.getProductPackageUnitOfMeasure());
		dto.setProductPrice(productModel.getProductPrice());
		
		return productDao.addProductDetails(dto);
	}

	@Override
	public Object deleteProductById(Long id) {
		
		ProductModel model = productDao.findByProductId(id)
				.orElseThrow(() -> new RuntimeException(ProductMessageDto.PRODUCT_ID_NOT_FOUND));
		model.setIsDeleted(true);
		return model;
		
	}

	@Override
	public List<ProductPriceHistoryDto> getProductPriceHistoryById(Long activeid) {
		
		List<ProductPriceHistoryDto> result = new ArrayList<>();
		Long currentId = activeid;

		while (currentId != null) {
			ProductModel model = productDao.findByProductId(currentId)
					.orElseThrow(() -> new RuntimeException(ProductMessageDto.PRODUCT_ID_NOT_FOUND + activeid));
			
			result.add(
					new ProductPriceHistoryDto(
					model.getProductId(), 
					model.getProductName(), 
					model.getProductPrice(),
					model.getProductEffectiveDate()));
			currentId = model.getOldProductId();
		}

//		result.sort(Comparator.comparing(ProductPriceHistoryDto :: getProductEffectiveDate));

		return result;
		
	}

}
