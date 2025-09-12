package com.supermarketmanagement.api.Model.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderStatusDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetailsModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "order_date", nullable = false)
	private LocalDateTime orderDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private CustomerModel customer;

	@Column(name = "order_expected_date")
	private LocalDate orderExpectedDate;

	@OneToOne
	@JoinColumn(name = "order_status", referencedColumnName = "CODE")
	private SuperMarketCode orderStatus;

	@Column(name = "created_date")
	private LocalDateTime createdDate = LocalDateTime.now();

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@Column(name = "total_price")
	private float totalprice;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderLineItemDetailsModel> lineItemDetailsModels = new ArrayList<>();

	public float getTotalprice() {
		return totalprice;
	}
	
	public void setTotalprice(float totalprice) {
		this.totalprice = totalprice;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public CustomerModel getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}

	public LocalDate getOrderExpectedDate() {
		return orderExpectedDate;
	}

	public void setOrderExpectedDate(LocalDate orderExpectedDate) {
		this.orderExpectedDate = orderExpectedDate;
	}

	public SuperMarketCode getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(SuperMarketCode orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public List<OrderLineItemDetailsModel> getLineItemDetailsModels() {
		return lineItemDetailsModels;
	}

	public void setLineItemDetailsModels(List<OrderLineItemDetailsModel> lineItemDetailsModels) {
		this.lineItemDetailsModels = lineItemDetailsModels;
	}

	public OrderDetailsModel() {

		this.orderStatus = new SuperMarketCode();
		this.orderStatus.setCode("NEW");

	}
}
