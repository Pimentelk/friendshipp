package com.friendshipp.friendshipp;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static final String TAG = "DatabaseHelper";
	
	// Database
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "userManager.db";
	
	// Tables
	private static final String TABLE_USER = "user";
	private static final String TABLE_IMAGES = "user_images";
	private static final String TABLE_FRIENDS = "friends";
	private static final String TABLE_SHIPPED = "shipped";
	private static final String TABLE_RECEIVED = "received";
	private static final String TABLE_CHAT = "chat";
	private static final String TABLE_MESSAGE = "message";
	private static final String TABLE_REWARDS = "rewards";
		
	// user Columns
	private static final String KEY_FIRST_NAME = "first_name";
	private static final String KEY_LAST_NAME = "last_name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_SOCIAL_ID = "social_user_id";
	private static final String KEY_ABOUT_ME = "about";
	private static final String KEY_ACCESS_TOKEN = "token";
	private static final String KEY_LOGIN_TIMESTAMP = "last_login_timestamp";
	private static final String KEY_START_TIMESTAMP = "first_login_timestamp";
	private static final String KEY_DEVICE = "device";
	
	// user_images Columns
	private static final String KEY_IMAGE = "image_url";
	private static final String KEY_ORDINAL = "image_ordinal";
	
	// friends Columns
	private static final String KEY_FRIEND_ID = "friend_id";
	private static final String KEY_MATCH_TIMESTAMP = "match_timestamp";
	
	// shipped Columns
	private static final String KEY_SHIPPMENT_ID = "shipped_friend_id";
	private static final String KEY_FROM_USER_ID = "from_user_Id";
	private static final String KEY_TO_USER_ID = "to_user_id";
	private static final String KEY_SHIPMENT_TIMESTAMP = "shipped_timestamp";
	private static final String KEY_SHIPPED_STATUS = "shipped_status";
	
	// received Columns
	private static final String KEY_RECEIVED_SHIPP_ID = "received_shipp_id";
	private static final String KEY_RECEIVED_SHIPP_STATUS = "received_shipp_status";
	private static final String KEY_RECEIVED_SHIPP_TIMESTAMP = "received_shipp_timestamp";
	
	// chat Columns
	private static final String KEY_CHAT_ID = "chat_id";
	private static final String KEY_CHAT_KEY = "chat_key";

	// message Columns
	private static final String KEY_MESSAGE_ID = "messenger_id";
	private static final String KEY_MESSAGE = "message";
	private static final String KEY_MESSAGE_TIMESTAMP = "message_sent_timestamp";
	
	// rewards Columns
	private static final String KEY_REWARDS_ID = "rewards_id";
	private static final String KEY_CREDIT_NAME = "credit_name";
	private static final String KEY_CREDIT_EARNED = "credit_earned";
	private static final String KEY_CREDIT_EARNED_TIMESTAMP = "credit_earned_timestamp";
		
	// Create Table Statements
	private static final String CREATE_USER_TABLE = "CREATE TABLE " 
			+ TABLE_USER + "(" + KEY_SOCIAL_ID + " TEXT," 
			+ KEY_FIRST_NAME + " TEXT," + KEY_LAST_NAME + " TEXT," 
			+ KEY_EMAIL + " TEXT," + KEY_ABOUT_ME + " TEXT," 
			+ KEY_ACCESS_TOKEN + " TEXT" + KEY_LOGIN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP," 
			+ KEY_START_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP," + KEY_DEVICE + " TEXT)";
	
	private static final String CREATE_IMAGES_TABLE = "CREATE TABLE "
			+ TABLE_IMAGES + "(" + KEY_SOCIAL_ID + " INTEGER PRIMARY KEY,"
			+ KEY_IMAGE + " TEXT," + KEY_ORDINAL + " TEXT);"; 
	
	private static final String CREATE_FRIENDS_TABLE = "CREATE TABLE "
			+ TABLE_FRIENDS + "(" + KEY_FRIEND_ID + " INTEGER PRIMARY KEY,"
			+ KEY_MATCH_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
	
	private static final String CREATE_SHIPPED_FRIENDS_TABLE = "CREATE TABLE "
			+ TABLE_SHIPPED + "(" + KEY_SHIPPMENT_ID + " INTEGER PRIMARY KEY,"
			+ KEY_FROM_USER_ID + " INTEGER," + KEY_TO_USER_ID + " INTEGER,"
			+ KEY_SHIPMENT_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP," + KEY_SHIPPED_STATUS + " INTEGER);";
	
	private static final String CREATE_RECEIVED_SHIPPS_TABLE = "CREATE TABLE "
			+ TABLE_RECEIVED + "(" + KEY_RECEIVED_SHIPP_ID + " INTEGER PRIMARY KEY,"
			+ KEY_RECEIVED_SHIPP_STATUS + " INTEGER," + KEY_RECEIVED_SHIPP_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
	
	private static final String CREATE_CHAT_TABLE = "CREATE TABLE "
			+ TABLE_CHAT + "(" + KEY_CHAT_ID + " INTEGER," + KEY_CHAT_KEY + " TEXT);";
	
	private static final String CREATE_MESSAGE_TABLE = "CREATE TABLE "
			+ TABLE_MESSAGE + "(" + KEY_MESSAGE_ID + " INTEGER PRIMARY KEY,"
			+ KEY_MESSAGE + " TEXT," + KEY_FRIEND_ID + " INTEGER,"
			+ KEY_MESSAGE_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
	
	private static final String CREATE_REWARDS_TABLE = "CREATE TABLE "
			+ TABLE_REWARDS + "(" + KEY_REWARDS_ID + " INTEGER PRIMARY KEY,"
			+ KEY_CREDIT_NAME + " TEXT," + KEY_CREDIT_EARNED + " TEXT,"
			+ KEY_CREDIT_EARNED_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
	
	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.i(TAG, "onCreate()");
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "onCreate();");
		
		try {
			db.execSQL(CREATE_USER_TABLE);
			//db.execSQL(CREATE_IMAGES_TABLE);
			//db.execSQL(CREATE_FRIENDS_TABLE);
			//db.execSQL(CREATE_SHIPPED_FRIENDS_TABLE);
			//db.execSQL(CREATE_RECEIVED_SHIPPS_TABLE);
			//db.execSQL(CREATE_CHAT_TABLE);
			//db.execSQL(CREATE_MESSAGE_TABLE);
			//db.execSQL(CREATE_REWARDS_TABLE);
		} finally {
			
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "onUpgrade();");
		
		try {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHIPPED);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEIVED);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_REWARDS);
		} finally {
			if(db != null && db.isOpen()){
				onCreate(db);
			}
		}
	}
	
	@Override
	public void onOpen(SQLiteDatabase db){
		
	}
	
	public void createUser(FriendShippUser user){
		Log.i(TAG, "createUser();");
		
		SQLiteDatabase db = null;		
		
		try {
			db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(KEY_FIRST_NAME, user.getUserFirstName());
			values.put(KEY_LAST_NAME, user.getUserLastName());
			values.put(KEY_EMAIL, user.getUserEmail());
			values.put(KEY_SOCIAL_ID, user.getUserSocialId());		
			values.put(KEY_ACCESS_TOKEN, user.getUserAccessToken());		
			values.put(KEY_DEVICE, user.getUserDevice());
			values.put(KEY_ABOUT_ME, user.getUserAboutMe());
			
			db.insert(TABLE_USER, null, values);
			
		} finally {
			if(db != null && db.isOpen()){
				db.close();
			}
		}
	}
	
	public void updateUserAboutMe(String about, String user_social_id){
		Log.i(TAG, "updateUserAboutMe(" + about + ");");
		String KEY_WHERE = KEY_SOCIAL_ID + "=" + user_social_id; 		
		
		SQLiteDatabase db = null;
		
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();		
			values.put(KEY_ABOUT_ME, about);		
			db.update(TABLE_USER, values, KEY_WHERE, null);
		} finally {
			if(db != null && db.isOpen()){
				db.close();
			}
		}
	}
	
	public void updateUserGallery(ArrayList<PhotoItem> Photos){
		Log.i(TAG, "updateUserGallery();");
		
		SQLiteDatabase db = null;
		
		try {
			db = this.getWritableDatabase();		
			ContentValues values = new ContentValues();		
			for(int i = 0; i < Photos.size(); i++){
				values.put(KEY_IMAGE, Photos.get(i).getSourceUrl());
				values.put(KEY_ORDINAL, Photos.get(i).getPosition());
			}		
			db.insert(TABLE_USER, null, values);
		} finally {
			if(db != null && db.isOpen()){
				db.close();
			}
		}
	}
	
	public boolean isUser(String user_social_id){
		Log.i(TAG, "isUser();");
		int count = 0;
		
		SQLiteDatabase db = null;
		
		try{
			db = this.getWritableDatabase();		
			Cursor cursor = db.query(TABLE_USER, new String[] {KEY_SOCIAL_ID}, KEY_SOCIAL_ID + "=?", new String[] {user_social_id}, null, null, null, null);
			count = cursor.getCount();
		} finally {
			if(db != null && db.isOpen()){
				db.close();
			}
		}		
		
		if(count > 0){
			return true;
		} else {
			return false;
		}
	}
	
	public String[] userSetup(){	
		Log.i(TAG, "getUser();");
		
		String[] columns = new String[]{KEY_EMAIL, KEY_LAST_NAME, KEY_FIRST_NAME, KEY_ABOUT_ME, KEY_DEVICE};
		String[] values = new String[columns.length];
		
		SQLiteDatabase db = null; 
		
		try{
			db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE_USER, columns, null, null, null, null, null, null);
			
			if(cursor != null){
				cursor.moveToFirst();
			}
			
			for(int i = 0; i < columns.length; i++){
				values[i] = cursor.getString(i);
			}
			
		} finally {
			if(db != null && db.isOpen()){
				db.close();
			}
		}
		return values;
	}
}
