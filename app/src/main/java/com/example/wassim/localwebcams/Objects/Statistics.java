package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistics implements Parcelable {

    public final static Parcelable.Creator<Statistics> CREATOR = new Creator<Statistics>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Statistics createFromParcel(Parcel in) {
            return new Statistics(in);
        }

        public Statistics[] newArray(int size) {
            return (new Statistics[size]);
        }

    };
    @SerializedName("views")
    @Expose
    private int views;

    protected Statistics(Parcel in) {
        this.views = ((int) in.readValue((int.class.getClassLoader())));
    }

    public Statistics() {
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(views);
    }

    public int describeContents() {
        return 0;
    }

}
