package com.friendshipp.friendshipp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public abstract class FacebookJSONParser {

	private static final String TAG = "FacebookJSONParser";

	public static ArrayList<AlbumItem> parseAlbums(String json,String access_token) throws JSONException{
		Log.i(TAG, "FacebookJSONParser:parseAlbums();");
	    ArrayList<AlbumItem> albums = new ArrayList<AlbumItem>();
	    JSONObject rootObj = new JSONObject(json);
	    JSONArray itemList = rootObj.getJSONArray("data");
	    
	    for(int i = 0; i < itemList.length(); i++){
	    	JSONObject album = itemList.getJSONObject(i);
	    	String description = "";
	    	String image_url = "https://graph.facebook.com/";
	    	
	    	if(album.has("cover_photo")) {
	    		image_url += album.getString("cover_photo") 
	    				  + "/picture?type=thumbnail&access_token=" 
	    				  + access_token;
	    	} else {
	    		image_url += album.getString("id") 
	    				  + "/picture?type=thumbnail&access_token=" 
	    				  + access_token;
	    	}
	    	
	    	try {
	    		description = album.getString("description");
	    	} catch(JSONException x){
	    		/*not implemented*/
	    	}

	    	albums.add(new AlbumItem(album.getString("id"),album.getString("name"),description,image_url));
	    }
	    
	    return albums;
	}
	
	public static ArrayList<PhotoItem> parsePhotos(String json) throws JSONException{
		Log.i(TAG, "FacebookJSONParser:parsePhotos();");
	    ArrayList<PhotoItem> photos = new ArrayList<PhotoItem>();
	    //Log.i(TAG, "json = " + json);
	    
    	JSONObject rootObj = new JSONObject(json);
	    JSONArray data = rootObj.getJSONArray("data");
	    
	    for(int i = 0; i < data.length(); i++){
	    	JSONObject photo = data.getJSONObject(i);
	    	photos.add(new PhotoItem(i,photo.getString("picture"),photo.getString("source")));
	    }

	    return(photos);
	}
}
