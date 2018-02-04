
package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Year implements Parcelable {

    public final static Parcelable.Creator<Year> CREATOR = new Creator<Year>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Year createFromParcel(Parcel in) {
            return new Year(in);
        }

        public Year[] newArray(int size) {
            return (new Year[size]);
        }

    };
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("embed")
    @Expose
    private String embed;

    protected Year(Parcel in) {
        this.available = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.embed = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Year() {
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(available);
        dest.writeValue(link);
        dest.writeValue(embed);
    }

    public int describeContents() {
        return 0;
    }

}
