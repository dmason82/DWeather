package com.mason.doug.weather;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.content.Loader;
import android.widget.TextView;
import com.mason.doug.weather2.R;
import android.widget.ImageView;



/**
 * Created by dougmason on 5/23/13.
 */
public class WeatherCurrentFragment extends Fragment {
    TextView currentTemp,currentWind,currentDay,currentHumidity,currentCondition;
    ImageView im;
    private Cursor c;
    
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


    private class CurrentConditionsAdapter extends SimpleCursorAdapter {

        public CurrentConditionsAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);
            if(view == null){
                LayoutInflater li = getActivity().getLayoutInflater();
               view = li.inflate(R.layout.current_conditions,null);
            }
            currentCondition.setText(cursor.getString(4));
            currentDay.setText(cursor.getString(1));
            currentWind.setText(cursor.getString(5));
            currentHumidity.setText(cursor.getString(7));
            currentTemp.setText(Float.toString(cursor.getFloat(6)));
            new BitmapWorkerTask(im).execute(cursor.getString(3));

        }
    }
}