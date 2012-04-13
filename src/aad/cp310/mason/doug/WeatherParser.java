package aad.cp310.mason.doug;
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
		}
		else if (localName.equals("current_conditions"))
		{
//			Log.v("notify","Starting Current Conditions");
			this.myWeather.setCurrentCondtions(new WeatherCurrentCondition());
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
			}
			else if(localName.equals("postal_code"))
			{
			}
			else if(localName.equals("postal code"))
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
				
			}
			else if(localName.equals("current_date_time"))
			{
				
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
						myWeather.getCurrentConditions().setTemp(Integer.parseInt(data));
					}
					else if(localName.equals("temp_c"))
					{
						myWeather.getCurrentConditions().setTemp(Utilities.cToF(Integer.parseInt(data)));
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
