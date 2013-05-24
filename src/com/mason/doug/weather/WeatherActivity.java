package com.mason.doug.weather;

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
    private SimpleCursorAdapter adapter;
    private static final String CURRENT_TAG = "current";
    private static final String FORECAST_TAG = "forecast";
    private static final String INPUT_TAG = "input";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dweather);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}