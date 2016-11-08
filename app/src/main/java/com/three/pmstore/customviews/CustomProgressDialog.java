package com.three.pmstore.customviews;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.three.pmstore.R;
import com.three.pmstore.utility.Utility;


public class CustomProgressDialog {

	private Dialog mDialog;
	private TextView mTxtMessage;
	//private ProgressBar mProgressBar;

	public CustomProgressDialog(Context context) {
		mDialog = new Dialog(context);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater mInflater = LayoutInflater.from(context);
		mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		View layout = mInflater.inflate(R.layout.custom_progress_dialog, null);
		mDialog.setContentView(layout);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setCancelable(false);


		mTxtMessage = (TextView) mDialog.findViewById(R.id.txt_message);
		//mProgressBar = (ProgressBar) mDialog.findViewById(R.id.progress_bar);
	}

	public boolean showProgress(String message) {
		if(Utility.isValueNullOrEmpty(message))	{
			mTxtMessage.setVisibility(View.GONE);
		}
		else {
			mTxtMessage.setText(""+message);
		}


		if(mDialog != null) {
			mDialog.show();
		}
		return false;
	}

	public void dismissProgress() {
		if(mDialog != null || mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}
}
