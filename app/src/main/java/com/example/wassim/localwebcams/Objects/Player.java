
package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player implements Parcelable {

    public final static Parcelable.Creator<Player> CREATOR = new Creator<Player>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return (new Player[size]);
        }

    };
    @SerializedName("live")
    @Expose
    private Live live;
    @SerializedName("day")
    @Expose
    private Day day;
    @SerializedName("month")
    @Expose
    private Month month;
    @SerializedName("year")
    @Expose
    private Year year;
    @SerializedName("lifetime")
    @Expose
    private Lifetime lifetime;

    protected Player(Parcel in) {
        this.live = ((Live) in.readValue((Live.class.getClassLoader())));
        this.day = ((Day) in.readValue((Day.class.getClassLoader())));
        this.month = ((Month) in.readValue((Month.class.getClassLoader())));
        this.year = ((Year) in.readValue((Year.class.getClassLoader())));
        this.lifetime = ((Lifetime) in.readValue((Lifetime.class.getClassLoader())));
    }

    public Player() {
    }

    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Lifetime getLifetime() {
        return lifetime;
    }

    public void setLifetime(Lifetime lifetime) {
        this.lifetime = lifetime;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(live);
        dest.writeValue(day);
        dest.writeValue(month);
        dest.writeValue(year);
        dest.writeValue(lifetime);
    }

    public int describeContents() {
        return 0;
    }

}
