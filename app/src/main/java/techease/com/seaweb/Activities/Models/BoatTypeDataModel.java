package techease.com.seaweb.Activities.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BoatTypeDataModel {

    @SerializedName("boat_type")
    @Expose
    private String boatType;

    public String getBoatType() {
        return boatType;
    }

    public void setBoatType(String boatType) {
        this.boatType = boatType;
    }

}
