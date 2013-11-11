package com.mason.doug.weather;

import android.content.IntentFilter;
import android.support.v4.content.CursorLoader;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;


/**
 * Created by dougmason on 5/23/13.
 */
public class WeatherActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private WeatherBroadcastReceiver receiver;
    private static final String CURRENT_TAG = "current";
    private WeatherCurrentFragment currentFragment;
    private WeatherInputFragment inputFragment;
    SimpleCursorAdapter mForecastAdapter;
    private static final int CURRENT_NUM = 1;
    private static final int FORECAST_NUM = 2;
    private static final String FORECAST_TAG = "forecast";
    private static final String INPUT_TAG = "input";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dweather);
//        IntentFilter statusFilter = new IntentFilter(Weather.ACTIVITY_BROADCAST);
//        receiver = new WeatherBroadcastReceiver();
//        registerReceiver(receiver,statusFilter);
        currentFragment = (WeatherCurrentFragment)getSupportFragmentManager().findFragmentById(R.id.weatherCurrentFragment);
        inputFragment = (WeatherInputFragment)getSupportFragmentManager().findFragmentById(R.id.weatherInputFragment);
        getSupportLoaderManager().initLoader(CURRENT_NUM,savedInstanceState,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader loader = null;
        switch (i){
            case CURRENT_NUM:
                loader = new CursorLoader(getApplicationContext(),WeatherProvider.CURRENT_URL,Weather.CurrentConditions.PROJECTION,null,null,null);

                break;
            case FORECAST_NUM:
                loader = new CursorLoader(getApplicationContext(),WeatherProvider.FORECAST_URL,Weather.CurrentConditions.PROJECTION,null,null,null);
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        switch (cursorLoader.getId()){
            case CURRENT_NUM:

                currentFragment.updateCurrent(cursor);
                inputFragment.updateCity(cursor.getString(2));
                cursor.setNotificationUri(getContentResolver(),Weather.CURRENT_URI);
                break;
            case FORECAST_NUM:
                mForecastAdapter.swapCursor(cursor);
                cursor.setNotificationUri(getContentResolver(),Weather.FORECAST_URI);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
    @Override
    public void onPause(){
        super.onPause();
       // unregisterReceiver(receiver);
    }
}