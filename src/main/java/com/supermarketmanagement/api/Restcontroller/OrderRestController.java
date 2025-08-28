package com.supermarketmanagement.api.Restcontroller;

import java.util.List;

import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.Response;
import com.bandwidth.http.response.ApiResponse;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderMessageDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderUpdateRequestDto;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Service.OrderDetailsService;

@RestController
public class OrderRestController {
	
	@Autowired
	private OrderDetailsService detailsService;
	
	
	@PostMapping("/product/placeOrder")
	public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDto requestDto)
	{
		Object result = detailsService.placeOrder(requestDto);

	    if (result instanceof String) {
	        return ResponseEntity.badRequest().body(result);
	    }

	    return ResponseEntity.ok(OrderMessageDto.ORDER_PLACED);
	}

	@PostMapping("/product/placedOrder/update")
	public ResponseEntity<?> updatePlacedOrder(@RequestBody OrderUpdateRequestDto updaterequestDto)
	{
		Object response = detailsService.updatePlacedOrder(updaterequestDto);
		
		if(response instanceof String) {
	        return ResponseEntity.badRequest().body(response);

		}
		return ResponseEntity.ok(OrderMessageDto.UPDATE_PLACED_ORDER);
	}
	
	@PostMapping("/product/orderStatus/{status}/{id}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable String status,@PathVariable Long id){
		return ResponseEntity.ok(detailsService.updateOrderStatus(status,id));
		}
	
	@PostMapping("/product/orderLineStatus/{status}")
	public ResponseEntity<?> updateOrderLineStatus(@PathVariable String status,@RequestBody List<Long> id){
		return ResponseEntity.ok(detailsService.updateLineOrderLineStatus(status,id));
		}
}
