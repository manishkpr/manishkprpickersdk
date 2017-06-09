package com.manishkprpickersdkit.adapter;

/**
 * Created by edge on 9/6/17.
 */


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manishkprpickersdkit.ChildFragments;
import com.manishkprpickersdkit.GalleryFragment;
import com.manishkprpickersdkit.R;
import com.manishkprpickersdkit.models.FoldersAndFiles;

import java.util.List;

public class DirectoriesListAdapter extends RecyclerView.Adapter<DirectoriesListAdapter.FolderFileViewHolder> {

    private List<FoldersAndFiles> folderList;
    Activity activity;



    public class FolderFileViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title,  tv_total_images;
        public ImageView imageView;
        public LinearLayout linearLayout;

        public FolderFileViewHolder(View view) {
            super(view);
            tv_title            = (TextView) view.findViewById(R.id.tv_title);
            tv_total_images     = (TextView) view.findViewById(R.id.tv_total_images);
            imageView           = (ImageView) view.findViewById(R.id.imageView);
            linearLayout        = (LinearLayout) view.findViewById(R.id.ll_main);
        }

    }


    public DirectoriesListAdapter(Activity activity,List<FoldersAndFiles> moviesList) {
        this.folderList = moviesList;
        this.activity = activity;
    }

    @Override
    public FolderFileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder_files, parent, false);

        return new FolderFileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FolderFileViewHolder holder, int position) {
        final FoldersAndFiles foldersAndFiles = folderList.get(position);
        holder.tv_title.setText(foldersAndFiles.getFolderName());
        holder.tv_total_images.setText((foldersAndFiles.getTotalImages() > 1 ? foldersAndFiles.getTotalImages()+" photos": foldersAndFiles.getTotalImages()+" photo"));

        Glide.with(activity)
                .load(foldersAndFiles.getUri().toString())
                .thumbnail(0.1f)
                //.fit()
                //   .override(holder.mThumbnail.getWidth(), holder.mThumbnail.getWidth())
                //  .override(holder.root.getWidth(), holder.root.getWidth())
                .centerCrop()
                .placeholder(R.drawable.place_holder_gallery)
                .error(R.drawable.no_image)

                .into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFragment.folderName = foldersAndFiles.getFolderName();
                ChildFragments.childFragments.addFragment(new GalleryFragment(),true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }
}
