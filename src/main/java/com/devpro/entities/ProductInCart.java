package com.devpro.entities;

import java.math.BigDecimal;

public class ProductInCart {
	private int productId;
	private String tenSP;
	private int soluong;
	private BigDecimal giaBan;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getTenSP() {
		return tenSP;
	}
	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}
	public int getSoluong() {
		return soluong;
	}
	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}
	public BigDecimal getGiaBan() {
		return giaBan;
	}
	public void setGiaBan(BigDecimal giaBan) {
		this.giaBan = giaBan;
	}
	
	
	
}
