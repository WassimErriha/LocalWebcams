
package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Live implements Parcelable {

    public final static Parcelable.Creator<Live> CREATOR = new Creator<Live>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Live createFromParcel(Parcel in) {
            return new Live(in);
        }

        public Live[] newArray(int size) {
            return (new Live[size]);
        }

    };
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("embed")
    @Expose
    private String embed;

    protected Live(Parcel in) {
        this.available = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.embed = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Live() {
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(available);
        dest.writeValue(embed);
    }

    public int describeContents() {
        return 0;
    }

}
