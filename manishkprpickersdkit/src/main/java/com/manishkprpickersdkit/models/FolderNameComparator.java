package com.manishkprpickersdkit.models;

import java.util.Comparator;

/**
 * Created by edge on 9/6/17.
 */

public  class FolderNameComparator implements Comparator<FoldersAndFiles> {
    @Override
    public int compare(FoldersAndFiles a, FoldersAndFiles b) {
        return a.getFolderName().compareToIgnoreCase(b.getFolderName());
    }
}