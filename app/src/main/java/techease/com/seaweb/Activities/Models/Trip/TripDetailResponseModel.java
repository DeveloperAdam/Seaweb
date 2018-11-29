package techease.com.seaweb.Activities.Models.Trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TripDetailResponseModel {

    @SerializedName("success")
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
    private TripDetailsDataModel data;

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

    public TripDetailsDataModel getData() {
        return data;
    }

    public void setData(TripDetailsDataModel data) {
        this.data = data;
    }


}
