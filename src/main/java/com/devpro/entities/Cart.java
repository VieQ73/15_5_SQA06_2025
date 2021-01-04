package com.devpro.entities;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;

import com.devpro.repositories.ProductRepo;

public class Cart {
	private List<ProductInCart> sanPhamTrongGioHangs = new ArrayList<ProductInCart>();

	public List<ProductInCart> getSanPhamTrongGioHangs() {
		return sanPhamTrongGioHangs;
	}

	public void setSanPhamTrongGioHangs(List<ProductInCart> sanPhamTrongGioHangs) {
		this.sanPhamTrongGioHangs = sanPhamTrongGioHangs;
	}
	
	public BigDecimal getTotal(ProductRepo productRepo) {
		BigDecimal decimal = BigDecimal.ZERO;
		for(ProductInCart phamTrongGioHang : sanPhamTrongGioHangs) {
			Product product = productRepo.getOne(phamTrongGioHang.getProductId());
			decimal = decimal.add(product.getPrice());
		}
		return decimal;
	}
}
