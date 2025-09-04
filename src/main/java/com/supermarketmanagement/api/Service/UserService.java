package com.supermarketmanagement.api.Service;

import java.util.List;

import org.springframework.util.MultiValueMap;

import com.supermarketmanagement.api.Model.Custom.Users.AddUsersDto;
import com.supermarketmanagement.api.Model.Custom.Users.UsersDetailsDto;

public interface UserService {

	Object addUsers(AddUsersDto userDetailsDto);

	List<UsersDetailsDto> getAllUsersDetails();

	Object deleteUserById(Long id);

}
