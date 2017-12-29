package com.friendshipp.friendshipp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

	private final static int SCROLL_FRAGMENT_COUNT = 5;	
	
	public PagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);		
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = null;
		
		switch(i){
			case 0: //Profile
				fragment = new Profile();
				break;
			case 1: //Received
				fragment = new Received();
				break;
			case 2: //Friendshipp
				fragment = new FriendShipp();
				break;
			case 3: //Chat
				fragment = new Chat();
				break;
			case 4: //Rewards
				fragment = new Rewards();				
				break;
		}
				
		return fragment;
	}

	@Override
	public int getCount() {
		return SCROLL_FRAGMENT_COUNT;
	}
}
