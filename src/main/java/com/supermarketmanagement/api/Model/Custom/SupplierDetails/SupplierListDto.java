package com.supermarketmanagement.api.Model.Custom.SupplierDetails;

import java.time.LocalDateTime;

public class SupplierListDto {
	
    private Long supplierId;
    private String supplierName;
    private String email;
    private String mobileNumber;
    private String address;
    private String city;
    private String pincode;
    private String country;
    private Boolean isActive;

    public SupplierListDto() {
    }

    public SupplierListDto(Long supplierId, String supplierName, String email, String mobileNumber, String address,
                       String city, String pincode, String country, Boolean isActive) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.city = city;
        this.pincode = pincode;
        this.country = country;
        this.isActive = isActive;
    }

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}		   
}
