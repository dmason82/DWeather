package com.mason.doug.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mason.doug.weather2.R;
import android.widget.ImageView;
/**
 * Created by dougmason on 5/23/13.
 */
public class WeatherCurrentFragment extends Fragment {
    TextView currentTemp,currentWind,currentDay,currentHumidity,currentCondition;
    ImageView im;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.current_conditions,container,false);
        currentTemp = (TextView)v.findViewById(R.id.currentDegreeText);
        currentWind = (TextView)v.findViewById(R.id.currentWindText);
        currentDay = (TextView)v.findViewById(R.id.currentDayText);
        currentHumidity = (TextView)v.findViewById(R.id.currentHumidityText);
        currentCondition = (TextView)v.findViewById(R.id.currentConditionText);
        im =(ImageView)v.findViewById(R.id.imageView1);
        return v;
    }


}
