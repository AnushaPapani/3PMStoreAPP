package com.three.pmstore.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.squareup.picasso.Picasso;
import com.three.pmstore.R;
import com.three.pmstore.activities.HomeActivity;
import com.three.pmstore.activities.YoutubeVideoActivity;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.customviews.DialogClass;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shankar.
 */
public class ProductFragment extends Fragment implements View.OnClickListener {
    private Button description;
    private Button specifications;

    private TextView text_desc;

    private HomeActivity mParent;
    private View rootView;
    private TextView txt_left_icon;
    private TextView txt_right_icon;
    private TextView text_name;
    private TextView txt_name_bottom;
    private TextView txt_strike,txt_mrp;
    private TextView txt_buy, txt_buynow, txt_pmpricevalue;
    private ImageView img_highlighted;
    private ImageView image_thumbnail;
    private ImageView image_VideoPlayButton;

    private ImageView img_first;
    private ImageView img_second;
    private ImageView img_third;
    private ImageView img_four;
    private ImageView img_five;
    TableLayout table_layout;
    private Spinner spin_one;
    private Spinner spin_two;
    private Spinner spin_three;
    private Spinner spin_four;
    LinkedList<List<String>> columns = new LinkedList<List<String>>();
    private int mPosition = -1;
    private int mSelectedPosition = 0;
    String preTable, postTable;
    String out, out1;
    String infor;
    View toastRoot;
    View toastRoot2;
    Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (HomeActivity) getActivity();
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
            Utility.showLog("position", "position" + mPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product, container, false);
        toastRoot = inflater.inflate(R.layout.toast, null);
        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
        toast = new Toast(getActivity());
        initUI();
        return rootView;
    }

    private void initUI() {
        FacebookSdk.sdkInitialize(getActivity());
        AppEventsLogger.activateApp(getActivity());
        //		setContentView(R.layout.secs);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        AppEventsLogger logger = AppEventsLogger.newLogger(getActivity());
        logger.logEvent("pageview");
        text_name = (TextView) rootView.findViewById(R.id.text_name);
        txt_name_bottom = (TextView) rootView.findViewById(R.id.txt_name_bottom);
        txt_strike = (TextView) rootView.findViewById(R.id.txt_strike);
        txt_mrp = (TextView) rootView.findViewById(R.id.txt_mrp);
        txt_buy = (TextView) rootView.findViewById(R.id.txt_buy);
        txt_buynow = (TextView) rootView.findViewById(R.id.txt_buynow);
        txt_pmpricevalue = (TextView) rootView.findViewById(R.id.txt_pmpricevalue);
        img_highlighted = (ImageView) rootView.findViewById(R.id.img_highlighted);
        txt_left_icon = (TextView) rootView.findViewById(R.id.txt_left_icon);
        txt_right_icon = (TextView) rootView.findViewById(R.id.txt_right_icon);
        table_layout = (TableLayout) rootView.findViewById(R.id.tableLayout1);
        description = (Button) rootView.findViewById(R.id.btn_desc);
        specifications = (Button) rootView.findViewById(R.id.btn_spec);
        text_desc = (TextView) rootView.findViewById(R.id.text_desc);

        txt_pmpricevalue.setText(""+getString(R.string.rs)+HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
        img_first = (ImageView) rootView.findViewById(R.id.img_first);
        img_second = (ImageView) rootView.findViewById(R.id.img_second);
        img_third = (ImageView) rootView.findViewById(R.id.img_third);
        img_four = (ImageView) rootView.findViewById(R.id.img_four);
        img_five = (ImageView) rootView.findViewById(R.id.img_five);

        spin_one = (Spinner) rootView.findViewById(R.id.spin_one);
        ArrayList<String> spinnerArray = new ArrayList<>();
        if (HomeActivity.mProductItemsList != null && HomeActivity.mProductItemsList.get(mPosition).getAttrTypes() != null && HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().size() > 0) {
            spinnerArray.add("Select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(0) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0));
            String[] separated = HomeActivity.mProductItemsList.get(mPosition).getAttrValues().get(0).split(",");
            for (int i = 0; i < separated.length; i++) {
                spinnerArray.add(separated[i]);
            }
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    spinnerArray);
            spin_one.setAdapter(spinnerArrayAdapter);
        } else {
            spin_one.setVisibility(View.GONE);
        }

        spin_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (HomeActivity.mProductItemsList.get(mPosition).getStock().equalsIgnoreCase("0") || !Utility.isValueNullOrEmpty("" + HomeActivity.mProductItemsList.get(mPosition).getP_Qty())) {
                    if (HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0).equalsIgnoreCase("Quantity") && i != 0) {
//                        txt_buy.setText("BUY for " + Utility.getResourcesString(getActivity(), R.string.rs) + Integer.parseInt(spin_one.getSelectedItem().toString()) * HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
                    } else {
//                        txt_buy.setText("BUY for " + Utility.getResourcesString(getActivity(), R.string.rs) + 1 * HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
                    }
                } else {
                    txt_buy.setEnabled(false);
                    txt_buy.setText("Out Of Stock");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin_two = (Spinner) rootView.findViewById(R.id.spin_two);
        ArrayList<String> secondArray = new ArrayList<>();
        if (HomeActivity.mProductItemsList != null && HomeActivity.mProductItemsList.get(mPosition).getAttrTypes() != null && HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().size() > 1) {
            secondArray.add("Select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(1) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1));
            String[] second_separated = HomeActivity.mProductItemsList.get(mPosition).getAttrValues().get(1).split(",");
            for (int i = 0; i < second_separated.length; i++) {
                secondArray.add(second_separated[i]);
            }
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    secondArray);
            spin_two.setAdapter(spinnerArrayAdapter);
        } else {
            spin_two.setVisibility(View.GONE);
        }

        spin_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (HomeActivity.mProductItemsList.get(mPosition).getStock().equalsIgnoreCase("0") || !Utility.isValueNullOrEmpty("" + HomeActivity.mProductItemsList.get(mPosition).getP_Qty())) {
                    if (HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1).equalsIgnoreCase("Quantity") && i != 0) {
//                        txt_buy.setText("BUY for " + Utility.getResourcesString(getActivity(), R.string.rs) + Integer.parseInt(spin_two.getSelectedItem().toString()) * HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
                    } else {
//                        txt_buy.setText("BUY for " + Utility.getResourcesString(getActivity(), R.string.rs) + 1 * HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
                    }
                } else {
                    txt_buy.setEnabled(false);
                    txt_buy.setText("Out Of Stock");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin_three = (Spinner) rootView.findViewById(R.id.spin_three);
        ArrayList<String> thirdArray = new ArrayList<>();
        if (HomeActivity.mProductItemsList != null && HomeActivity.mProductItemsList.get(mPosition).getAttrTypes() != null && HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().size() > 2) {
            thirdArray.add("Select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(2) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2));
            String[] third_separated = HomeActivity.mProductItemsList.get(mPosition).getAttrValues().get(2).split(",");
            for (int i = 0; i < third_separated.length; i++) {
                thirdArray.add(third_separated[i]);
            }
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    thirdArray);
            spin_three.setAdapter(spinnerArrayAdapter);
        } else {
            spin_three.setVisibility(View.GONE);
        }

        spin_three.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (HomeActivity.mProductItemsList.get(mPosition).getStock().equalsIgnoreCase("0") || !Utility.isValueNullOrEmpty("" + HomeActivity.mProductItemsList.get(mPosition).getP_Qty())) {
                    if (HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2).equalsIgnoreCase("Quantity") && i != 0) {
//                        txt_buy.setText("BUY for " + Utility.getResourcesString(getActivity(), R.string.rs) + Integer.parseInt(spin_three.getSelectedItem().toString()) * HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
                    } else {
//                        txt_buy.setText("BUY for " + Utility.getResourcesString(getActivity(), R.string.rs) + 1 * HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
                    }
                } else {
                    txt_buy.setEnabled(false);
                    txt_buy.setText("Out Of Stock");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spin_four = (Spinner) rootView.findViewById(R.id.spin_four);
        ArrayList<String> fourArray = new ArrayList<>();
        if (HomeActivity.mProductItemsList != null && HomeActivity.mProductItemsList.get(mPosition).getAttrTypes() != null && HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().size() > 3) {
            fourArray.add("Select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(3) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(3));
            String[] four_separated = HomeActivity.mProductItemsList.get(mPosition).getAttrValues().get(3).split(",");
            for (int i = 0; i < four_separated.length; i++) {
                fourArray.add(four_separated[i]);
            }
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    fourArray);
            spin_four.setAdapter(spinnerArrayAdapter);
        } else {
            spin_four.setVisibility(View.GONE);
        }

        spin_four.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (HomeActivity.mProductItemsList.get(mPosition).getStock().equalsIgnoreCase("0") || !Utility.isValueNullOrEmpty("" + HomeActivity.mProductItemsList.get(mPosition).getP_Qty())) {
                    if (HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(3).equalsIgnoreCase("Quantity") && i != 0) {
//                        txt_buy.setText("BUY for " + Utility.getResourcesString(getActivity(), R.string.rs) + Integer.parseInt(spin_four.getSelectedItem().toString()) * HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
                    } else {
//                        txt_buy.setText("BUY for " + Utility.getResourcesString(getActivity(), R.string.rs) + 1 * HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
                    }
                } else {
                    txt_buy.setEnabled(false);
                    txt_buy.setText("Out Of Stock");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        image_thumbnail = (ImageView) rootView.findViewById(R.id.image_thumbnail);
        image_VideoPlayButton = (ImageView) rootView.findViewById(R.id.img_VideoPreviewPlayButton);
        if (HomeActivity.mProductItemsList != null && HomeActivity.mProductItemsList.size() > 0) {
            if (!Utility.isValueNullOrEmpty(HomeActivity.mProductItemsList.get(mPosition).getP_Video())) {
                image_thumbnail.setVisibility(View.VISIBLE);
                image_VideoPlayButton.setVisibility(View.VISIBLE);
                Picasso.with(getActivity()).load(getFullFilledImage("http://img.youtube.com/vi/" + HomeActivity.mProductItemsList.get(mPosition).getP_Video() + "/0.jpg")).placeholder(Utility.getDrawable(getActivity(), R.drawable.logo))
                        .into(image_thumbnail);
            } else {
                image_thumbnail.setVisibility(View.GONE);
                image_VideoPlayButton.setVisibility(View.GONE);
            }

            txt_right_icon.setTypeface(Utility.setTypeFace_fontawesome(getActivity()));
            txt_left_icon.setTypeface(Utility.setTypeFace_fontawesome(getActivity()));

            text_name.setText("" + HomeActivity.mProductItemsList.get(mPosition).getP_Name());
            txt_name_bottom.setText("" + HomeActivity.mProductItemsList.get(mPosition).getP_Name());
            if (HomeActivity.mProductItemsList.get(mPosition).getStock().equalsIgnoreCase("0") || !Utility.isValueNullOrEmpty("" + HomeActivity.mProductItemsList.get(mPosition).getP_Qty())) {
//                txt_buy.setText("BUY for " + Utility.getResourcesString(getActivity(), R.string.rs) + HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
            } else {
                txt_buy.setEnabled(false);
                txt_buy.setText("Out Of Stock");
            }
            text_desc.setText(Html.fromHtml(HomeActivity.mProductItemsList.get(mPosition).getP_Description()).toString());
            //text_desc.setText(Html.fromHtml(HomeActivity.mProductItemsList.get(mPosition).getP_Information()).toString());
            if (!Utility.isValueNullOrEmpty(HomeActivity.mProductItemsList.get(mPosition).getStrikeMrp())) {
                txt_strike.setText("" + HomeActivity.mProductItemsList.get(mPosition).getStrikeMrp());
                txt_strike.setPaintFlags(txt_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else
            {
                txt_mrp.setVisibility(View.GONE);
            }
            infor = text_desc.getText().toString();

            Picasso.with(getActivity()).load(getFullFilledImage(HomeActivity.mProductItemsList.get(mPosition)
                    .getImages().get(0))).placeholder(Utility.getDrawable(getActivity(), R.drawable.logo))
                    .into(img_highlighted);

            updateUI(mPosition, mSelectedPosition);
            updateLowerImages();

            txt_left_icon.setOnClickListener(this);
            txt_right_icon.setOnClickListener(this);

            img_first.setOnClickListener(this);
            img_second.setOnClickListener(this);
            img_third.setOnClickListener(this);
            img_four.setOnClickListener(this);
            img_five.setOnClickListener(this);
            txt_buy.setOnClickListener(this);
            txt_buynow.setOnClickListener(this);
            image_thumbnail.setOnClickListener(this);
            specifications.setOnClickListener(this);
            description.setOnClickListener(this);
        }
    }

    private void updateLowerImages() {
        Picasso.with(getActivity()).load(getFullFilledImage(HomeActivity.mProductItemsList.get(mPosition).getImages().get(0)))
                .resize(100, 100)
                .placeholder(Utility.getDrawable(getActivity(), R.drawable.logo))
                .into(img_first);
        Picasso.with(getActivity()).load(getFullFilledImage(HomeActivity.mProductItemsList.get(mPosition).getImages().get(1)))
                .resize(100, 100)
                .placeholder(Utility.getDrawable(getActivity(), R.drawable.logo))
                .into(img_second);
        Picasso.with(getActivity()).load(getFullFilledImage(HomeActivity.mProductItemsList.get(mPosition).getImages().get(2)))
                .resize(100, 100)
                .placeholder(Utility.getDrawable(getActivity(), R.drawable.logo))
                .into(img_third);
        Picasso.with(getActivity()).load(getFullFilledImage(HomeActivity.mProductItemsList.get(mPosition).getImages().get(3)))
                .resize(100, 100)
                .placeholder(Utility.getDrawable(getActivity(), R.drawable.logo))
                .into(img_four);
        Picasso.with(getActivity()).load(getFullFilledImage(HomeActivity.mProductItemsList.get(mPosition).getImages().get(4)))
                .resize(100, 100)
                .placeholder(Utility.getDrawable(getActivity(), R.drawable.logo))
                .into(img_five);
    }

    private String getFullFilledImage(String image) {
        return image.replaceAll(" ", "%20");
    }

    private void updateUI(int mPosition, int selectedPosition) {
        if (selectedPosition == 0 && HomeActivity.mProductItemsList.get(mPosition).getImages().size() == 1) {
            txt_left_icon.setVisibility(View.INVISIBLE);
            txt_right_icon.setVisibility(View.INVISIBLE);
        } else if (selectedPosition == 0) {
            txt_left_icon.setVisibility(View.INVISIBLE);
            txt_right_icon.setVisibility(View.VISIBLE);
        } else if (selectedPosition == HomeActivity.mProductItemsList.get(mPosition).getImages().size() - 1) {
            txt_left_icon.setVisibility(View.VISIBLE);
            txt_right_icon.setVisibility(View.INVISIBLE);
        } else {
            txt_left_icon.setVisibility(View.VISIBLE);
            txt_right_icon.setVisibility(View.VISIBLE);
        }

        if (selectedPosition == 0) {
            img_first.setBackground(Utility.getDrawable(mParent, R.drawable.border_select));
            img_second.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_third.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_four.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_five.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
        } else if (selectedPosition == 1) {
            img_first.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_second.setBackground(Utility.getDrawable(mParent, R.drawable.border_select));
            img_third.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_four.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_five.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
        } else if (selectedPosition == 2) {
            img_first.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_second.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_third.setBackground(Utility.getDrawable(mParent, R.drawable.border_select));
            img_four.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_five.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
        } else if (selectedPosition == 3) {
            img_first.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_second.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_third.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_four.setBackground(Utility.getDrawable(mParent, R.drawable.border_select));
            img_five.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
        } else if (selectedPosition == 4) {
            img_first.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_second.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_third.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_four.setBackground(Utility.getDrawable(mParent, R.drawable.border_unselect));
            img_five.setBackground(Utility.getDrawable(mParent, R.drawable.border_select));
        }
        String txtString = HomeActivity.mProductItemsList.get(mPosition).getP_Information();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left_icon:
                updateLeftImage();
                break;
            case R.id.txt_right_icon:
                updateRightImage();
                break;
            case R.id.img_first:
                updateParticularImage(0);
                break;
            case R.id.img_second:
                updateParticularImage(1);
                break;
            case R.id.img_third:
                updateParticularImage(2);
                break;
            case R.id.img_four:
                updateParticularImage(3);
                break;
            case R.id.img_five:
                updateParticularImage(4);
                break;
            case R.id.txt_buy:
                buyProduct();
                break;
            case R.id.txt_buynow:
                buyProduct2();
                break;

            case R.id.image_thumbnail:
                Intent intent = new Intent(getActivity(), YoutubeVideoActivity.class);
                intent.putExtra("videoId", HomeActivity.mProductItemsList.get(mPosition).getP_Video());
                startActivity(intent);
                break;
            case R.id.btn_desc:
                text_desc.setText(Html.fromHtml(HomeActivity.mProductItemsList.get(mPosition).getP_Description()).toString());
                description.setBackgroundResource(R.drawable.homeborders);
                specifications.setBackgroundResource(R.drawable.borders);
                table_layout.removeAllViews();
                text_desc.setText(Html.fromHtml(HomeActivity.mProductItemsList.get(mPosition).getP_Description()).toString());
                break;
            case R.id.btn_spec:
                text_desc.setText(Html.fromHtml(HomeActivity.mProductItemsList.get(mPosition).getP_Information()).toString());
                infor = (HomeActivity.mProductItemsList.get(mPosition).getP_Information()).toString();
                columns  =  new LinkedList<>();
                if (infor.contains("<table")) {
                    preTable = infor.substring(0, infor.indexOf("<table"));
                    postTable = infor.substring(infor.indexOf("<table"));
                    out = Jsoup.parse(preTable).text();
                    out1 = Jsoup.parse(postTable).text();
                    Log.d("infor1", out);
                    Log.d("infor2", out1);

                    org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(postTable);

                    //List<String> headings = new LinkedList<String>();;

                    Element elementsByTag = doc.getElementsByTag("table").get(0);
                    Elements rows = elementsByTag.getElementsByTag("tr");
                    int rowCount = 0;
                    for (Element row : rows) {


                        List<String> individualColumn = new ArrayList<String>();
                        ;
                        for (int headerCount = 0; headerCount < row.getElementsByTag("td").size(); headerCount++) {
                            individualColumn.add(row.getElementsByTag("td").get(headerCount).text());
                        }
                        columns.add(individualColumn);

                    }

                    text_desc.setText(Html.fromHtml(preTable).toString());
                    table_layout.removeAllViews();
                    BuildTable(columns);

                } else {
                    table_layout.removeAllViews();
                    out = Jsoup.parse(infor).text();
                }

                description.setBackgroundResource(R.drawable.borders);
                specifications.setBackgroundResource(R.drawable.homeborders);
                break;
        }
    }

    private void buyProduct() {
        if (txt_buy.getText().toString().equalsIgnoreCase("Out Of Stock") || HomeActivity.mProductItemsList.get(mPosition).getStock().equalsIgnoreCase("0")) {
            txt_buy.setEnabled(false);
            txt_buy.setText("Out of Stock!!");
            txt_buy.setBackgroundColor(Color.parseColor("#FF0000"));
            Utility.showToastMessage(getActivity(), "Sorry! Out Of Stock..!");
        } else {
            if (isValidFields()) {
                postSelectedItem();
            }
        }
    }

    private void buyProduct2() {
        if (txt_buynow.getText().toString().equalsIgnoreCase("Out Of Stock") || HomeActivity.mProductItemsList.get(mPosition).getStock().equalsIgnoreCase("0")) {
            txt_buynow.setEnabled(false);
            txt_buynow.setText("Out of Stock!!");
            txt_buynow.setBackgroundColor(Color.parseColor("#FF0000"));
            Utility.showToastMessage(getActivity(), "Sorry! Out Of Stock..!");
        } else {
            if (isValidFields()) {
                postSelectedItem2();
            }
        }
    }

    private void BuildTable(LinkedList<List<String>> columns) {

        // outer for loop
        int rows = 5;
        int cols = 5;
        table_layout.removeAllViews();
        for (int i = 0; i < columns.size(); i++) {

            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 0; j < columns.get(i).size(); j++) {

                TextView tv = new TextView(getActivity());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(columns.get(i).get(j).toString());

                row.addView(tv);

            }

            table_layout.addView(row);

        }
    }

    private void postSelectedItem() {
        if (Utility.isNetworkAvailable(getActivity())) {
            new PostProductAsyncTask().execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }
    private void postSelectedItem2() {
        if (Utility.isNetworkAvailable(getActivity())) {
            new PostProductAsyncTask2().execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }


    private boolean isValidFields() {
        if (HomeActivity.mProductItemsList.get(mPosition).getAttrTypes() != null && HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().size() > 0) {
            if (HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().size() == 1) {
                String text_one = spin_one.getSelectedItem().toString();
                if (!text_one.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))) {
                    return true;
                } else {
                    if (text_one.contains("Quantity")) {
                        spin_one.setSelection(1);
                        return true;
                    } else {
                        Utility.showToastMessage(getActivity(), "Please select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(0) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0));
                        return false;
                    }
                }
            } else if (HomeActivity.mProductItemsList.get(mPosition).getAttrNames().size() == 2) {
                String text_one = spin_one.getSelectedItem().toString();
                String text_two = spin_two.getSelectedItem().toString();
                if (!text_one.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))
                        && !text_two.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))) {
                    return true;
                } else {
                    if (text_one.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))) {
                        if (text_one.contains("Quantity")) {
                            spin_one.setSelection(1);
                            return true;
                        } else {
                            Utility.showToastMessage(getActivity(), "Please select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(0) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0));
                            return false;
                        }
                    } else if (text_two.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))) {
                        if (text_two.contains("Quantity")) {
                            spin_two.setSelection(1);
                            return true;
                        } else {
                            Utility.showToastMessage(getActivity(), "Please select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(1) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1));
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else if (HomeActivity.mProductItemsList.get(mPosition).getAttrNames().size() == 3) {
                String text_one = spin_one.getSelectedItem().toString();
                String text_two = spin_two.getSelectedItem().toString();
                String text_three = spin_three.getSelectedItem().toString();
                if (!text_one.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))
                        && !text_two.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1)) &&
                        !text_three.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2))) {
                    return true;
                } else {
                    if (text_one.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))) {
                        if (text_one.contains("Quantity")) {
                            spin_one.setSelection(1);
                            return true;
                        } else {
                            Utility.showToastMessage(getActivity(), "Please select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(0) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0));
                            return false;
                        }
                    } else if (text_two.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))) {
                        if (text_two.contains("Quantity")) {
                            spin_two.setSelection(1);
                            return true;
                        } else {
                            Utility.showToastMessage(getActivity(), "Please select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(1) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1));
                            return false;
                        }
                    } else if (text_three.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2))) {
                        if (text_three.contains("Quantity")) {
                            spin_three.setSelection(1);
                            return true;
                        } else {
                            Utility.showToastMessage(getActivity(), "Please select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(2) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2));
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else if (HomeActivity.mProductItemsList.get(mPosition).getAttrNames().size() == 4) {
                String text_one = spin_one.getSelectedItem().toString();
                String text_two = spin_two.getSelectedItem().toString();
                String text_three = spin_three.getSelectedItem().toString();
                String text_four = spin_four.getSelectedItem().toString();

                if (!text_one.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))
                        && !text_two.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))
                        && !text_four.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))
                        && !text_three.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2))) {
                    return true;
                } else {
                    if (text_one.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))) {
                        if (text_one.contains("Quantity")) {
                            spin_one.setSelection(1);
                            return true;
                        } else {
                            Utility.showToastMessage(getActivity(), "Please select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(0) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0));
                            return false;
                        }
                    } else if (text_two.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))) {
                        if (text_two.contains("Quantity")) {
                            spin_two.setSelection(1);
                            return true;
                        } else {
                            Utility.showToastMessage(getActivity(), "Please select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(1) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1));
                            return false;
                        }
                    } else if (text_three.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2))) {
                        if (text_three.contains("Quantity")) {
                            spin_three.setSelection(1);
                            return true;
                        } else {
                            Utility.showToastMessage(getActivity(), "Please select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(2) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2));
                            return false;
                        }
                    } else if (text_four.contains(HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(3))) {
                        if (text_four.contains("Quantity")) {
                            spin_four.setSelection(1);
                            return true;
                        } else {
                            Utility.showToastMessage(getActivity(), "Please select " + HomeActivity.mProductItemsList.get(mPosition).getAttrNames().get(3) + " " + HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().get(3));
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private void updateParticularImage(int mSelectedPosition) {
        this.mSelectedPosition = mSelectedPosition;
        Picasso.with(getActivity()).load(getFullFilledImage(HomeActivity.mProductItemsList.get(mPosition).getImages()
                .get(mSelectedPosition))).placeholder(Utility.getDrawable(getActivity(), R.drawable.logo))
                .into(img_highlighted);
        updateUI(mPosition, mSelectedPosition);
    }

    private void updateLeftImage() {
        mSelectedPosition = mSelectedPosition - 1;
        Picasso.with(getActivity()).load(getFullFilledImage(HomeActivity.mProductItemsList.get(mPosition).getImages()
                .get(mSelectedPosition))).placeholder(Utility.getDrawable(getActivity(), R.drawable.logo))
                .into(img_highlighted);
        updateUI(mPosition, mSelectedPosition);
    }

    private void updateRightImage() {
        mSelectedPosition = mSelectedPosition + 1;
        Picasso.with(getActivity()).load(getFullFilledImage(HomeActivity.mProductItemsList.get(mPosition).getImages()
                .get(mSelectedPosition))).placeholder(Utility.getDrawable(getActivity(), R.drawable.logo))
                .into(img_highlighted);
        updateUI(mPosition, mSelectedPosition);
    }

    private class PostProductAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public PostProductAsyncTask() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("U_ID", Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID));
                paramsList.put("P_ID", HomeActivity.mProductItemsList.get(mPosition).getP_id());
                paramsList.put("cartValue", "" + HomeActivity.mCartTotal);
                paramsList.put("P_Name", HomeActivity.mProductItemsList.get(mPosition).getP_Name());
                paramsList.put("P_Cost", "" + HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
                paramsList.put("P_Qty", getSelectedSpinner("Quantity"));
                paramsList.put("gender", getSelectedSpinner("Gender"));
                paramsList.put("customattribute", getSelectedSpinner("Custom"));
                paramsList.put("color", getSelectedSpinner("Color"));
                paramsList.put("size", getSelectedSpinner("Size"));
                paramsList.put("cartId", HomeActivity.mCartId);

                result = Utility.httpPostRequestToServer(ApiConstants.INSERT_CHECK_PRODUCTS, Utility.getParams(paramsList));
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject.optString("success").equalsIgnoreCase("1")) {
                        HomeActivity.mCartId = jsonobject.optString("cartId");
                        HomeActivity.mCartValue = jsonobject.optInt("cartCount");
                        HomeActivity.mCartTotal = jsonobject.optInt("cartValue");
                        HomeActivity.cart_layout_button_set_text.setText("" + HomeActivity.mCartValue);


                        Utility.showToastMessage(getActivity(), "Product Added to Cart Successfully");
                    } else if (jsonobject.optString("success").equalsIgnoreCase("2")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("This product has expired. Please check out todays product!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                        dialog.cancel();
//                                      Utility.navigateDashBoardFragment(new ProductFragment(), ProductFragment.TAG, null, getActivity());
                                        Intent i = new Intent(getActivity(), HomeActivity.class);
                                        startActivity(i);
//                                        finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else if (jsonobject.optString("success").equalsIgnoreCase("3")) {

                        Utility.showToastMessage(getActivity(), "Sorry! Product has reached maximum quantity per order.");
                    } else {

                        Utility.showToastMessage(getActivity(), "Some network error occurred please try again later.");
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String getSelectedSpinner(String mSelectedName) {
        String mSelectedThing = "";
        if (HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().contains(mSelectedName)) {
            int position = HomeActivity.mProductItemsList.get(mPosition).getAttrTypes().indexOf(mSelectedName);
            if (position == 0) {
                mSelectedThing = spin_one.getSelectedItem().toString();
            } else if (position == 1) {
                mSelectedThing = spin_two.getSelectedItem().toString();
            } else if (position == 2) {
                mSelectedThing = spin_three.getSelectedItem().toString();
            } else if (position == 3) {
                mSelectedThing = spin_four.getSelectedItem().toString();
            }
        }
        return mSelectedThing;
    }

    private class PostProductAsyncTask2 extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public PostProductAsyncTask2() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("U_ID", Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID));
                paramsList.put("P_ID", HomeActivity.mProductItemsList.get(mPosition).getP_id());
                paramsList.put("cartValue", "" + HomeActivity.mCartTotal);
                paramsList.put("P_Name", HomeActivity.mProductItemsList.get(mPosition).getP_Name());
                paramsList.put("P_Cost", "" + HomeActivity.mProductItemsList.get(mPosition).getP_Cost());
                paramsList.put("P_Qty", getSelectedSpinner("Quantity"));
                paramsList.put("gender", getSelectedSpinner("Gender"));
                paramsList.put("customattribute", getSelectedSpinner("Custom"));
                paramsList.put("color", getSelectedSpinner("Color"));
                paramsList.put("size", getSelectedSpinner("Size"));
                paramsList.put("cartId", HomeActivity.mCartId);

                result = Utility.httpPostRequestToServer(ApiConstants.INSERT_CHECK_PRODUCTS, Utility.getParams(paramsList));
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject.optString("success").equalsIgnoreCase("1")) {
                        HomeActivity.mCartId = jsonobject.optString("cartId");
                        HomeActivity.mCartValue = jsonobject.optInt("cartCount");
                        HomeActivity.mCartTotal = jsonobject.optInt("cartValue");
//                        HomeActivity.cart_layout_button_set_text.setText("" + HomeActivity.mCartValue);

//                        Utility.showToastMessage(getActivity(), "Product Added to Cart Successfully");

                        if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID))) {
                            Utility.navigateDashBoardFragment(new ReviewOrderFragment_Before_Login(), ReviewOrderFragment_Before_Login.TAG, null, getActivity());
                        }
                        else if(Utility.getSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT).toString().equals("0")&&
                                (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID))))
                        {
                            Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null,getActivity());
                        }
                        else if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID)) &&
                                !Utility.getSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT).toString().equals("0")) {
                            Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, getActivity());
                        } else {
                            Utility.showToastMessage(getActivity(), "Add at least one item to cart");
                        }
                    } else if (jsonobject.optString("success").equalsIgnoreCase("2")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("This product has expired. Please check out todays product!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                        dialog.cancel();
//                                        Utility.navigateDashBoardFragment(new ProductFragment(), ProductFragment.TAG, null, getActivity());
                                        Intent i = new Intent(getActivity(), HomeActivity.class);
                                        startActivity(i);
//                                        finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else if (jsonobject.optString("success").equalsIgnoreCase("3")) {

                        Utility.showToastMessage(getActivity(), "Sorry! Product has reached maximum quantity per order.");
                    } else {

                        Utility.showToastMessage(getActivity(), "Some network error occurred please try again later.");
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
