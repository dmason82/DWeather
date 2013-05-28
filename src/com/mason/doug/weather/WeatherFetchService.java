package com.mason.doug.weather;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.content.ContentValues;

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
            getContentResolver().update(WeatherProvider.CURRENT_URL,values,Weather.CurrentConditions.id+"=1",null);
            getContentResolver().notifyAll();
        }
    }
}
