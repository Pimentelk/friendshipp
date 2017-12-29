package com.friendshipp.friendshipp;

import java.util.HashMap;

import android.graphics.Bitmap;

public abstract class ImageCache {

	private static HashMap<String,Bitmap> hashMap;
	
	public static synchronized Bitmap get(String imageUrl){
		if(hashMap == null){
			hashMap = new HashMap<String,Bitmap>();
		}			
		return (hashMap.get(imageUrl));
	}
	
	public static synchronized void put(String imageUrl,Bitmap bitmap){
		if(hashMap == null){
			hashMap = new HashMap<String,Bitmap>();
		}
		hashMap.put(imageUrl, bitmap);
	}
}
