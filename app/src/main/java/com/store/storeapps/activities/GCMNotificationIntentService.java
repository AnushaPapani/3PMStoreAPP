package com.store.storeapps.activities;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.store.storeapps.R;
import com.store.storeapps.Volley.ApplicationConstants;
import com.store.storeapps.Volley.Utility;
import com.store.storeapps.fragments.MyOrderFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GCMNotificationIntentService extends IntentService {
	// Sets an ID for the notification, so it can be updated
	public static final int notifyID = 9001;
	NotificationCompat.Builder builder;
	Bitmap Images;
	String message;

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

//		try {
//			JSONObject jsonObject = new JSONObject(greetMsg);
//			Images = getBitmapFromURL(jsonObject.getString("image"));
//			message = jsonObject.getString("message");
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}

		NotificationCompat.Builder mNotifyBuilder;
		NotificationManager mNotificationManager;
		int defaults = 0;
		defaults = defaults | Notification.DEFAULT_LIGHTS;
		defaults = defaults | Notification.DEFAULT_VIBRATE;
		defaults = defaults | Notification.DEFAULT_SOUND;

		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		if(greetMsg.contains("product"))
		{
			mNotifyBuilder = new NotificationCompat.Builder(this)
					.setContentTitle("Product Updates from 3PMstore!!!")
					.setContentText(message)
					.setSmallIcon(R.drawable.cart)
					.setStyle(new NotificationCompat.BigTextStyle().bigText(greetMsg))
					.setDefaults(defaults);
			Intent i =new Intent(getApplicationContext(),HomeActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent resultPendingIntent2 = PendingIntent.getActivity(this, 0,
					i, PendingIntent.FLAG_ONE_SHOT);
			mNotifyBuilder.setContentIntent(resultPendingIntent2);
		}
		else {
			mNotifyBuilder = new NotificationCompat.Builder(this)
					.setContentTitle("Updates from 3PMstore!!!")
					.setContentText(greetMsg)
					.setSmallIcon(R.drawable.cart)
					.setStyle(new NotificationCompat.BigTextStyle().bigText(greetMsg))
					.setDefaults(defaults);

			Intent i =new Intent(getApplicationContext(),HomeActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent resultPendingIntent2 = PendingIntent.getActivity(this, 0,
					i, PendingIntent.FLAG_ONE_SHOT);
			mNotifyBuilder.setContentIntent(resultPendingIntent2);
		}

//		if (greetMsg.contentEquals("New Product")){
//			inboxStyle.setBigContentTitle("3PMstore|What's New Today?");
//			Intent i =new Intent(getApplicationContext(),HomeActivity.class);
//			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			PendingIntent resultPendingIntent1 = PendingIntent.getActivity(this, 0,
//					i, PendingIntent.FLAG_ONE_SHOT);
//			mNotifyBuilder.setContentIntent(resultPendingIntent1);
//		}else if (greetMsg.contentEquals("")) {
//			inboxStyle.setSummaryText(greetMsg);
//			Intent i =new Intent(getApplicationContext(),HomeActivity.class);
//			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			PendingIntent resultPendingIntent2 = PendingIntent.getActivity(this, 0,
//					i, PendingIntent.FLAG_ONE_SHOT);
//			mNotifyBuilder.setContentIntent(resultPendingIntent2);
//		}else {
//			inboxStyle.setBigContentTitle("3PMstore|What's New Today?");
//			Intent i =new Intent(getApplicationContext(),HomeActivity.class);
//			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			PendingIntent resultPendingIntent3 = PendingIntent.getActivity(this, 0,
//					i, PendingIntent.FLAG_ONE_SHOT);
//			mNotifyBuilder.setContentIntent(resultPendingIntent3);
//		}
		//			else {
		//				inboxStyle.setBigContentTitle("Updates from 3PMstore!");
		//			}	
		//		for (int i=0; i < events.length; i++) {
		//			inboxStyle.addLine(events[i]);
		//		}
		// Moves the expanded layout object into the notification object.
		//mNotifyBuilder.setStyle(inboxStyle);
		// Issue the notification here.
		mNotifyBuilder.setAutoCancel(true);
		//		mNotifyBuilder.setContentIntent(resultPendingIntent);
		// Post a notification
		mNotificationManager.notify(notifyID, mNotifyBuilder.build());
	}

	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
