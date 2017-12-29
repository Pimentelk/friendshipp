package com.friendshipp.friendshipp;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class OnPhotoClickedListener implements OnItemClickListener{
	
	@Override
	public void onItemClick(AdapterView<?> parent,View view,int position,long id){
		//selectedImage = (PhotoItem)gallerySelectedAlbum.getItemAtPosition(position);
		showImageDialog();
	}
	
	private void showImageDialog(){
	  try{
		  //create the dialog
		  //LayoutInflater inflater=(LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		  //View layout=inflater.inflate(R.layout.viewimagelayout,(ViewGroup)findViewById(R.id.ViewImageDialogRoot));
		  //AlertDialog.Builder builder=new AlertDialog.Builder(this);
		  //builder.setView(layout);
		  //builder.setTitle("View Image");
		  //builder.setPositiveButton("Close",null);
		  //AlertDialog imageDialog=builder.create();
		  
		  //show the dialog
		  //imageDialog.show();
		  //this.imageViewSelectedImage=(ImageView)imageDialog.findViewById(R.id.ImageViewSourceImage);
		  
		  //load the image
		  //new DownloadImageTask().execute("");
		  
	  	}catch(Exception x){
		  //Log.e(TAG,"showImageDialog",x);
	  	}
	}
}
