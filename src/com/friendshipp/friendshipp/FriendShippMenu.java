package com.friendshipp.friendshipp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FriendShippMenu extends Fragment{

	private static final String TAG = "FriendShippMenu";
	private ImageView profileView, receivedView, friendshippView, chatView, rewardsView;
	private CustomOnClickEvent fetchListener;
	
	public void setListener(CustomOnClickEvent listener) {
        this.fetchListener = listener;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		Log.i(TAG, "onCreateView();");
		View view = inflater.inflate(R.layout.friendshipp_menu, container, false);			
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		profileView = (ImageView) view.findViewById(R.id.my_profile);
		receivedView = (ImageView) view.findViewById(R.id.friends_received);
		friendshippView = (ImageView) view.findViewById(R.id.friendshipp);
		chatView = (ImageView) view.findViewById(R.id.friend_chat);
		rewardsView = (ImageView) view.findViewById(R.id.rewards);
		
		profileView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), "Profile", Toast.LENGTH_SHORT).show();
				fetchListener.onClick("0");
			}
		});
		
		receivedView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), "Received", Toast.LENGTH_SHORT).show();
				fetchListener.onClick("1");
			}			
		});
		
		friendshippView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), "FriendShipp", Toast.LENGTH_SHORT).show();
				fetchListener.onClick("2");
			}			
		});
		
		chatView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), "Chat", Toast.LENGTH_SHORT).show();
				fetchListener.onClick("3");
			}			
		});
		
		rewardsView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), "Rewards", Toast.LENGTH_SHORT).show();
				fetchListener.onClick("4");
			}			
		});
	}
	
}


