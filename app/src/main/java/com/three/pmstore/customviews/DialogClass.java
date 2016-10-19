package com.three.pmstore.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public abstract class DialogClass implements DialogInterface.OnClickListener {

	static Context context;
	public DialogClass(Context context) {
		super();
		DialogClass.context = context;
	}

	/*
	 * Create Alert Dialog is method with parameters message that message
	 * displays as dialog
	 */
	public static void createDAlertDialog(Context context, final String message) {
		String setmessage = message;
		AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
		alertbox.setTitle("Alert Message");
		alertbox.setMessage(setmessage);
		alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		alertbox.show();
	}
}