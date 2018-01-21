package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current_ implements Parcelable {

    public final static Parcelable.Creator<Current_> CREATOR = new Creator<Current_>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Current_ createFromParcel(Parcel in) {
            return new Current_(in);
        }

        public Current_[] newArray(int size) {
            return (new Current_[size]);
        }

    };
    @SerializedName("desktop")
    @Expose
    private String desktop;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    protected Current_(Parcel in) {
        this.desktop = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Current_() {
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
