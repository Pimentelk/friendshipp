package com.friendshipp.friendshipp;


import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class LoginFragment extends Fragment {

	private final static String TAG = "LoginFragment";
	private boolean ACTIVITY_CALLED = false;
	private UiLifecycleHelper uiHelper;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);	    
	    uiHelper = new UiLifecycleHelper(getActivity(), statusCallback);
	    uiHelper.onCreate(savedInstanceState);	    
	    Log.i(TAG, "onCreate();");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		
		// For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }

	    Log.i(TAG, " - onResume();");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {		
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	    
	    Log.i(TAG, " - onActivityResult();");
	}

	@Override
	public void onPause() {		
	    super.onPause();
	    uiHelper.onPause();
	    
	    Log.i(TAG, " - onPause();");
	}

	@Override
	public void onDestroy() {		
	    super.onDestroy();
	    uiHelper.onDestroy();
	    
	    Log.i(TAG, " - onDestroy();");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	    
	    Log.i(TAG, " - onSaveInstanceState();");
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.activity_facebook_login, container, false);
		
		LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    authButton.setFragment(this);
	    authButton.setReadPermissions(Arrays.asList("basic_info","email","friends_hometown","user_photos","friends_photos"));
	    	    
	    Log.i(TAG, " - onSaveInstanceState();");	    
		return view;
	}
	
	private void onSessionStateChange(final Session session, SessionState state, Exception exception){
		Log.i(TAG, " - onSessionStateChange();");
		if(state.isOpened()){
			Log.i(TAG, " - onSessionStateChange():Logged In");
			
			// Request user data and show the results
		    Request.newMeRequest(session, new Request.GraphUserCallback() {

		        @Override
		        public void onCompleted(GraphUser user, Response response) {
		            if (user != null && !ACTIVITY_CALLED) {		            	
		            	Intent intent = null;
		            	FriendShippUser friendshippUser = new FriendShippUser();
		            	Context context = getActivity().getApplicationContext();
		            	String user_social_id = user.getProperty("id").toString();
		            	
		            	friendshippUser.startDatabaseHelper(context);
		            	boolean returning_user = friendshippUser.isUser(user_social_id);
		            	
		            	if(!returning_user){
		            		friendshippUser.setUserFirstName(user.getProperty("first_name").toString());
		            		friendshippUser.setUserLastName(user.getProperty("last_name").toString());
		            		friendshippUser.setUserEmail(user.getProperty("email").toString());
		            		friendshippUser.setUserSocialId(user_social_id);
		            		friendshippUser.setUserAccessToken(session.getAccessToken());
		            		friendshippUser.setUserDeviceId(context);
		            		friendshippUser.createUser();
					        intent = new Intent(getActivity(), ProfileImages.class);
		            	} else {
		            		friendshippUser.dataSyncHandler();
		            		intent = new Intent(getActivity(), Dashboard.class);
		            	}
		            	
		            	ACTIVITY_CALLED = true; // Prevent if from being called twice.
		            	
		            	if(intent != null){
			            	intent.putExtra("EXTRA_FRIENDSHIP_USER", friendshippUser);
			            	startActivity(intent);
		            	}
		            }
		        }
		    }).executeAsync();
		} else {
			Log.i(TAG, " - onSessionStateChange():Logged Out");
		}
	}
	
	private Session.StatusCallback statusCallback = new Session.StatusCallback(){
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	    	Log.i(TAG, " - StatusCallback():Call();");
	        onSessionStateChange(session, state, exception);
	    }
	};
}
