package com.example.wassim.localwebcams.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Webcam implements Parcelable {

    public final static Parcelable.Creator<Webcam> CREATOR = new Creator<Webcam>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Webcam createFromParcel(Parcel in) {
            return new Webcam(in);
        }

        public Webcam[] newArray(int size) {
            return (new Webcam[size]);
        }

    };
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("statistics")
    @Expose
    private Statistics statistics;
    @SerializedName("url")
    @Expose
    private Url url;
    @SerializedName("user")
    @Expose
    private User user;

    protected Webcam(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((Image) in.readValue((Image.class.getClassLoader())));
        this.location = ((Location) in.readValue((Location.class.getClassLoader())));
        this.statistics = ((Statistics) in.readValue((Statistics.class.getClassLoader())));
        this.url = ((Url) in.readValue((Url.class.getClassLoader())));
        this.user = ((User) in.readValue((User.class.getClassLoader())));
    }

    public Webcam() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(status);
        dest.writeValue(title);
        dest.writeValue(image);
        dest.writeValue(location);
        dest.writeValue(statistics);
        dest.writeValue(url);
        dest.writeValue(user);
    }

    public int describeContents() {
        return 0;
    }

}
