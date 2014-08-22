package com.belikeastamp.blasuser.util;

import java.util.ArrayList;
import java.util.List;

import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.db.model.Tutorial;
import com.belikeastamp.blasuser.db.model.Workshop;

public class UploadedData {

	private List<Tutorial> tutorials;
	private List<Project> submitProjects;
	private List<Project> savedProjects;
	private List<Workshop> workshops;
	
	public UploadedData() {
		this.tutorials = new ArrayList<Tutorial>();
		this.submitProjects = new ArrayList<Project>();
		this.savedProjects = new ArrayList<Project>();
		this.workshops = new ArrayList<Workshop>();
	}
	
	
	public List<Tutorial> getTutorials() {
		return tutorials;
	}
	public void setTutorials(List<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}
	public List<Project> getSubmitProjects() {
		return submitProjects;
	}
	public void setSubmitProjects(List<Project> submitProjects) {
		this.submitProjects = submitProjects;
	}
	public List<Project> getSavedProjects() {
		return savedProjects;
	}
	public void setSavedProjects(List<Project> savedProjects) {
		this.savedProjects = savedProjects;
	}
	public List<Workshop> getWorkshops() {
		return workshops;
	}
	public void setWorkshops(List<Workshop> workshops) {
		this.workshops = workshops;
	}

}
