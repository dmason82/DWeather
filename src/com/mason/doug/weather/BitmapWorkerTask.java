package com.mason.doug.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dougmason on 5/25/13.
 */
class BitmapWorkerTask extends AsyncTask<String,Void,Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private String data = "";
    public BitmapWorkerTask(ImageView image){
        imageViewReference = new WeakReference<ImageView>(image);
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        // TODO Auto-generated method stub
        data = params[0];
        URL toConnect;
        try {
            toConnect = new URL(data);
            Log.v("URL",data);
            URLConnection connect = toConnect.openConnection();
            connect.connect();
            InputStream content = (InputStream)toConnect.getContent();
            Bitmap map = BitmapFactory.decodeStream(content);
            return map;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(Bitmap bitmap){
        if(imageViewReference !=null && bitmap !=null){
            final ImageView imageView = imageViewReference.get();
            if(imageView !=null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
