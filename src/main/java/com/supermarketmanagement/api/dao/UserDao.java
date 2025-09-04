package com.supermarketmanagement.api.dao;

import java.util.List;

import com.supermarketmanagement.api.Model.Custom.Users.AddUsersDto;
import com.supermarketmanagement.api.Model.Custom.Users.UsersDetailsDto;
import com.supermarketmanagement.api.Model.Entity.UserModel;

public interface UserDao {

	UserModel findByUserId(Long userId);

	void saveUser(UserModel usermodel);

	List<UsersDetailsDto> getAllUsersDetails();
}
