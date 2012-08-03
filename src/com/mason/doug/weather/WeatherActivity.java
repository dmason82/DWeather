//  Copyright 2012 Doug Mason
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.mason.doug.weather;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.CompoundButton.OnCheckedChangeListener; 

public class WeatherActivity extends Activity implements OnClickListener,OnEditorActionListener,OnCheckedChangeListener {
    /** Called when the activity is first created. */

    private EditText cityText;
    private Button submit;
    private URL url;
    private String city;
    private CheckBox inC;
    private TextView currentTemp,currentWind,currentDay,currentHumidity,currentCondition,currentLabel,todayDay,todayHigh,todayLow,todayCondition,cityLabel,
    tomorrowDay,tomorrowHigh,tomorrowLow,tomorrowCondition,threeDayDay,threeDayHigh,threeDayLow,threeDayCondition,fourDayDay,fourDayHigh,fourDayLow,fourDayCondition;
    private ImageView currentImage,todayImage,tomorrowImage,threeDayImage,fourDayImage;
    private WeatherCollection col;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        submit = (Button)findViewById(R.id.goButton);
        cityText = (EditText)findViewById(R.id.cityText);
        inC = (CheckBox)findViewById(R.id.inCCheck);
        inC.setOnCheckedChangeListener(this);
        cityLabel=(TextView)findViewById(R.id.cityWeatherLabel);
        cityText.setOnEditorActionListener(this);
                
        //Current day
        currentTemp = (TextView)findViewById(R.id.currentDegreeText); 
        currentImage = (ImageView)findViewById(R.id.currentImage);
        currentWind = (TextView) findViewById(R.id.currentWindText);
        currentHumidity=(TextView)findViewById(R.id.currentHumidityText);
        currentCondition=(TextView)findViewById(R.id.currentConditionText);
        currentDay = (TextView)findViewById(R.id.currentDayText);
        
        //Today's forecast
        todayDay = (TextView)findViewById(R.id.todayForecastLabel);
        todayHigh = (TextView)findViewById(R.id.todayHighText); 
        todayLow = (TextView)findViewById(R.id.todayLowText);
        todayCondition = (TextView)findViewById(R.id.todayConditionText);
        todayImage = (ImageView)findViewById(R.id.todayImage);
       
        //Tomorrow's forecast
        tomorrowDay = (TextView)findViewById(R.id.tomorrowForecastLabel);
        tomorrowHigh = (TextView)findViewById(R.id.tomorrowHighText); 
        tomorrowLow = (TextView)findViewById(R.id.tomorrowLowText);
        tomorrowCondition = (TextView)findViewById(R.id.tomorrowConditionText);
        tomorrowImage = (ImageView)findViewById(R.id.tomorrowImage);
        
        //Three days out
        threeDayDay = (TextView)findViewById(R.id.threeDayForecastLabel);
        threeDayHigh = (TextView)findViewById(R.id.threeDayHighText); 
        threeDayLow = (TextView)findViewById(R.id.threeDayLowText);
        threeDayCondition = (TextView)findViewById(R.id.threeDayConditionText);
        threeDayImage = (ImageView)findViewById(R.id.threeDayImage);
        
        //Four Days out
        fourDayDay = (TextView)findViewById(R.id.fourDayForecastLabel);
        fourDayHigh = (TextView)findViewById(R.id.fourDayHighText); 
        fourDayLow = (TextView)findViewById(R.id.fourDayLowText);
        fourDayCondition = (TextView)findViewById(R.id.fourDayConditionText);
        fourDayImage = (ImageView)findViewById(R.id.fourDayImage);     
        
