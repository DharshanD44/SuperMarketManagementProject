package com.supermarketmanagement.api.daoImp;

import java.util.ArrayList;
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
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;
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
	public ProductModel findByProductId(Long productId) {
		return productRepository.findByProductId(productId);
	}

	@Override
	public Object saveProduct(ProductModel newProduct) {
		return productRepository.save(newProduct);
	}

	@Override
	public Map<String, Object> getAllProductDetails(ProductFilterRequest productFilterRequest) {
		CriteriaBuilder criteriabuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductListDto> criteriaQuery = criteriabuilder.createQuery(ProductListDto.class);
		Root<ProductModel> root = criteriaQuery.from(ProductModel.class);
		List<Predicate> predicates = new ArrayList<>();
		Map<String, Object> response = new LinkedHashMap<>();


		if (productFilterRequest.getIsActive() != null && productFilterRequest.getDate() != null) {
			if (productFilterRequest.getIsActive()) {
				Predicate activePredicate = criteriabuilder.lessThanOrEqualTo(root.get("productEffectiveDate"), productFilterRequest.getDate());
				Predicate activePredicate2 = criteriabuilder.or(criteriabuilder.isNull(root.get("productLastEffectiveDate")),
						criteriabuilder.greaterThanOrEqualTo(root.get("productLastEffectiveDate"), productFilterRequest.getDate()));
				predicates.add(criteriabuilder.and(activePredicate, activePredicate2));
			} else {
				Predicate futurePredicate = criteriabuilder.greaterThan(root.get("productEffectiveDate"), productFilterRequest.getDate());
				Predicate expiredPredicate = criteriabuilder.and(criteriabuilder.isNotNull(root.get("productLastEffectiveDate")),
						criteriabuilder.lessThan(root.get("productLastEffectiveDate"), productFilterRequest.getDate()));
				predicates.add(criteriabuilder.or(futurePredicate, expiredPredicate));
			}
		}
		
		boolean searchColumn=false;
		if (!productFilterRequest.getSearchValue().isEmpty()) {
			
			if(!productFilterRequest.getSearchBy().isEmpty())
			{
				String value = productFilterRequest.getSearchValue().toLowerCase();
				switch (productFilterRequest.getSearchBy().toLowerCase()) {
				case WebServiceUtil.PRODUCT_NAME:
					predicates.add(criteriabuilder.like(criteriabuilder.concat(" ", criteriabuilder.concat(criteriabuilder.lower(root.get("productName")), " ")),
							"% " + value + " %"));
					break;
				case WebServiceUtil.PRODUCT_PRICE:
					predicates.add(criteriabuilder.equal(root.get("productPrice"), Double.valueOf(productFilterRequest.getSearchValue())));
					break;
				case WebServiceUtil.PRODUCT_PACK_QUANTITY:
					predicates.add(criteriabuilder.equal(root.get("productPackQuantity"), Integer.valueOf(productFilterRequest.getSearchValue())));
					break;
				default:
					searchColumn=true;
					break;
				}
			}
			else
			{
				try {
					predicates.add(criteriabuilder.equal(root.get("productId"), Long.valueOf(productFilterRequest.getSearchValue())));
					}
					catch (NumberFormatException e) {
			            response.put("message", WebServiceUtil.INVALID_PRODUCT_SEARCH_VALUE);
			            return response;
			        }
			}
			
		}
		
		if (!productFilterRequest.getPriceRange().isEmpty() && (productFilterRequest.getRangeValue1() != null)) {
			switch (productFilterRequest.getPriceRange()) {
			case WebServiceUtil.RANGE_LESS_THAN:
				predicates
						.add(criteriabuilder.lessThan(root.get("productPrice"), productFilterRequest.getRangeValue1()));
				break; // <-- missing
			case WebServiceUtil.RANGE_LESS_THAN_OR_EQUALTO:
				predicates.add(criteriabuilder.lessThanOrEqualTo(root.get("productPrice"),
						productFilterRequest.getRangeValue1()));
				break;
			case WebServiceUtil.RANGE_GREATER_THAN:
				predicates.add(
						criteriabuilder.greaterThan(root.get("productPrice"), productFilterRequest.getRangeValue1()));
				break;
			case WebServiceUtil.RANGE_GREATER_THAN_EQUALTO:
				predicates.add(criteriabuilder.greaterThanOrEqualTo(root.get("productPrice"),
						productFilterRequest.getRangeValue1()));
				break;
			case WebServiceUtil.RANGE_EQUALTO:
				predicates.add(criteriabuilder.equal(root.get("productPrice"), productFilterRequest.getRangeValue1()));
				break;
			case WebServiceUtil.RANGE_IN_BETWEEN:
				predicates.add(criteriabuilder.between(root.get("productPrice"), productFilterRequest.getRangeValue1(),
						productFilterRequest.getRangeValue2()));
				break;
			}
		}

		
		if(searchColumn) {
			response.put("status", WebServiceUtil.FAILED_STATUS);
	        response.put("message", WebServiceUtil.INVALID_SEARCH_COLUMN);
	        return response;
		}
		
	    predicates.add(criteriabuilder.notEqual(root.get("isDeleted"), true));
	    
		criteriaQuery.select(criteriabuilder.construct(ProductListDto.class, 
				root.get("productId"), 
				root.get("productName"),
				root.get("productPackageType"), 
				root.get("productPackQuantity"),
				root.get("productPackageUnitOfMeasure"), 
				root.get("productPrice"),
				root.get("productCurrentStockPackageCount"), 
				root.get("productEffectiveDate"),
				root.get("productLastEffectiveDate"), 
				root.get("productStatus").get("description")))
				.where(criteriabuilder.and(predicates.toArray(new Predicate[predicates.size()])));


		if (!productFilterRequest.getOrderBy().isEmpty() && !productFilterRequest.getOrderType().isEmpty()) {
			switch (productFilterRequest.getOrderBy()) {
			case WebServiceUtil.SORT_BY_SNO:
				if (WebServiceUtil.SORT_ASECENDING.equalsIgnoreCase(productFilterRequest.getOrderType())) {
					criteriaQuery.orderBy(criteriabuilder.asc(root.get("productId")));
				} 
				else if((WebServiceUtil.SORT_DESCENDING.equalsIgnoreCase(productFilterRequest.getOrderType()))){
					criteriaQuery.orderBy(criteriabuilder.desc(root.get("productId")));
				}
				break;
			default:
				if (WebServiceUtil.SORT_ASECENDING.equalsIgnoreCase(productFilterRequest.getOrderType()))
					criteriaQuery.orderBy(criteriabuilder.asc(root.get(productFilterRequest.getOrderBy())));
				else
					criteriaQuery.orderBy(criteriabuilder.desc(root.get(productFilterRequest.getOrderBy())));
				break;
			}
		}
		TypedQuery<ProductListDto> queryresult = entityManager.createQuery(criteriaQuery);
		List<ProductListDto> results = queryresult.getResultList();
		
		
		CriteriaQuery<Long> totalCountQuery = criteriabuilder.createQuery(Long.class);
		totalCountQuery.select(criteriabuilder.count(totalCountQuery.from(ProductModel.class)));
		Long totalCount = entityManager.createQuery(totalCountQuery).getSingleResult();

		CriteriaQuery<Long> filteredCountQuery = criteriabuilder.createQuery(Long.class);
		Root<ProductModel> filterRoot = filteredCountQuery.from(ProductModel.class);
		filteredCountQuery.select(criteriabuilder.count(filterRoot))
				.where(criteriabuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		Long filteredCount = entityManager.createQuery(filteredCountQuery).getSingleResult();
		int start = productFilterRequest.getStart();

		if (WebServiceUtil.SORT_BY_SNO.equalsIgnoreCase(productFilterRequest.getOrderBy())
				&& WebServiceUtil.SORT_DESCENDING.equalsIgnoreCase(productFilterRequest.getOrderType())) {

			for (int i = 0; i < results.size(); i++) {
				results.get(i).setSno((int) (totalCount - (start + i)));
			}

		} else {
			for (int i = 0; i < results.size(); i++) {
				results.get(i).setSno(start + i + 1);
			}
		}

		if (productFilterRequest.getStart() != null) {
			queryresult.setFirstResult(productFilterRequest.getStart());
		}
		if (productFilterRequest.getLength() != null) {
			queryresult.setMaxResults(productFilterRequest.getLength());
		}

		response.put("status", WebServiceUtil.SUCCESS_STATUS);
		response.put("totalCount", totalCount);
		response.put("filterCount", filteredCount);
		response.put("data", results);

		return response;
	}

	@Override
	public ProductListDto getProductDetailsById(Long productid) {
		CriteriaBuilder criteriabuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductListDto> criteriaQuery = criteriabuilder.createQuery(ProductListDto.class);
		Root<ProductModel> root = criteriaQuery.from(ProductModel.class);
		Predicate predicate = criteriabuilder.equal(root.get("productId"), productid);
		criteriaQuery.select(criteriabuilder.construct(ProductListDto.class, 
				root.get("productId"), 
				root.get("productName"),
				root.get("productPackageType"), 
				root.get("productPackQuantity"),
				root.get("productPackageUnitOfMeasure"), 
				root.get("productPrice"),
				root.get("productCurrentStockPackageCount"), 
				root.get("productEffectiveDate"),
				root.get("productLastEffectiveDate"), 
				root.get("productStatus").get("description"))
				).where(predicate,criteriabuilder.equal(root.get("isDeleted"), false));
		return entityManager.createQuery(criteriaQuery).getResultStream().findFirst().orElse(null);
	}

}
