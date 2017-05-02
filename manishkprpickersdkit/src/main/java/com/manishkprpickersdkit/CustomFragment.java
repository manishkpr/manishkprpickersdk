/*
 * Copyright (c) 2016. Ted Park. All Rights Reserved
 */

package com.manishkprpickersdkit;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.manishkprpickersdkit.view.CustomSquareFrameLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Gil on 04/03/2014.
 */
public class CustomFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_custom, container, false);

        return rootView;
    }


}