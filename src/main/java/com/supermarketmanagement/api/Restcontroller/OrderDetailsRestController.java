package com.supermarketmanagement.api.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Service.OrderDetailsService;

@RestController
@RequestMapping("orderdetails")
public class OrderDetailsRestController {

	@Autowired
	private OrderDetailsService orderDetailsService;

	@GetMapping("/list")
	public ResponseEntity<?> getOrderListDetails(@RequestBody CommonListRequestModel commonListRequestModel){
		return ResponseEntity.ok(orderDetailsService.getOrderListDetails(commonListRequestModel));
	}

	@PostMapping("/orderStatus/{orderstatus}/{orderid}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable String orderstatus, @PathVariable Long orderid) {
		return ResponseEntity.ok(orderDetailsService.updateOrderStatus(orderstatus, orderid));
	}
	
	@PostMapping("assign/orders/{orderid}/{supplierid}")
	public ResponseEntity<?> assignOrderToSupplier(@PathVariable Long orderid,@PathVariable Long supplierid){
		return ResponseEntity.ok(orderDetailsService.assignOrderToSupplier(orderid,supplierid));
	}

}
