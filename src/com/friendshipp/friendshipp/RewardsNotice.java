package com.friendshipp.friendshipp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class RewardsNotice extends Activity {

	private FriendShippUser friendShippUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rewards_notice);
		
		friendShippUser = (FriendShippUser) getIntent().getParcelableExtra("EXTRA_FRIENDSHIP_USER");
		setOnClickListeners();
	}

	private void setOnClickListeners() {
		// TODO Auto-generated method stub
		
		Button next_button = (Button) findViewById(R.id.next_button);
		next_button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Log.i(":::::::::::::::::::::::::::::RewardsNotice::setOnClickListeners();", " - Called::::::::::::::::::::::::::");
				Intent intent = new Intent(RewardsNotice.this, BeginActivity.class);
				intent.putExtra("EXTRA_FRIENDSHIP_USER", friendShippUser);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rewards_notice, menu);
		return true;
	}

}
