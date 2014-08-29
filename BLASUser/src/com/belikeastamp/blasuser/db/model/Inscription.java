package com.belikeastamp.blasuser.db.model;

import java.io.Serializable;

public class Inscription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5505328983740753385L;
	
	Long id;
	Long workshopId;
	String name;
	String phoneNumber;
	String email;
	String expertise;
	int partcipants;
	
	public Inscription() {}
	
	public Inscription(Long workshopId, String name, String phoneNumber,
			String email, String expertise, int partcipants) {
		super();
		this.workshopId = workshopId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.expertise = expertise;
		this.partcipants = partcipants;
	}


	public Long getWorkshopId() {
		return workshopId;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setWorkshopId(Long workshopId) {
		this.workshopId = workshopId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getExpertise() {
		return expertise;
	}


	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}


	public int getPartcipants() {
		return partcipants;
	}


	public void setPartcipants(int partcipants) {
		this.partcipants = partcipants;
	}

	@Override
	public String toString() {
		return "Inscription [workshopId=" + workshopId + ", name=" + name
				+ ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", expertise=" + expertise + ", partcipants=" + partcipants
				+ "]";
	}
	
	
}
