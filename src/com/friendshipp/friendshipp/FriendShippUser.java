package com.friendshipp.friendshipp;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings.Secure;
import android.util.Log;

public class FriendShippUser implements Parcelable {
	
	private static final String TAG = "FriendShippUser";
	
	private String user_first_name;
	private String user_last_name;
	private String user_email;
	private String user_social_id;
	private String user_access_token;	
	private String user_about_me;
	private String user_device_id;
	private ArrayList<PhotoItem> user_photo_item;
	private DatabaseHelper databaseHelper;
    
	private FriendShippUser(Parcel in) {
    	user_first_name = in.readString();
    	user_last_name = in.readString();
    	user_email = in.readString();
    	user_social_id = in.readString();
    	user_access_token = in.readString();
    	user_about_me = in.readString();
    	user_photo_item = new ArrayList<PhotoItem>();
    	in.readTypedList(user_photo_item, PhotoItem.CREATOR);
    }
	
	public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(user_first_name);
        out.writeString(user_last_name);
        out.writeString(user_email);
        out.writeString(user_social_id);
        out.writeString(user_access_token);
        out.writeString(user_about_me);
        out.writeTypedList(user_photo_item);
    }

    public static final Parcelable.Creator<FriendShippUser> CREATOR = new Parcelable.Creator<FriendShippUser>() {
        public FriendShippUser createFromParcel(Parcel in) {
            return new FriendShippUser(in);
        }

        public FriendShippUser[] newArray(int size) {
            return new FriendShippUser[size];
        }
    };

    public void startDatabaseHelper(Context context){    	
    	if(databaseHelper == null){
    		databaseHelper = new DatabaseHelper(context);
    	}    	
    }

    public void createUser(){
    	Log.i(TAG, "createUser(1);");
    	if(databaseHelper != null){
    		Log.i(TAG, "createUser(2);");
    		databaseHelper.createUser(this);
    	} else {
    		Log.i(TAG, "createUser(3);");
    	}
    }
    
    /*
	 * 
	 * Setters 
	 * 
	 */
    
    public FriendShippUser(){}

	public void setUserAboutMe(String aboutme){
		Log.i(TAG, "setUserAboutMe(1);");
		
    	if(aboutme != null){
    		user_about_me = aboutme;
    		Log.i(TAG, "setUserAboutMe(2);");
    		
    		if(databaseHelper != null){ 
    			Log.i(TAG, "setUserAboutMe(3);");
    			databaseHelper.updateUserAboutMe(user_about_me, user_social_id);
    		} else {
    			Log.i(TAG, "No data inserted into the database.");    			
    		}
    		
    	} else {
    		user_about_me = null;
    	}
    }
    
	public void setUserFirstName(String firstname){
		if(firstname != null){
			user_first_name = firstname;
		} else {
			user_first_name = null;
		}
	}
	
	public void setUserLastName(String lastname){
		if(lastname != null){
			user_last_name = lastname;
		} else {
			user_last_name = null;
		}
	}

	public void setUserEmail(String email){
		if(email != null){
			user_email = email;
		} else {
			user_email = null;
		}
	}

	public void setUserSocialId(String id){
		if(id != null){
			user_social_id = id;
		} else {
			user_social_id = null;
		}
	}
	
	public void setUserAccessToken(String token){
		if(token != null){
			user_access_token = token;
		} else {
			user_access_token = null;
		}
	}
	
	public void setUserImage(int position, String image, String source) {	
		if(user_photo_item == null){
			user_photo_item = new ArrayList<PhotoItem>();			
		}		
		user_photo_item.add(new PhotoItem(position, image, source));
	}
	
	public void setUserDeviceId(Context context){
		if(context != null){
			user_device_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		} else {
			user_device_id = null;
		}
	}
	
	public void setUserDeviceId(String deviceId){
		if(deviceId != null){
			user_device_id = deviceId;
		} else {
			user_device_id = null;
		}
	}

	/*
	 * 
	 * Getters 
	 * 
	 */

	public String getUserAboutMe(){
		if(user_about_me != null){
			return user_about_me;
		} else {
			return null;
		}
	}
	
	public String getUserFirstName(){
		if(user_first_name != null){
			return user_first_name;
		} else {
			return null;
		}
	}

	public String getUserLastName(){
		if(user_last_name != null){
			return user_last_name;
		} else {
			return null;
		}
	}
	
	public String getUserEmail(){
		if(user_email != null){
			return user_email;
		} else {
			return null;
		}
	}
	
	public String getUserSocialId(){
		if(user_social_id != null){
			return user_social_id;
		} else {
			return null;
		}
	}
	
	public String getUserAccessToken(){
		if(user_access_token != null){
			return user_access_token;
		} else {
			return null;
		}
	}
	
	public String getUserDevice(){
		if(user_device_id != null){
			return user_device_id;
		} else {
			return null;
		}		
	}
	
	public String getUserImage(int position){		
		return user_photo_item.get(position).getPictureUrl();
	}
	
	public String getUserSource(int position){		
		return user_photo_item.get(position).getSourceUrl();
	}

	public boolean isUser(String user_id) {
		if(databaseHelper != null){
			return databaseHelper.isUser(user_id);
		} else {
			return false;
		}
	}
	
	public void dataSyncHandler(){
		Log.i(TAG, "dataSyncHandler();");
		this.user_first_name = null;
		this.user_last_name = null;
		this.user_email = null;	
		this.user_about_me = null;
		this.user_device_id = null;
		
		String[] values = new String[4];
		values = databaseHelper.userSetup(); //columns = {KEY_EMAIL, KEY_LAST_NAME, KEY_FIRST_NAME, KEY_ABOUT_ME, KEY_DEVICE};
		
		setUserEmail(values[0]);
		setUserLastName(values[1]);
		setUserFirstName(values[2]);
		setUserAboutMe(values[3]);
		setUserDeviceId(values[4]);
	}
}
