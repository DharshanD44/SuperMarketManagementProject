package com.supermarketmanagement.api.Restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarketmanagement.api.Model.Custom.Users.AddUsersDto;
import com.supermarketmanagement.api.Model.Custom.Users.UsersDetailsDto;
import com.supermarketmanagement.api.Service.UserService;

@RestController
@RequestMapping("/users")
public class UserRestcontroller {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("adduser")
	public ResponseEntity<?> addUsers(@RequestBody AddUsersDto userDetailsDto){
		return new ResponseEntity<>(userService.addUsers(userDetailsDto),HttpStatus.OK);
	}
	
	@GetMapping("/list/all")
	public ResponseEntity<List<UsersDetailsDto>> getAllUsersDetails(){
		return new ResponseEntity<List<UsersDetailsDto>>(userService.getAllUsersDetails(),HttpStatus.OK);
	}

	@PostMapping("/delete/id/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id){
		return ResponseEntity.ok(userService.deleteUserById(id));
	}
}
