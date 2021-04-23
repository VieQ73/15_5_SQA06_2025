package com.devpro.entities;

import java.math.BigDecimal;

public class ProductCustom {
	private Product product;
	
	private Integer discount = 0;
	
	private BigDecimal price_sale = BigDecimal.ZERO;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public BigDecimal getPrice_sale() {
		return price_sale;
	}

	public void setPrice_sale(BigDecimal price_sale) {
		this.price_sale = price_sale;
	}
	
}
