package com.friendshipp.friendshipp;


import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class AlbumSelectedListener implements OnItemSelectedListener{
	public void onItemSelected(AdapterView<?> parent,View view,int pos,long id){
		//selectedAlbum = (AlbumItem) spinnerSelectAlbum.getItemAtPosition(pos);
	    updateAlbum();
	}

	public void onNothingSelected(AdapterView<?> parent){
		//Do something
	}
	
	private void updateAlbum(){
	  //this.gallerySelectedAlbum.setEnabled(false);
	  //this.textViewAlbumDescription.setText("Loading "+this.selectedAlbum.getName()+"...");
	  //new UpdateAlbumGalleryTask().execute("");
	}
}