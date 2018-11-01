package techease.com.seaweb.Activities.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.List;


public class BoatDetailsDataModel {
    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("people")
    @Expose
    private String people;
    @SerializedName("skipper")
    @Expose
    private String skipper;
    @SerializedName("births")
    @Expose
    private String births;
    @SerializedName("cabinats")
    @Expose
    private String cabinats;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("user_picture")
    @Expose
    private String userPicture;
    @SerializedName("whole_price")
    @Expose
    private String wholePrice;
    @SerializedName("adult_price")
    @Expose
    private String adultPrice;
    @SerializedName("child_price")
    @Expose
    private String childPrice;
    @SerializedName("is_favorite")
    @Expose
    private String isFavorite;
    @SerializedName("files")
    @Expose
    private List<File> files = null;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getSkipper() {
        return skipper;
    }

    public void setSkipper(String skipper) {
        this.skipper = skipper;
    }

    public String getBirths() {
        return births;
    }

    public void setBirths(String births) {
        this.births = births;
    }

    public String getCabinats() {
        return cabinats;
    }

    public void setCabinats(String cabinats) {
        this.cabinats = cabinats;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getWholePrice() {
        return wholePrice;
    }

    public void setWholePrice(String wholePrice) {
        this.wholePrice = wholePrice;
    }

    public String getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(String adultPrice) {
        this.adultPrice = adultPrice;
    }

    public String getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(String childPrice) {
        this.childPrice = childPrice;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

}

