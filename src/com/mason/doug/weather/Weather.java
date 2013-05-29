package com.mason.doug.weather;

import android.provider.BaseColumns;
import android.net.Uri;

public class Weather {
    public static final Uri CURRENT_URI = Uri.parse("content://" + WeatherProvider.AUTHORITY + "/current");
    public static final Uri FORECAST_URI = Uri.parse("content://" + WeatherProvider.AUTHORITY + "/forecast");
    public static final String ACTIVITY_BROADCAST = "com.mason.doug.weather2.ACTIVITY_BROADCAST";
    // Defines the key for the status "extra" in an Intent
    public static final String EXTENDED_DATA_STATUS = "com.mason.doug.weather2.STATUS";
    public static final String STATUS_OK = "ok";
    public static final class CurrentConditions{

     public static final String TABLE_NAME = "current";
	 	public static final String id=BaseColumns._ID;
		public static final String day="day",iconPath="iconpath",humidity="humidity",
		condition="condition",wind="wind",city="city",temp="temp";
		
	 public static final String[] PROJECTION = new String[]{
		 Weather.CurrentConditions.id,
		 Weather.CurrentConditions.day,
		 Weather.CurrentConditions.city,
		 Weather.CurrentConditions.iconPath,
		 Weather.CurrentConditions.condition,
		 Weather.CurrentConditions.wind,
		 Weather.CurrentConditions.temp,
		 Weather.CurrentConditions.humidity
	 };
 }
 public static final class ForecastConditions{
	 public static final String TABLE_NAME = "forecast";
	 public static final String id=BaseColumns._ID;
		public static final String lowTemp="lowtemp",highTemp="hightemp",day="day",icon="icon",condition="condition";
		public static final String[] PROJECTION = new String[]{
			Weather.ForecastConditions.id,
			Weather.ForecastConditions.day,
			Weather.ForecastConditions.icon,
			Weather.ForecastConditions.condition,
			Weather.ForecastConditions.lowTemp,
			Weather.ForecastConditions.highTemp
		};
 }
}
