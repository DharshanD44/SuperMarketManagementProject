package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;
import com.supermarketmanagement.api.Model.Custom.Users.AddUsersDto;
import com.supermarketmanagement.api.Model.Custom.Users.UsersDetailsDto;
import com.supermarketmanagement.api.Model.Entity.UserModel;
import com.supermarketmanagement.api.Repository.UserDetailsRepository;
import com.supermarketmanagement.api.Service.UserService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.UserDao;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class UserServiceImp implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsServiceImp.class);

	@Autowired
	private UserDao userDao;
	
	@Autowired
    private UserDetailsRepository userRepository;
	/**
	 * Adds a new user or updates an existing user.
	 *
	 * @param userDetailsDto user details DTO
	 * @return success message indicating user created or updated
	 */
	@Override
	public CommonMessageResponse addUsers(AddUsersDto userDetailsDto) {
	    logger.info("addUsers: Adding or updating user '{}'", userDetailsDto.getUsername());

	    UserModel usermodel;
	    CommonMessageResponse response = new CommonMessageResponse();

        Optional<UserModel> existingUser = userRepository.findByUsernameAndIsDeletedFalse(userDetailsDto.getUsername());
	    if (userDetailsDto.getUserId() == null) {
	        if (existingUser.isPresent()) {
	            response.setStatus(WebServiceUtil.FAILED_STATUS);
	            response.setData("Username already exists, please create new username.");
	            return response;
	        }
	        usermodel = new UserModel();
	        usermodel.setCreatedDate(LocalDateTime.now());
	    } else {
	        usermodel = userDao.findByUserId(userDetailsDto.getUserId());
	        usermodel.setUpdatedDate(LocalDateTime.now());
	    }

	    usermodel.setPassword(userDetailsDto.getPassword());
	    usermodel.setUsername(userDetailsDto.getUsername());
	    usermodel.setUserRole(userDetailsDto.getUserRole());

	    userDao.saveUser(usermodel);

	    if (userDetailsDto.getUserId() == null) {
	        response.setStatus(WebServiceUtil.SUCCESS_STATUS);
	        response.setData(WebServiceUtil.USER_CREATED);
	    } else {
	        response.setStatus(WebServiceUtil.SUCCESS_STATUS);
	        response.setData(WebServiceUtil.USER_UPDATED);
	    }

	    return response;
	}


	
	/**
	 * Retrieves a list of all users with their details.
	 *
	 * @return list of {@link UsersDetailsDto} containing all user details
	 */
	@Override
	public Map<String, Object> getAllUsersDetails() {
		logger.info("getAllUsersDetails: Retrieving all user details");

		List<UsersDetailsDto> result = userDao.getAllUsersDetails();
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("status",WebServiceUtil.SUCCESS_STATUS);
	    response.put("data",result);
	    return response;
	}


	
	/**
	 * Deletes a user by marking the record as deleted.
	 *
	 * @param id user ID
	 * @return success message indicating user deleted
	 */
	@Override
	public CommonMessageResponse deleteUserById(Long id) {
		logger.info("deleteUserById: Deleting user with ID {}", id);

		UserModel userModel = userDao.findByUserId(id);
		CommonMessageResponse response = new CommonMessageResponse();
		if(userModel==null) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(WebServiceUtil.USER_ID_NOT_FOUND);
		}
		else {
			userModel.setDeleted(true);
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setData(WebServiceUtil.USER_DELETED);
		}
		return response;
	}
	
}	
