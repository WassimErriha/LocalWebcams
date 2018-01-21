package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Toenail implements Parcelable {

    public final static Parcelable.Creator<Toenail> CREATOR = new Creator<Toenail>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Toenail createFromParcel(Parcel in) {
            return new Toenail(in);
        }

        public Toenail[] newArray(int size) {
            return (new Toenail[size]);
        }

    };
    @SerializedName("width")
    @Expose
    private int width;
    @SerializedName("height")
    @Expose
    private int height;

    protected Toenail(Parcel in) {
        this.width = ((int) in.readValue((int.class.getClassLoader())));
        this.height = ((int) in.readValue((int.class.getClassLoader())));
    }

    public Toenail() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(width);
        dest.writeValue(height);
    }

    public int describeContents() {
        return 0;
    }

}
