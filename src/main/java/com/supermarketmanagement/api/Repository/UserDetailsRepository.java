package com.supermarketmanagement.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Entity.UserModel;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserModel, Long>{

}
