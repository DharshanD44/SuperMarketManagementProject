package com.supermarketmanagement.api.daoImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductMessageDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Repository.ProductRepository;
import com.supermarketmanagement.api.dao.ProductDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ProductDaoImp implements ProductDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<ProductListDto> getAllProductDetails() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductListDto> criteriaQuery = cb.createQuery(ProductListDto.class);

		Root<ProductModel> root = criteriaQuery.from(ProductModel.class);
		criteriaQuery
				.multiselect(root.get("productId"), root.get("productName"), root.get("productPackageType"),
						root.get("productPackQuantity"), root.get("productPackageUnitOfMeasure"),
						root.get("productPrice"), root.get("productCurrentStockPackageCount"),
						root.get("productEffectiveDate"), root.get("productLastEffectiveDate"),
						root.get("oldProductId"), root.get("productCreatedDate"), root.get("productUpdatedtedDate"))
				.where(cb.isFalse(root.get("isDeleted")));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public ProductModel updateProduct(ProductModel updatedProduct) {

		Optional<ProductModel> optionalProduct = productRepository.findById(updatedProduct.getProductId());
		ProductModel existingProduct = optionalProduct.get();

		if (optionalProduct.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not Found!");
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
				existingProduct.setProductCreatedDate(LocalDate.now());
				existingProduct.setProductCurrentStockPackageCount(updatedProduct.getProductCurrentStockPackageCount());
				existingProduct.setProductUpdatedtedDate(LocalDate.now());

				return productRepository.save(existingProduct);
			} else {
				existingProduct.setProductLastEffectiveDate(updatedProduct.getProductLastEffectiveDate());
				productRepository.save(existingProduct);

				ProductModel newProduct = new ProductModel();
				newProduct.setProductName(updatedProduct.getProductName());
				newProduct.setProductPackageType(updatedProduct.getProductPackageType());
				newProduct.setProductPackQuantity(updatedProduct.getProductPackQuantity());
				newProduct.setProductPackageUnitOfMeasure(updatedProduct.getProductPackageUnitOfMeasure());
				newProduct.setProductPrice(updatedProduct.getProductPrice());
				newProduct.setProductCurrentStockPackageCount(updatedProduct.getProductCurrentStockPackageCount());
				newProduct.setProductEffectiveDate(updatedProduct.getProductEffectiveDate());
				newProduct.setOldProductId(existingProduct.getProductId());
				newProduct.setProductCreatedDate(LocalDate.now());
				newProduct.setProductUpdatedtedDate(LocalDate.now());

				return productRepository.save(newProduct);
			}

		}

	}

	@Override
	public ProductModel getProductDetailsById(int id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductModel> criteriaQuery = cb.createQuery(ProductModel.class);
		Root<ProductModel> root = criteriaQuery.from(ProductModel.class);
		Predicate predicate = cb.equal(root.get("productId"), id);
		criteriaQuery.where(predicate);
		try {
			ProductModel result = entityManager.createQuery(criteriaQuery).getSingleResult();
			return result;
		} catch (NoResultException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + " not found!");
		}
	}

	@Override
	public List<ActiveProductsListDto> getActiveProductDetails(LocalDate date) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ActiveProductsListDto> criteriaQuery = cb.createQuery(ActiveProductsListDto.class);

		Root<ProductModel> root = criteriaQuery.from(ProductModel.class);
		Predicate predicate = cb.lessThanOrEqualTo(root.get("productEffectiveDate"), date);
		Predicate predicate1 = cb.or(cb.isNull(root.get("productLastEffectiveDate")),
				cb.greaterThanOrEqualTo(root.get("productLastEffectiveDate"), date));
		criteriaQuery.select(cb.construct(ActiveProductsListDto.class, root.get("productId"), root.get("productName"),
				root.get("productPackageType"), root.get("productPackQuantity"),
				root.get("productPackageUnitOfMeasure"), root.get("productPrice"),
				root.get("productCurrentStockPackageCount"), root.get("productEffectiveDate"),
				root.get("productCreatedDate"))).where(cb.and(predicate, predicate1));

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<ActiveProductsListDto> getInActiveProductDetails(LocalDate date) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ActiveProductsListDto> criteriaQuery = cb.createQuery(ActiveProductsListDto.class);

		Root<ProductModel> root = criteriaQuery.from(ProductModel.class);
		Predicate effectivePredicate = cb.greaterThan(root.get("productEffectiveDate"), date);

		Predicate ExpiredPredicate = cb.and(cb.isNotNull(root.get("productLastEffectiveDate")),
				cb.lessThan(root.get("productLastEffectiveDate"), date));

		Predicate inactivePredicate = cb.or(effectivePredicate, ExpiredPredicate);

		criteriaQuery.select(cb.construct(ActiveProductsListDto.class, root.get("productId"), root.get("productName"),
				root.get("productPackageType"), root.get("productPackQuantity"),
				root.get("productPackageUnitOfMeasure"), root.get("productPrice"),
				root.get("productCurrentStockPackageCount"), root.get("productEffectiveDate"),
				root.get("productCreatedDate"))).where(inactivePredicate);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public ProductModel addProductDetails(ProductModel productModel) {

		return productRepository.save(productModel);
	}

	@Override
	public Object deleteProductById(Long id) {

		ProductModel model = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(ProductMessageDto.PRODUCT_ID_NOT_FOUND));
		model.setIsDeleted(true);
		return productRepository.save(model);
	}

	@Override
	public List<ProductPriceHistoryDto> getProductPriceHistoryById(Long activeid) {

		List<ProductPriceHistoryDto> result = new ArrayList<>(); 
		Long currentId = activeid;

		while(currentId!=null) {
			ProductModel model = productRepository.findById(currentId).orElseThrow(
					()-> new RuntimeException(ProductMessageDto.PRODUCT_ID_NOT_FOUND +activeid)
					);
			result.add(
					new ProductPriceHistoryDto(
							model.getProductId(),
							model.getProductName(),
							model.getProductPrice(),
							model.getProductEffectiveDate()
							));
			currentId = model.getOldProductId(); 
		}
		
//		result.sort(Comparator.comparing(ProductPriceHistoryDto :: getProductEffectiveDate));
		
		return result;
	}

}
