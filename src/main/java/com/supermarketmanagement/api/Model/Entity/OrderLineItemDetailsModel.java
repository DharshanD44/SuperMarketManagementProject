package com.supermarketmanagement.api.Model.Entity;

import java.time.LocalDate;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "order_line_item_status", nullable = false)
    private OrderStatusDto statusDto=OrderStatusDto.NEW;

    @Column(name = "created_date", updatable = false)
    private LocalDate createdDate = LocalDate.now();

    @Column(name = "update_date")
    private LocalDate updateDate;

     public OrderLineItemDetailsModel() {
	
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

    public OrderStatusDto getOrderLineItemStatus() {
        return statusDto;
    }

    public void setOrderLineItemStatus(OrderStatusDto orderLineItemStatus) {
        this.statusDto = orderLineItemStatus;
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

	public OrderLineItemDetailsModel(OrderDetailsModel order, ProductModel product, Integer orderQuantityIndividualUnit,
			Integer orderQuantityInPackage, OrderStatusDto statusDto, LocalDate createdDate, LocalDate updateDate) {
		this.order = order;
		this.product = product;
		this.orderQuantityIndividualUnit = orderQuantityIndividualUnit;
		this.orderQuantityInPackage = orderQuantityInPackage;
		this.statusDto = statusDto;
		this.createdDate = createdDate;
		this.updateDate = updateDate;
	}
    
}

