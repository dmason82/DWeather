package com.mason.doug.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.CursorLoader;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

import com.mason.doug.weather2.R;

/**
 * Created by dougmason on 5/23/13.
 */
public class WeatherActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private WeatherBroadcastReceiver receiver;
    private SimpleCursorAdapter adapter;
    private static final String CURRENT_TAG = "current";
    SimpleCursorAdapter mCurrentAdapter;
    SimpleCursorAdapter mForecastAdapter;
    private static final int CURRENT_NUM = 1;
    private static final int FORECAST_NUM = 2;
    private static final String FORECAST_TAG = "forecast";
    private static final String INPUT_TAG = "input";


    public class WeatherBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context c, Intent i){
            if (i.hasExtra(Weather.EXTENDED_DATA_STATUS)){
                if (i.getStringExtra(Weather.EXTENDED_DATA_STATUS).equalsIgnoreCase(Weather.STATUS_OK)){
                    getContentResolver().notifyAll();
                }

            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dweather);
        IntentFilter statusFilter = new IntentFilter(Weather.ACTIVITY_BROADCAST);
        statusFilter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new WeatherBroadcastReceiver();
        registerReceiver(receiver,statusFilter);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader loader = null;
        switch (i){
            case CURRENT_NUM:

                loader = new CursorLoader(getBaseContext(),WeatherProvider.CURRENT_URL,Weather.CurrentConditions.PROJECTION,null,null,null);

                break;
            case FORECAST_NUM:
                loader = new CursorLoader(getBaseContext(),WeatherProvider.FORECAST_URL,Weather.CurrentConditions.PROJECTION,null,null,null);
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        switch (cursorLoader.getId()){
            case CURRENT_NUM:

                mCurrentAdapter.swapCursor(cursor);
                break;
            case FORECAST_NUM:
                mForecastAdapter.swapCursor(cursor);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
    @Override
    public void onPause(){
        unregisterReceiver(receiver);
    }
}