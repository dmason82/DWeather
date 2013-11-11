package com.mason.doug.weather;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


/**
 * Created by dougmason on 5/23/13.
 */
public class WeatherForecastFragment extends ListFragment {
    SimpleCursorAdapter cursorAdapter;
    private class ForecastViewBinder implements SimpleCursorAdapter.ViewBinder{

        /*
        		public static final String[] PROJECTION = new String[]{
			Weather.ForecastConditions.id,      0
			Weather.ForecastConditions.day,     1
			Weather.ForecastConditions.icon,    2
			Weather.ForecastConditions.condition,3
			Weather.ForecastConditions.lowTemp,4
			Weather.ForecastConditions.highTemp5
		};
         */
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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.weather_forecast,container,false);
        cursorAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),R.layout.forecast_conditions,getActivity().getContentResolver().query(Weather.FORECAST_URI,Weather.ForecastConditions.PROJECTION,null,null,null), Weather.ForecastConditions.PROJECTION,new int[]{android.R.layout.list_content}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        cursorAdapter.setViewBinder(new ForecastViewBinder());
        setListAdapter(cursorAdapter);
        return v;
    }
}
