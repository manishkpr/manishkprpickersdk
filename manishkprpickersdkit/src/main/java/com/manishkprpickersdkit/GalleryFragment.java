/*
 * Copyright (c) 2016. Ted Park. All Rights Reserved
 */

package com.manishkprpickersdkit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.manishkprpickersdkit.view.CustomSquareFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Gil on 04/03/2014.
 */
public class GalleryFragment extends Fragment {

    public static ImageGalleryAdapter mGalleryAdapter;
    public  ImagePickerActivity mActivity;
    public static String folderName = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.picker_fragment_gallery, container, false);
        GridView galleryGridView = (GridView) rootView.findViewById(R.id.gallery_grid);
        mActivity = ((ImagePickerActivity) getActivity());




        List<Uri> images = getImagesFromGallary(getActivity());
        mGalleryAdapter = new ImageGalleryAdapter(getActivity(), images);

        galleryGridView.setAdapter(mGalleryAdapter);
        galleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(ImagePickerActivity.getConfig().isEnableFirstCoulmnCamera()) {

                    if (i != 0) {
                        selectionClick(i);
                    } else {
                        openCamera();
                    }
                }else{
                    selectionClick(i);
                }


            }
        });


        setHasOptionsMenu(true);

        return rootView;
    }

    void selectionClick(int i){
        Uri mUri = mGalleryAdapter.getItem(i);


        if (!mActivity.containsImage(mUri)) {
            mActivity.addImage(mUri);
        } else {
            mActivity.removeImage(mUri);
        }

        mGalleryAdapter.notifyDataSetChanged();
    }

    void openCamera(){
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, CameraActivity.INTENT_CAMERA);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();    //remove all items
        getActivity().getMenuInflater().inflate(R.menu.menu_confirm, menu);
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshGallery(getActivity());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Save");
    }

    public void refreshGallery(Context context) {

        List<Uri> images = getImagesFromGallary(context);
        //images.add(null);
        if (mGalleryAdapter == null) {

            mGalleryAdapter = new ImageGalleryAdapter(context, images);
        } else {

            mGalleryAdapter.clear();
            mGalleryAdapter.addAll(images);
            mGalleryAdapter.notifyDataSetChanged();

        }


    }


    public List<Uri> getImagesFromGallary(Context context) {

        List<Uri> images = new ArrayList<Uri>();
        if(ImagePickerActivity.getConfig().isEnableFirstCoulmnCamera()) {
            images.add(null);
        }
        Cursor imageCursor = null;
        try {

            final String[] columns = {

                    MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME

            };

            final String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";


            if(ImagePickerActivity.getConfig().isDirectoryMode()) {

                imageCursor = context.getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, MediaStore.Images.Media.DATA + " like ? ", new String[]{"%/"+folderName+"/%"}, orderBy);

            }else {

                imageCursor = context.getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
            }

            while (imageCursor.moveToNext()) {

                int columnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
                Log.e(getClass().getSimpleName()," "+columnIndex);

                columnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

                Log.e(getClass().getSimpleName()," "+imageCursor.getString(columnIndex));

                Uri uri = Uri.parse(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)));

                Log.e(getClass().getSimpleName().toString(),uri.getPath());

                if(uri!=null) {
                    images.add(uri);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageCursor != null && !imageCursor.isClosed()) {
                imageCursor.close();
            }
        }


        return images;

    }


    class ViewHolder {
        LinearLayout root;

        ImageView mThumbnail;

        LinearLayout ll_selected_view,ll_camera;
        CustomSquareFrameLayout ll_image_view;

        // This is like storing too much data in memory.
        // find a better way to handle this
        Uri uri;

        public ViewHolder(View view) {
            root = (LinearLayout) view.findViewById(R.id.root);
            mThumbnail = (ImageView) view.findViewById(R.id.thumbnail_image);
            ll_selected_view = (LinearLayout) view.findViewById(R.id.ll_selected_view);
            ll_camera = (LinearLayout) view.findViewById(R.id.ll_camera);
            ll_image_view = (CustomSquareFrameLayout) view.findViewById(R.id.ll_image_view);

        }

    }

    public class ImageGalleryAdapter extends ArrayAdapter<Uri> {

        Context context;


        public ImageGalleryAdapter(Context context, List<Uri> images) {
            super(context, 0, images);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.picker_grid_item_gallery_thumbnail, null);
                holder = new ViewHolder(convertView);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            final Uri mUri = getItem(position);

            boolean isSelected = mActivity.containsImage(mUri);

            if(isSelected){
                holder.ll_selected_view.setVisibility(View.VISIBLE);
            }else{
                holder.ll_selected_view.setVisibility(View.GONE);
            }

            /*if(position==0){
                holder.ll_camera.setVisibility(View.VISIBLE);
                holder.ll_image_view.setVisibility(View.GONE);
            }else{
                holder.ll_camera.setVisibility(View.GONE);
                holder.ll_image_view.setVisibility(View.VISIBLE);
            }*/

            /*if(holder.root instanceof FrameLayout){
                ((FrameLayout)holder.root).setForeground(isSelected ? ResourcesCompat.getDrawable(getResources(),R.drawable.btn_sky_checked_icon,null) : null);
            }*/

            if (holder.uri == null || !holder.uri.equals(mUri)) {

                if(mUri!=null) {
                    Glide.with(context)
                            .load(mUri.toString())
                            .thumbnail(0.1f)
                            //.fit()
                            //   .override(holder.mThumbnail.getWidth(), holder.mThumbnail.getWidth())
                            //  .override(holder.root.getWidth(), holder.root.getWidth())
                            .centerCrop()
                            .placeholder(R.drawable.place_holder_gallery)
                            .error(R.drawable.no_image)

                            .into(holder.mThumbnail);
                    holder.uri = mUri;
                }


                else{

                    if(position==0) {
                        if(ImagePickerActivity.getConfig().isEnableFirstCoulmnCamera()) {
                            Glide.with(context)
                                    .load("")
                                    .thumbnail(0.1f)
                                    //.fit()
                                    //   .override(holder.mThumbnail.getWidth(), holder.mThumbnail.getWidth())
                                    //  .override(holder.root.getWidth(), holder.root.getWidth())
                                    .centerCrop()
                                    .placeholder(new ColorDrawable(getActivity().getResources().getColor(R.color.custom_bg_grey)))
                                    .error(R.drawable.ic_take_photo
                                    )

                                    .into(holder.mThumbnail);
                        }

                    }else{

                    }



                    holder.uri = mUri;
                }


            }


            return convertView;
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        folderName = "";
    }
}