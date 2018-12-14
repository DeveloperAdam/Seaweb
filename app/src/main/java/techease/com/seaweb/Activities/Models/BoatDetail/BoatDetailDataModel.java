package techease.com.seaweb.Activities.Models.BoatDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BoatDetailDataModel {
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
    @SerializedName("owner_id")
    @Expose
    private String ownerId;
    @SerializedName("user_picture")
    @Expose
    private String userPicture;
    @SerializedName("fullday_price")
    @Expose
    private String fulldayPrice;
    @SerializedName("is_favorite")
    @Expose
    private String isFavorite;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("files")
    @Expose
    private List<BoatFileModel> files = null;
    @SerializedName("facilities")
    @Expose
    private List<BoatFacilityParentModel> facilities = null;
    @SerializedName("price_list")
    @Expose
    private List<BoatDetailPriceListModel> priceList = null;

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

    public String getFulldayPrice() {
        return fulldayPrice;
    }

    public void setFulldayPrice(String fulldayPrice) {
        this.fulldayPrice = fulldayPrice;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<BoatFileModel> getFiles() {
        return files;
    }

    public void setFiles(List<BoatFileModel> files) {
        this.files = files;
    }

    public List<BoatFacilityParentModel> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<BoatFacilityParentModel> facilities) {
        this.facilities = facilities;
    }

    public List<BoatDetailPriceListModel> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<BoatDetailPriceListModel> priceList) {
        this.priceList = priceList;
    }
}
