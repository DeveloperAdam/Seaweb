package techease.com.seaweb.Activities.Models.BoatDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoatDetailChildModel {


    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
