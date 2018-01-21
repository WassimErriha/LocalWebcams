package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current implements Parcelable {

    public final static Parcelable.Creator<Current> CREATOR = new Creator<Current>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Current createFromParcel(Parcel in) {
            return new Current(in);
        }

        public Current[] newArray(int size) {
            return (new Current[size]);
        }

    };
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("preview")
    @Expose
    private String preview;
    @SerializedName("toenail")
    @Expose
    private String toenail;

    protected Current(Parcel in) {
        this.icon = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
        this.preview = ((String) in.readValue((String.class.getClassLoader())));
        this.toenail = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Current() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getToenail() {
        return toenail;
    }

    public void setToenail(String toenail) {
        this.toenail = toenail;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(icon);
        dest.writeValue(thumbnail);
        dest.writeValue(preview);
        dest.writeValue(toenail);
    }

    public int describeContents() {
        return 0;
    }

}
