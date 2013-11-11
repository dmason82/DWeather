package com.mason.doug.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by dougmason on 11/10/13.
 */

public class WeatherBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context c, Intent i){
        Log.v("Intent received", i.toString());
        if (i.hasExtra(Weather.EXTENDED_DATA_STATUS)){
            Log.v("Weather status",Weather.EXTENDED_DATA_STATUS);
            String status = i.getStringExtra(Weather.EXTENDED_DATA_STATUS);
            if (status.equals(Weather.STATUS_OK)){
                c.getContentResolver().notifyAll();

            }

        }
    }


}