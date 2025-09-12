package com.supermarketmanagement.api.Restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.UpdateOrderLineItemsDto;
import com.supermarketmanagement.api.Service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/placeOrder")
	public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDto requestDto) {
		return ResponseEntity.ok(orderService.placeOrder(requestDto));
	}

	@PostMapping("/placedOrder/update")
	public ResponseEntity<?> updatePlacedOrder(@RequestBody UpdateOrderLineItemsDto updaterequestDto) {
		Object response = orderService.updatePlacedOrder(updaterequestDto);

		return ResponseEntity.ok(response);
	}

}
