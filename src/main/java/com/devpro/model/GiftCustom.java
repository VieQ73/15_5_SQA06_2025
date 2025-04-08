package com.devpro.model;



import java.util.ArrayList;
import java.util.List;

import com.devpro.entities.Gift;
import com.devpro.entities.Product;

public class GiftCustom {
	private Gift gift;
	private List<Product> product = new ArrayList<>();
	public Gift getGift() {
		return gift;
	}
	public void setGift(Gift gift) {
		this.gift = gift;
	}
	public List<Product> getProduct() {
		return product;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
	public GiftCustom(Gift gift, List<Product> product) {
		super();
		this.gift = gift;
		this.product = product;
	}
	public GiftCustom() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
