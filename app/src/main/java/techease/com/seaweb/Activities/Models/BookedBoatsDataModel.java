package techease.com.seaweb.Activities.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BookedBoatsDataModel {

    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("user_picture")
    @Expose
    private String userPicture;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("boat_image")
    @Expose
    private String boatImage;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBoatImage() {
        return boatImage;
    }

    public void setBoatImage(String boatImage) {
        this.boatImage = boatImage;
    }
}
