package com.friendshipp.friendshipp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.friendshipp.library.chat.ChatClient;

public class Chat extends Fragment {
	private static final String TAG = "Profile";
	private ChatClient chatClient;
	private ListView listview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		Log.i(TAG, "onCreateView();");
		
		View view = inflater.inflate(R.layout.chat_layout, container, false);
		
		final EditText recipient = (EditText) view.findViewById(R.id.recipient);		
		final EditText textMessage = (EditText) view.findViewById(R.id.text_message);
		listview = (ListView) view.findViewById(R.id.list_messages);
		
		Button send = (Button) view.findViewById(R.id.send_message);
		send.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String to = recipient.getText().toString();
				String text = textMessage.getText().toString();
				
				if(chatClient != null){
					chatClient.sendMessage(to, text);
				}
			}			
		});	
		return view;
	}
	
	@Override
	public void onResume(){
		super.onResume();		
		chatClient = new ChatClient("kevin", "728YLMKD42", getActivity().getApplicationContext(), listview);
		if(chatClient != null){
			chatClient.connect();
		}					
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();		
		if(chatClient != null){
    		chatClient.close();
    	}
	}
}
