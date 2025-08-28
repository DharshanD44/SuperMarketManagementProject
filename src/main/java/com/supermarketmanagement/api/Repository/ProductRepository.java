package com.supermarketmanagement.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Entity.ProductModel;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long>{

}
