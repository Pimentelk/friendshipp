package com.friendshipp.friendshipp;

import java.util.ArrayList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.friendshipp.slidingmenu.adapter.NavDrawerListAdapter;
import com.friendshipp.slidingmenu.model.NavDrawerItem;

public class Dashboard extends ActionBarActivity {

	private final static String TAG = "FriendShipp";
	private final static int DEFAULT_ITEM = 2;
	
	private FriendShippMenu friendShippMenuFragment;
	private FragmentManager fragmentManager;	
	private FriendShippUser friendShippUser;
	private ViewPager viewPager;
	
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	
	private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate()");
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_dashboard);
		
		/******************** Slide Menu Setup ******************************/
		mTitle = getTitle();
		
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        
        navDrawerItems = new ArrayList<NavDrawerItem>();
        
        //navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], true, "22")); Timer example
        
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
        // Invite
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
        // Push Notifications
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));
        // Rewards
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3]));
        // Privacy
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));
        // How friendshipp Works
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5]));
        // Terms &amp; Policies
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6]));
        // Contact Us
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7]));
        // Logout
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[8]));
        
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        ActionBar action = getSupportActionBar();		
		//action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		//action.setCustomView(R.layout.actionbar_layout);
		action.setDisplayHomeAsUpEnabled(true);
		action.setHomeButtonEnabled(true);
        
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name){
			public void onDrawerClosed(View view){
				getSupportActionBar().setTitle(mTitle);
				//invalidateOptionsMenu();
			}
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		/******************* Slide Menu Setup end *******************************/
				
		fragmentManager = getSupportFragmentManager();
		friendShippUser = (FriendShippUser) getIntent().getParcelableExtra("EXTRA_FRIENDSHIP_USER");
		
		viewPager = (ViewPager) findViewById(R.id.pager);		
		viewPager.setAdapter(new PagerAdapter(fragmentManager));
		viewPager.setCurrentItem(DEFAULT_ITEM);
		
		friendShippMenuFragment = new FriendShippMenu();
		fragmentManager.beginTransaction().replace(R.id.menu_fragment,friendShippMenuFragment).commit();
	}

	
	@Override
    protected void onStart(){
    	super.onStart();
    	Log.i(TAG,"onStart();");
    }
    
    @Override
    protected void onResume(){
    	super.onResume();    	
    	Log.i(TAG,"onResume();");
    	
    	friendShippMenuFragment.setListener(new CustomOnClickEvent(){
			@Override
			public void onClick(String button) {		
				
				int selection = Integer.parseInt(button);
				viewPager.setCurrentItem(selection);
			}			
		});
    }
        
    @Override
    protected void onStop(){
    	super.onStop();
    	Log.i(TAG,"onStop();");
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	Log.i(TAG,"onDestroy();");    	
    }

	public FriendShippUser getFriendShippUser(){
		if(friendShippUser != null){
			return friendShippUser;
		} else {
			return null;
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        Toast.makeText(getApplicationContext(), item.getItemId(), Toast.LENGTH_SHORT).show();
        
        switch (item.getItemId()) {
	        case R.id.action_settings:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
 
 	/*
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
