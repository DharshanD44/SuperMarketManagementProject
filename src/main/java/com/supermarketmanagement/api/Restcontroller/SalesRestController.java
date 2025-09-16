package com.supermarketmanagement.api.Restcontroller;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.auto.value.AutoAnnotation;
import com.supermarketmanagement.api.Model.Custom.Product.SalesProductListDto;
import com.supermarketmanagement.api.Service.SalesService;

@RestController
public class SalesRestController {

	@Autowired
	private SalesService salesService;

	@GetMapping("/sales/top")
	public ResponseEntity<?> getTopSellingProducts(
			@RequestParam(required = false) String filter,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) 
		{	
        return ResponseEntity.ok(salesService.findTopSellingProducts(filter,startDate,endDate));       
    }

}
