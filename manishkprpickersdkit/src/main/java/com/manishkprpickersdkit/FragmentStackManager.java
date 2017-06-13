package com.manishkprpickersdkit;

/**
 * Created by edge on 4/4/17.
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Map;


public class FragmentStackManager {

    AppCompatActivity activity;
    FragmentTransaction ft,ft2;

    public static FragmentStackManager fragmentStackManager = null;

    public static synchronized FragmentStackManager getInstance(AppCompatActivity activity){
        if(fragmentStackManager==null){
            fragmentStackManager = new FragmentStackManager(activity);
        }
        return fragmentStackManager;
    }

    public FragmentStackManager(AppCompatActivity activity){
        this.activity=activity;
    }

    public FragmentStackManager(Activity activity){
        this.activity=(AppCompatActivity) activity;
    }

    public void addFragment(Fragment fragment, int id, boolean addToBackStack, String backStackTag, int transition, boolean want_animation) {

        ft = activity.getSupportFragmentManager().beginTransaction();

        if(want_animation){
            //ft.setCustomAnimations( R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right);
        }

        ft.replace(id, fragment);
        ft.setTransition(transition);
        if (addToBackStack)
            ft.addToBackStack(backStackTag);
        ft.commit();

    }



    public void addChildFragment(FragmentManager manager, int id, Fragment fragment, boolean addToBackStack, String backStackTag){
        FragmentManager childFragMan = manager;
        ft2 = childFragMan.beginTransaction();
        ft2.replace(id, fragment);
        if (addToBackStack)
            ft2.addToBackStack(backStackTag);
        ft2.commit();
    }

    public void addAnotherActivityFragment(FragmentManager manager, int id, Fragment fragment, boolean addToBackStack, String backStackTag){
        FragmentManager childFragMan = manager;
        ft2 = childFragMan.beginTransaction();
        ft2.replace(id, fragment);
        if (addToBackStack)
            ft2.addToBackStack(backStackTag);
        ft2.commit();
    }

    public void addFragment2(Fragment fragment, int id, boolean addToBackStack, boolean want_animation, String backStackTag) {

        ft = activity.getSupportFragmentManager().beginTransaction();

        if(want_animation){
            //	ft.setCustomAnimations( R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right);
        }

        ft.replace(id, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        if (addToBackStack)
            ft.addToBackStack(backStackTag);
        ft.commit();

    }

    public static void popAll(FragmentManager fragmentManager){
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public  void popAll(){
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static Bundle putBundleSerial(Serializable object,String name){
        Bundle bundle = new Bundle();
        bundle.putSerializable(name, object);
        return bundle;
    }

    public static Bundle putBundleParcel(Parcelable object, String name){
        Bundle bundle = new Bundle();
        bundle.putParcelable(name, object);
        return bundle;
    }

    public static Bundle putBundleListParcel(Map<String,Parcelable > hashMap){

        Bundle bundle = new Bundle();

        for (Map.Entry<String, Parcelable> entry : hashMap.entrySet()) {
            String key       =  entry.getKey();
            Parcelable value =  entry.getValue();
            bundle.putParcelable(key, value);
        }

        return bundle;
    }

}
