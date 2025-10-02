package com.supermarketmanagement.api.Restcontroller;

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
import com.supermarketmanagement.api.Service.UserService;

@RestController
@RequestMapping("/users")
public class UserRestcontroller {
	
	@Autowired
	private UserService userService;
	
	/**
	 * Adds a new user.
	 *
	 * @param userDetailsDto request body
	 * @return ResponseEntity with success message after adding new user
	 */
	@PostMapping("adduser")
	public ResponseEntity<?> addUsers(@RequestBody AddUsersDto userDetailsDto){
		return new ResponseEntity<>(userService.addUsers(userDetailsDto), HttpStatus.OK);
	}
	
	/**
	 * Retrieves all users based on filter/search criteria.
	 *
	 * @return ResponseEntity with list of users
	 */
	@GetMapping("/list/all")
	public ResponseEntity<?> getAllUsersDetails(){
		return ResponseEntity.ok(userService.getAllUsersDetails());
	}

	/**
	 * Deletes a user by ID.
	 *
	 * @param id path variable
	 * @return ResponseEntity with success message after deletion
	 */
	@PostMapping("/delete/id/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id){
		return ResponseEntity.ok(userService.deleteUserById(id));
	}
}
