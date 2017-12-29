package com.friendshipp.friendshipp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class BeginActivity extends Activity {

	private FriendShippUser friendShippUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_begin);
		
		friendShippUser = (FriendShippUser) getIntent().getParcelableExtra("EXTRA_FRIENDSHIP_USER");
		setOnClickListeners();
	}

	private void setOnClickListeners() {
		Button begin_button = (Button) findViewById(R.id.begin_button);
		begin_button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(BeginActivity.this, Dashboard.class);			            	
		    	intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
				startActivity(intent);
				finish();
			}
		});
		
		Button back_button = (Button) findViewById(R.id.back_button);
		back_button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(BeginActivity.this, RewardsNotice.class);			            	
		    	intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.begin, menu);
		return true;
	}

}
