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

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListRequest;
import com.supermarketmanagement.api.Model.Custom.SupplierDetails.SupplierListDto;
import com.supermarketmanagement.api.Service.SupplierDetailsService;

import ch.qos.logback.core.status.Status;
import retrofit2.http.Path;

@RestController
@RequestMapping("suppliers")
public class SupplierDetailsRestcontroller {
	
	@Autowired
	private SupplierDetailsService supplierDetailsService;
	
	/**
	 * Retrieves all supplier details based on filter/search criteria.
	 *
	 * @param commonListRequestModel request body
	 * @return ResponseEntity with supplier details list.
	 */
	@PostMapping("/list/all")
	public ResponseEntity<?> getSuppliersDetails(@RequestBody CustomerListRequest commonListRequestModel){
		return ResponseEntity.ok(supplierDetailsService.getSuppliersDetails(commonListRequestModel));
	}
	
	
	/**
	 * Adds a new supplier.
	 *
	 * @param supplierListDto request body
	 * @return ResponseEntity with success message after adding new supplier
	 */
	@PostMapping("saveOrUpdate")
	public ResponseEntity<?> addSupplierDetails(@RequestBody SupplierListDto supplierListDto){
		return ResponseEntity.ok(supplierDetailsService.saverUpdateSupplierDetails(supplierListDto));
	}
	

	/**
	 * Retrieves assigned order details for a supplier.
	 *
	 * @param supplierId path variable
	 * @return ResponseEntity with list of assigned orders
	 */
	@GetMapping("view/orders/assigned/{supplierId}")
	public ResponseEntity<?> getAssignedOrderDetails(@PathVariable Long supplierId){
		return ResponseEntity.ok(supplierDetailsService.getAssignedOrderDetails(supplierId));
	}
	
	
	/**
	 * Deletes a supplier by ID.
	 *
	 * @param supplier_id path variable
	 * @return ResponseEntity with success message after deletion
	 */
	@PostMapping("delete/{supplierid}")
	public ResponseEntity<?> deleteSupplierById(@PathVariable Long supplierid){
		return ResponseEntity.ok(supplierDetailsService.deleteSupplierById(supplierid));
	}
	
	/**
	 * Activating or deactivating supplier using supplier ID.
	 *
	 * @param supplier_id request param
	 * @param status requesr param
	 * @return ResponseEntity with success message after activating or decativating.
	 */
	@PostMapping("activeOrInactive")
	public ResponseEntity<?> activateOrInactivate(@RequestParam Long supplierid,@RequestParam Boolean status){
		return ResponseEntity.ok(supplierDetailsService.activateOrInactivate(status,supplierid));
	}
}

