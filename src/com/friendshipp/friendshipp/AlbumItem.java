package com.friendshipp.friendshipp;

public class AlbumItem {

	private String id;
	private String name;
	private String description;
	private String url;
	
	public AlbumItem(String id, String name, String description, String url){
		this.id = id;
		this.name = name;
		this.description = description;
		this.url = url;
	}
	
	public String getAlbumId(){
		if(this.id != null){
			return this.id;
		} else {
			return null;
		}
	}
	
	public String getAlbumName(){
		if(this.name != null){
			return this.name;
		} else {
			return null;
		}
	}
	
	public String getAlbumDescription(){
		if(this.description != null){
			return this.description;
		} else {
			return null;
		}
	}
	
	public String getAlbumUrl(){
		if(this.url != null){
			return this.url;
		} else {
			return null;
		}
	}

	public void setAlbumId(String id){
		if(id != null){
			this.id = id;
		} else {
			this.id = null;
		}
	}
	
	public void setAlbumName(String name){
		if(name != null){
			this.name = name;
		} else {
			this.name = null;
		}
	}
	
	public void setAlbumDescription(String description){
		if(description != null){
			this.description = description;
		} else {
			this.description = null;
		}
	}
	
	public void setAlbumUrl(String url){
		if(url != null){
			this.url = url;
		} else {
			this.url = null;
		}
	}
}
