/*
 * Copyright (c) 2016. Ted Park. All Rights Reserved
 */

package com.manishkprpickersdkit;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;


public class PagerAdapter_Picker extends FragmentPagerAdapter {


    String[] tab_titles;
    TabLayout tabLayout;

    public PagerAdapter_Picker(Context context, FragmentManager fm,TabLayout tabLayout) {
        super(fm);
        this.tabLayout = tabLayout;
        tab_titles = (ImagePickerActivity.getConfig().getTabs()==null ? context.getResources().getStringArray(R.array.tab_titles) : ImagePickerActivity.getConfig().getTabs());

        if(tab_titles.length==1){
            tabLayout.setVisibility(View.GONE);
        }else{
            tabLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_titles[position];
    }

    @Override
    public int getCount() {
        return tab_titles.length;
    }

    @Override
    public Fragment getItem(int position) {


        if(ImagePickerActivity.getConfig().getTabs()==null) {

            switch (position) {
                case 0:
                    CwacCameraFragment profileInfoFragment = new CwacCameraFragment();
                    CwacCameraFragment.setConfig(ImagePickerActivity.getConfig());
                    return profileInfoFragment;


                case 1:
                    return new GalleryFragment();


                default:
                    return null;
            }
        }else{

            if(ImagePickerActivity.getConfig().getTabs().length ==2 ) {
                switch (position) {


                    case 0:
                        return new GalleryFragment();


                    default:
                        return new CustomFragment();
                }
            }

        }

        if(ImagePickerActivity.getConfig().isDirectoryMode()) {
            return new ChildFragments();
        }

        return new GalleryFragment();


    }


}
