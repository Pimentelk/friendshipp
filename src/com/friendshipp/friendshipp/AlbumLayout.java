package com.friendshipp.friendshipp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class AlbumLayout extends Activity {

	private static final String TAG = "AlbumLayout";
	private AlbumAdapter albumAdapter;
	private FriendShippUser friendShippUser;
	private ListView listView;
	private AlbumLayout albumLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album_layout);		
		Log.i(TAG, "onCreate();");		
		
		friendShippUser = (FriendShippUser) getIntent().getParcelableExtra("EXTRA_FRIENDSHIP_USER");
		albumLayout = this;
		listView = (ListView) findViewById(R.id.album_list);
		View header = (View)getLayoutInflater().inflate(R.layout.facebook_album_header, null);
		listView.addHeaderView(header);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		Log.i(TAG, "onResume();");
		
		if(( this.albumAdapter == null )||( this.albumAdapter.getCount() < 1)){
			//if(friendShippUser == null){
				//friendShippUser = new FriendShippUser();
			//}			
			new GetAlbumsTask().execute((String) friendShippUser.getUserAccessToken());
		}
	}
		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		if(requestCode == 1000){
			if(resultCode == RESULT_OK){
				Bitmap bitmap = (Bitmap) intent.getParcelableExtra("EXTRA_BITMAP_IMAGE");
				String image = intent.getExtras().getString("EXTRA_IMAGE"); 
				String source = intent.getExtras().getString("EXTRA_SOURCE");
				
				/*
				Log.i(TAG,"---------------------------------------------------------------------------------------");
				Log.i(TAG,"bitmap = " + bitmap);
				Log.i(TAG,"image = " + image);
				Log.i(TAG,"source = " + source);
				Log.i(TAG,"---------------------------------------------------------------------------------------");
				*/
				
				//Toast.makeText(this.getApplicationContext(), "Bitmap = " + bitmap, Toast.LENGTH_SHORT).show();
				Intent returnIntent = new Intent();
				returnIntent.putExtra("EXTRA_BITMAP_IMAGE",bitmap);
				returnIntent.putExtra("EXTRA_IMAGE",image);
				returnIntent.putExtra("EXTRA_SOURCE",source);
				setResult(RESULT_OK,returnIntent);
				this.finish();
			}
						
			if(resultCode == RESULT_CANCELED || resultCode != RESULT_OK){
				Toast.makeText(this.getApplicationContext(), "RESULT_CANCELED", Toast.LENGTH_SHORT).show();
				
			}
		}
	}
	
	private class GetAlbumsTask extends AsyncTask<String,String,String>{

		@Override
		protected void onPreExecute(){
			super.onPreExecute();			
			Log.i(TAG, "GetAlbumsTask:onPreExecute()");
		}
				
		@Override
		protected String doInBackground(String... params) {
			Log.i(TAG, "GetAlbumsTask:doInBackground()");
			String accessToken = params[0];			
			
			try{				
				//Invoke the API to get albums
				String imageUrl = getResources().getString(R.string.facebook_url_albums) + "?access_token=" + accessToken;
				String albumsJson = RestInvoke.invoke(imageUrl);
				
				//Parse the album
				ArrayList<AlbumItem> albums = FacebookJSONParser.parseAlbums(albumsJson,accessToken);
				
				albumAdapter = new AlbumAdapter(albumLayout,  albums);
				albumAdapter.setListener(new CustomOnClickEvent(){
					@Override
					public void onClick(String item) {
						//Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
						Log.i(TAG, "Album ID = " + item);
						Intent intent = new Intent(AlbumLayout.this, ImageGrid.class);
						intent.putExtra("EXTRA_ALBUM_ID", item);
						intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
						startActivityForResult(intent,1000);
					}					
				});
				
				return "";
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}			
		}
		
		protected void onProgressUpdate(String... values){
			super.onProgressUpdate(values);	
			Log.i(TAG, "GetAlbumsTask:onProgressUpdate()");			
		}
		
		@Override
		protected void onPostExecute(String result){
			super.onPostExecute(result);
			Log.i(TAG, "GetAlbumsTask:onPostExecute()");
			
			if((albumAdapter == null)||(albumAdapter.isEmpty())){
				//Do stuff.
			} else {				
				listView.setAdapter(albumAdapter);
			}
		}
	}
}

