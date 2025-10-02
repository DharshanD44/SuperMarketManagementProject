package com.supermarketmanagement.api.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import java.util.List;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendOutOfStockAlert(List<String> productDetails) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom("dharshangk333@gmail.com");
			helper.setTo("dharshanvj44@gmail.com");
			helper.setSubject("⚠ Out of Stock Alert");
			
			StringBuilder body = new StringBuilder();
			body.append("<h3>The following products are out of stock:</h3>");
			body.append("<table border='1' cellpadding='5' cellspacing='0'>");
			body.append("<tr><th>ID</th><th>Name</th><th>PackQuantity</th><th>Price</th></tr>");

			for (String detail : productDetails) {
				String[] parts = detail.split("\\|"); 
				body.append("<tr>");
				for (String col : parts) {
					body.append("<td>").append(col.split(":")[1].trim()).append("</td>");
				}
				body.append("</tr>");
			}

			body.append("</table>");
			body.append("<br><b>Please restock them immediately.</b>");

			helper.setText(body.toString(), true); 

			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dharshangk333@gmail.com");
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("Use the following token to reset your password:\n\n" + token +
                        "\n\nThis token will expire in 15 minutes.");

        mailSender.send(message);
    }


	public void sendOtpEmail(String username, String otp) {
		   SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("dharshangk333@gmail.com");
	        message.setTo(username);
	        message.setSubject("Your OTP for Password Reset");
	        message.setText("Your OTP for resetting your password is: " + otp +
	                        "\n\nThis OTP is valid for 5 minutes only.");

	        mailSender.send(message);		
	}
}
