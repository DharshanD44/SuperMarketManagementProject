package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Service.ProductService;
import com.supermarketmanagement.api.dao.ProductDao;

@Service
public class ProductServiceImp implements ProductService{
	
	@Autowired
	private ProductDao productDao;

	@Override
	public List<ProductListDto> getAllProductDetails() {

		return productDao.getAllProductDetails();
	}

	@Override
	public ProductModel updateProduct(ProductModel updatedProduct) {

		return productDao.updateProduct(updatedProduct);
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
	public ProductModel addProductDetails(ProductModel productModel) {
		
		ProductModel dto = new ProductModel();

		dto.setProductCreatedDate(LocalDate.now());
		dto.setProductCurrentStockPackageCount(productModel.getProductCurrentStockPackageCount());
		dto.setProductEffectiveDate(productModel.getProductEffectiveDate());
		dto.setProductLastEffectiveDate(productModel.getProductLastEffectiveDate());
		dto.setProductName(productModel.getProductName());
		dto.setProductPackageType(productModel.getProductPackageType());
		dto.setProductPackQuantity(productModel.getProductPackQuantity());
		dto.setProductUpdatedtedDate(LocalDate.now());
		dto.setProductPackageUnitOfMeasure(productModel.getProductPackageUnitOfMeasure());
		dto.setProductPrice(productModel.getProductPrice());
		
		return productDao.addProductDetails(dto);
	}

	@Override
	public Object deleteProductById(Long id) {
		
		return productDao.deleteProductById(id);
		
	}

	@Override
	public List<ProductPriceHistoryDto> getProductPriceHistoryById(Long id) {
		
		return productDao.getProductPriceHistoryById(id);
	}

}
