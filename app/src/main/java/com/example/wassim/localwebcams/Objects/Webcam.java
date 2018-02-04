
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
    @SerializedName("player")
    @Expose
    private Player player;

    protected Webcam(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((Image) in.readValue((Image.class.getClassLoader())));
        this.location = ((Location) in.readValue((Location.class.getClassLoader())));
        this.player = ((Player) in.readValue((Player.class.getClassLoader())));
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(status);
        dest.writeValue(title);
        dest.writeValue(image);
        dest.writeValue(location);
        dest.writeValue(player);
    }

    public int describeContents() {
        return 0;
    }

}
