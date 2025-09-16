package com.supermarketmanagement.api.Util;

public class WebServiceUtil {
	
	//Status
	public static final String SUCCESS_STATUS = "success";
	public static final String FAILED_STATUS = "failed";
	
	public static final String KEY_TOTAL_COUNT = "totalCount";
	public static final String KEY_FILTER_COUNT = "filterCount";
	public static final String KEY_DATA = "data";

	//Users
	public static final String USER_CREATED = "User has been Created!";
	public static final String USER_UPDATED = "User has been Updated!";
	public static final String USER_DELETED = "User has been Deleted!";
	public static final String USER_ID_NOT_FOUND= "User with Id Not Found!";
	
	//Customer
	public static final String NEW_CUSTOMER_ADDED ="New customer created successfully!";
	public static final String CUSTOMER_UPDATED = "Customer details Updated Successfully";
	public static final String CUSTOMER_DELETED = "Customer deleted Successfully";
	public static final String CUSTOMER_NOT_FOUND ="Customer id not Found!";
	
	// OrderDetails
	public static final String ORDER_PLACED = "Order has been placed!";
	public static final String UPDATE_PLACED_ORDER = "Order has been updated!";
	public static final String ORDER_ID_NOT_FOUND = "Order id not found!";
	public static final String ORDER_LINE_ID_NOT_FOUND = "Order line id not found!";
	public static final String CANT_UPDATE_ORDER = "Order has been packed! update not allowed";
	public static final String SUPPLIER_ASSIGNED = "Order assigned successfully to supplier";

	// OrderStatus
	public static final String ORDER_STATUS_PACKED = "Order status - packed has been updated!";
	public static final String ORDER_STATUS_SHIPPED = "Order status - shipped has been updated!";
	public static final String ORDER_STATUS_DELIVERED = "Order status - delivered has been updated!";
	public static final String ORDER_STATUS_CANCELLED = "Order status - cancelled has been updated!";
	public static final String ORDER_QUANTITY_OUT_OF_STOCK = "Requested pack quantity not available! check the product available stock";
	public static final String INVALID_ORDER_STATUS = "Invalid order status!";
	public static final String PACKED_STATUS="PACKED";

	//Product
	public static final String PRODUCT_DELETED="Product has been deleted Successfully!";
	public static final String NEW_PRODUCT_ADDED="New Product created Successfully!";
	public static final String PRODUCT_ID_NOT_FOUND="PRODUCT ID NOT FOUND";
	public static final String PRODUCT_UPDATED="Product has been Updated Successfully!";
	
	//Supplier
	public static final String SUPPLIER_ID_NOT_FOUND="Supplier id not found";
	public static final String SUPPLIER_ORDER_ID_NOT_FOUND="Both supplier and order id not found";
	public static final String NEW_SUPPLIER_ADDED="New supplier created successfully";
	
	//Sales
	public static final String START_AND_END_DATE="Start and end date required for Custom filter";
}
