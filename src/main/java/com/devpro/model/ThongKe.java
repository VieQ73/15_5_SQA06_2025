package com.devpro.model;

import java.math.BigDecimal;

import com.devpro.entities.Product;

public class ThongKe {
	private Product product;
	private Integer tongSoLuong;
	private BigDecimal tongGia;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getTongSoLuong() {
		return tongSoLuong;
	}
	public void setTongSoLuong(Integer tongSoLuong) {
		this.tongSoLuong = tongSoLuong;
	}
	public BigDecimal getTongGia() {
		return tongGia;
	}
	public void setTongGia(BigDecimal tongGia) {
		this.tongGia = tongGia;
	}
	@Override
	public String toString() {
		return "ThongKe [product=" + product + ", tongSoLuong=" + tongSoLuong + ", tongGia=" + tongGia + "]";
	}
	
}
