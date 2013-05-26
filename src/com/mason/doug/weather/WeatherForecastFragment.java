package com.mason.doug.weather;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mason.doug.weather2.R;

/**
 * Created by dougmason on 5/23/13.
 */
public class WeatherForecastFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.weather_forecast,container,false);

        return v;
    }
}
