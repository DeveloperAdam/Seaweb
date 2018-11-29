package techease.com.seaweb.Activities.Models.Trip;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import techease.com.seaweb.Activities.Models.Trip.AllTripsDataModel;


public class AllTripsResponseModel {

    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<AllTripsDataModel> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AllTripsDataModel> getData() {
        return data;
    }

    public void setData(List<AllTripsDataModel> data) {
        this.data = data;
    }

}
