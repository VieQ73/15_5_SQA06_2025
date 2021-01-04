package com.devpro.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tbl_contact")
public class Contact extends BaseEntity{
	@Column(name="name", nullable = false)
	private String name;
	
	@Column (name="email", nullable = false)
	private String email;
	
	@Column (name="message", nullable = false)
	private String messgae;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessgae() {
		return messgae;
	}

	public void setMessgae(String messgae) {
		this.messgae = messgae;
	}
	
	
}
