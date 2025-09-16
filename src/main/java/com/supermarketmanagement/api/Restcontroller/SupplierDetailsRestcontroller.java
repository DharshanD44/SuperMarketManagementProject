package com.supermarketmanagement.api.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.SupplierDetails.SupplierListDto;
import com.supermarketmanagement.api.Service.SupplierDetailsService;

@RestController
@RequestMapping("suppliers")
public class SupplierDetailsRestcontroller {
	
	@Autowired
	private SupplierDetailsService supplierDetailsService;
	
	@GetMapping("/list")
	public ResponseEntity<?> getSuppliersDetails(@RequestBody CommonListRequestModel commonListRequestModel){
		return ResponseEntity.ok(supplierDetailsService.getSuppliersDetails(commonListRequestModel));
	}

	@GetMapping("view/orders/assigned/{supplierId}")
	public ResponseEntity<?> getAssignedOrderDetails(@PathVariable Long supplierId){
		return ResponseEntity.ok(supplierDetailsService.getAssignedOrderDetails(supplierId));
	}
	
	@PostMapping("add")
	public ResponseEntity<?> addSupplierDetails(@RequestBody SupplierListDto supplierListDto){
		return ResponseEntity.ok(supplierDetailsService.addSupplierDetails(supplierListDto));
	}
}
