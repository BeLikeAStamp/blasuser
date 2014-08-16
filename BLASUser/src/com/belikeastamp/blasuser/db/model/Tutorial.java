package com.belikeastamp.blasuser.db.model;

import java.io.Serializable;

public class Tutorial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1778891610597448685L;
	
	String title;
	String file;
	Boolean availabale;
	String date;
	Integer onDemand;
	
	public Tutorial(String title, Boolean avail, String file, String date, Integer demand) {
		super();
		this.title = title;
		this.availabale = avail;
		this.file = file;
		this.date = date;
		this.onDemand = demand;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Boolean getAvailabale() {
		return availabale;
	}

	public void setAvailabale(Boolean availabale) {
		this.availabale = availabale;
	}

	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getOnDemand() {
		return onDemand;
	}

	public void setOnDemand(Integer onDemand) {
		this.onDemand = onDemand;
	}

	@Override
	public String toString() {
		return "Tutorial [title=" + title + ", file=" + file + ", availabale="
				+ availabale + ", date=" + date + ", demand=" + onDemand + "]";
	}

	
	
	
	
	

}
