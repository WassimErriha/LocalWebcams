package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result implements Parcelable {

    public final static Parcelable.Creator<Result> CREATOR = new Creator<Result>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        public Result[] newArray(int size) {
            return (new Result[size]);
        }

    };
    @SerializedName("offset")
    @Expose
    private int offset;
    @SerializedName("limit")
    @Expose
    private int limit;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("webcams")
    @Expose
    private List<Webcam> webcams = new ArrayList<Webcam>();

    protected Result(Parcel in) {
        this.offset = ((int) in.readValue((int.class.getClassLoader())));
        this.limit = ((int) in.readValue((int.class.getClassLoader())));
        this.total = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.webcams, (Webcam.class.getClassLoader()));
    }

    public Result() {
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Webcam> getWebcams() {
        return webcams;
    }

    public void setWebcams(List<Webcam> webcams) {
        this.webcams = webcams;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(offset);
        dest.writeValue(limit);
        dest.writeValue(total);
        dest.writeList(webcams);
    }

    public int describeContents() {
        return 0;
    }

}
