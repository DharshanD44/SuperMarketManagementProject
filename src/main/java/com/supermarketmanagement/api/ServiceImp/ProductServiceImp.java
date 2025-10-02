package com.supermarketmanagement.api.ServiceImp;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.CommonListResponseDto;
import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductFilterRequest;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductPriceHistoryDto;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Service.ProductService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.ProductDao;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Transactional
public class ProductServiceImp implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsServiceImp.class);
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SuperMarketCodeDao superMarketCodeDao;

	/**
	 * Updates an existing product or creates a new product if the effective date has passed.
	 *
	 * @param updatedProduct - product details to be updated
	 * @return {@link CommonMessageResponse} with success/failure and message
	 */
	@Override
	public Object updateProduct(ProductListDto updatedProduct) {
	    logger.info("updateProduct: Updating product with ID {}", updatedProduct.getProductId());

	    CommonMessageResponse responseMessage = new CommonMessageResponse();
	    List<String> errors = new ArrayList<>();

	    // Field validations
	    if (updatedProduct.getProductName() == null || updatedProduct.getProductName().trim().isEmpty()) {
	        errors.add("Product name is required");
	    }
	    if (updatedProduct.getProductPackQuantity() == null || updatedProduct.getProductPackQuantity() <= 0) {
	        errors.add("Product pack quantity must be greater than 0");
	    }
	    if (updatedProduct.getProductPrice() == null || updatedProduct.getProductPrice() <= 0) {
	        errors.add("Product price must be greater than 0");
	    }
	    if (updatedProduct.getProductCurrentStockPackageCount() == null || updatedProduct.getProductCurrentStockPackageCount() < 0) {
	        errors.add("Product stock required and cannot be empty");
	    }
	    if (updatedProduct.getProductEffectiveDate() == null) {
	        errors.add("Product effective date is required");
	    }
	    if (updatedProduct.getProductStatus() == null) {
	        errors.add("Product status is required");
	    }

	    if (!errors.isEmpty()) {
	        responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
	        responseMessage.setData(String.join(", ", errors));
	        return responseMessage;
	    }

	    ProductModel existingProduct = productDao.findByProductId(updatedProduct.getProductId());
	    if (existingProduct == null) {
	        responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
	        responseMessage.setData(WebServiceUtil.PRODUCT_ID_NOT_FOUND);
	        return responseMessage;
	    }

	    if (existingProduct.getProductEffectiveDate().isAfter(LocalDate.now())) {
	    	
	    	if(existingProduct.getProductEffectiveDate().isEqual(LocalDate.now()) || 
	    			existingProduct.getProductEffectiveDate().isAfter(LocalDate.now()))
	    		existingProduct.setProductEffectiveDate(updatedProduct.getProductEffectiveDate());
	    	else {
	    		responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
		  	    responseMessage.setData(WebServiceUtil.INVALID_LAST_EFFECTIVE);
			    return responseMessage;
	    	}
	        existingProduct.setProductName(updatedProduct.getProductName());
	        existingProduct.setProductPackageType(updatedProduct.getProductPackageType());
	        existingProduct.setProductPackQuantity(updatedProduct.getProductPackQuantity());
	        existingProduct.setProductPackageUnitOfMeasure(updatedProduct.getProductPackageUnitOfMeasure());
	        existingProduct.setProductPrice(updatedProduct.getProductPrice());
	        existingProduct.setProductCurrentStockPackageCount(updatedProduct.getProductCurrentStockPackageCount());
	        existingProduct.setProductLastEffectiveDate(updatedProduct.getProductLastEffectiveDate());
	        existingProduct.setProductUpdatedtedDate(LocalDateTime.now());
	    } else {
	        SuperMarketCode activeCode = superMarketCodeDao.findByCode(WebServiceUtil.STATUS_ACTIVE);
	        existingProduct.setProductLastEffectiveDate(LocalDate.now());
	        existingProduct.setProductUpdatedtedDate(LocalDateTime.now());

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
	        newProduct.setProductStatus(activeCode);

	        productDao.saveProduct(newProduct);
	    }

	    responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
	    responseMessage.setData(WebServiceUtil.PRODUCT_UPDATED);
	    return responseMessage;
	}

	
	/**
	 * Adds a new product to the system.
	 *
	 * @param productModel - product details DTO
	 * @return {@link CommonMessageResponse} with success status and message
	 */
	@Override
	public Object addProductDetails(ProductListDto productModel) {
	    logger.info("addProductDetails: Adding new product '{}'", productModel.getProductName());

	    CommonMessageResponse responseMessage = new CommonMessageResponse();
	    List<String> errors = new ArrayList<>();

	    if (productModel.getProductName() == null || productModel.getProductName().trim().isEmpty()) {
	        errors.add("Product name is required");
	    }
	    if (productModel.getProductPackQuantity() == null || productModel.getProductPackQuantity() <= 0) {
	        errors.add("Product pack quantity must be greater than 0");
	    }
	    if (productModel.getProductPrice() == null || productModel.getProductPrice() <= 0) {
	        errors.add("Product price must be greater than 0");
	    }
	    if (productModel.getProductCurrentStockPackageCount() == null || productModel.getProductCurrentStockPackageCount() < 0) {
	        errors.add("Product stock required and cannot be empty");
	    }
	    if (productModel.getProductEffectiveDate() == null) {
	        errors.add("Product effective date is required");
	    }
	    if (productModel.getProductStatus() == null || productModel.getProductStatus().trim().isEmpty()) {
	        errors.add("Product status is required");
	    }

	    if (!errors.isEmpty()) {
	        responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
	        responseMessage.setData(String.join(", ", errors));
	        return responseMessage;
	    }

	    ProductModel dto = new ProductModel();
	    dto.setProductCreatedDate(LocalDateTime.now());
	    dto.setProductName(productModel.getProductName());
	    dto.setProductPackQuantity(productModel.getProductPackQuantity());
	    dto.setProductPrice(productModel.getProductPrice());
	    dto.setProductCurrentStockPackageCount(productModel.getProductCurrentStockPackageCount());
	    if(productModel.getProductEffectiveDate().isEqual(LocalDate.now()) || 
	    		productModel.getProductEffectiveDate().isAfter(LocalDate.now()))
	    	dto.setProductEffectiveDate(productModel.getProductEffectiveDate());
	    else
	    {
	    	responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
	  	    responseMessage.setData(WebServiceUtil.INVALID_LAST_EFFECTIVE);
		    return responseMessage;
	    }
	    dto.setProductLastEffectiveDate(productModel.getProductLastEffectiveDate());
	    dto.setProductPackageType(productModel.getProductPackageType());
	    dto.setProductPackageUnitOfMeasure(productModel.getProductPackageUnitOfMeasure());

	    SuperMarketCode status = superMarketCodeDao.findByDescription(productModel.getProductStatus());
	    if (status == null) {
	        responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
	        responseMessage.setData("Invalid product status");
	        return responseMessage;
	    }
	    dto.setProductStatus(status);

	    productDao.saveProduct(dto);

	    responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
	    responseMessage.setData(WebServiceUtil.NEW_PRODUCT_ADDED);
	    logger.info("New product '{}' added successfully", dto.getProductName());
	    return responseMessage;
	}


	
	/**
	 * Marks a product as deleted by its ID.
	 *
	 * @param id - product ID
	 * @return {@link CommonMessageResponse} with success/failure and message
	 */
	@Override
	public Object deleteProductById(Long id) {
		logger.info("deleteProductById: Deleting product with ID {}", id);

		ProductModel existingProduct = productDao.findByProductId(id);
		CommonMessageResponse responseMessage = new CommonMessageResponse();
		if (existingProduct == null) {
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setData(WebServiceUtil.PRODUCT_ID_NOT_FOUND);
			return responseMessage;
		}
		existingProduct.setIsDeleted(true);
		responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
		responseMessage.setData(WebServiceUtil.PRODUCT_DELETED);
		return responseMessage;
	}

	
	/**
	 * Retrieves the price history of a product following its version chain.
	 *
	 * @param activeId - current active product ID
	 * @return {@link CommonMessageResponse} containing a list of price history records
	 */
	@Override
	public Map<String, Object> getProductPriceHistoryById(Long activeId) {
		logger.info("getProductPriceHistoryById: Retrieving price history for product ID {}", activeId);

	    List<ProductPriceHistoryDto> result = new ArrayList<>();
	    Long currentId = activeId;
	    Map<String, Object> response = new LinkedHashMap<String, Object>();

	    while (currentId != null) {
	        ProductModel product = productDao.findByProductId(currentId);
	        if(product == null) {
		        response.put("status",WebServiceUtil.FAILED_STATUS);
			    response.put("data",WebServiceUtil.PRODUCT_ID_NOT_FOUND);
			    return response;
	        }
	        result.add(new ProductPriceHistoryDto(
	                product.getProductId(),
	                product.getProductName(),
	                product.getProductPrice(),
	                product.getProductEffectiveDate()
	        ));
	        currentId = product.getOldProductId();
	    }
	    response.put("status",WebServiceUtil.SUCCESS_STATUS);
	    response.put("data",result);
	    return response;
	}

	/**
	 * Retrieves all product details with applied filters.
	 *
	 * @param request - filter request
	 * @return map of product details	
	 */
	
	@Override
	public Object getAllProductDetails(ProductFilterRequest request) {
		logger.info("getAllProductDetails: Retrieving all product details with applied filters");

		Map<String , Object> productList = productDao.getAllProductDetails(request);
		
		CommonListResponseDto response = new CommonListResponseDto();
        response.setData(productList.get("data"));
        response.setStatus(WebServiceUtil.SUCCESS_STATUS);
        response.setDraw(request.getDraw());
        response.setRecordsTotal((Long) productList.get(WebServiceUtil.KEY_TOTAL_COUNT));
        response.setRecordsFiltered((Long) productList.get(WebServiceUtil.KEY_FILTER_COUNT));
        return response;
	}

	
	/**
	 * Deactivates a product by setting its last effective date and status to INACTIVE.
	 *
	 * @param productid - product ID
	 * @param expirydate - expiry date to set
	 * @return {@link CommonMessageResponse} with success/failure and message
	 */
	@Override
	public Object deactivateProduct(Long productid, LocalDate expirydate) {
		logger.info("deactivateProduct: Deactivating product with ID {} and setting expiry date {}", productid, expirydate);

		ProductModel existingProduct = productDao.findByProductId(productid);
		CommonMessageResponse responseMessage = new CommonMessageResponse();
		if (existingProduct == null) {
			responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
			responseMessage.setData(WebServiceUtil.PRODUCT_ID_NOT_FOUND);
			return responseMessage;
		}
		else {
			existingProduct.setProductLastEffectiveDate(expirydate);
			existingProduct.setProductUpdatedtedDate(LocalDateTime.now());
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setData(WebServiceUtil.PRODUCT_DEACTIVATED);
			return responseMessage;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void exportProductsToExcel(HttpServletResponse response, ProductFilterRequest request) throws IOException {
	    logger.info("exportProductsToExcel: Exporting product details to Excel");

	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");
	    
		Map<String , Object> productList = productDao.getAllProductDetails(request);

	    List<ProductListDto> products = (List<ProductListDto>) productList.get("data");

	    Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Products");

	    Row header = sheet.createRow(0);
	    header.createCell(0).setCellValue("Product ID");
	    header.createCell(1).setCellValue("Product Name");
	    header.createCell(2).setCellValue("Package Type");
	    header.createCell(3).setCellValue("Pack Quantity");
	    header.createCell(4).setCellValue("Unit Of Measure");
	    header.createCell(5).setCellValue("Price");
	    header.createCell(6).setCellValue("Current Stock");
	    header.createCell(7).setCellValue("Effective Date");
	    header.createCell(8).setCellValue("Last Effective Date");
	    header.createCell(9).setCellValue("Status");

	    int rowIdx = 1;
	    for (ProductListDto product : products) {
	        Row row = sheet.createRow(rowIdx++);
	        row.createCell(0).setCellValue(product.getProductId());
	        row.createCell(1).setCellValue(product.getProductName());
	        row.createCell(2).setCellValue(product.getProductPackageType());
	        row.createCell(3).setCellValue(product.getProductPackQuantity());
	        row.createCell(4).setCellValue(product.getProductPackageUnitOfMeasure());
	        row.createCell(5).setCellValue(product.getProductPrice());
	        row.createCell(6).setCellValue(product.getProductCurrentStockPackageCount());
	        row.createCell(7).setCellValue(product.getProductEffectiveDate() != null ? product.getProductEffectiveDate().toString() : "");
	        row.createCell(8).setCellValue(product.getProductLastEffectiveDate() != null ? product.getProductLastEffectiveDate().toString() : "");
	        row.createCell(9).setCellValue(product.getProductStatus());
	    }

	    for (int i = 0; i < 10; i++) {
	        sheet.autoSizeColumn(i);
	    }

	    workbook.write(response.getOutputStream());
	    workbook.close();
	}


	@Override
	public CommonMessageResponse getProductDetailsById(Long productid) {
		ProductListDto productList = productDao.getProductDetailsById(productid);
		CommonMessageResponse response = new CommonMessageResponse();

		if(productList==null) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(WebServiceUtil.PRODUCT_ID_NOT_FOUND);
		}
		else
		{
			productList.setSno(1);
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setData(productList);
		}
		return response;
	}


}
