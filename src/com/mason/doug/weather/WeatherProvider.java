package com.mason.doug.weather;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class WeatherProvider extends ContentProvider {
	public static final String AUTHORITY = "com.mason.doug.weather.WeatherProvider";
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
			return db.delete(Weather.CurrentConditions.TABLE_NAME, null , null);
		}
		else if(mURIMatcher.match(uri) == FORECAST){
			return db.delete(Weather.ForecastConditions.TABLE_NAME,null,selectionArgs);
		}
		else if (mURIMatcher.match(uri) == FORECAST_ID){
			return db.delete(Weather.ForecastConditions.TABLE_NAME,Weather.ForecastConditions.id+"="+uri.getQuery(),selectionArgs);
		}
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
        if (mURIMatcher.match(uri) != CURRENT&& (mURIMatcher.match(uri) != FORECAST)) {
            throw new IllegalArgumentException("Unknow URI" + uri);
        }
		else{
            SQLiteDatabase data = mSQLHelper.getWritableDatabase();
            switch(mURIMatcher.match(uri)){
                case CURRENT:
                    long insertedcurrent = data.insert(Weather.CurrentConditions.TABLE_NAME,null,values);
                    if(insertedcurrent > 0){
                        Uri currentURI = ContentUris.withAppendedId(Weather.CURRENT_URI,insertedcurrent);
                        getContext().getContentResolver().notifyChange(uri, null);
                        return currentURI;
                    }
                    break;
                case FORECAST:
                    long insertedForecast = data.insert(Weather.ForecastConditions.TABLE_NAME,null,values);
                    if(insertedForecast > 0){
                        Uri forecastURI = ContentUris.withAppendedId(Weather.FORECAST_URI,insertedForecast);
                        getContext().getContentResolver().notifyChange(uri, null);
                        return forecastURI;
                    }
                    break;
            }
        }
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
        Cursor value = null;
        switch (mURIMatcher.match(uri)){

            case CURRENT:
                value= db.query(Weather.CurrentConditions.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                break;
            case FORECAST:
                value= db.query(Weather.ForecastConditions.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                break;
            case FORECAST_ID:
                value= db.query(Weather.ForecastConditions.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                break;
        }
		return value;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
        int val=0;
        switch (mURIMatcher.match(uri)){
            case CURRENT:
                val = db.update(Weather.CurrentConditions.TABLE_NAME,values,selection,selectionArgs);
            case FORECAST:
                val = db.update(Weather.ForecastConditions.TABLE_NAME,values,selection,selectionArgs);
                break;
            case FORECAST_ID:
                val = db.update(Weather.ForecastConditions.TABLE_NAME,values,selection,selectionArgs);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
		return val;
	}
}
