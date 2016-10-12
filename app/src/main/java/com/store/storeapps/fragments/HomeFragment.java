package com.store.storeapps.fragments;

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

import com.store.storeapps.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Shankar.
 */
public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";
    private TabLayout tabLayout;
    private View rootView;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
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

        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView textview = (TextView) tabOne.findViewById(R.id.txt_image);
        ImageView img_icon = (ImageView) tabOne.findViewById(R.id.img_icon);
        textview.setText("APPAREL");
        img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_first_gray));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView textview_two = (TextView) tabTwo.findViewById(R.id.txt_image);
        ImageView img_icon_two = (ImageView) tabTwo.findViewById(R.id.img_icon);
        textview_two.setText("FOOTWEAR");
        img_icon_two.setImageDrawable(getResources().getDrawable(R.drawable.image_second_gray));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        LinearLayout tabThree = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView textview_three = (TextView) tabThree.findViewById(R.id.txt_image);
        ImageView img_icon_three = (ImageView) tabThree.findViewById(R.id.img_icon);
        textview_three.setText("STATIONARY");
        img_icon_three.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
        tabLayout.getTabAt(2).setCustomView(tabThree);

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
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_first_white));
                    viewPager.setCurrentItem(0);
                } else if (tab.getPosition() == 1) {
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_second_white));
                    viewPager.setCurrentItem(1);
                } else {
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_white));
                    viewPager.setCurrentItem(2);
                }
                textview.setTextColor(getResources().getColor(R.color.white));
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
                } else if (tab.getPosition() == 1) {
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_second_gray));
                } else {
                    img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_third_gray));
                }
                textview.setTextColor(getResources().getColor(R.color.txt_unselected_color));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.getTabAt(2).select();
        tabLayout.getTabAt(1).select();
        tabLayout.getTabAt(0).select();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new ProductFragment(), "ONE");
        adapter.addFrag(new ProductFragment(), "TWO");
        adapter.addFrag(new ProductFragment(), "THREE");
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
            bundle.putInt("position", position);
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
