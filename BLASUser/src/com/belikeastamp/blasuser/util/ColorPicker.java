package com.belikeastamp.blasuser.util;

public class ColorPicker {
	private int red;
	private int green;
	private int blue;
	private int alpha;
	private int colorCode;
	
	public ColorPicker(int red, int green, int blue, int alpha)
	{
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public ColorPicker(int colorId) {
		super();
		this.colorCode = colorId;
	}
	
	public int getRed() {
		return red;
	}
	
	public void setRed(int red) {
		this.red = red;
	}
	
	public int getGreen() {
		return green;
	}
	
	public void setGreen(int green) {
		this.green = green;
	}
	
	public int getBlue() {
		return blue;
	}
	
	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	public int getAlpha()
	{
		return alpha;
	}
	
	public void setAlpha(int alpha)
	{
		this.alpha = alpha;
	}
	public int getColorCode() {
		return colorCode;
	}

	public void setColorCode(int colorCode) {
		this.colorCode = colorCode;
	}

	@Override
	public String toString()
	{
		return "ColorPicker [red=" + red + ", green=" + green + ", blue=" + blue + ", alpha=" + alpha + "]";

	}
	
}