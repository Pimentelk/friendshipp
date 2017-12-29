package com.friendshipp.friendshipp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class LoadImage {
	
	private static final String TAG = "LoadImage";
	private String imageUrl;
	private Bitmap bitmap;
	
	public LoadImage(String imageUrl) {
		this.imageUrl = imageUrl;
		//new DownloadImageTask().execute();
	}

	public void setImageUrl(String imageUrl){
		if(imageUrl != null){
			this.imageUrl = imageUrl;
		} else {
			this.imageUrl = null;
		}
	}
	
	public String getImageUrl(){
		if(this.imageUrl != null){
			return imageUrl;
		} else {
			return null;
		}
	}
	
	public Bitmap getBitmap(){
		if(this.bitmap != null){
			return bitmap;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	private class DownloadImageTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute(){
			super.onPreExecute();			
		}
		
		@Override
		protected String doInBackground(String... params){
	
			Log.i(TAG, "doInBackground()");
			URL url = null;
			bitmap = null;
			
			try {
				url = new URL(imageUrl);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			try {
				bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values){
			super.onProgressUpdate(values);			
		}
		
		@Override
		protected void onPostExecute(String result){
			Log.i(TAG, "onProgressUpdate()");
			super.onPostExecute(result);
		}
	}
}