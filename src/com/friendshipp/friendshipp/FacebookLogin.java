package com.friendshipp.friendshipp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

public class FacebookLogin extends FragmentActivity {

	private final static String TAG = "FacebookLogin";
	private LoginFragment loginFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i(TAG, "onCreate();");
    	
		if(savedInstanceState == null){
			loginFragment = new LoginFragment();
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, loginFragment).commit();
		} else {
			loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
		}
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.facebook_login, menu);		
		Log.i(TAG,"onCreateOptionsMenu();");		
		return true;
	}

}
