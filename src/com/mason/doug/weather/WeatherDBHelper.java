package com.mason.doug.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDBHelper extends SQLiteOpenHelper {

	public WeatherDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
/*
		 Weather.CurrentConditions.id,
		 Weather.CurrentConditions.day,
		 Weather.CurrentConditions.city,
		 Weather.CurrentConditions.iconPath,
		 Weather.CurrentConditions.condition,
		 Weather.CurrentConditions.wind,
		 Weather.CurrentConditions.temp,
		 Weather.CurrentConditions.humidity
 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
        String createTableCurrent = 
                "CREATE TABLE " + 
                Weather.CurrentConditions.TABLE_NAME + " (" + 
                Weather.CurrentConditions.id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + 
                Weather.CurrentConditions.day + " TEXT, " +
                Weather.CurrentConditions.city + " TEXT, " +        
                Weather.CurrentConditions.iconPath + " TEXT, " + 
                Weather.CurrentConditions.condition + " TEXT, " +
                Weather.CurrentConditions.wind + " TEXT, " +
                Weather.CurrentConditions.temp + " TEXT, " +
                Weather.CurrentConditions.humidity + " TEXT, " + ");";
        db.execSQL(createTableCurrent);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS"+Weather.CurrentConditions.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS"+Weather.ForecastConditions.TABLE_NAME);
		
	}

}
