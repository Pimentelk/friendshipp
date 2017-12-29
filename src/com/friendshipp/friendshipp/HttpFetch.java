package com.friendshipp.friendshipp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class HttpFetch {
	public static InputStream fetch(String address) throws MalformedURLException,IOException{
		HttpGet httpRequest = new HttpGet(URI.create(address));
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = (HttpResponse)httpclient.execute(httpRequest);
		HttpEntity entity = response.getEntity();
		BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
		InputStream instream = bufHttpEntity.getContent();
		return(instream);
	}
	
	public static Bitmap fetchBitmap(String imageAddress) throws MalformedURLException,IOException{
		return(BitmapFactory.decodeStream(fetch(imageAddress)));
	}
}
