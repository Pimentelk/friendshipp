package com.friendshipp.friendshipp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridAdapter extends BaseAdapter {
    private Activity context;
    public ArrayList<PhotoItem> photos;
    private static String TAG = "CustomGridAdapter";
    private ImageView imageView;
	private CustomSelectedEvent fetchListener;
    
    public CustomGridAdapter(ImageGrid imageGrid, ArrayList<PhotoItem> photos) {
    	Log.i(TAG, "CustomGridAdapter();");
        this.context = imageGrid;
        this.photos = photos;
    }
    
    static class PhotoHolder {
		ImageView imageIcon;
		TextView txtView;
	}
    
    public void setListener(CustomSelectedEvent listener) {
        this.fetchListener = listener;
    }
    
    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position).getPictureUrl();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	//Log.i(TAG, "CustomGridAdapter:getView();");        	
    	View view = convertView;
    	
    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.facebook_photo_cell,null);
		
		String imageUrl = photos.get(position).getPictureUrl();
		String imageSource = photos.get(position).getSourceUrl();
		
		imageView = (ImageView) view.findViewById(R.id.imgv);
		imageView.setTag(imageUrl);
		
		new DownloadGrid(imageUrl, imageSource).execute(imageView);
		
        return view;
    }
    
	private class DownloadGrid extends AsyncTask<ImageView, Void, String> {
		
		private Bitmap bitmap = null;
		private String url;
		private String source;
		private ImageView image;
		
		public DownloadGrid(String url, String source){
			this.url = url;
			this.source = source;
		}
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(ImageView... data){
			//Log.i(TAG, "DownloadGrid:doInBackground()");
			
			this.image = data[0];
			//Log.i(TAG, "image = " + image);
			
			String result = null;
			//Log.i(TAG, "result = " + result);
			
			if(image != null){
				
				url = (String) image.getTag();
				//Log.i(TAG, "URL: " + url);
				
				bitmap = ImageCache.get(url);
				//Log.i(TAG, "bitmap: " + bitmap);
				
				if( bitmap == null){
					try {
						bitmap = HttpFetch.fetchBitmap(url);
						//Log.i(TAG, "bitmap: " + bitmap);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					ImageCache.put(url,bitmap);
				}				
			}			
			return result;
		}
		
		@Override
		protected void onPostExecute(String result){
			//Log.i(TAG, "DownloadGrid:onPostExecute()");
			super.onPostExecute(result);			
			
			if(image.getTag().toString().equals(url)){
				image.setScaleType(ImageView.ScaleType.CENTER_CROP);
				image.setLayoutParams(new GridView.LayoutParams(70, 70));
				image.setImageBitmap(bitmap);				
				image.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						//Toast.makeText(context, "Bitmap Image = " + bitmap, Toast.LENGTH_SHORT).show();
						fetchListener.onSelected(bitmap, url, source);
						/*
						Log.i(TAG,"---------------------------------------------------------------------------------------");
						Log.i(TAG,"bitmap = " + bitmap);
						Log.i(TAG,"url = " + url);
						Log.i(TAG,"source = " + source);
						Log.i(TAG,"---------------------------------------------------------------------------------------");
						*/
					}	
				});
			}
		}
	}
}
