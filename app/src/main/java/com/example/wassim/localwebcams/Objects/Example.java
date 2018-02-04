
package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example implements Parcelable {

    public final static Parcelable.Creator<Example> CREATOR = new Creator<Example>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Example createFromParcel(Parcel in) {
            return new Example(in);
        }

        public Example[] newArray(int size) {
            return (new Example[size]);
        }

    };
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private Result result;

    protected Example(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.result = ((Result) in.readValue((Result.class.getClassLoader())));
    }

    public Example() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(result);
    }

    public int describeContents() {
        return 0;
    }

}
