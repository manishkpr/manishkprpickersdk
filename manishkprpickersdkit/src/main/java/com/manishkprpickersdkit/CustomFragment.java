/*
 * Copyright (c) 2016. Ted Park. All Rights Reserved
 */

package com.manishkprpickersdkit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.manishkprpickersdkit.models.stockphotos.Image;
import com.manishkprpickersdkit.models.stockphotos.StockPhotos;
import com.manishkprpickersdkit.view.CustomSquareFrameLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Gil on 04/03/2014.
 */
public class CustomFragment extends Fragment {

    GridView galleryGridView;
    public  ImagePickerActivity mActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        if(ImagePickerActivity.getConfig().getStockPhotos()!=null) {
            rootView = inflater.inflate(R.layout.picker_fragment_gallery, container, false);

            galleryGridView = (GridView) rootView.findViewById(R.id.gallery_grid);
            mActivity = ((ImagePickerActivity) getActivity());
            parse();
        }else if(ImagePickerActivity.getConfig().getSinglePhoto()!=null){
            rootView = inflater.inflate(R.layout.picker_fragment_gallery, container, false);
            stockPhotos = new StockPhotos();
            List<Image> images = new ArrayList<>();
            images.add(new Image(0,ImagePickerActivity.getConfig().getSinglePhoto(),false));
            stockPhotos.setImages(images);
            mActivity = ((ImagePickerActivity) getActivity());
            Uri uri = Uri.parse(stockPhotos.getImages().get(0).getImage());
            if (mActivity.containsImage(uri)) {
                stockPhotos.getImages().get(0).setSelected(true);
            }else{
                stockPhotos.getImages().get(0).setSelected(false);
            }
            galleryGridView = (GridView) rootView.findViewById(R.id.gallery_grid);
            mActivity = ((ImagePickerActivity) getActivity());
            final ImageGalleryAdapter imageGalleryAdapter = new ImageGalleryAdapter(getActivity(),stockPhotos);
            galleryGridView.setAdapter(imageGalleryAdapter);
            galleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Uri uri = Uri.parse(stockPhotos.getImages().get(i).getImage());
                    if (!mActivity.containsImage(uri)) {
                        mActivity.addImage(uri);
                        Log.e("image","added");
                    } else {
                        mActivity.removeImage(uri);

                    }
                    stockPhotos.getImages().get(i).setSelected((stockPhotos.getImages().get(i).isSelected()  ? false : true));
                    imageGalleryAdapter.notifyDataSetChanged();

                }
            });
        }
        else{
            rootView = inflater.inflate(R.layout.fragment_custom, container, false);
        }
        return rootView;

    }
    StockPhotos stockPhotos;
    void parse(){
        if(ImagePickerActivity.getConfig().getStockPhotos()!=null) {
            stockPhotos = new Gson().fromJson(ImagePickerActivity.getConfig().getStockPhotos(), StockPhotos.class);
            for (int i = 0; i < stockPhotos.getImages().size(); i++) {
                stockPhotos.getImages().get(i).setImage(ImagePickerActivity.getConfig().getPhotoUrl()+stockPhotos.getImages().get(i).getImage());
                Uri uri = Uri.parse(stockPhotos.getImages().get(i).getImage());
                if (mActivity.containsImage(uri)) {
                    stockPhotos.getImages().get(i).setSelected(true);
                }else{
                    stockPhotos.getImages().get(i).setSelected(false);
                }
                Log.e(CustomFragment.class.getSimpleName(), stockPhotos.getImages().get(i).getImage().toString());

            }
            final ImageGalleryAdapter imageGalleryAdapter = new ImageGalleryAdapter(getActivity(),stockPhotos);
            galleryGridView.setAdapter(imageGalleryAdapter);
            galleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Uri uri = Uri.parse(stockPhotos.getImages().get(i).getImage());
                    if (!mActivity.containsImage(uri)) {
                        mActivity.addImage(uri);
                        Log.e("image","added");
                    } else {
                        mActivity.removeImage(uri);

                    }
                    stockPhotos.getImages().get(i).setSelected((stockPhotos.getImages().get(i).isSelected()  ? false : true));
                    imageGalleryAdapter.notifyDataSetChanged();

                }
            });
        }


    }



    class ViewHolder {
        LinearLayout root;

        ImageView mThumbnail;

        LinearLayout ll_selected_view,ll_camera;
        CustomSquareFrameLayout ll_image_view;



        public ViewHolder(View view) {
            root = (LinearLayout) view.findViewById(R.id.root);
            mThumbnail = (ImageView) view.findViewById(R.id.thumbnail_image);
            ll_selected_view = (LinearLayout) view.findViewById(R.id.ll_selected_view);
            ll_camera = (LinearLayout) view.findViewById(R.id.ll_camera);
            ll_image_view = (CustomSquareFrameLayout) view.findViewById(R.id.ll_image_view);

        }

    }


    public class ImageGalleryAdapter extends BaseAdapter {

        Context context;
        StockPhotos stockPhotos;

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return stockPhotos.getImages().size();
        }

        public ImageGalleryAdapter(Context context, StockPhotos stockPhotos) {
            this.context = context;
            this.stockPhotos = stockPhotos;
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



            if(stockPhotos.getImages().get(position).isSelected()){
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





                if(stockPhotos.getImages()!=null) {
                    String photo = stockPhotos.getImages().get(position).getImage();
                    Glide.with(context)
                            .load(photo)
                            .thumbnail(0.1f)
                            //.fit()
                            //   .override(holder.mThumbnail.getWidth(), holder.mThumbnail.getWidth())
                            //  .override(holder.root.getWidth(), holder.root.getWidth())
                            .centerCrop()
                            .placeholder(R.drawable.place_holder_gallery)
                            .error(R.drawable.no_image)

                            .into(holder.mThumbnail);
                }





            return convertView;
        }
    }


}