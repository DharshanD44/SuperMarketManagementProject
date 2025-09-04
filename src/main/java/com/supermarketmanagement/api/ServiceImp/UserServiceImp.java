package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarketmanagement.api.Model.Custom.Users.AddUsersDto;
import com.supermarketmanagement.api.Model.Custom.Users.UsersDetailsDto;
import com.supermarketmanagement.api.Model.Entity.UserModel;
import com.supermarketmanagement.api.Service.UserService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.UserDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImp implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public Object addUsers(AddUsersDto userDetailsDto) {
		
		UserModel usermodel;
		
		if(userDetailsDto.getUserId()!=null) {
			usermodel= userDao.findByUserId(userDetailsDto.getUserId());
			usermodel.setUpdatedDate(LocalDateTime.now());
		}
		else {
			usermodel = new UserModel();
			usermodel.setCreatedDate(LocalDateTime.now());
		}
		usermodel.setPassword(userDetailsDto.getPassword());
		usermodel.setUsername(userDetailsDto.getUsername());
		
		userDao.saveUser(usermodel);
		return (userDetailsDto.getUserId()==null)?WebServiceUtil.USER_CREATED:WebServiceUtil.USER_UPDATED;
	}

	@Override
	public List<UsersDetailsDto> getAllUsersDetails() {
		return userDao.getAllUsersDetails();
	}

	@Override
	public Object deleteUserById(Long id) {
		UserModel userModel = userDao.findByUserId(id);
		userModel.setDeleted(true);
		return WebServiceUtil.USER_DELETED;
	}
	
}	
