package com.friendshipp.friendshipp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Fragment {

	private static final String TAG = "Profile";
	private TextView about_tv, user_tv;
	private FriendShippUser friendShippUser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		Log.i(TAG, "onCreateView();");
		View view = inflater.inflate(R.layout.profile_layout, container, false);		
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Log.i(TAG, "onViewCreated();");
		about_tv = (TextView) view.findViewById(R.id.about_text_view);
		user_tv = (TextView) view.findViewById(R.id.usertag_text_view);
		
		friendShippUser = ((Dashboard) getActivity()).getFriendShippUser();		
		if(friendShippUser != null){
			
			if(friendShippUser.getUserFirstName() != null && friendShippUser.getUserFirstName() != null){
				user_tv.setText(friendShippUser.getUserFirstName() + " " + friendShippUser.getUserLastName());
			}			
			
			if(friendShippUser.getUserAboutMe() != null){
				about_tv.setText(friendShippUser.getUserAboutMe());
			}
			
		} else {
			Toast.makeText(getActivity(), "OH NO! An ERROR WAS DETECTED :(", Toast.LENGTH_SHORT).show();
		}
	}
}
