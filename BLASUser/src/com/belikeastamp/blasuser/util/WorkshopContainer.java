package com.belikeastamp.blasuser.util;

import java.util.ArrayList;
import java.util.List;

import com.belikeastamp.blasuser.db.model.Workshop;



public class WorkshopContainer {
	
	public List<Workshop> wkList;
	
	
	public WorkshopContainer() {
		this.wkList = new ArrayList<Workshop>();
	}
	
	public WorkshopContainer(List<Workshop> wkList) {
		this.wkList = wkList;
	}

	public List<Workshop> getWkList() {
		return this.wkList;
	}
	
	public void setWkList(List<Workshop> list) {
		this.wkList = list;
	}

}
