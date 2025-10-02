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
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListRequest;
import com.supermarketmanagement.api.Service.CustomerService;


@RestController
@RequestMapping(value = "/customers")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	/**
	 * Retrieves customer list details based on filter criteria.
	 *
	 * @param commonListRequestModel request body 
	 * @return ResponseEntity with list of customer details
	 */
	@PostMapping("/list/all")
	public ResponseEntity<?> getOrderListDetails(@RequestBody CustomerListRequest commonListRequestModel) {
		return ResponseEntity.ok(customerService.getCustomerDetails(commonListRequestModel));
	}

	
	/**
	 * Adds a new customer or updates an existing customer's details.
	 *
	 * @param customerListDto request body 
	 * @return ResponseEntity with success message after updation
	 */
	@PostMapping("/saveOrUpdate")
	public ResponseEntity<?> addorUpdateCustomerDetails(@RequestBody CustomerListDto customerListDto){
		 return ResponseEntity.ok(customerService.addorUpdateCustomerDetails(customerListDto));
	}

	
	/**
	 * Deletes a customer by ID.
	 *
	 * @param id path variable 
	 * @return ResponseEntity with success message after deletion
	 */
	@PostMapping("/delete/id/{customerid}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable Long customerid) {
		return ResponseEntity.ok(customerService.deleteCustomerById(customerid));
	}
	
	
	/**
	 * Retrieves customer list details based on customer id.
	 *
	 * @param commonListRequestModel request body 
	 * @return ResponseEntity with customer details for the given customer id.
	 */
	@GetMapping("/get/id/{customerid}")
	public ResponseEntity<?> getCustomerDetailsById(@PathVariable Long customerid){
		return ResponseEntity.ok(customerService.findCustomerDetailsById(customerid));
	}
	
	
	/**
	 * Activating or deactivating customer using customer ID.
	 *
	 * @param customerid request param
	 * @param status requesr param
	 * @return ResponseEntity with success message after activating or decativating.
	 */
	@PostMapping("activeOrInactive")
	public ResponseEntity<?> activateOrInactivatedCustomer(@RequestParam Long customerid,@RequestParam Boolean status){
		return ResponseEntity.ok(customerService.activateOrInactivatedCustomer(status,customerid)); 
	}
}

