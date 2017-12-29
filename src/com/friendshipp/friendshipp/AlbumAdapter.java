package com.friendshipp.friendshipp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumAdapter extends BaseAdapter {

	private final static String TAG = "AlbumAdapter";
	protected static final MotionEvent MotionEvent = null;
	private Context context;
	private ArrayList<AlbumItem> albumItems;
	private CustomOnClickEvent fetchListener = null;
	
	public AlbumAdapter(Context context, ArrayList<AlbumItem> albumItems /*,int resourceId, Album[] data*/ ){
		this.context = context;
		this.albumItems = albumItems;
		//this.resourceId = resourceId;
		//this.data = data;		
	}
	
	public void setListener(CustomOnClickEvent listener) {
        this.fetchListener = listener;
    }
		
	static class AlbumHolder {
		ImageView imageIcon;
		TextView txtTitle;
	}
	
	@Override  
	public int getCount() {  
		return albumItems.size();  
	 }  
	
	@Override
	public AlbumItem getItem(int position){
		return albumItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {		
		View view = convertView;
		
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.facebook_album_item,null);
			
			AlbumHolder holder = new AlbumHolder();
			holder.imageIcon = (ImageView)view.findViewById(R.id.imgIcon);
			holder.txtTitle = (TextView)view.findViewById(R.id.txtTitle);
			
			view.setTag(holder);
		}
		
		AlbumHolder holder = (AlbumHolder)view.getTag();
		holder.txtTitle.setText(albumItems.get(position).getAlbumName());
		holder.imageIcon.setTag(albumItems.get(position).getAlbumUrl());
		holder.imageIcon.setImageDrawable(null);
		
		view.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				//Toast.makeText(context.getApplicationContext(), albumItems.get(position).getAlbumId(), Toast.LENGTH_SHORT).show();
				fetchListener.onClick(albumItems.get(position).getAlbumId());
			}
		});
		
		new DownloadImageTask().execute(holder);
		return view;
	}
	
	private class DownloadImageTask extends AsyncTask<AlbumHolder, Void, Boolean> {
		
		private Bitmap bitmap = null;
		private ImageView avatarImage;
		private String url;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();			
		}
		
		@Override
		protected Boolean doInBackground(AlbumHolder... params){
	
			Log.i(TAG, "doInBackground()");
			AlbumHolder item = (AlbumHolder) params[0];
			boolean result = false;
			
			if(item != null){
				avatarImage = item.imageIcon;
				url = item.imageIcon.getTag().toString();
				
				Log.i(TAG, "URL: " + url);
				bitmap = ImageCache.get(url);
				
				if( bitmap == null){
					try {
						bitmap = HttpFetch.fetchBitmap(url);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					ImageCache.put(url,bitmap);
				}

				result = true;
			}			
			return result;
		}
		
		@Override
		protected void onPostExecute(Boolean result){
			Log.i(TAG, "onProgressUpdate()");
			super.onPostExecute(result);
			
			if(result && (bitmap != null)){
				if(avatarImage.getTag().toString().equals(url)){
					avatarImage.setImageBitmap(bitmap);
				}
			}
		}
	}
}






