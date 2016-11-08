package com.three.pmstore.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.three.pmstore.R;
import com.three.pmstore.activities.HomeActivity;
import com.three.pmstore.utility.Utility;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Shankar.
 */
public class HomeFragment extends Fragment {
    //        implements SwipeRefreshLayout.OnRefreshListener  {
    public static final String TAG = "HomeFragment";
    private TabLayout tabLayout;
    private View rootView;
    private ViewPager viewPager;
    //    public static SwipeRefreshLayout swipeRefreshLayout;
    private HomeActivity mParent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (HomeActivity) getActivity();
    }

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

//        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
//        swipeRefreshLayout.setOnRefreshListener(this);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {

        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView textview = (TextView) tabOne.findViewById(R.id.txt_image);
        ImageView img_icon = (ImageView) tabOne.findViewById(R.id.img_icon);
        try {
            textview.setText(HomeActivity.mProductItemsList.get(0).getCategory().toUpperCase());
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        Picasso.with(getActivity()).load((HomeActivity.mProductItemsList.get(0).getCategory_Icon())).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh)).into(img_icon);
        //img_icon.setImageDrawable(getResources().getDrawable(R.drawable.image_first_gray));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView textview_two = (TextView) tabTwo.findViewById(R.id.txt_image);
        ImageView img_icon_two = (ImageView) tabTwo.findViewById(R.id.img_icon);
        textview_two.setText(HomeActivity.mProductItemsList.get(1).getCategory().toUpperCase());
        Picasso.with(getActivity()).load((HomeActivity.mProductItemsList.get(1).getCategory_Icon())).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh)).into(img_icon_two);
        //img_icon_two.setImageDrawable(getResources().getDrawable(R.drawable.image_second_gray));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        LinearLayout tabThree = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView textview_three = (TextView) tabThree.findViewById(R.id.txt_image);
        ImageView img_icon_three = (ImageView) tabThree.findViewById(R.id.img_icon);
        textview_three.setText(HomeActivity.mProductItemsList.get(2).getCategory().toUpperCase());
        Picasso.with(getActivity()).load((HomeActivity.mProductItemsList.get(2).getCategory_Icon())).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh)).into(img_icon_three);
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
                    Picasso.with(getActivity()).load((HomeActivity.mProductItemsList.get(0).getCategory_Icon())).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh)).into(img_icon);
                    viewPager.setCurrentItem(0);
                } else if (tab.getPosition() == 1) {
                    Picasso.with(getActivity()).load((HomeActivity.mProductItemsList.get(1).getCategory_Icon())).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh)).into(img_icon);
                    viewPager.setCurrentItem(1);
                } else {
                    Picasso.with(getActivity()).load((HomeActivity.mProductItemsList.get(2).getCategory_Icon())).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh)).into(img_icon);
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
                    Picasso.with(getActivity()).load((HomeActivity.mProductItemsList.get(0).getCategory_Icon_grey())).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh)).into(img_icon);
                } else if (tab.getPosition() == 1) {
                    Picasso.with(getActivity()).load((HomeActivity.mProductItemsList.get(1).getCategory_Icon_grey())).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh)).into(img_icon);
                } else {
                    Picasso.with(getActivity()).load((HomeActivity.mProductItemsList.get(2).getCategory_Icon_grey())).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh)).into(img_icon);
                }
                textview.setTextColor(getResources().getColor(R.color.Tab_Cat_name));
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

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
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

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
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

//    @Override
//    public void onRefresh() {
//        mParent.getProductsList();
//        if (swipeRefreshLayout.isRefreshing()) {
//
//            swipeRefreshLayout.setRefreshing(false);
//        }
//
//
//    }

}
