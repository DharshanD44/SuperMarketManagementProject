package com.supermarketmanagement.api.Model.Entity;

import java.time.LocalDateTime;

import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderStatusDto;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_line_item_details")
public class OrderLineItemDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id")
    private Long orderLineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderDetailsModel order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;

    @Column(name = "order_quantity_individual_unit", nullable = false)
    private Integer orderQuantityIndividualUnit;

    @Column(name = "order_quantity_in_package", nullable = false)
    private Integer orderQuantityInPackage;

	@OneToOne
	@JoinColumn(name = "order_status",referencedColumnName = "CODE",nullable = false)
	private SuperMarketCode orderStatus;
	
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "update_date")
    private LocalDateTime updateDate;
    
    @Column(name = "individual_price")
    private float price;

    
    public OrderLineItemDetailsModel() {
    	  this.orderStatus = new SuperMarketCode();
          this.orderStatus.setCode("NEW");
	
    }

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public OrderDetailsModel getOrder() {
        return order;
    }

    public void setOrder(OrderDetailsModel order) {
        this.order = order;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public Integer getOrderQuantityIndividualUnit() {
        return orderQuantityIndividualUnit;
    }

    public void setOrderQuantityIndividualUnit(Integer orderQuantityIndividualUnit) {
        this.orderQuantityIndividualUnit = orderQuantityIndividualUnit;
    }

    public Integer getOrderQuantityInPackage() {
        return orderQuantityInPackage;
    }

    public void setOrderQuantityInPackage(Integer orderQuantityInPackage) {
        this.orderQuantityInPackage = orderQuantityInPackage;
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

	public SuperMarketCode getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(SuperMarketCode orderStatus) {
		this.orderStatus = orderStatus;
	}	

}
