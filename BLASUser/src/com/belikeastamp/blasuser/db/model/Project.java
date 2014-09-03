package com.belikeastamp.blasuser.db.model;

import java.io.File;
import java.io.Serializable;

import android.net.Uri;

public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8213396978710850752L;

	private String name = "";
	private String detail= "";
	private String subDate= "";
	private String type= "";
	private String orderDate= "";
	private String path_to_prototype= "";
	private Uri trackFile = null;
	private String perso= "";
	private String colors= "";
	private int status = 0;
	private int quantity = 0;
	private Long remoteId;
	private Long userId;

	public Project() {}

	public Project(Project p) {
		this.name = p.getName();
		this.subDate = p.getSubDate();
		this.status = p.getStatus();
		this.detail = p.getDetail();
		this.type = p.getType();
		this.orderDate = p.getOrderDate();
		this.quantity = p.getQuantity();
		this.remoteId = p.getRemoteId();
		this.colors = p.getColors();
		this.userId = p.getUserId();

	}

	public Project(String project_name, String sub_date, int project_status,
			String detail, String type, String order_date, int nbr_cards, String perso) {
		super();
		this.name = project_name;
		this.subDate = sub_date;
		this.status = project_status;
		this.detail = detail;
		this.type = type;
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

	public String getDetail() {
		return detail;
	}



	public void setDetail(String detail) {
		this.detail = detail;
	}



	public String getSubDate() {
		return subDate;
	}

	public void setSubDate(String subDate) {
		this.subDate = subDate;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Uri getTrackFile() {
		return trackFile;
	}

	public void setTrackFile(Uri uri) {
		this.trackFile = uri;
	}


	public String getPrintableDetails() {
		
		String s = getDetail().replace(", ", "\n");
		return s;
	}

	@Override
	public String toString() {
		return "Project [name=" + name + ", detail=" + detail + ", subDate="
				+ subDate + ", type=" + type + ", orderDate=" + orderDate
				+ ", path_to_prototype=" + path_to_prototype + ", trackFile="
				+ trackFile + ", perso=" + perso + ", colors=" + colors
				+ ", status=" + status + ", quantity=" + quantity
				+ ", remoteId=" + remoteId + ", userId=" + userId + "]";
	}
	
	
	
}
