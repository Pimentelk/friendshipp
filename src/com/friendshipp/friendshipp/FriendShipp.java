package com.friendshipp.friendshipp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FriendShipp extends Fragment {
private static final String TAG = "Profile";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		Log.i(TAG, "onCreateView();");
		View view = inflater.inflate(R.layout.friendshipp_layout, container, false);			
		return view;
	}
}
