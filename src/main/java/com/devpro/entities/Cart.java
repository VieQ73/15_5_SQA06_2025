package com.devpro.entities;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.devpro.model.ProductInCart;
import com.devpro.repositories.ProductRepo;
import com.devpro.services.ProductSaleService;

public class Cart {
	
	
	private List<ProductInCart> sanPhamTrongGioHangs = new ArrayList<ProductInCart>();

	public List<ProductInCart> getSanPhamTrongGioHangs() {
		return sanPhamTrongGioHangs;
	}

	public void setSanPhamTrongGioHangs(List<ProductInCart> sanPhamTrongGioHangs) {
		this.sanPhamTrongGioHangs = sanPhamTrongGioHangs;
	}
	
	public BigDecimal getTotal(ProductRepo productRepo, ProductSaleService productSaleService) {
		BigDecimal decimal = BigDecimal.ZERO;
		for(ProductInCart phamTrongGioHang : sanPhamTrongGioHangs) {
			Product product = productRepo.getOne(phamTrongGioHang.getProductId());
			Integer discount = productSaleService.getDiscountByIdProduct(product.getId());
			
			BigDecimal t = product.getPrice().subtract(product.getPrice().multiply(new BigDecimal(discount).divide(new BigDecimal(100))));
				decimal = decimal.add(t);
		}
		return decimal;
	}
	
}
