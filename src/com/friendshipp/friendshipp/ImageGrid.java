package com.friendshipp.friendshipp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

public class ImageGrid extends Activity {

	private static String TAG = "ImageGrid";
	private String EXTRA_ALBUM_ID;
	//private ImageView selection;
	//private String[] images;	
	private GridView grid;
	private FriendShippUser friendShippUser;
	private CustomGridAdapter gridAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_grid);
		
		//selection = (ImageView) findViewById(R.id.selection);
		grid = (GridView) findViewById(R.id.grid);
		
		EXTRA_ALBUM_ID = getIntent().getExtras().getString("EXTRA_ALBUM_ID");
		friendShippUser = (FriendShippUser) getIntent().getParcelableExtra("EXTRA_FRIENDSHIP_USER");
		
		Log.i(TAG, "EXTRA_ALBUM_ID = " + EXTRA_ALBUM_ID);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		Log.i(TAG, "onResume();");
		
		new GetAlbumPhotos().execute();
	}
	
	private void returnIntentResult(Bitmap bitmap, String image, String source){	
		/*
		Log.i(TAG,"---------------------------------------------------------------------------------------");
		Log.i(TAG,"bitmap = " + bitmap);
		Log.i(TAG,"image = " + image);
		Log.i(TAG,"source = " + source);
		Log.i(TAG,"---------------------------------------------------------------------------------------");
		*/
		Intent returnIntent = new Intent();
		returnIntent.putExtra("EXTRA_BITMAP_IMAGE",bitmap);
		returnIntent.putExtra("EXTRA_IMAGE",image);
		returnIntent.putExtra("EXTRA_SOURCE",source);
		setResult(RESULT_OK,returnIntent);
		this.finish();
	}

	private class GetAlbumPhotos extends AsyncTask<String,String,Bitmap>{
		public Bitmap doInBackground(String... params){
			Log.i(TAG, "GetAlbumPhotos:doInBackground();");
			//Invoke the API to get albums
			String facebookUrlPhotos = (getResources().getString(R.string.facebook_url_photos)).replace("[ALBUMID]",EXTRA_ALBUM_ID) + "&access_token=" + friendShippUser.getUserAccessToken();
			Log.i(TAG, "facebookUrlPhotos = " + facebookUrlPhotos);
			
			try {
				//Get JSON
				String jsonPhotos = RestInvoke.invoke(facebookUrlPhotos);
				
				//Parse the photos
				ArrayList<PhotoItem> photos = FacebookJSONParser.parsePhotos(jsonPhotos);
				
				gridAdapter = new CustomGridAdapter(ImageGrid.this,photos);
				gridAdapter.setListener(new CustomSelectedEvent(){					
					@Override
					public void onSelected(Bitmap bitmap, String image, String source) {
						//Toast.makeText(getBaseContext(), "Bitmap Image = " + bitmap, Toast.LENGTH_SHORT).show();		
						returnIntentResult(bitmap, image, source);
					}
				});
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
		 
		public void onPostExecute(Bitmap result){		
			Log.i(TAG, "GetAlbumPhotos:onPostExecute();");
			if((gridAdapter == null)||(gridAdapter.isEmpty())){
				//Do stuff.				
			} else {
				grid.setAdapter(gridAdapter);
			}
	    }
	}
}
