package com.supermarketmanagement.api.Restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarketmanagement.api.Model.Custom.CommonListResponse;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Service.OrderLineDetailsService;

@RestController
@RequestMapping("orderlinedetails")
public class OrderLineDetailsRestController {

	@Autowired
	private OrderLineDetailsService orderLineDetailsService;

	@PostMapping("/orderLineStatus/{status}")
	public ResponseEntity<?> updateOrderLineStatus(@PathVariable String status, @RequestBody List<Long> id) {
		return ResponseEntity.ok(orderLineDetailsService.updateLineOrderLineStatus(status, id));
	}
	
	@GetMapping("list/all")
	public ResponseEntity<?> getOrderLineDetails(){
		return new ResponseEntity<CommonListResponse<?>>(orderLineDetailsService.getOrderLineDetails(),HttpStatus.OK);
	}
	
	@GetMapping("list/id/{lineid}")
	public ResponseEntity<?> getOrderLineDetailsById(@PathVariable Long lineid)
	{
		return ResponseEntity.ok(orderLineDetailsService.getOrderLineDetailsById(lineid));
	}
}
