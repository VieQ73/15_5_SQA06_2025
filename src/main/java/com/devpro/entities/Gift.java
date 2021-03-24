package com.devpro.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
@Entity
@Table(name = "tbl_gifts")
public class Gift extends BaseEntity{
	@Column(name = "title", nullable = true)
	private String title;

	@Column(name = "price", precision = 13, scale = 2, nullable = false)
	private BigDecimal price;


	@Lob
	@Column(name = "description", nullable = true)
	private String description;

	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gift", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Images> giftImages = new ArrayList<Images>();
	
	public void addGiftImages(Images _giftImages) {
		_giftImages.setGift(this);
		giftImages.add(_giftImages);
	}
	
	public void removeGiftImages(Images _giftImages) {
		_giftImages.setGift(null);
		giftImages.remove(_giftImages);
	}

	public void removeGiftImages() {
		for(Images giftImages : giftImages) {
			removeGiftImages(giftImages);
		}
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Images> getGiftImages() {
		return giftImages;
	}

	public void setGiftImages(List<Images> giftImages) {
		this.giftImages = giftImages;
	}

	
	
	
	
}
