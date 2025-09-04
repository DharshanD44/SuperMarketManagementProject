package com.supermarketmanagement.api.Model.Custom.Product;

public class ProductPagination {

	    private Integer start = 0;   
	    private Integer length = 10; 
	    
	    public static class ProductFilter {
	        private Double price;
	        private Integer productPackQuantity;

	        public Double getPrice() {
	            return price;
	        }
	        public void setPrice(Double price) {
	            this.price = price;
	        }
	        public Integer getProductPackQuantity() {
	            return productPackQuantity;
	        }
	        public void setProductPackQuantity(Integer productPackQuantity) {
	            this.productPackQuantity = productPackQuantity;
	        }
	    }

	    public Integer getStart() {
	        return start;
	    }
	    public void setStart(Integer start) {
	        this.start = start;
	    }
	    public Integer getLength() {
	        return length;
	    }
	    public void setLength(Integer length) {
	        this.length = length;
	    }
}
