package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {

    public final static Parcelable.Creator<Image> CREATOR = new Creator<Image>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        public Image[] newArray(int size) {
            return (new Image[size]);
        }

    };
    @SerializedName("current")
    @Expose
    private Current current;
    @SerializedName("daylight")
    @Expose
    private Daylight daylight;
    @SerializedName("sizes")
    @Expose
    private Sizes sizes;
    @SerializedName("update")
    @Expose
    private int update;

    protected Image(Parcel in) {
        this.current = ((Current) in.readValue((Current.class.getClassLoader())));
        this.daylight = ((Daylight) in.readValue((Daylight.class.getClassLoader())));
        this.sizes = ((Sizes) in.readValue((Sizes.class.getClassLoader())));
        this.update = ((int) in.readValue((int.class.getClassLoader())));
    }

    public Image() {
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Daylight getDaylight() {
        return daylight;
    }

    public void setDaylight(Daylight daylight) {
        this.daylight = daylight;
    }

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(current);
        dest.writeValue(daylight);
        dest.writeValue(sizes);
        dest.writeValue(update);
    }

    public int describeContents() {
        return 0;
    }

}
