
package com.manishkprpickersdkit.models.stockphotos;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image")
    @Expose
    private String image;

    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static Creator<Image> getCREATOR() {
        return CREATOR;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Image(){

    }

    public Image(Integer id, String image, boolean selected) {
        this.id = id;
        this.image = image;
        this.selected = selected;
    }

    public final static Creator<Image> CREATOR = new Creator<Image>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Image createFromParcel(Parcel in) {
            Image instance = new Image();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.image = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Image[] newArray(int size) {
            return (new Image[size]);
        }

    }
    ;
    private final static long serialVersionUID = 1434086993584814649L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(image);
    }

    public int describeContents() {
        return  0;
    }

}
