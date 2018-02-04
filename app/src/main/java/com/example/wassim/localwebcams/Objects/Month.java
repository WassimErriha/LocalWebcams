
package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Month implements Parcelable {

    public final static Parcelable.Creator<Month> CREATOR = new Creator<Month>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Month createFromParcel(Parcel in) {
            return new Month(in);
        }

        public Month[] newArray(int size) {
            return (new Month[size]);
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

    protected Month(Parcel in) {
        this.available = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.embed = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Month() {
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
