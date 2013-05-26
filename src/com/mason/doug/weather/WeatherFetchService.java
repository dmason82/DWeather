package com.mason.doug.weather;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by dougmason on 5/25/13.
 */
public class WeatherFetchService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }
}
