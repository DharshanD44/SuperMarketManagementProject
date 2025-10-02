package com.supermarketmanagement.api.Service;

import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;
import com.supermarketmanagement.api.Model.Custom.Users.AddUsersDto;
import com.supermarketmanagement.api.Model.Custom.Users.UsersDetailsDto;

public interface UserService {

	CommonMessageResponse addUsers(AddUsersDto userDetailsDto);

	Map<String, Object> getAllUsersDetails();

	CommonMessageResponse deleteUserById(Long id);

}
