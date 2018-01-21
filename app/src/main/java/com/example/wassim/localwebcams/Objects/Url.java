package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Url implements Parcelable {

    public final static Parcelable.Creator<Url> CREATOR = new Creator<Url>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Url createFromParcel(Parcel in) {
            return new Url(in);
        }

        public Url[] newArray(int size) {
            return (new Url[size]);
        }

    };
    @SerializedName("current")
    @Expose
    private Current_ current;
    @SerializedName("daylight")
    @Expose
    private Daylight_ daylight;
    @SerializedName("edit")
    @Expose
    private String edit;

    protected Url(Parcel in) {
        this.current = ((Current_) in.readValue((Current_.class.getClassLoader())));
        this.daylight = ((Daylight_) in.readValue((Daylight_.class.getClassLoader())));
        this.edit = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Url() {
    }

    public Current_ getCurrent() {
        return current;
    }

    public void setCurrent(Current_ current) {
        this.current = current;
    }

    public Daylight_ getDaylight() {
        return daylight;
    }

    public void setDaylight(Daylight_ daylight) {
        this.daylight = daylight;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(current);
        dest.writeValue(daylight);
        dest.writeValue(edit);
    }

    public int describeContents() {
        return 0;
    }

}
