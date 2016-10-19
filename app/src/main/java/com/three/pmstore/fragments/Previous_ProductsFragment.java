package com.three.pmstore.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.three.pmstore.R;
import com.three.pmstore.activities.Previous_ProductsActivity;
import com.three.pmstore.models.Previous_ItemDetails;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Shankar.
 */
public class Previous_ProductsFragment extends Fragment {
    public static final String TAG = "Previous_ProductsFragment";
    private TabLayout tabLayout;
    private View rootView;
    private ViewPager viewPager;
    Previous_ItemDetails getdates;
    String date1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_previous_products, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
    }

    private void setupTabIcons() {
        getdates = new Previous_ItemDetails();
        System.out.println("Getting dates" + Previous_ProductsActivity.dates);
                for (int i = 0; i < Previous_ProductsActivity.dates.length(); i++) {
                    try {
                        if (i==0){
                            String date1 = Previous_ProductsActivity.dates.getString(i);
                            System.out.println("loopdate1 "+date1);
                            JSONArray product1= Previous_ProductsActivity.products.getJSONArray(date1.toString());
                            LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.previousproducts_custom_tab, null);
                            TextView textview = (TextView) tabOne.findViewById(R.id.txt_image);
                            ImageView img_icon = (ImageView) tabOne.findViewById(R.id.img_icon);

                            textview.setText(date1);
                            img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_first_gray));
                            tabLayout.getTabAt(0).setCustomView(tabOne);
                        }

                        if (i == 1){
                            String date2 = Previous_ProductsActivity.dates.getString(i);
                            System.out.println("loopdate2 "+date2);
                            LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.previousproducts_custom_tab, null);
                            TextView textview_two = (TextView) tabTwo.findViewById(R.id.txt_image);
                            ImageView img_icon_two = (ImageView) tabTwo.findViewById(R.id.img_icon);
                            textview_two.setText(date2);
                            img_icon_two.setImageDrawable(getResources().getDrawable(R.drawable.image_second_gray));
                            tabLayout.getTabAt(1).setCustomView(tabTwo);
                        }

                        if (i == 2){
                            String date3 = Previous_ProductsActivity.dates.getString(i);
                            System.out.println("loopdate3 "+date3);
                            LinearLayout tabThree = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.previousproducts_custom_tab, null);
                            TextView textview_three = (TextView) tabThree.findViewById(R.id.txt_image);
                            ImageView img_icon_three = (ImageView) tabThree.findViewById(R.id.img_icon);
                            textview_three.setText(date3);
                            img_icon_three.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
                            tabLayout.getTabAt(2).setCustomView(tabThree);
                        }

                        if (i == 3){
                            String date4 = Previous_ProductsActivity.dates.getString(i);
                            System.out.println("loopdate3 "+date4);
                            LinearLayout tabFour = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.previousproducts_custom_tab, null);
                            TextView textview_four = (TextView) tabFour.findViewById(R.id.txt_image);
                            ImageView img_icon_four = (ImageView) tabFour.findViewById(R.id.img_icon);
                            textview_four.setText(date4);
                            img_icon_four.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
                            tabLayout.getTabAt(3).setCustomView(tabFour);
                        }

                        if (i == 4){
                            String date5 = Previous_ProductsActivity.dates.getString(i);
                            System.out.println("loopdate3 "+date5);
                            LinearLayout tabFive = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.previousproducts_custom_tab, null);
                            TextView textview_five = (TextView) tabFive.findViewById(R.id.txt_image);
                            ImageView img_icon_five = (ImageView) tabFive.findViewById(R.id.img_icon);
                            textview_five.setText(date5);
                            img_icon_five.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
                            tabLayout.getTabAt(4).setCustomView(tabFive);
                        }

                        if (i == 5){
                            String date6 = Previous_ProductsActivity.dates.getString(i);
                            System.out.println("loopdate3 "+date6);
                            LinearLayout tabSix = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.previousproducts_custom_tab, null);
                            TextView textview_six = (TextView) tabSix.findViewById(R.id.txt_image);
                            ImageView img_icon_six = (ImageView) tabSix.findViewById(R.id.img_icon);
                            textview_six.setText(date6);
                            img_icon_six.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
                            tabLayout.getTabAt(5).setCustomView(tabSix);

                        }
                        if (i==6){
                            String date7 = Previous_ProductsActivity.dates.getString(i);
                            System.out.println("loopdate3 "+date7);
                            LinearLayout tabSeven = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.previousproducts_custom_tab, null);
                            TextView textview_seven = (TextView) tabSeven.findViewById(R.id.txt_image);
                            ImageView img_icon_seven = (ImageView) tabSeven.findViewById(R.id.img_icon);
                            textview_seven.setText(date7);
                            img_icon_seven.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
                            tabLayout.getTabAt(6).setCustomView(tabSeven);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }



                }

      tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout selectedTab = (LinearLayout) tab.getCustomView();
                selectedTab.setLayoutParams(new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                selectedTab.setBackgroundColor(getResources().getColor(R.color.titlbar));
                TextView textview = (TextView) selectedTab.findViewById(R.id.txt_image);
                ImageView img_icon = (ImageView) selectedTab.findViewById(R.id.img_icon);
                if (tab.getPosition() == 0) {
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_first_white));
                    viewPager.setCurrentItem(0);
                } else if (tab.getPosition() == 1) {
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_second_white));
                    viewPager.setCurrentItem(1);
                } else if (tab.getPosition() == 2){
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_white));
                    viewPager.setCurrentItem(2);
                }else if (tab.getPosition() == 3){
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_white));
                    viewPager.setCurrentItem(3);
                }else if (tab.getPosition() == 4){
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_white));
                    viewPager.setCurrentItem(4);
                textview.setTextColor(getResources().getColor(R.color.white));
                }else if (tab.getPosition() == 5) {
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_white));
                    viewPager.setCurrentItem(5);
                }else {
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_white));
                    viewPager.setCurrentItem(6);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout selectedTab = (LinearLayout) tab.getCustomView();
                selectedTab.setLayoutParams(new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                selectedTab.setBackgroundColor(getResources().getColor(R.color.white));
                TextView textview = (TextView) selectedTab.findViewById(R.id.txt_image);
                ImageView img_icon = (ImageView) selectedTab.findViewById(R.id.img_icon);
                if (tab.getPosition() == 0) {
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_first_gray));
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                } else if (tab.getPosition() == 1) {
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_second_gray));
                } else if (tab.getPosition() == 2){
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
                }else if (tab.getPosition() == 3){
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
                }else if (tab.getPosition() == 4){
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
                }else if (tab.getPosition() == 5){
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
                }else if (tab.getPosition() == 6){
                    textview.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
                }
                textview.setTextColor(getResources().getColor(R.color.txt_unselected_color));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(6).select();
        tabLayout.getTabAt(5).select();
        tabLayout.getTabAt(4).select();
        tabLayout.getTabAt(3).select();
        tabLayout.getTabAt(2).select();
        tabLayout.getTabAt(1).select();
        tabLayout.getTabAt(0).select();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new PreviousProductFragment(), "ONE");
        adapter.addFrag(new PreviousProductFragment(), "TWO");
        adapter.addFrag(new PreviousProductFragment(), "THREE");
        adapter.addFrag(new PreviousProductFragment(), "FOUR");
        adapter.addFrag(new PreviousProductFragment(), "FIVE");
        adapter.addFrag(new PreviousProductFragment(), "SIX");
        adapter.addFrag(new PreviousProductFragment(), "SEVEN");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mFragmentList.get(position);
            Bundle bundle = new Bundle();
            bundle.putInt("prevprodposition",position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
