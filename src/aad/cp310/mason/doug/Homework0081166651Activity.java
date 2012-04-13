package aad.cp310.mason.doug;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Homework0081166651Activity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */

    private EditText cityText;
    private Button submit;
    private URL url;
    private String city;
    private TextView currentTemp,todayTemp,tomorrowTemp,threeDayTemp,fourDayTemp;
    private ImageView currentImage,todayImage,tomorrowImage,threeDayImage,fourDayImage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        submit = (Button)findViewById(R.id.goButton);
        cityText = (EditText)findViewById(R.id.cityText);
        currentTemp = (TextView)findViewById(R.id.currentDegreeText); 
        currentImage = (ImageView)findViewById(R.id.currentImage);
        todayTemp = (TextView)findViewById(R.id.todayDegreeText); 
        todayImage = (ImageView)findViewById(R.id.todayImage);
        tomorrowTemp = (TextView)findViewById(R.id.tomorrowDegreeText); 
        tomorrowImage = (ImageView)findViewById(R.id.tomorrowImage);
        threeDayTemp = (TextView)findViewById(R.id.threeDayDegreeText); 
        threeDayImage = (ImageView)findViewById(R.id.threeDayImage);
        fourDayTemp = (TextView)findViewById(R.id.fourDayDegreeText); 
        fourDayImage = (ImageView)findViewById(R.id.fourDayImage);
        submit.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.goButton:
			try
			{
				city = cityText.getText().toString();
				String query = "http://www.google.com/ig/api?weather="+city;
				String q = query.replace(" ", "%20");
				Log.v("Debug",q);
				url = new URL(q);
				
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();
				XMLReader reader = parser.getXMLReader();
				WeatherParser process = new WeatherParser();
				reader.setContentHandler(process);
				reader.parse(new InputSource(url.openStream()));
				WeatherCollection col = process.getWeather();
//				Log.v("Test",col.getCurrentConditions().toString());
				
				//Current
				currentTemp.setText(col.getCurrentConditions().getTemp().toString()+"¡F");
				this.setImageForURL(currentImage,"http://www.google.com"+col.getCurrentConditions().getIconPath());
				
				
				//Today Forecast
				todayTemp.setText(Integer.toString(col.getForecastCondtions().get(0).getHighTemp())+"¡F");
				this.setImageForURL(todayImage,"http://www.google.com"+col.getForecastCondtions().get(0).getIcon());
				
				//Tomorrow Forecast
				tomorrowTemp.setText(Integer.toString(col.getForecastCondtions().get(1).getHighTemp())+"¡F");
				this.setImageForURL(tomorrowImage,"http://www.google.com"+col.getForecastCondtions().get(1).getIcon());
				
				//Three Day Forecast
				threeDayTemp.setText(Integer.toString(col.getForecastCondtions().get(2).getHighTemp())+"¡F");
				this.setImageForURL(threeDayImage,"http://www.google.com"+col.getForecastCondtions().get(2).getIcon());
				
				//Four Day Forecast
				fourDayTemp.setText(Integer.toString(col.getForecastCondtions().get(3).getHighTemp())+"¡F");
				this.setImageForURL(fourDayImage,"http://www.google.com"+col.getForecastCondtions().get(3).getIcon());
			}
			catch(Exception e)
			{
				Log.e("ERROR!","Weather query error",e);
			}
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
}