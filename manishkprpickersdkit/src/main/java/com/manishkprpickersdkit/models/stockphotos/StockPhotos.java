
package com.manishkprpickersdkit.models.stockphotos;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockPhotos implements Serializable, Parcelable
{

    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    public final static Creator<StockPhotos> CREATOR = new Creator<StockPhotos>() {


        @SuppressWarnings({
            "unchecked"
        })
        public StockPhotos createFromParcel(Parcel in) {
            StockPhotos instance = new StockPhotos();
            in.readList(instance.images, (Image.class.getClassLoader()));
            return instance;
        }

        public StockPhotos[] newArray(int size) {
            return (new StockPhotos[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8787845771310231321L;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(images);
    }

    public int describeContents() {
        return  0;
    }

}
