package com.friendshipp.friendshipp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileAbout extends Activity {

	private static final String TAG = "ProfileAbout";
	
	private FriendShippUser friendShippUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_about);
		
		friendShippUser = (FriendShippUser) getIntent().getParcelableExtra("EXTRA_FRIENDSHIP_USER");		
		setOnClickListeners();
	}

	private void setOnClickListeners() {
		
		Button next_button = (Button) findViewById(R.id.next_button);
		next_button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){				
				EditText editText = (EditText) findViewById(R.id.about_me);
				Log.i(TAG, "Setting User About Me.");
				friendShippUser.startDatabaseHelper(getApplicationContext());
				friendShippUser.setUserAboutMe(editText.getText().toString());
				Intent intent = new Intent(ProfileAbout.this, RewardsNotice.class);
				intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
				startActivity(intent);
				finish();
			}
		});
		
		Button back_button = (Button) findViewById(R.id.back_button);
		back_button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(ProfileAbout.this, ProfileImages.class);	
				intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_about, menu);
		return true;
	}

}
