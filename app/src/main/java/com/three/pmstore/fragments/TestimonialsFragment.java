package com.three.pmstore.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.three.pmstore.R;
import com.three.pmstore.activities.FriendInfor;
import com.three.pmstore.adapters.ListViewAdapter;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/*
Created by Anusha
 */
public class TestimonialsFragment extends Fragment {
    public static final String TAG = "TestimonialsFragment";
    private View rootView;
    String Aboutus = "Testimonials";
    ArrayList<HashMap<String, String>> mytestimonialsList;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_TESTIMONIALS = "tbl_testimonials";
    private static final String TAG_ID = "ID";
    private static final String TAG_NAME = "Name";
    private static final String TAG_CITY = "City";
    private static final String TAG_PROFESSION = "Profession";
    private static final String TAG_TESTIMONIAL = "Message";
    private static final String TAG_IMAGE = "Image";
    private static final String TAG_DATE = "Date";
    private String id, pname, city, profession, testimonial, image, date, c2;
    private LinkedList<FriendInfor> friends;
    ListView listView;
    private List<String> testiname = new ArrayList<String>();
    private List<String> testimessage = new ArrayList<String>();
    private List<String> testiimage = new ArrayList<String>();
    private List<String> testicity = new ArrayList<String>();
    private List<String> testiprofession = new ArrayList<String>();
    boolean firsttime = false;
    private ListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_testimonials, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        TextView aboutustext = (TextView) rootView.findViewById(R.id.testimmonial);
        SpannableString content = new SpannableString(Aboutus);
        content.setSpan(new UnderlineSpan(), 0, Aboutus.length(), 0);
        aboutustext.setText(content);
        mytestimonialsList = new ArrayList<HashMap<String, String>>();
        listView = (ListView) rootView.findViewById(R.id.list);
        new GetTestimonials().execute();
    }

    private void setListViewAdapter() {
        adapter = new ListViewAdapter(getActivity(), R.layout.item_even_listview, friends);
        listView.setAdapter(adapter);
    }

    class GetTestimonials extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public GetTestimonials() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));

        }

        @Override
        protected String doInBackground(String... params){
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                result = Utility.httpPostRequestToServer(ApiConstants.TESTIMONIALS, Utility.getParams(paramsList));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                // Checking for SUCCESS TAG
                JSONObject jsonobject = new JSONObject(response);
                if (jsonobject.optString("success").equalsIgnoreCase("1")) {
                    // products found
                    // Getting Array of Products
                    JSONArray testimonials = jsonobject.getJSONArray(TAG_TESTIMONIALS);
                    // looping through All Products
                    for (int i = 0; i < testimonials.length(); i++) {
                        JSONObject c = testimonials.getJSONObject(i);
                        // Storing each json item in variable
                        id = c.getString(TAG_ID);
                        pname = c.getString(TAG_NAME);
                        city = c.getString(TAG_CITY);
                        profession = c.getString(TAG_PROFESSION);
                        testimonial = c.getString(TAG_TESTIMONIAL);
                        image = c.getString(TAG_IMAGE).replaceAll(" ", "%20");
                        date = c.getString(TAG_DATE);

                        if (profession.equals("")) {
                            String c1 = profession.concat("");
                            c2 = c1.concat(city);

                        } else {
                            String c1 = profession.concat(", ");
                            c2 = c1.concat(city);
                        }
                        testiname.add(pname);
                        testimessage.add(testimonial);
                        testiimage.add(image);
                        testicity.add(city);
                        testiprofession.add(c2);
                    }
                    friends = new LinkedList<FriendInfor>();
                    if (!firsttime) {

                        for (int i = 0; i < testimonials.length(); i++) {
                            friends.add(new FriendInfor(testiname.get(i), testimessage.get(i),
                                    testiimage.get(i).toString(), testicity.get(i)
                                    , testiprofession.get(i)
                            ));

                            String Image = testiimage.get(i);
                            if (profession.equals("")) {
                                String c1 = profession.concat("");
                                c2 = c1.concat(city);

                            } else {
                                String c1 = profession.concat(", ");
                                c2 = c1.concat(city);
                            }

                            firsttime = true;
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(TAG_NAME, pname);
                            map.put(TAG_TESTIMONIAL, testimonial);
                            map.put(TAG_CITY, city);
                            map.put(TAG_PROFESSION, c2);
                            map.put(TAG_IMAGE, image);

                            // adding HashList to ArrayList
                            mytestimonialsList.add(map);
                        }

                        if(mytestimonialsList.size() > 0)
                        {
                            setListViewAdapter();
                        }
                    }
                } else {
                    Utility.navigateDashBoardFragment(new NoTestimonialsFragment(), NoTestimonialsFragment.TAG, null, getActivity());

                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
