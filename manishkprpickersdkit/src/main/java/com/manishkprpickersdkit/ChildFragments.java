package com.manishkprpickersdkit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;



/**
 * Created by edge on 9/6/17.
 */

public class ChildFragments extends Fragment {

    FragmentStackManager fragmentStackManager;
    FrameLayout frameLayout;

    public static ChildFragments childFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView  = inflater.inflate(R.layout.fragment_child, container, false);

        childFragments = this;

        frameLayout    =  (FrameLayout) rootView.findViewById(R.id.root_view);

        addFragment(new GalleryDirectoriesFragment(),false);


        return rootView;

    }

    public void addFragment(Fragment fragment,boolean backStack){

        if(fragmentStackManager==null) {
            fragmentStackManager = new FragmentStackManager(getActivity());
        }

        fragmentStackManager.addFragment2(fragment,R.id.root_view,backStack,false,getClass().getSimpleName().toString());
        //fragmentStackManager.addChildFragment(getChildFragmentManager(),R.id.root_view,fragment,backStack,getClass().getSimpleName().toString());

    }






}
