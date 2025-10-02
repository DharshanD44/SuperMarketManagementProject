package com.supermarketmanagement.api.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "customer_master")
public class CustomerModel {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_firstname")
    private String customerFirstName;
    
    @Column(name = "customer_middlename")
    private String customerMiddleName;
    
    @Column(name = "customer_lastname")
    private String customerLastName;

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
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_status", referencedColumnName = "CODE")
	private SuperMarketCode customerStatus;
    
    public SuperMarketCode getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(SuperMarketCode customerStatus) {
		this.customerStatus = customerStatus;
	}

	public LocalDateTime getCustomerUpdatedDate() {
		return customerUpdatedDate;
	}

	public void setCustomerUpdatedDate(LocalDateTime customerUpdatedDate) {
		this.customerUpdatedDate = customerUpdatedDate;
	}

    public CustomerModel() {
    	this.customerStatus = new SuperMarketCode();
    	this.customerStatus.setCode("ACTIVE");
    }

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

    public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerMiddleName() {
		return customerMiddleName;
	}

	public void setCustomerMiddleName(String customerMiddleName) {
		this.customerMiddleName = customerMiddleName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
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
