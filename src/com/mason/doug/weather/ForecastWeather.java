package com.mason.doug.weather;

public class ForecastWeather {
	private int lowTemp,highTemp;
	private String day,icon,condition;
	
	public void setDayOfWeek(String data) {
		// TODO Auto-generated method stub
		this.day = data;
	}

	public void setIconPath(String data) {
		// TODO Auto-generated method stub
		this.icon = data;
	}

	public void setCondition(String data) {
		// TODO Auto-generated method stub
		this.condition = data;
	}
	
	public void setTempLow(int data)
	{
		this.setLowTemp(data);
	}
	public void setTempHigh(int data)
	{
		this.setHighTemp(data);
	}

	public int getLowTemp() {
		return lowTemp;
	}

	public void setLowTemp(int lowTemp) {
		this.lowTemp = lowTemp;
	}

	public int getHighTemp() {
		return highTemp;
	}

	public void setHighTemp(int highTemp) {
		this.highTemp = highTemp;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getCondition()
	{
		return this.condition;
	}
}
