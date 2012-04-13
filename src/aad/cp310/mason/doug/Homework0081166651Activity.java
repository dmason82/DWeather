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
    private TextView todayTemp;
    private ImageView todayImage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        submit = (Button)findViewById(R.id.goButton);
        cityText = (EditText)findViewById(R.id.cityText);
        todayTemp = (TextView)findViewById(R.id.todayDegreeText);
        
        todayImage = (ImageView)findViewById(R.id.todayImage);
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
//				todayTemp.setText(col.getCurrentConditions().getTemp().toString()+"¡F");
//				this.setImageForURL(todayImage,col.getCurrentConditions().getIconPath());
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
			image.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
		}
		
	}
}