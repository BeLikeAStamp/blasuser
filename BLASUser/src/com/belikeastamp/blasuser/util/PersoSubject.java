package com.belikeastamp.blasuser.util;

public class PersoSubject {
	private String name;
	private String sexe;
	private String age;

	public PersoSubject() {
		name = "X";
		sexe = "X";
		age = "X";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return name + ", " + sexe + ", " + age;
	}
	
}
