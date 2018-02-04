
package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Icon implements Parcelable {

    public final static Parcelable.Creator<Icon> CREATOR = new Creator<Icon>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Icon createFromParcel(Parcel in) {
            return new Icon(in);
        }

        public Icon[] newArray(int size) {
            return (new Icon[size]);
        }

    };
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;

    protected Icon(Parcel in) {
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Icon() {
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
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
