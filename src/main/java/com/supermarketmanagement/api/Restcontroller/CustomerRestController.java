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
import com.supermarketmanagement.api.Model.Custom.ApiResponse;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Service.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public ResponseEntity<?> getOrderListDetails(@RequestBody CommonListRequestModel commonListRequestModel) {
		return ResponseEntity.ok(customerService.getCustomerDetails(commonListRequestModel));
	}

	@PostMapping("/addorUpdate")
	public ResponseEntity<?> addorUpdateCustomerDetails(@RequestBody CustomerListDto customerListDto){
		 return ResponseEntity.ok(customerService.addorUpdateCustomerDetails(customerListDto));
	}

	@PostMapping("/delete/id/{id}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable Long id) {

		return ResponseEntity.ok(customerService.deleteCustomerById(id));
	}

}
