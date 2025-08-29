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
	public Object addProductDetails(ProductModel productModel) {

		return productRepository.save(productModel);
	}


	@Override
	public Optional<ProductModel> findByProductId(Long productId) {
		return productRepository.findById(productId);
	}

}
