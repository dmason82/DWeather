package com.mason.doug.weather;

public class WeatherCurrentCondition {
	private String day,iconPath,humidity,condition,wind,city;
	private Integer temp;
	public void setDayOfWeek(String data) {
		// TODO Auto-generated method stub
		day = data;
	}

	public void setIconPath(String data) {
		// TODO Auto-generated method stub
		iconPath = data;
	}

	public void setCondition(String data) {
		// TODO Auto-generated method stub
		condition = data;
	}
	public void setTemp(int data)
	{
		temp = data;
	}
	public void setHumidity(String data)
	{
		humidity=data;
	}
	public void setWind(String data)
	{
		wind = data;
	}
	public String getDay()
	{
		return day;
	}
	public String getIconPath()
	{
		return iconPath;
	}
	public String getCondition(){
		return condition;
	}
	public String getWind()
	{
		return wind;
	}
	public String getHumidity(){
		return humidity;
	}
	public Integer getTemp()
	{
		return temp;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}