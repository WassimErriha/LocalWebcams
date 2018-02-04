
package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sizes implements Parcelable {

    public final static Parcelable.Creator<Sizes> CREATOR = new Creator<Sizes>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Sizes createFromParcel(Parcel in) {
            return new Sizes(in);
        }

        public Sizes[] newArray(int size) {
            return (new Sizes[size]);
        }

    };
    @SerializedName("icon")
    @Expose
    private Icon icon;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("preview")
    @Expose
    private Preview preview;
    @SerializedName("toenail")
    @Expose
    private Toenail toenail;

    protected Sizes(Parcel in) {
        this.icon = ((Icon) in.readValue((Icon.class.getClassLoader())));
        this.thumbnail = ((Thumbnail) in.readValue((Thumbnail.class.getClassLoader())));
        this.preview = ((Preview) in.readValue((Preview.class.getClassLoader())));
        this.toenail = ((Toenail) in.readValue((Toenail.class.getClassLoader())));
    }

    public Sizes() {
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public Toenail getToenail() {
        return toenail;
    }

    public void setToenail(Toenail toenail) {
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
