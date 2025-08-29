package com.supermarketmanagement.api.Model.Entity;

import java.time.LocalDate;
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
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "customer_id", nullable = false)  
    private CustomerModel customer;

    public float getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(float totalprice) {
		this.totalprice = totalprice;
	}

	@Column(name = "order_expected_date")
    private LocalDate orderExpectedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatusDto orderStatus=OrderStatusDto.NEW;

    @Column(name = "created_date")
    private LocalDate createdDate = LocalDate.now();

    @Column(name = "update_date")
    private LocalDate updateDate;
    
    @Column(name = "total_price")
    private float totalprice;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLineItemDetailsModel> lineItemDetailsModels = new ArrayList<>();


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	public LocalDate getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(LocalDate orderDate) {
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


	public OrderStatusDto getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(OrderStatusDto orderStatus) {
		this.orderStatus = orderStatus;
	}


	public LocalDate getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}


	public LocalDate getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}


	public List<OrderLineItemDetailsModel> getLineItemDetailsModels() {
		return lineItemDetailsModels;
	}


	public void setLineItemDetailsModels(List<OrderLineItemDetailsModel> lineItemDetailsModels) {
		this.lineItemDetailsModels = lineItemDetailsModels;
	}

	public OrderDetailsModel(LocalDate orderDate, CustomerModel customer, LocalDate orderExpectedDate,
			OrderStatusDto orderStatus, LocalDate createdDate, LocalDate updateDate, float totalprice,
			List<OrderLineItemDetailsModel> lineItemDetailsModels) {
		super();
		this.orderDate = orderDate;
		this.customer = customer;
		this.orderExpectedDate = orderExpectedDate;
		this.orderStatus = orderStatus;
		this.createdDate = createdDate;
		this.updateDate = updateDate;
		this.totalprice = totalprice;
		this.lineItemDetailsModels = lineItemDetailsModels;
	}

	public OrderDetailsModel() {
		
	}
 
   
    
}

