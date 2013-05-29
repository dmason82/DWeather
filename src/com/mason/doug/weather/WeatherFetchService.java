package com.mason.doug.weather;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.content.ContentValues;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
/**
 * Created by dougmason on 5/25/13.
 */
public class WeatherFetchService extends IntentService {
    String city;
    WUEngine engine;
    public IBinder onBind(Intent intent) {
        return null;
    }
    public WeatherFetchService(){
        super("WeatherFetchService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String status="";
        if(intent.hasExtra("city")){
            this.city = intent.getStringExtra("city");

            engine = new WUEngine();
            WeatherCollection collection = (WeatherCollection)engine.getWeather(city,getApplicationContext());
            WeatherCurrentCondition current = collection.getCurrentConditions();
            ContentValues values = new ContentValues();
            values.put(Weather.CurrentConditions.city,current.getCity());
            values.put(Weather.CurrentConditions.condition,current.getCondition());
            values.put(Weather.CurrentConditions.day,current.getDay());
            values.put(Weather.CurrentConditions.humidity,current.getHumidity());
            values.put(Weather.CurrentConditions.iconPath,current.getIconPath());
            values.put(Weather.CurrentConditions.temp,current.getTemp());
            values.put(Weather.CurrentConditions.wind,current.getWind());
            //().insert(WeatherProvider.CURRENT_URL,values);
            getApplication().getContentResolver().insert(WeatherProvider.CURRENT_URL,values);
            ArrayList<ForecastWeather> array = collection.getForecastCondtions();
            for(int i = 1; i < array.size();i++){
                ForecastWeather forecast = array.get(i-1);
                ContentValues cv = new ContentValues();
                cv.put(Weather.ForecastConditions.day,forecast.getDay());
                cv.put(Weather.ForecastConditions.condition,forecast.getCondition());
                cv.put(Weather.ForecastConditions.highTemp,forecast.getHighTemp());
                cv.put(Weather.ForecastConditions.icon,forecast.getIcon());
                cv.put(Weather.ForecastConditions.lowTemp,forecast.getLowTemp());
               // getContentResolver().update(WeatherProvider.FORECAST_URL,cv,Weather.ForecastConditions.id+"="+i,null);

            }
            status = Weather.STATUS_OK;
        }
        if(intent.hasExtra("activity")){
            Intent localintent = new Intent(Weather.ACTIVITY_BROADCAST).putExtra(Weather.EXTENDED_DATA_STATUS,status);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localintent);
        }else{

        }
    }
}
