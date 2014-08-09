package com.belikeastamp.blasuser.db.model;

import java.io.Serializable;

import android.net.Uri;

public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8213396978710850752L;

	private String name;
	private String subDate;
	private String theme;
	private String type;
	private String style;
	private String orderDate;
	private String path_to_prototype;
	private Uri path_to_track;
	private String perso;
	private String colors;
	private int status;
	private int quantity;
	private Long remoteId;

	public Project() {}



	public Project(String project_name, String sub_date, int project_status,
			String theme, String type, String style, String order_date, int nbr_cards, String perso) {
		super();
		this.name = project_name;
		this.subDate = sub_date;
		this.status = project_status;
		this.theme = theme;
		this.type = type;
		this.style = style;
		this.orderDate = order_date;
		this.quantity = nbr_cards;
		this.perso = perso;
		this.remoteId = Long.valueOf("-1");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubDate() {
		return subDate;
	}

	public void setSubDate(String subDate) {
		this.subDate = subDate;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStyle() {
		return style;
	}



	public void setStyle(String style) {
		this.style = style;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getPath_to_prototype() {
		return path_to_prototype;
	}

	public void setPath_to_prototype(String path_to_prototype) {
		this.path_to_prototype = path_to_prototype;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(Long remoteId) {
		this.remoteId = remoteId;
	}



	public String getPerso() {
		return perso;
	}



	public void setPerso(String perso) {
		this.perso = perso;
	}



	public String getColors() {
		return colors;
	}



	public void setColors(String colors) {
		this.colors = colors;
	}



	public Uri getPath_to_track() {
		return path_to_track;
	}



	public void setPath_to_track(Uri path_to_track) {
		this.path_to_track = path_to_track;
	}



	@Override
	public String toString() {
		return "Project [name=" + name + "\nsubDate=" + subDate + "\ntheme="
				+ theme + "\ntype=" + type + "\nstyle=" + style
				+ "\norderDate=" + orderDate + "\nperso=" + perso + "\ncolors="
				+ colors + "\nstatus=" + status + "\nquantity=" + quantity
				+ "\nremoteId=" + remoteId + "]";
	}

	
	
}
