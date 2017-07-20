/*
 * Copyright (c) 2016. Ted Park. All Rights Reserved
 */

package com.manishkprpickersdkit;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manishkprpickersdkit.adapter.DirectoriesListAdapter;
import com.manishkprpickersdkit.models.FolderNameComparator;
import com.manishkprpickersdkit.models.FoldersAndFiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by Gil on 04/03/2014.
 */
public class GalleryDirectoriesFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton fab_camera;

    List<FoldersAndFiles> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gallery_directories, container, false);
        recyclerView  = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        fab_camera    = (FloatingActionButton) rootView.findViewById(R.id.fab_camera);


        data = getDirectories();
        Collections.sort(data,new FolderNameComparator());


        initList();
        setUpClick();
        return rootView;

    }

    void setUpClick(){
        fab_camera.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }

    void openCamera(){
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, CameraActivity.INTENT_CAMERA);

    }

    void initList(){
        DirectoriesListAdapter directoriesListAdapter = new DirectoriesListAdapter(getActivity(),data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(directoriesListAdapter);
    }




    List<FoldersAndFiles> getDirectories(){

        List<FoldersAndFiles> data = new ArrayList<>();
        //images.add(null);
        Cursor imageCursor = null;
        try {

            final String[] columns = {

                    MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME

            };

            final String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";



            //imageCursor = context.getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%/Camera/%"}, orderBy);

            imageCursor = getActivity().getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);

            while (imageCursor.moveToNext()) {



                Uri uri = Uri.parse(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)));

                int columnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);

                Log.e(getClass().getSimpleName()," "+columnIndex);

                columnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                String dirName = imageCursor.getString(columnIndex);

                FoldersAndFiles foldersAndFiles = new FoldersAndFiles(dirName,uri,1);

                if(data.size()==0){
                    data.add(foldersAndFiles);
                }else{

                    if(!isDirectoryExist(dirName,data)){
                        data.add(foldersAndFiles);
                    }

                }



                Log.e(getClass().getSimpleName()," "+imageCursor.getString(columnIndex));



            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageCursor != null && !imageCursor.isClosed()) {
                imageCursor.close();
            }
        }
        return data;

    }

    boolean isDirectoryExist(String dirName,List<FoldersAndFiles> data){

        for(int i=0;i<data.size();i++){

            if(dirName.equalsIgnoreCase(data.get(i).getFolderName())){
                int count = data.get(i).getTotalImages();
                count = count+1;
                data.get(i).setTotalImages(count);
                return true;
            }
        }

        return false;
    }


}