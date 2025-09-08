package com.supermarketmanagement.api.Restcontroller;

import java.util.List;

import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.Response;
import com.bandwidth.http.response.ApiResponse;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderListResponse;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderMessageDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderUpdateRequestDto;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Service.OrderDetailsService;
import com.supermarketmanagement.api.Service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/placeOrder")
	public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDto requestDto)
	{
		Object result = orderService.placeOrder(requestDto);
	    return ResponseEntity.ok(result);
	}

	@PostMapping("/placedOrder/update")
	public ResponseEntity<?> updatePlacedOrder(@RequestBody OrderUpdateRequestDto updaterequestDto)
	{
		Object response = orderService.updatePlacedOrder(updaterequestDto);
		
		return ResponseEntity.ok(response);
	}
	
}
