package com.supermarketmanagement.api.Util;

public class WebServiceUtil {
	
	//Status
	public static final String SUCCESS_STATUS = "Success";
	public static final String INVALID_STATUS = "Invalid status";
	public static final String FAILED_STATUS = "Failed";
	public static final String WARNING_STATUS ="Warning";
	public static final String SEARCH_VALUE_NULL="search value cannot be null";
	public static final String SELECT_SEARCH_COLUMN="Please select the search column";
	public static final String INVALID_SEARCH_VALUE="Please search using order id";
	public static final String INVALID_PRODUCT_SEARCH_VALUE="Please search using product id";
	public static final String STATUS_ACTIVE ="ACTIVE";
	public static final String STATUS_INACTIVE ="INACTIVE";

	
	//Filter
	public static final String DATE_VALUE_NULL="date value cannot be null";
	public static final String SEARCH_AND_DATE_VALUE_NULL="search and date value cannot be null";
	public static final String INVALID_SEARCH_COLUMN="Invalid search column";

	//FilterCount
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
	public static final String CUSTOMER_ALREADY_DELETED = "Customer already deleted";
	public static final String CUSTOMER_NOT_FOUND ="Customer id not Found!";
	public static final String CUSTOMER_STATUS_ACTIVE = "Customer activated successfully!";
	public static final String CUSTOMER_STATUS_INACTIVE = "Customer deactivated successfully!";
	
	// OrderDetails
	public static final String ORDER_PLACED = "Order has been placed!";
	public static final String UPDATE_PLACED_ORDER = "Order has been updated!";
	public static final String ORDER_ID_NOT_FOUND = "Order id not found!";
	public static final String ORDER_LINE_ID_NOT_FOUND = "Order line id not found!";
	public static final String CANT_UPDATE_ORDER = "Order has been packed! update not allowed";
	public static final String SUPPLIER_ASSIGNED = "Order successfully assigned to supplier";

	// OrderStatus
	public static final String ORDER_STATUS_PACKED = "Order status - Packed has been updated!";
	public static final String ORDER_STATUS_SHIPPED = "Order status - Shipped has been updated!";
	public static final String ORDER_STATUS_DELIVERED = "Order status - Delivered has been updated!";
	public static final String ORDER_STATUS_CANCELLED = "Order status - Cancelled has been updated!";
	public static final String ORDER_QUANTITY_OUT_OF_STOCK = "Requested pack quantity not available! check the product available stock";
	public static final String INVALID_ORDER_STATUS = "Invalid order status!";
	public static final String PACKED_STATUS="PACKED";
	public static final String UPDATE_ORDERLINEITEM_STATUS="Update order line items status:";

	
	//Product
	public static final String PRODUCT_DELETED="Product has been deleted Successfully!";
	public static final String NEW_PRODUCT_ADDED="New Product created Successfully!";
	public static final String PRODUCT_ID_NOT_FOUND="PRODUCT ID NOT FOUND";
	public static final String PRODUCT_UPDATED="Product has been Updated Successfully!";
	public static final String PRODUCT_DEACTIVATED= "Product has been deactivated";
	public static final String INVALID_LAST_EFFECTIVE="Last effective date must be today or future";

	
	//Supplier
	public static final String SUPPLIER_ID_NOT_FOUND="Supplier id not found";
	public static final String SUPPLIER_ORDER_ID_NOT_FOUND="Both supplier and order id not found";
	public static final String NEW_SUPPLIER_ADDED="New supplier created successfully";
	public static final String SUPPLIER_UPDATED="Supplier details updated successfully";
	public static final String SUPPLIER_DELETED = "Supplier deleted Successfully";
	public static final String SUPPLIER_ALREADY_DELETED = "Supplier already deleted";
	public static final String SUPPLIER_STATUS_ACTIVE = "Supplier activated successfully!";
	public static final String SUPPLIER_STATUS_INACTIVE = "Supplier deactivated successfully!";
	
	//Sales
	public static final String START_AND_END_DATE="Start and end date required for Custom filter";
	
	
	//CustomerFilter
	public static final String CUSTOMER_NAME = "customername";
	public static final String CUSTOMER_MOBILE_NO = "customermobileno";
	public static final String CUSTOMER_EMAIL = "customeremail";

	//OrderDetailFilter
	public static final String ORDER_STATUS = "orderstatus";
	public static final String ORDER_DATE = "orderdate";
	public static final String CUSTOMER_ID = "customerid";
	
	//OrderLineDetailFilter
	public static final String ORDER_LINE_STATUS = "orderstatus";
	public static final String CREATED_DATE = "createddate";
	public static final String ORDER_LINE_ID = "orderlineid";
	
	//ProductFilter
	public static final String PRODUCT_NAME ="productname";
	public static final String PRODUCT_PRICE ="productprice";
	public static final String PRODUCT_PACK_QUANTITY="productpackquantity";
	public static final String PRODUCT_ID = "productid";
	
	//Supplierfilter
	public static final String SUPPLIER_NAME="suppliername";
	public static final String SUPPLIER_MOBILE_NO="mobilenumber";
	public static final String SUPPLIER_EMAIL_ID="emailid";

	
	//Sorting
	public static final String SORT_ASECENDING ="aesc";
	public static final String SORT_DESCENDING ="desc";
	public static final String SORT_BY_CUSTOMER_NAME="name";
	public static final String SORT_BY_SNO="sno";

	
	//Range Values
	public static final String RANGE_LESS_THAN="<";
	public static final String RANGE_LESS_THAN_OR_EQUALTO="<=";
	public static final String RANGE_GREATER_THAN=">";
	public static final String RANGE_GREATER_THAN_EQUALTO=">=";
	public static final String RANGE_EQUALTO="==";
	public static final String RANGE_IN_BETWEEN="between";

	
	//Login
	public static final String USER_NOT_FOUND="User not found";
	public static final String PASSWORD_RESET="Password reset successful";

	
}
