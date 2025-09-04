package com.supermarketmanagement.api.Model.Custom.Product;

public class ProductListRequestModel {
	
	private ProductPagination pagination;
    private ProductPagination.ProductFilter filter;
    private String search;
    private String searchColumn;

    public ProductPagination getPagination() {
        return pagination;
    }
    public void setPagination(ProductPagination pagination) {
        this.pagination = pagination;
    }

    public ProductPagination.ProductFilter getFilter() {
        return filter;
    }
    public void setFilter(ProductPagination.ProductFilter filter) {
        this.filter = filter;
    }

    public String getSearch() {
        return search;
    }
    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearchColumn() {
        return searchColumn;
    }
    public void setSearchColumn(String searchColumn) {
        this.searchColumn = searchColumn;
    }
}
