package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Daylight_ implements Parcelable {

    public final static Parcelable.Creator<Daylight_> CREATOR = new Creator<Daylight_>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Daylight_ createFromParcel(Parcel in) {
            return new Daylight_(in);
        }

        public Daylight_[] newArray(int size) {
            return (new Daylight_[size]);
        }

    };
    @SerializedName("desktop")
    @Expose
    private String desktop;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    protected Daylight_(Parcel in) {
        this.desktop = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Daylight_() {
    }

    public String getDesktop() {
        return desktop;
    }

    public void setDesktop(String desktop) {
        this.desktop = desktop;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(desktop);
        dest.writeValue(mobile);
    }

    public int describeContents() {
        return 0;
    }

}
