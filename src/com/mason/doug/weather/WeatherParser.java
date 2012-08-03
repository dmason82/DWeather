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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
public class WeatherParser extends DefaultHandler {
	private WeatherCollection myWeather = null;
	private boolean inForecastInfo = false;
	private boolean inCurrent = false;
	private boolean inForecastCond = false;
	private boolean inDegC = false;
	public WeatherCollection getWeather()
	{
		return this.myWeather;
	}
	
	public void startDocument() throws SAXException
	{
		Log.v("Notify","Document started");
		this.myWeather = new WeatherCollection();
	}
	public void endDocument() throws SAXException
	{
		//there is nothing to be done here
	}
	@Override
	public void startElement(String uri,String localName,String qName,Attributes att) throws SAXException
	{
//		Log.v("Starting parsing on",localName);
		//Top level responses
		if(localName.equals("forecast_information"))
		{
			this.setInForecastInfo(true);
			this.myWeather.setCurrentCondtions(new WeatherCurrentCondition());
		}
		else if (localName.equals("current_conditions"))
		{
//			Log.v("notify","Starting Current Conditions");
			this.inCurrent = true;
		}
		else if (localName.equals("forecast_conditions"))
		{
//			Log.v("Level",localName);
			this.myWeather.getForecastCondtions().add(new ForecastWeather());
			this.inForecastCond = true;
		}
		else
		{
			String data = att.getValue("data");
			//Log.v("top level",data);
			if (localName.equals("city"))
			{
				Log.v("City",data);
				this.myWeather.getCurrentConditions().setCity(data);
			}
			else if(localName.equals("postal_code"))
			{
			}
			else if(localName.equals("latitude_e6"))
			{
				
			}
			else if(localName.equals("longitude_e6"))
			{
				
			}
			else if(localName.equals("forecast_date"))
			{
				//this.myWeather.getLastForecastCondition().setDay(data);
				this.myWeather.getCurrentConditions().setDayOfWeek(data);
			}
			else if(localName.equals("current_date_time"))
			{
				//Log.v("Data check",data);
				//
			}
			else if(localName.equals("unit_system"))
			{
				if(att.equals("SI"))
				{
					this.inDegC = true;
				}
			}
			else if(localName.equals("day_of_week"))
			{
				//Shared data types between forecast and current conditions
				if(this.inCurrent)
				{
					Log.v("Notification",data);
					this.myWeather.getCurrentConditions().setDayOfWeek(data);
				}
				else if(this.inForecastCond)
				{
					this.myWeather.getLastForecastCondition().setDayOfWeek(data);	
				}
			}
				else if(localName.equals("icon"))
				{
					if(this.inCurrent)
					{
						this.myWeather.getCurrentConditions().setIconPath(data);
					}
					else if(this.inForecastCond)
					{
						this.myWeather.getLastForecastCondition().setIconPath(data);
					}
				}
					else if(localName.equals("condition"))
					{
						if(this.inCurrent)
						{
							this.myWeather.getCurrentConditions().setCondition(data);
						}
						else if (inForecastCond){
							this.myWeather.getLastForecastCondition().setCondition(data);
						}
					}
					else if(localName.equals("temp_f"))
					{
						Log.v("Current temp",data);
						myWeather.getCurrentConditions().setTemp(Integer.parseInt(data));
					}
					else if(localName.equals("temp_c"))
					{
						if(this.inDegC){
						myWeather.getCurrentConditions().setTemp(Utilities.cToF(Integer.parseInt(data)));
						}
					}
					else if (localName.equals("humidity")) 
					{
                        this.myWeather.getCurrentConditions().setHumidity(data);
                    }
					else if (localName.equals("wind_condition")) 
					{
                        this.myWeather.getCurrentConditions()
                                        .setWind(data);
                    }
                // 'Inner' Tags within forecast conditions
					
                else if (localName.equals("low")) 
                {
                        int temp = Integer.parseInt(data);
                        if (this.inDegC) {
                                this.myWeather.getLastForecastCondition()
                                                .setTempLow(Utilities.fToC(temp));
                        } 
                        else {
                                this.myWeather.getLastForecastCondition()
                                                .setTempLow(temp);
                        }
                } 
                else if (localName.equals("high")) 
                {
                        int temp = Integer.parseInt(data);
                        if (this.inDegC) 
                        {
                                this.myWeather.getLastForecastCondition().setTempHigh(Utilities.cToF(temp));
                        } 
                        
                        else 
                        {
                        	this.myWeather.getLastForecastCondition().setTempHigh(temp);
                        }
                 }
		}
	}
//	public void startElement(String )
//	{
//		
//	}
	public void endElement(String uri,String localName,String qName) throws SAXException
	{
		if (localName.equals("forecast_information"))
		{
			this.inForecastInfo = false;
		}
		else if(localName.equals("current_conditions"))
		{
			this.inCurrent = false;
		}
		else if(localName.equals("forecast_conditions"))
		{
			this.inForecastCond = false;
		}
	}
	public boolean isInForecastInfo() {
		return inForecastInfo;
	}

	public void setInForecastInfo(boolean inForecastInfo) {
		this.inForecastInfo = inForecastInfo;
	}

}
