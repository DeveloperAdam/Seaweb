package techease.com.seaweb.Activities.Models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SearchedBoatsDataModel {

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
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("owner_id")
    @Expose
    private String ownerId;
    @SerializedName("user_picture")
    @Expose
    private String userPicture;
    @SerializedName("price_day")
    @Expose
    private String priceDay;
    @SerializedName("files")
    @Expose
    private List<SearchBoatsFileModel> files = null;
    @SerializedName("rating")
    @Expose
    private String rating;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(String priceDay) {
        this.priceDay = priceDay;
    }

    public List<SearchBoatsFileModel> getFiles() {
        return files;
    }

    public void setFiles(List<SearchBoatsFileModel> files) {
        this.files = files;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
