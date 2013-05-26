package com.mason.doug.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mason.doug.weather2.R;

/**
 * Created by dougmason on 5/23/13.
 */
public class WeatherInputFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.weather_input,container,false);


        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