        submit.setOnClickListener(this);
        fetchWeather();
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.goButton:
			this.fetchWeather();
		}
	}
	private void fetchWeather() {
		// TODO Auto-generated method stub
		try
		{
			city = cityText.getText().toString();
			String query = "http://www.google.com/ig/api?weather="+city;
			String q = query.replace(" ", "%20");
//			Log.v("Debug",q);
			url = new URL(q);
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			WeatherParser process = new WeatherParser();
			reader.setContentHandler(process);
			reader.parse(new InputSource(url.openStream()));
			this.col = process.getWeather();
			updateDisplay();
			
			//Current
			
		}
		catch(Exception e)
		{
			Log.e("ERROR!","Weather query error",e);
		}
	}
	
	private void updateDisplay()
	{
		if(!(inC.isChecked())){
		currentTemp.setText(col.getCurrentConditions().getTemp().toString()+"¡F");
		currentDay.setText(col.getCurrentConditions().getDay());
		currentWind.setText(col.getCurrentConditions().getWind());
		currentHumidity.setText(col.getCurrentConditions().getHumidity());
		currentCondition.setText(col.getCurrentConditions().getCondition());
		cityLabel.setText("Weather for "+col.getCurrentConditions().getCity());
		this.setImageForURL(currentImage,"http://www.google.com"+col.getCurrentConditions().getIconPath());
		
		//Today Forecast
		todayDay.setText(col.getForecastCondtions().get(0).getDay());
		todayLow.setText("Low: "+ col.getForecastCondtions().get(0).getLowTemp()+"¡F");
		todayHigh.setText("High: "+Integer.toString(col.getForecastCondtions().get(0).getHighTemp())+"¡F");
		todayCondition.setText(col.getForecastCondtions().get(0).getCondition());
		this.setImageForURL(todayImage,"http://www.google.com"+col.getForecastCondtions().get(0).getIcon());
		
		//Tomorrow Forecast
		tomorrowDay.setText(col.getForecastCondtions().get(1).getDay());
		tomorrowLow.setText("Low: "+ col.getForecastCondtions().get(1).getLowTemp()+"¡F");
		tomorrowHigh.setText("High: "+Integer.toString(col.getForecastCondtions().get(1).getHighTemp())+"¡F");
		tomorrowCondition.setText(col.getForecastCondtions().get(1).getCondition());
		this.setImageForURL(tomorrowImage,"http://www.google.com"+col.getForecastCondtions().get(1).getIcon());
		
		//Three Day Forecast
		threeDayDay.setText(col.getForecastCondtions().get(2).getDay());
		threeDayLow.setText("Low: "+ col.getForecastCondtions().get(2).getLowTemp()+"¡F");
		threeDayHigh.setText("High: "+Integer.toString(col.getForecastCondtions().get(2).getHighTemp())+"¡F");
		threeDayCondition.setText(col.getForecastCondtions().get(2).getCondition());
		this.setImageForURL(threeDayImage,"http://www.google.com"+col.getForecastCondtions().get(2).getIcon());
		
		//Four Day Forecast
		fourDayDay.setText(col.getForecastCondtions().get(3).getDay());
		fourDayLow.setText("Low: "+ col.getForecastCondtions().get(3).getLowTemp()+"¡F");
		fourDayHigh.setText("High: "+Integer.toString(col.getForecastCondtions().get(3).getHighTemp())+"¡F");
		fourDayCondition.setText(col.getForecastCondtions().get(3).getCondition());
		this.setImageForURL(fourDayImage,"http://www.google.com"+col.getForecastCondtions().get(3).getIcon());
		}
		else
		{
			currentTemp.setText(Integer.toString(Utilities.fToC(col.getCurrentConditions().getTemp()))+"¡C");
			currentDay.setText(col.getCurrentConditions().getDay());
			currentWind.setText(col.getCurrentConditions().getWind());
			currentHumidity.setText(col.getCurrentConditions().getHumidity());
			currentCondition.setText(col.getCurrentConditions().getCondition());
			cityLabel.setText("Weather for "+col.getCurrentConditions().getCity());
			this.setImageForURL(currentImage,"http://www.google.com"+col.getCurrentConditions().getIconPath());
			
			//Today Forecast
			todayDay.setText(col.getForecastCondtions().get(0).getDay());
			todayLow.setText("Low: "+ Integer.toString(Utilities.fToC(col.getForecastCondtions().get(0).getLowTemp()))+"¡C");
			todayHigh.setText("High: "+Integer.toString(Utilities.fToC(col.getForecastCondtions().get(0).getHighTemp()))+"¡C");
			todayCondition.setText(col.getForecastCondtions().get(0).getCondition());
			this.setImageForURL(todayImage,"http://www.google.com"+col.getForecastCondtions().get(0).getIcon());
			
			//Tomorrow Forecast
			tomorrowDay.setText(col.getForecastCondtions().get(1).getDay());
			tomorrowLow.setText("Low: "+ Integer.toString(Utilities.fToC(col.getForecastCondtions().get(1).getLowTemp()))+"¡C");
			tomorrowHigh.setText("High: "+Integer.toString(Utilities.fToC(col.getForecastCondtions().get(1).getHighTemp()))+"¡C");
			tomorrowCondition.setText(col.getForecastCondtions().get(1).getCondition());
			this.setImageForURL(tomorrowImage,"http://www.google.com"+col.getForecastCondtions().get(1).getIcon());
			
			//Three Day Forecast
			threeDayDay.setText(col.getForecastCondtions().get(2).getDay());
			threeDayLow.setText("Low: "+ Integer.toString(Utilities.fToC(col.getForecastCondtions().get(2).getLowTemp()))+"¡C");
			threeDayHigh.setText("High: "+Integer.toString(Utilities.fToC(col.getForecastCondtions().get(2).getHighTemp()))+"¡C");
			threeDayCondition.setText(col.getForecastCondtions().get(2).getCondition());
			this.setImageForURL(threeDayImage,"http://www.google.com"+col.getForecastCondtions().get(2).getIcon());
			
			//Four Day Forecast
			fourDayDay.setText(col.getForecastCondtions().get(3).getDay());
			fourDayLow.setText("Low: "+ Integer.toString(Utilities.fToC(col.getForecastCondtions().get(3).getLowTemp()))+"¡C");
			fourDayHigh.setText("High: "+Integer.toString(Utilities.fToC(col.getForecastCondtions().get(3).getLowTemp()))+"¡C");
			fourDayCondition.setText(col.getForecastCondtions().get(3).getCondition());
			this.setImageForURL(fourDayImage,"http://www.google.com"+col.getForecastCondtions().get(3).getIcon());
		}
	}
	private void setImageForURL(ImageView image, String iconPath) {
		// TODO Auto-generated method stub
		try
		{
		URL toConnect = new URL(iconPath);
		URLConnection connect = toConnect.openConnection();
		connect.connect();
		InputStream in = connect.getInputStream();
		BufferedInputStream bufferedStream = new BufferedInputStream(in);
		Bitmap map = BitmapFactory.decodeStream(bufferedStream);
		bufferedStream.close();
		in.close();
		image.setImageBitmap(map);
		}
		catch(Exception e)
		{
			Log.v("Exception!",e.toString());
			image.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
		}
		
	}
	@Override
	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		if (arg2 != null && arg2.getKeyCode() == KeyEvent.KEYCODE_ENTER)
		{
			InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(arg0.getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            this.fetchWeather();
		}
		return false;
	}
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		updateDisplay();
	}
}