package com.friendshipp.friendshipp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

public class Splash extends Activity {

	private final static int SPLASH_TIME_OUT = 5000;
	private final static String TAG = "Splash";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Activity is being created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);        
        Log.i(TAG,"onCreate();");  
    }

    @Override
    protected void onStart(){
    	// Activity is about to become visible
    	super.onStart();
    	Log.i(TAG,"onStart();");
    }
    
    @Override
    protected void onResume(){
    	// Another activity is taking focus, this activity is about to be paused.
    	super.onResume();
    	Log.i(TAG,"onResume();");    	
    	runSplash();
    }
    
    @Override
    protected void onStop(){
    	// Activity is no longer visible.
    	super.onStop();
    	Log.i(TAG,"onStop();");    	
    }
    
    @Override
    protected void onDestroy(){
    	// Activity is about to be destroyed.
    	super.onDestroy();
    	Log.i(TAG,"onDestroy();");    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }    
    
    private void runSplash(){
    	new Handler().postDelayed(new Runnable(){        	
        	@Override
        	public void run(){        		
        		Log.i(TAG, "Splash Timer Complete");
        		Intent intent = new Intent(Splash.this, FacebookLogin.class);
        		startActivity(intent);
        		finish();
        	}
        }, SPLASH_TIME_OUT);
    }
}
