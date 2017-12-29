package com.friendshipp.friendshipp;

import java.util.EventListener;

import android.graphics.Bitmap;

public interface CustomSelectedEvent extends EventListener {
	void onSelected(Bitmap bitmap, String image, String source);
}
