package techease.com.seaweb.Activities.Models.BoatDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Facility {

    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
}
