package com.friendshipp.library.chat;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import com.friendshipp.friendshipp.R;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChatClient {

	private final static String TAG = "ChatClient";
	private final static String XMPP_CHAT_HOST = "friendshipp.me";
	private final static int XMPP_CHAT_PORT = 5222;
	private final static String XMPP_CHAT_SERVICE = "friendshipp.me";	
	
	private String USERNAME;
	private String PASSWORD;	
	private Context context;
	private ListView messageList; 
	private XMPPConnection connection;	
	private Handler messageHandler = new Handler();
	private ArrayList<String> messages = new ArrayList<String>();
	
	public ChatClient(String USERNAME, String PASSWORD, Context context, ListView messageList){
		if(USERNAME != null){
			this.USERNAME = USERNAME;
		}		
		if(PASSWORD != null){
			this.PASSWORD = PASSWORD;
		}		
		if(context != null){
			this.context = context;
			SmackAndroid.init(this.context);
		}		
		if(messageList != null){
			this.messageList = messageList;
		}
	}
	
	private void setConnection(XMPPConnection connection){
		this.connection = connection;
		
		if(connection != null){
			// Add a packet listener to get messages sent to us.
			PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
			connection.addPacketListener(new PacketListener(){

				@Override
				public void processPacket(Packet packet) {
					Message message = (Message) packet;
					
					if(message.getBody() != null){
						String fromName = StringUtils.parseBareAddress(message.getFrom());
						Log.i(TAG,"[SettingsDialog] Text received " + message.getBody() + " from " + fromName);
						
						messages.add(fromName + ":");
						messages.add(message.getBody());
						
						// Add the incoming message to the list view
						messageHandler.post(new Runnable() {
							@Override
							public void run() {
								setListAdapter();
							}							
						});
					}
				}				
			}, filter);
		}
	}
	
	public void connect(){
		Log.i(TAG,"[SettingsDialog] connect();");
		//final ProgressDialog dialog = ProgressDialog.show(context, "Connecting...", "Please wait...", false);
		
		Log.i(TAG,"[SettingsDialog] Runnable()");
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				
				// Create a connection
				ConnectionConfiguration config = new ConnectionConfiguration(XMPP_CHAT_HOST, XMPP_CHAT_PORT, XMPP_CHAT_SERVICE);
				connection = new XMPPConnection(config);
				config.setSASLAuthenticationEnabled(true);
				
				// Set Android TrustStore
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
					config.setTruststoreType("AndroidCAStore");
					config.setTruststorePassword(null);
					config.setTruststorePath(null);
				} else {
					config.setTruststoreType("BKS");						
					String path = System.getProperty("javax.net.ssl.trustStore");
					
					if(path == null){
						path = System.getProperty("java.home") + File.separator + "etc"
								+ File.separator + "security" + File.separator
								+ "cacerts.bks";
					}					
					config.setTruststorePath(path);
				}
				
				try { 
					// Connect to host
					connection.connect();
					Log.i(TAG,"[SettingsDialog] Connected to " + connection.getHost());
				} catch(XMPPException e){
					Log.i(TAG,"[SettingsDialog] Failed to connect to " + connection.getHost());
					Log.i(TAG, e.toString());
					setConnection(null);
				}
				
				try{ 
					// Login as user
					connection.login(USERNAME, PASSWORD);
					Log.i(TAG,"[SettingsDialog] Logged in as  " + connection.getUser());
					
					// Set the status to available
					Presence presence = new Presence(Presence.Type.available);
					connection.sendPacket(presence);
					setConnection(connection);
					
					Roster roster = connection.getRoster();
					Collection<RosterEntry> entries = roster.getEntries();
					
					for(RosterEntry entry : entries){
						Log.i(TAG, "Entry: " + entry);
						Log.i(TAG, "Entry User: " + entry.getUser());
						Log.i(TAG, "Entry Name: " + entry.getName());
						Log.i(TAG, "Entry Status: " + entry.getStatus());
						Log.i(TAG, "Entry Type: " + entry.getType());
						
						Presence entryPresence = roster.getPresence(entry.getUser());						
						Presence.Type userType = entryPresence.getType();
						
						if(userType == Presence.Type.available){
							Log.i(TAG, "Presence AVAILABLE");
						}
						
						Log.i(TAG, "Presence: " + entryPresence);
					}				
					
					roster.addRosterListener(new RosterListener(){
						@Override
						public void entriesAdded(Collection<String> arg0) {
							Log.i(TAG, "entriesAdded();");
						}

						@Override
						public void entriesDeleted(Collection<String> arg0) {
							Log.i(TAG, "entriesDeleted();");
						}

						@Override
						public void entriesUpdated(Collection<String> arg0) {
							Log.i(TAG, "entriesUpdated();");
						}

						@Override
						public void presenceChanged(Presence arg0) {
							Log.i(TAG, "presenceChanged();");
						}						
					});					
				} catch(XMPPException e){
					Log.i(TAG,"[SettingsDialog] Failed to login as  " + USERNAME);
					Log.i(TAG, e.toString());
					setConnection(null);					
				}
			}			
		});
		
		t.start();
		//dialog.show();
	}
	
	private void setListAdapter(){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.listitem, messages);
		messageList.setAdapter(adapter);
	}
	
	public void sendMessage(String to, String text){
		Message msg = new Message(to, Message.Type.chat);
		msg.setBody(text);
		
		if(connection != null){
			Log.i(TAG,"[SettingsDialog] Text sent " + msg.getBody() + " to " + to);
			connection.sendPacket(msg);
			messages.add(connection.getUser() + ":");
			messages.add(text);
			setListAdapter();			
		}
	}
	
	public void close(){
		if(connection != null){
			try{
				connection.disconnect();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public boolean isConnected(){
		if(connection != null){
			return connection.isConnected();
		} else {
			return false;
		}
	}
	
	public String getHost(){
		if(connection != null){
			return connection.getHost();
		} else {
			return null;
		}
	}
	
	public String getUser(){
		if(connection != null){
			return connection.getUser();
		} else {
			return null;
		}
	}
}
