package com.mason.doug.weather;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class WeatherProvider extends ContentProvider {
	private static final String AUTHORITY = "com.mason.doug.weather.WeatherProvider";
	private static final String TAG = com.mason.doug.weather.WeatherProvider.class.getSimpleName();
	private static final int CURRENT = 1;
	private static final int FORECAST = 2;
	private static final int FORECAST_ID = 3;
	private static final UriMatcher mURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	WeatherDBHelper mSQLHelper;
	private SQLiteDatabase db;
	static {	
	mURIMatcher.addURI(AUTHORITY, "current",CURRENT); 		//maps authority and path to integer 1
	mURIMatcher.addURI(AUTHORITY, "forecast", FORECAST); 	// maps authority and path to integer 2
	mURIMatcher.addURI(AUTHORITY, "forecast/#", FORECAST_ID);
	}	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		if(mURIMatcher.match(uri) == CURRENT){
			return db.delete(Weather.CurrentConditions.TABLE_NAME, null, null);
		}
		else if(mURIMatcher.match(uri) == FORECAST){
			
		}
		else if (mURIMatcher.match(uri) == FORECAST_ID){
			
		}
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		db.insert(Weather.TABLE_NAME, null, values);
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}
