package com.mason.doug.weather;

import android.content.IntentFilter;
import android.support.v4.content.CursorLoader;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * Created by dougmason on 5/23/13.
 */
public class WeatherActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private WeatherBroadcastReceiver receiver;
    private static final String CURRENT_TAG = "current";
    private WeatherCurrentFragment currentFragment;
    private WeatherForecastFragment forecastFragment;
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
        forecastFragment=(WeatherForecastFragment)getSupportFragmentManager().findFragmentById(R.id.weatherForecastFragment);
        getSupportLoaderManager().initLoader(CURRENT_NUM,savedInstanceState,this);
        getSupportLoaderManager().initLoader(FORECAST_NUM,savedInstanceState,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader loader = null;
        switch (i){
            case CURRENT_NUM:
                loader = new CursorLoader(getApplicationContext(),WeatherProvider.CURRENT_URL,Weather.CurrentConditions.PROJECTION,null,null,null);

                break;
            case FORECAST_NUM:
                loader = new CursorLoader(getApplicationContext(),WeatherProvider.FORECAST_URL,Weather.ForecastConditions.PROJECTION,null,null,null);
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
                Log.v("Cursor check",Integer.toString(cursor.getCount()));
                cursor.setNotificationUri(getContentResolver(), Weather.FORECAST_URI);
                SimpleCursorAdapter cursorAdapter = new android.widget.SimpleCursorAdapter(this,R.layout.forecast_conditions,cursor, Weather.ForecastConditions.PROJECTION,new int[]{android.R.layout.list_content}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                cursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int i) {
                        TextView forecastDay = (TextView)view.findViewById(R.id.forecastLabel);
                        TextView forecastHigh = (TextView)view.findViewById(R.id.forecastHighText);
                        TextView forecastLow = (TextView)view.findViewById(R.id.forecastLowText);
                        TextView forecastCondtion = (TextView)view.findViewById(R.id.forecastConditionText);
                        ImageView forecastImage = (ImageView)view.findViewById(R.id.forecastImage);
                        forecastDay.setText(cursor.getString(1));
                        forecastLow.setText("Low: "+ String.format("%.2f",cursor.getFloat(4)+"�F"));
                        forecastHigh.setText("High: "+String.format("%.2f",cursor.getFloat(5)+"�F"));
                        forecastCondtion.setText(cursor.getString(3));
                        new BitmapWorkerTask(forecastImage).execute(cursor.getString(2));
                        return true;
                    }
                });

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