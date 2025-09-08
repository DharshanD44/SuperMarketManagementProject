package com.supermarketmanagement.api.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderListResponse;
import com.supermarketmanagement.api.Service.OrderDetailsService;

@RestController
@RequestMapping("orderdetails")
public class OrderDetailsRestController {

	@Autowired
	private OrderDetailsService orderDetailsService;

	@GetMapping("/list/all")
	public ResponseEntity<?> getOrderListDetails() {
		return new ResponseEntity<OrderListResponse>(orderDetailsService.getOrderListDetails(), HttpStatus.OK);
	}

	@PostMapping("/orderStatus/{status}/{id}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable String status, @PathVariable Long id) {
		return ResponseEntity.ok(orderDetailsService.updateOrderStatus(status, id));
	}
	
	@GetMapping("list/id/{orderid}")
	public ResponseEntity<?> getOrderDetailsById(@PathVariable Long orderid){
		return new ResponseEntity<>(orderDetailsService.getOrderDetailsById(orderid),HttpStatus.OK);
	}

}
