package com.supermarketmanagement.api.Model.Custom.Login;

import java.time.LocalDateTime;

public class OTPDetails {
	private String otp;
	private LocalDateTime expiry;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getExpiry() {
		return expiry;
	}

	public void setExpiry(LocalDateTime expiry) {
		this.expiry = expiry;
	}

}
