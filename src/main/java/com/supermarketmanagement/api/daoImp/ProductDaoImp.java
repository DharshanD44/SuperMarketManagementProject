package com.supermarketmanagement.api.daoImp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.supermarketmanagement.api.Exceptionhandler.ProductNotFoundException;
import com.supermarketmanagement.api.Model.Custom.Product.ActiveProductsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.InactiveProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListRequestModel;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Repository.ProductRepository;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.ProductDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
	public ProductModel getProductDetailsById(int id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductModel> criteriaQuery = cb.createQuery(ProductModel.class);
		Root<ProductModel> root = criteriaQuery.from(ProductModel.class);
		Predicate predicate = cb.equal(root.get("productId"), id);
		criteriaQuery.where(predicate);
		try {
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<ActiveProductsListDto> getActiveProductDetails(LocalDateTime date) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ActiveProductsListDto> criteriaQuery = cb.createQuery(ActiveProductsListDto.class);

		Root<ProductModel> root = criteriaQuery.from(ProductModel.class);
		Predicate predicate = cb.lessThanOrEqualTo(root.get("productEffectiveDate"), date);
		Predicate predicate1 = cb.or(cb.isNull(root.get("productLastEffectiveDate")),
				cb.greaterThanOrEqualTo(root.get("productLastEffectiveDate"), date));
		criteriaQuery.select(
				cb.construct(ActiveProductsListDto.class, 
				root.get("productId"), 
				root.get("productName"),
				root.get("productPackageType"), 
				root.get("productPackQuantity"),
				root.get("productPackageUnitOfMeasure"), 
				root.get("productPrice"),
				root.get("productCurrentStockPackageCount"), 
				root.get("productEffectiveDate"))).where(cb.and(predicate, predicate1));

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<InactiveProductListDto> getInActiveProductDetails(LocalDateTime date) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<InactiveProductListDto> criteriaQuery = cb.createQuery(InactiveProductListDto.class);

		Root<ProductModel> root = criteriaQuery.from(ProductModel.class);

		Predicate ExpiredPredicate = cb.and(cb.isNotNull(root.get("productLastEffectiveDate")),
				cb.lessThan(root.get("productLastEffectiveDate"), date));

		criteriaQuery.select(cb.construct(InactiveProductListDto.class, root.get("productId"), root.get("productName"),
				root.get("productPackageType"), root.get("productPackQuantity"),
				root.get("productPackageUnitOfMeasure"), root.get("productPrice"),
				root.get("productCurrentStockPackageCount"),
				root.get("productLastEffectiveDate"))).where(ExpiredPredicate);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public Object addProductDetails(ProductModel productModel) {

		return productRepository.save(productModel);
	}

	@Override
	public ProductModel findByProductId(Long productId) {
		return productRepository.findByProductId(productId);
	}

	@Override
	public Object saveProduct(ProductModel newProduct) {
		return productRepository.save(newProduct);
	}

	private List<ProductListDto> fetchProducts(CriteriaBuilder cb, ProductListRequestModel requestModel) {
		CriteriaQuery<ProductListDto> cq = cb.createQuery(ProductListDto.class);
		Root<ProductModel> root = cq.from(ProductModel.class);

		cq.multiselect(root.get("productId"), root.get("productName"), root.get("productPackageType"),
				root.get("productPackQuantity"), root.get("productPackageUnitOfMeasure"), root.get("productPrice"),
				root.get("productCurrentStockPackageCount"), root.get("productEffectiveDate"),
				root.get("productLastEffectiveDate"));

		List<Predicate> predicates = buildPredicates(cb, root, requestModel);
		cq.where(cb.and(predicates.toArray(new Predicate[0])));

		TypedQuery<ProductListDto> query = entityManager.createQuery(cq);

		if (requestModel.getStart() != null && requestModel.getLength() != null) {
			query.setFirstResult(requestModel.getStart());
			query.setMaxResults(requestModel.getLength());
		}

		return query.getResultList();
	}

	private Long countTotalProducts(CriteriaBuilder cb) {
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<ProductModel> root = cq.from(ProductModel.class);

		cq.select(cb.count(root)).where(cb.isFalse(root.get("isDeleted")));

		return entityManager.createQuery(cq).getSingleResult();
	}

	private Long countFilteredProducts(CriteriaBuilder cb, ProductListRequestModel requestModel) {
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<ProductModel> root = cq.from(ProductModel.class);

		List<Predicate> predicates = buildPredicates(cb, root, requestModel);

		cq.select(cb.count(root)).where(cb.and(predicates.toArray(new Predicate[0])));

		return entityManager.createQuery(cq).getSingleResult();
	}

	private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<ProductModel> root,
			ProductListRequestModel requestModel) {

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(cb.isFalse(root.get("isDeleted")));

		ProductListRequestModel.ProductFilter filter = requestModel.getFilter();
		if (filter != null) {
			Predicate pricePredicate = null;
			Predicate quantityPredicate = null;

			if (filter.getPrice() != null) {
				pricePredicate = cb.equal(root.get("productPrice"), filter.getPrice());
			}
			if (filter.getProductPackQuantity() != null) {
				quantityPredicate = cb.equal(root.get("productPackQuantity"), filter.getProductPackQuantity());
			}

			if (pricePredicate != null && quantityPredicate != null) {
				predicates.add(cb.and(pricePredicate, quantityPredicate));
			} else if (pricePredicate != null) {
				predicates.add(pricePredicate);
			} else if (quantityPredicate != null) {
				predicates.add(quantityPredicate);
			}
		}

		if (requestModel.getSearch() != null && !requestModel.getSearch().isEmpty()) {
			String search = "%" + requestModel.getSearch().toLowerCase() + "%";
			predicates.add(cb.like(cb.lower(root.get(requestModel.getSearchColumn())), search));
		}

		return predicates;
	}

	@Override
	public Map<String, Object> getAllProductDetails(ProductListRequestModel requestModel) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		List<ProductListDto> products = fetchProducts(cb, requestModel);

		Long totalCount = countTotalProducts(cb);

		Long filterCount = countFilteredProducts(cb, requestModel);

		Map<String, Object> result = new HashMap<>();
		result.put(WebServiceUtil.KEY_TOTAL_COUNT, totalCount);
		result.put(WebServiceUtil.KEY_FILTER_COUNT, filterCount);
		result.put(WebServiceUtil.KEY_DATA, products);

		return result;
	}

}
