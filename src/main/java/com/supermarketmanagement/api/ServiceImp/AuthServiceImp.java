package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;
import com.supermarketmanagement.api.Model.Custom.Login.ForgotPasswordRequest;
import com.supermarketmanagement.api.Model.Custom.Login.LoginRequestDto;
import com.supermarketmanagement.api.Model.Custom.Login.LoginResponseDto;
import com.supermarketmanagement.api.Model.Custom.Login.OTPDetails;
import com.supermarketmanagement.api.Model.Custom.Login.ResetPasswordRequest;
import com.supermarketmanagement.api.Model.Entity.UserModel;
import com.supermarketmanagement.api.Repository.UserDetailsRepository;
import com.supermarketmanagement.api.Util.WebServiceUtil;

@Service
public class AuthServiceImp {

	@Autowired
	private UserDetailsRepository userRepository;

	@Autowired
	private EmailService emailService;

	private Map<String, OTPDetails> otpStore = new HashMap<>();

	public LoginResponseDto login(LoginRequestDto request) {
		LoginResponseDto response = new LoginResponseDto();

		Optional<UserModel> userOpt = userRepository.findByUsernameAndIsDeletedFalse(request.getUsername());

		if (userOpt.isEmpty()) {
			response.setMessage("Username not registered");
			response.setUserRole(null);
			response.setUserId(null);
			return response;
		}

		UserModel user = userOpt.get();

		if (!user.getPassword().equals(request.getPassword())) {
			response.setMessage("Invalid password");
			response.setUserRole(null);
			response.setUserId(null);
			return response;
		}

		response.setMessage("Login successful");
		response.setUserRole(user.getUserRole());
		response.setUserId(user.getUserId());
		return response;
	}

	public CommonMessageResponse forgotPassword(ForgotPasswordRequest request) {
		CommonMessageResponse response = new CommonMessageResponse();

		Optional<UserModel> userOpt = userRepository.findByUsernameAndIsDeletedFalse(request.getUsername());
		if (userOpt.isEmpty()) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(WebServiceUtil.CANT_UPDATE_ORDER);
			return response;
		}

		String otp = generateOtp();
		OTPDetails otpDetails = new OTPDetails();
		otpDetails.setOtp(otp);
		otpDetails.setExpiry(LocalDateTime.now().plusMinutes(5));
		otpStore.put(request.getUsername(), otpDetails);

		emailService.sendOtpEmail(request.getUsername(), otp);

		response.setStatus(WebServiceUtil.SUCCESS_STATUS);
		response.setData("OTP sent to " + request.getUsername());
		return response;
	}

	public CommonMessageResponse verifyOtpAndResetPassword(ResetPasswordRequest request) {
		CommonMessageResponse response = new CommonMessageResponse();

		String username = request.getUsername();
		OTPDetails otpDetails = otpStore.get(username);

		if (otpDetails == null || otpDetails.getExpiry().isBefore(LocalDateTime.now())) {
			response.setStatus("FAILURE");
			response.setData("OTP expired or username not found");
			return response;
		}

		if (!otpDetails.getOtp().equals(request.getOtp())) {
			response.setStatus("FAILURE");
			response.setData("Invalid OTP");
			return response;
		}

		Optional<UserModel> userOpt = userRepository.findByUsernameAndIsDeletedFalse(username);
		UserModel user = userOpt.get();
		user.setPassword(request.getNewPassword());
		user.setUpdatedDate(LocalDateTime.now());
		userRepository.save(user);

		otpStore.remove(username);

		response.setStatus(WebServiceUtil.SUCCESS_STATUS);
		response.setData(WebServiceUtil.PASSWORD_RESET);
		return response;
	}

	private String generateOtp() {
		Random random = new Random();
		return String.valueOf(100000 + random.nextInt(900000));
	}
}

