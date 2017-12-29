package com.friendshipp.friendshipp;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoItem implements Parcelable {
	
	private int position;
	private String pictureUrl;
	private String sourceUrl;
	
	public PhotoItem(int position, String pictureUrl, String sourceUrl) {
		this.position = position;
		this.pictureUrl = pictureUrl;
		this.sourceUrl = sourceUrl;
	}

	public void setPosition(int position){
		this.position = position;
	}
	
	public int getPosition(){
		return position;
	}
	
	public String getPictureUrl(){
		if(this.pictureUrl != null){
			return pictureUrl;
		} else {
			return null;
		}		
	}
	
	public void setPictureUrl(String pictureUrl){
		CharSequence target = "\\/";
		CharSequence replace = "/";
		
		String fixedUrl = pictureUrl.replace(target, replace);
		this.pictureUrl = fixedUrl;
	}
	
	public String getSourceUrl(){
		if(this.sourceUrl != null){
			return sourceUrl;
		} else {
			return null;
		}	
	}
	
	public void setSourceUrl(String sourceUrl){
		CharSequence target = "\\/";
		CharSequence replace = "/";
		
		String fixedUrl = sourceUrl.replace(target, replace);
		this.sourceUrl = fixedUrl;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(position);
		out.writeString(pictureUrl);
		out.writeString(sourceUrl);
	}
	
	public PhotoItem(Parcel in) {
		position = in.readInt();
		pictureUrl = in.readString();
		sourceUrl = in.readString();
	}
	
	public static final Parcelable.Creator<PhotoItem> CREATOR = new Parcelable.Creator<PhotoItem>() {
		public PhotoItem createFromParcel(Parcel in) {
			return new PhotoItem(in);
		}

		public PhotoItem[] newArray(int size) {
			return new PhotoItem[size];
		}
	};
}
