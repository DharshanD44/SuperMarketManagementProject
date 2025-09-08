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
	public static final String CUSTOMER_ADDED ="New Customer Added Successfully!";
	public static final String CUSTOMER_UPDATED = "Customer details Updated Successfully";
	public static final String CUSTOMER_DELETED = "Customer deleted Successfully";
	public static final String CUSTOMER_NOT_FOUND ="Customer id not Found!";
	
	//OrderDetails
	public static final String ORDER_PLACED ="ORDER HAS BEEN PLACED!";
	public static final String UPDATE_PLACED_ORDER ="ORDER HAS BEEN UPDATED!";
	public static final String ORDER_ID_NOT_FOUND ="ORDER WITH ID NOT FOUND!";
	public static final String ORDER_LINE_ID_NOT_FOUND ="ORDER LINE ID NOT FOUND!";
	public static final String CANT_UPDATE_ORDER ="HAS BEEN PACKED! UPDATE NOT ALLOWED";

	public static final String ORDER_STATUS_PACKED ="ORDER STATUS - PACKED HAS BEEN UPDATED!";
	public static final String ORDER_STATUS_SHIPPED ="ORDER STATUS - SHIPPED HAS BEEN UPDATED!";
	public static final String ORDER_STATUS_DELIVERED ="ORDER STATUS - DELIVERED HAS BEEN UPDATED!";
	public static final String ORDER_STATUS_CANCELLED ="ORDER STATUS - CANCELLED HAS BEEN UPDATED!";
	public static final String ORDER_QUANTITY_OUT_OF_STOCK ="REQUESTED PACK QUANTITY NOT AVAILABLE! CHECK THE PRODUCT AVAILABLE STOCK ";
	public static final String INVALID_ORDER_STATUS = "INVALID ORDER STATUS!";
	
	//Product
	public static final String PRODUCT_DELETED="Product has been deleted Successfully!";
	public static final String NEW_PRODUCT_ADDED="Product has been Added Successfully!";
	public static final String PRODUCT_ID_NOT_FOUND="PRODUCT ID NOT FOUND";
	public static final String PRODUCT_UPDATED="Product has been Updated Successfully!";
}
