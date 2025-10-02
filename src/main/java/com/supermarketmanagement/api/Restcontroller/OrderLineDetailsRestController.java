package com.supermarketmanagement.api.Restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Service.OrderLineDetailsService;

@RestController
@RequestMapping("orderlinedetails")
public class OrderLineDetailsRestController {

	@Autowired
	private OrderLineDetailsService orderLineDetailsService;

	/**
	 * Retrieves order line details based on filter/search criteria.
	 *
	 * @param commonListRequestModel request body
	 * @return ResponseEntity with success message
	 */
	@PostMapping("/list/all")
	public ResponseEntity<?> getOrderListDetails(@RequestBody CommonListRequestModel commonListRequestModel) {
		return ResponseEntity.ok(orderLineDetailsService.getOrderLineListDetails(commonListRequestModel));
	}
	
	
	/**
	 * Updates the status of multiple order line items.
	 *
	 * @param status path variable
	 * @param id request body
	 * @return ResponseEntity with success message after updation	
	 */
	@PostMapping("/update/orderLineStatus/{status}")
	public ResponseEntity<?> updateOrderLineStatus(@PathVariable String status, @RequestBody List<Long> id) {
		return ResponseEntity.ok(orderLineDetailsService.updateLineOrderLineStatus(status, id));
	}
	
	
	
	/**
	 * Retrieves order list details based on order id.
	 *
	 * @param orderlineid path variable 
	 * @return ResponseEntity with orderlineitem details for the given orderlineid
	 */
	@GetMapping("get/orderlineid/{orderlineid}")
	public ResponseEntity<?> getOrderLineItemDetailsById(@PathVariable Long orderlineid){
		return ResponseEntity.ok(orderLineDetailsService.getOrderLineItemDetailsById(orderlineid));
	}

}
