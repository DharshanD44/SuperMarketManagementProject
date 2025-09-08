package com.supermarketmanagement.api.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.supermarketmanagement.api.Model.Custom.ResponseMessage;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListResponse;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerMessageDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Service.CustomerService;

import retrofit2.http.PATCH;
import retrofit2.http.Path;

@RestController
@RequestMapping(value = "/customers")
public class CustomerRestController {
	
	@Autowired
	private CustomerService customerService;

	
	@GetMapping("/list/all")
	public ResponseEntity<CustomerListResponse>  getAllCustomerDetails(){
		return new ResponseEntity<CustomerListResponse>(customerService.getAllCustomerDetails(),HttpStatus.OK);
	}
	
	@PostMapping("/addorUpdate")
	public ResponseEntity<ResponseMessage> addorUpdateCustomerDetails(@RequestBody CustomerListDto customerListDto){
		boolean isNew = customerListDto.getCustomerId()==null;
		customerService.addorUpdateCustomerDetails(customerListDto);
		if(isNew) {
			return ResponseEntity.ok(customerService.addorUpdateCustomerDetails(customerListDto));
		}
		else {
			return ResponseEntity.ok(customerService.addorUpdateCustomerDetails(customerListDto));
		}
	}
	
	@PostMapping("/delete/id/{id}")
	public ResponseEntity<ResponseMessage> deleteCustomerById(@PathVariable Long id){
		
		return ResponseEntity.ok(customerService.deleteCustomerById(id));
	}
	
	@GetMapping("/list/id/{id}")
	public ResponseEntity<?> findCustomerDetailsById(@PathVariable Long id){
		return ResponseEntity.ok(customerService.findCustomerDetailsById(id));
	}
	

}
