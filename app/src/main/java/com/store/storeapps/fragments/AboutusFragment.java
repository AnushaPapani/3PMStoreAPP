package com.store.storeapps.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.store.storeapps.R;
import com.store.storeapps.utility.AppController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by home on 10/10/2016.
 */

public class AboutusFragment extends Fragment{
    public static final String TAG = "AboutusFragment";
    TextView pmrp,pcost,shortdesc,textCounter,head,thour,tvHour,tminutes,tvMinute,tvSecond,s,info,descrip;
    private View rootView;
    private CountDownTimer countDownTimer; // built in android class
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds;
    private static final String FORMAT = "%02d:%02d:%02d";
    // start time of start blinking
    private boolean blink; // controls the blinking .. on and off
    String Aboutus="About Us";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initUI();
        return rootView;
    }
    private void initUI() {

        TextView aboutustext =(TextView)rootView.findViewById(R.id.aboutustext);
        SpannableString content = new SpannableString(Aboutus);
        content.setSpan(new UnderlineSpan(), 0, Aboutus.length(), 0);
        aboutustext.setText(content);
        head=(TextView)rootView.findViewById(R.id.heading);
        //		lgender =(LinearLayout)findViewById(R.id.radio);
        head.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"LED.ttf"));
        head.setTextSize(20);
        thour = (TextView)rootView.findViewById(R.id.hour);
        tvHour = (TextView)rootView.findViewById(R.id.th);
        tvMinute=(TextView)rootView.findViewById(R.id.tmin);
        tminutes=(TextView)rootView.findViewById(R.id.min);
        tvSecond=(TextView)rootView.findViewById(R.id.tsec);
        s=(TextView)rootView.findViewById(R.id.seco);

        setTimer();
    }

    private void setTimer() {
        long minutesLeft = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("kk:mm:ss");
            Calendar calendar = Calendar.getInstance();
            String currenttime = new SimpleDateFormat("kk:mm:ss").format(Calendar.getInstance().getTime());
            System.out.println("CURRENT TIME FOR TIMER" +currenttime);

            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = calendar.getTime();
            String tomorrowAsString = dateFormat.format(tomorrow);

            System.out.println(tomorrowAsString);

            try {
                String dateStart = dateFormat.format(System.currentTimeMillis());
                Date date1 = dateFormat.parse(dateStart);

                Date date2 = dateFormat.parse(tomorrowAsString);
                Date time = dateFormat1.parse("15:00:00");
                Date time1 = dateFormat1.parse(currenttime);
                if(time1.getTime() > time.getTime() || time1.getTime() == time.getTime()){
                    long different = (date2.getTime()+time.getTime()) - (date1.getTime()+time1.getTime());
                    long seconds = different / 1000;
                    minutesLeft = seconds / 60;
                    //printDifference(date1, date2, time, time1);
                }
                else{
                    long different = (date2.getTime()+time.getTime()) - (date1.getTime()+time1.getTime());
                    long seconds = different / 1000;
                    minutesLeft = seconds / 60;
                }
            }catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(minutesLeft > 1440)
        {
            minutesLeft = minutesLeft - 1440 ;
        }

        totalTimeCountInMilliseconds = 60 * minutesLeft * 1000;
        timeBlinkInMilliseconds = 30 * 1000;
        startTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

				/*set Timer and font type to Timer Texts*/
                thour.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"LED.ttf"));
                tminutes.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"LED.ttf"));
                s.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"LED.ttf"));
                tvHour.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"LED.ttf"));
                tvMinute.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"LED.ttf"));
                tvSecond.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"LED.ttf"));

                AppController app= (AppController)getActivity().getApplicationContext();
                long hh=app.getHour();
                long mm=app.getMin();
                long ss=app.getSec();


                thour.setText(""+hh);
                tvHour.setText("H");
                tvMinute.setText(""+mm);
                tminutes.setText("M");
                tvSecond.setText(""+ss);
                s.setText("S");

                try {
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm:ss");
                    Calendar calendar = Calendar.getInstance();
                    String currenttime = new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime());
//					System.out.println("CURRENT TIME FOR TIMER" +currenttime);

                    calendar.add(Calendar.DAY_OF_YEAR, 1);

                    Date time = dateFormat1.parse("15:00:00");
                    Date time1 = dateFormat1.parse(currenttime);


                    String t= time.toString();
                    String t1=time1.toString();
                    if(t.equals(t1))
                    {
//						Intent i = new Intent(AboutusActivity.this,ProductsPage.class);
//						startActivity(i);
//						finish();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                if(hh<10)
                {
                    thour.setText(""+"0"+hh);
                }
                else
                {
                    thour.setText(""+hh);

                }
                if(mm<10)
                {
                    tvMinute.setText(""+"0"+mm);

                }
                else
                {
                    tvMinute.setText(""+mm);

                }

                if(ss<10)
                {
                    tvSecond.setText(""+"0"+ss);
                }
                else
                {
                    tvSecond.setText(""+ss);
                }

                thour.setTextSize(30);
                tvMinute.setTextSize(30);
                tvSecond.setTextSize(30);
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {

            }

        }.start();

    }
}
