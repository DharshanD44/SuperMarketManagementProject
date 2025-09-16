package com.supermarketmanagement.api.daoImp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.supermarketmanagement.api.Model.Custom.Product.ProductFilterRequest;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
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
	public ProductModel findByProductId(Long productId) {
		return productRepository.findByProductId(productId);
	}

	@Override
	public Object saveProduct(ProductModel newProduct) {
		return productRepository.save(newProduct);
	}
	
	@Override
	public Map<String, Object> getAllProductDetails(ProductFilterRequest request) {
		
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<ProductListDto> cq = cb.createQuery(ProductListDto.class);
	    Root<ProductModel> root = cq.from(ProductModel.class);

	    List<Predicate> predicates = new ArrayList<>();

	    if (request.getIsActive() != null && request.getDate() != null) {
	        if (request.getIsActive()) {
	            Predicate activePredicate = cb.lessThanOrEqualTo(root.get("productEffectiveDate"), request.getDate());
	            Predicate activePredicate2 = cb.or(
	                cb.isNull(root.get("productLastEffectiveDate")),
	                cb.greaterThanOrEqualTo(root.get("productLastEffectiveDate"), request.getDate())
	            );
	            predicates.add(cb.and(activePredicate, activePredicate2));
	        } else {
	            Predicate futurePredicate = cb.greaterThan(root.get("productEffectiveDate"), request.getDate());
	            Predicate expiredPredicate = cb.and(
	                cb.isNotNull(root.get("productLastEffectiveDate")),
	                cb.lessThan(root.get("productLastEffectiveDate"), request.getDate())
	            );
	            predicates.add(cb.or(futurePredicate, expiredPredicate));
	        }
	    }

	    if (request.getSearchBy() != null && request.getSearchValue() != null) {
	        String value = request.getSearchValue().toLowerCase();
	        switch (request.getSearchBy().toLowerCase()) {
	            case "productname":
	            	predicates.add(cb.like(cb.concat(" ", cb.concat(cb.lower(root.get("productName")), " ")),
							"% " + value + " %"));	                
	            	break;
	            case "productprice":
	                predicates.add(cb.equal(root.get("productPrice"), Double.valueOf(request.getSearchValue())));
	                break;
	            case "productpackquantity":
	                predicates.add(cb.equal(root.get("productPackQuantity"), Integer.valueOf(request.getSearchValue())));
	                break;
	            case "productid":
	                predicates.add(cb.equal(root.get("productId"), Long.valueOf(request.getSearchValue())));
	                break;
	            default:
	                break;
	        }
	    }

	    cq.select(cb.construct(ProductListDto.class,
	            root.get("productId"),
	            root.get("productName"),
	            root.get("productPackageType"),
	            root.get("productPackQuantity"),
	            root.get("productPackageUnitOfMeasure"),
	            root.get("productPrice"),
	            root.get("productCurrentStockPackageCount"),
	            root.get("productEffectiveDate"),
	            root.get("productLastEffectiveDate"),
	            root.get("productStatus").get("description")
	    )).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

	    TypedQuery<ProductListDto> queryresult = entityManager.createQuery(cq);
	    List<ProductListDto> results = queryresult.getResultList();

	    if (request.getStart() != null) {
	    	queryresult.setFirstResult(request.getStart());
	    }
	    if (request.getLength() != null) {
	    	queryresult.setMaxResults(request.getLength());
	    }

	    CriteriaQuery<Long> totalCountQuery = cb.createQuery(Long.class);
	    totalCountQuery.select(cb.count(totalCountQuery.from(ProductModel.class)));
	    Long totalCount = entityManager.createQuery(totalCountQuery).getSingleResult();

	    CriteriaQuery<Long> filteredCountQuery = cb.createQuery(Long.class);
	    Root<ProductModel> filterRoot = filteredCountQuery.from(ProductModel.class);
	    filteredCountQuery.select(cb.count(filterRoot))
	            .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
	    Long filteredCount = entityManager.createQuery(filteredCountQuery).getSingleResult();

	    Map<String, Object> response = new LinkedHashMap<>();

		if (results == null || results.isEmpty()) {
			response.put("status", WebServiceUtil.FAILED_STATUS);
			response.put("data", "NO DATA FOUND");
			response.put("totalCount", 0);
			response.put("filteredCount", 0);
		} else {
			response.put("status", WebServiceUtil.SUCCESS_STATUS);
			response.put("totalCount", totalCount);
			response.put("filteredCount", filteredCount);
			response.put("data", results);	
		}
		return response;
	}



}
