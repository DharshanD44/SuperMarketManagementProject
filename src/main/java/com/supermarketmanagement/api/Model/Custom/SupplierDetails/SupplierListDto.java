package com.supermarketmanagement.api.Model.Custom.SupplierDetails;

import java.time.LocalDateTime;

public class SupplierListDto {
	
	private Integer sno;
    private Long supplierId;
    private String supplierName;
    private String email;
    private Long mobileNumber;
    private String address;
    private String city;
    private String pincode;
    private String country;
    private String isActive;

    public SupplierListDto() {
    }

    public SupplierListDto(Long supplierId, String supplierName, String email, Long mobileNumber, String address,
                       String city, String pincode, String country, String isActive) {
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

    
	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
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

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}		   
}
