package com.mason.doug.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CheckBox;
/**
 * Created by dougmason on 5/23/13.
 */
public class WeatherInputFragment extends Fragment implements OnClickListener {
    private EditText cityText;
    private Button goButton;
    private TextView weatherCityLabel;
    private CheckBox currentLocationCheckbox;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.weather_input,container,false);
        cityText =(EditText)v.findViewById(R.id.cityText);
        goButton = (Button)v.findViewById(R.id.goButton);
        goButton.setOnClickListener(this);
        weatherCityLabel = (TextView)v.findViewById(R.id.cityWeatherLabel);
        currentLocationCheckbox = (CheckBox)v.findViewById(R.id.locationCheckBox);

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.goButton:
                Intent service = new Intent(getActivity(),WeatherFetchService.class);
                service.putExtra("city",cityText.getText().toString());
                service.putExtra("activity",getActivity().toString());
                getActivity().startService(service);
        }
    }
    public void updateCity(String city){
        this.weatherCityLabel.setText("Weather for "+city);
    }
}
