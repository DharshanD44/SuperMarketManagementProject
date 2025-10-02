package com.supermarketmanagement.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Entity.SuppliersModel;

@Repository
public interface SupplierDetailsRepository extends JpaRepository<SuppliersModel, Long>{

	SuppliersModel findBySupplierId(Long supplierid);

	SuppliersModel findBySupplierEmailId(String email);

	SuppliersModel findBySupplierMobileNumber(Long mobileNumber);

}
