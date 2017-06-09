package com.manishkprpickersdkit.models;

import android.net.Uri;

import java.util.Comparator;

/**
 * Created by edge on 9/6/17.
 */

public class FoldersAndFiles {

    String folderName;
    Uri uri;
    int totalImages;

    public FoldersAndFiles(String folderName, Uri uri, int totalImages) {
        this.folderName = folderName;
        this.uri = uri;
        this.totalImages = totalImages;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getTotalImages() {
        return totalImages;
    }

    public void setTotalImages(int totalImages) {
        this.totalImages = totalImages;
    }


}
