package com.supermarketmanagement.api.Restcontroller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supermarketmanagement.api.Model.Custom.ApiResponse;
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

	@PostMapping("/orderStatus/{status}/{id}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable String status, @PathVariable Long id) {
		return ResponseEntity.ok(orderDetailsService.updateOrderStatus(status, id));
	}

}
