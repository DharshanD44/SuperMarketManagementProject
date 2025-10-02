package com.supermarketmanagement.api.Model.Custom.Users;

import java.time.LocalDateTime;

public class AddUsersDto {

	private Long userId;
	private String username;
	private String password;
	private String userRole;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public AddUsersDto(Long userId, String username, String password, String userRole) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userRole = userRole;
	}

	public AddUsersDto() {
	}
}
