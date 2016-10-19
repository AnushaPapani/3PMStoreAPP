package com.three.pmstore.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.three.pmstore.R;
import com.three.pmstore.activities.HomeActivity;


public class NoOrdersFragment extends Fragment {
	public static final String TAG = "NoTestimonialsFragment";
	ImageView backtohome;
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.noorders, container, false);
		initUI();
		return rootView;
	}

	private void initUI() {
		backtohome = (ImageView)rootView.findViewById(R.id.backtoHome);

		backtohome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(), HomeActivity.class);
				startActivity(i);
			}
		});
	}



    
}