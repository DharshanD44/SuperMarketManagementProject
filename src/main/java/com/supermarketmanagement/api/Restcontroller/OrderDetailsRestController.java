package com.supermarketmanagement.api.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Service.OrderDetailsService;

@RestController
@RequestMapping("orderdetails")
public class OrderDetailsRestController {

	@Autowired
	private OrderDetailsService orderDetailsService;

	/**
	 * Retrieves order list details based on filter/search criteria.
	 *
	 * @param commonListRequestModel request body 
	 * @return ResponseEntity with list of order details
	 */
	@PostMapping("/list/all")
	public ResponseEntity<?> getOrderListDetails(@RequestBody CommonListRequestModel commonListRequestModel){
		return ResponseEntity.ok(orderDetailsService.getOrderListDetails(commonListRequestModel));
	}

	/**
	 * Updates the status of a specific order.
	 *
	 * @param orderstatus path variable 
	 * @param orderid path variable 
	 * @return ResponseEntity with success message after updation
	 */
	@PostMapping("/update/orderStatus")
	public ResponseEntity<?> updateOrderStatus(@RequestParam Long orderid, @RequestParam String orderstatus) {
		return ResponseEntity.ok(orderDetailsService.updateOrderStatus(orderstatus, orderid));
	}
	
	/**
	 * Assigns a specific order to a supplier.
	 *
	 * @param orderid path variable 
	 * @param supplierid path variable 
	 * @return ResponseEntity with success message after assigning orders to suppliers.
	 */
	@PostMapping("assign/orders")
	public ResponseEntity<?> assignOrderToSupplier(@RequestParam Long orderid, @RequestParam Long supplierid){
		return ResponseEntity.ok(orderDetailsService.assignOrderToSupplier(orderid, supplierid));
	}

	
	/**
	 * Retrieves order list details based on order id.
	 *
	 * @param orderid path variable 
	 * @return ResponseEntity with order details for the given order id
	 */
	@GetMapping("get/orderid/{orderid}")
	public ResponseEntity<?> getOrderDetailsById(@PathVariable Long orderid){
		return ResponseEntity.ok(orderDetailsService.getOrderDetailsById(orderid));
	}
}

