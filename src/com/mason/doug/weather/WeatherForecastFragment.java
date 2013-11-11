package com.mason.doug.weather;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dougmason on 5/23/13.
 */
public class WeatherForecastFragment extends ListFragment {

    private class ForecastConditionsAdapter extends ArrayAdapter<ForecastWeather> {

        public ForecastConditionsAdapter(Context context, int resource,
                                         int textViewResourceId, List<ForecastWeather> objects) {
            super(context, resource, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ForecastWeather forecast = this.getItem(position);
            if(row == null){
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row = inflater.inflate(R.layout.forecast_conditions, parent, false);
                row.setTag("Forecast "+Integer.toString(position));
            }
            TextView forecastDay = (TextView)row.findViewById(R.id.forecastLabel);
            TextView forecastHigh = (TextView)row.findViewById(R.id.forecastHighText);
            TextView forecastLow = (TextView)row.findViewById(R.id.forecastLowText);
            TextView forecastCondtion = (TextView)row.findViewById(R.id.forecastConditionText);
            ImageView forecastImage = (ImageView)row.findViewById(R.id.forecastImage);

            forecastDay.setText(forecast.getDay());
            /*if(!inC.isChecked()){
                forecastLow.setText("Low: "+ String.format("%.2f",forecast.getLowTemp())+"�F");
                forecastHigh.setText("High: "+String.format("%.2f",forecast.getHighTemp())+"�F");
            }*/
            //Temporary until I figure out how to get Celsius temperature properly notified throughout the activity.
            if(false){

            }
            else{
                float lowTemp = Utilities.fToC(forecast.getLowTemp());
                float highTemp = Utilities.fToC(forecast.getHighTemp());
                forecastLow.setText("Low: "+ String.format("%.2f",lowTemp)+"�C");
                forecastHigh.setText("High: "+String.format("%.2f",highTemp)+"�C");
            }
            forecastCondtion.setText(forecast.getCondition());
            new BitmapWorkerTask(forecastImage).execute(forecast.getIcon());
            return row;

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.weather_forecast,container,false);

        return v;
    }
}
