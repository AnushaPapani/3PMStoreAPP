package com.store.storeapps.activities;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.store.storeapps.R;
import com.store.storeapps.Volley.ApplicationConstants;


public class GCMNotificationIntentService extends IntentService {
	// Sets an ID for the notification, so it can be updated
	public static final int notifyID = 9001;
	NotificationCompat.Builder builder;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {

				sendNotification(""	+ extras.get(ApplicationConstants.MSG_KEY)); //When Message is received normally from GCM Cloud Server
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}


	private void sendNotification(String greetMsg) {
		//	        Intent resultIntent = new Intent(this, GreetingActivity.class);
		//	        resultIntent.putExtra("greetjson", greetMsg);
		//	        resultIntent.setAction(Intent.ACTION_MAIN);
		//	        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);


		Log.d("GCM", greetMsg);

		NotificationCompat.Builder mNotifyBuilder;
		NotificationManager mNotificationManager;

		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mNotifyBuilder = new NotificationCompat.Builder(this)
		.setContentTitle(greetMsg)
		.setContentText(""+greetMsg)
		.setSmallIcon(R.drawable.cart);
		
		// Set pending intent


		// Set Vibrate, Sound and Light	        
		int defaults = 0;
		defaults = defaults | Notification.DEFAULT_LIGHTS;
		defaults = defaults | Notification.DEFAULT_VIBRATE;
		defaults = defaults | Notification.DEFAULT_SOUND;

		mNotifyBuilder.setDefaults(defaults);
		// Set the content for Notification 
		mNotifyBuilder.setContentText(greetMsg);

		NotificationCompat.InboxStyle inboxStyle =
				new NotificationCompat.InboxStyle();
		String[] events = new String[6];
		// Sets a title for the Inbox in expanded layout
		//		Intent resultIntent = new Intent(this, GreetingActivity.class);
		//        resultIntent.putExtra("greetjson", greetMsg);
		//        resultIntent.setAction(Intent.ACTION_MAIN);
		//        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		//        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
		//                resultIntent, PendingIntent.FLAG_ONE_SHOT);

		if (greetMsg.contentEquals("New Product")){
			inboxStyle.setBigContentTitle("3PMstore|What's New Today?");
			Intent i =new Intent(getApplicationContext(),HomeActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent resultPendingIntent1 = PendingIntent.getActivity(this, 0,
					i, PendingIntent.FLAG_ONE_SHOT);
			mNotifyBuilder.setContentIntent(resultPendingIntent1);
			//			startActivity(i);
		}else if (greetMsg.contentEquals("Hi")) {
			inboxStyle.setSummaryText(greetMsg);
			Intent i =new Intent(getApplicationContext(),HomeActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent resultPendingIntent2 = PendingIntent.getActivity(this, 0,
					i, PendingIntent.FLAG_ONE_SHOT);
			mNotifyBuilder.setContentIntent(resultPendingIntent2);
		}else {
			inboxStyle.setBigContentTitle("3PMstore|What's New Today?");
			Intent i =new Intent(getApplicationContext(),HomeActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent resultPendingIntent3 = PendingIntent.getActivity(this, 0,
					i, PendingIntent.FLAG_ONE_SHOT);
			mNotifyBuilder.setContentIntent(resultPendingIntent3);
		}
		//			else {
		//				inboxStyle.setBigContentTitle("Updates from 3PMstore!");
		//			}	
		//		for (int i=0; i < events.length; i++) {
		//			inboxStyle.addLine(events[i]);
		//		}
		// Moves the expanded layout object into the notification object.
		mNotifyBuilder.setStyle(inboxStyle);
		// Issue the notification here.
		mNotifyBuilder.setAutoCancel(true);
		//		mNotifyBuilder.setContentIntent(resultPendingIntent);
		// Post a notification
		mNotificationManager.notify(notifyID, mNotifyBuilder.build());
	}



}
