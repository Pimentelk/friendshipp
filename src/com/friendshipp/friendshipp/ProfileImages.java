package com.friendshipp.friendshipp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class ProfileImages extends Activity {

	private static final String TAG = "ProfileImages";
	private FriendShippUser friendShippUser;
	private ImageView imageView_01, imageView_02, imageView_03, imageView_04, imageView_05;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_images);
		
		imageView_01 = (ImageView) findViewById(R.id.image_view_01);
		imageView_02 = (ImageView) findViewById(R.id.image_view_02);
		imageView_03 = (ImageView) findViewById(R.id.image_view_03);
		imageView_04 = (ImageView) findViewById(R.id.image_view_04);
		imageView_05 = (ImageView) findViewById(R.id.image_view_05);
		
		friendShippUser = (FriendShippUser) getIntent().getParcelableExtra("EXTRA_FRIENDSHIP_USER");		
		setOnClickListeners();
		Log.i(TAG, "onCreate()");	
	}
	
	@Override
    protected void onStart(){
    	//Activity is about to become visible
    	super.onStart();
    	Log.i(TAG,"onStart();");
    }
    
    @Override
    protected void onResume(){
    	//Another activity is taking focus, this activity is about to be paused.
    	super.onResume();
    	Log.i(TAG,"onResume();");
    }
    
    @Override
    protected void onStop(){
    	//Activity is no longer visible.
    	super.onStop();
    	Log.i(TAG,"onStop();");    	
    }
    
    @Override
    protected void onDestroy(){
    	//Activity is about to be destroyed.
    	super.onDestroy();
    	Log.i(TAG,"onDestroy();");    	
    }
	
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
    	
    	if(resultCode == RESULT_OK){
    		
    		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("EXTRA_BITMAP_IMAGE");
    		String image = intent.getExtras().getString("EXTRA_IMAGE");
    		String source = intent.getExtras().getString("EXTRA_SOURCE");
    		friendShippUser.setUserImage(requestCode, image, source);
    		
    		/*
    		Log.i(TAG,"---------------------------------------------------------------------------------------");
    		Log.i(TAG,"Bitmap = " + bitmap);
    		Log.i(TAG,"image = " + image);
    		Log.i(TAG,"source = " + source);
    		Log.i(TAG,"---------------------------------------------------------------------------------------");
    		*/
    		
	    	switch(requestCode){    	
		    	case 1:
		    		imageView_01.setImageBitmap(bitmap);
					break;
				case 2:
					imageView_02.setImageBitmap(bitmap);
					break;
				case 3:
					imageView_03.setImageBitmap(bitmap);
					break;
				case 4:
					imageView_04.setImageBitmap(bitmap);
					break;
				case 5:
					imageView_05.setImageBitmap(bitmap);					
					break;
	    	}
    	}
    }
    
	private void setOnClickListeners() {
		ImageView imageView_1 = (ImageView) findViewById(R.id.image_view_01);
		imageView_1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(ProfileImages.this, AlbumLayout.class);			            	
		    	intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
		    	startActivityForResult(intent,1);
			}
		});
		
		ImageView imageView_2 = (ImageView) findViewById(R.id.image_view_02);
		imageView_2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(ProfileImages.this, AlbumLayout.class);			            	
		    	intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
		    	startActivityForResult(intent,2);
			}
		});
		
		ImageView imageView_3 = (ImageView) findViewById(R.id.image_view_03);
		imageView_3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(ProfileImages.this, AlbumLayout.class);			            	
		    	intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
		    	startActivityForResult(intent,3);
			}
		});
		
		ImageView imageView_4 = (ImageView) findViewById(R.id.image_view_04);
		imageView_4.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(ProfileImages.this, AlbumLayout.class);			            	
		    	intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
		    	startActivityForResult(intent,4);
			}
		});
		
		ImageView imageView_5 = (ImageView) findViewById(R.id.image_view_05);
		imageView_5.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(ProfileImages.this, AlbumLayout.class);			            	
		    	intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
		    	startActivityForResult(intent,5);
			}
		});
		
		Button next_button = (Button) findViewById(R.id.next_button);
		next_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProfileImages.this, ProfileAbout.class);			            	
		    	intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_images, menu);
		return true;
	}
}
