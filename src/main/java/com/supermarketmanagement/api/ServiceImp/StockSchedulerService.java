package com.supermarketmanagement.api.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Repository.ProductRepository;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockSchedulerService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EmailService emailService;
    

    @Scheduled(cron = "*/10 * 10 * * *")
    public void checkStockQuantity() {
        List<ProductModel> outOfStockProducts = productRepository.findByProductCurrentStockPackageCount(0);

	        if (!outOfStockProducts.isEmpty()) {
	        	List<String> productDetails = outOfStockProducts.stream()
	                    .map(p -> "ID: " + p.getProductId()
	                            + " | Name: " + p.getProductName()
	                            + " | PackQuantity: " + p.getProductPackQuantity()
	                            + " | Price: " + p.getProductPrice())
	                    .collect(Collectors.toList());

	            System.out.println("⚠ Products OUT OF STOCK: " + productDetails);
	
	            emailService.sendOutOfStockAlert(productDetails);
	        }
    }
}
