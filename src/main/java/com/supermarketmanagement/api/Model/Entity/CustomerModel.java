package com.supermarketmanagement.api.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Entity
@Table(name = "customer_master")
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @OneToOne
    @JoinColumn(name = "customer_gender",referencedColumnName = "CODE", nullable = false)
    private SuperMarketCode customerGender;
    
    @Column(name = "customer_mobileno", unique = true)
    private Long customerMobileno;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_location")
    private String customerLocation;

    @Column(name = "customer_city")
    private String customerCity;

    @Column(name = "customer_pincode")
    private String customerPincode;

    @Column(name = "customer_email", unique = true)
    private String customerEmail;

    @Column(name = "customer_createdDate")
    private LocalDateTime customerCreatedDate;

    @Column(name = "customer_last_effective_date")
    private LocalDateTime customerLastEffectiveDate;

    @Column(name = "customer_updated_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime customerUpdatedDate;
    
	@OneToMany
    private List<OrderDetailsModel> detailsModels;
    
    public LocalDateTime getCustomerUpdatedDate() {
		return customerUpdatedDate;
	}

	public void setCustomerUpdatedDate(LocalDateTime customerUpdatedDate) {
		this.customerUpdatedDate = customerUpdatedDate;
	}

    public CustomerModel() {}

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public SuperMarketCode getCustomerGender() {
		return customerGender;
	}

	public void setCustomerGender(SuperMarketCode customerGender) {
		this.customerGender = customerGender;
	}

	public List<OrderDetailsModel> getDetailsModels() {
		return detailsModels;
	}

	public void setDetailsModels(List<OrderDetailsModel> detailsModels) {
		this.detailsModels = detailsModels;
	}

	public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCustomerMobileno() {
        return customerMobileno;
    }

    public void setCustomerMobileno(Long customerMobileno) {
        this.customerMobileno = customerMobileno;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerLocation() {
        return customerLocation;
    }

    public void setCustomerLocation(String customerLocation) {
        this.customerLocation = customerLocation;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerPincode() {
        return customerPincode;
    }

    public void setCustomerPincode(String customerPincode) {
        this.customerPincode = customerPincode;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public LocalDateTime getCustomerCreatedDate() {
        return customerCreatedDate;
    }

    public void setCustomerCreatedDate(LocalDateTime customerCreatedDate) {
        this.customerCreatedDate = customerCreatedDate;
    }

	public LocalDateTime getCustomerLastEffectiveDate() {
		return customerLastEffectiveDate;
	}

	public void setCustomerLastEffectiveDate(LocalDateTime customerLastEffectiveDate) {
		this.customerLastEffectiveDate = customerLastEffectiveDate;
	}
	
}
