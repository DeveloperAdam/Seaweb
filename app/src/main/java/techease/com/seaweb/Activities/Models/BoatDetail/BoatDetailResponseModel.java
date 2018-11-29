package techease.com.seaweb.Activities.Models.BoatDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoatDetailResponseModel {

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
    private BoatDetailDataModel boatDetailDataModel;

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

    public BoatDetailDataModel getBoatDetailDataModel() {
        return boatDetailDataModel;
    }

    public void setBoatDetailDataModel(BoatDetailDataModel boatDetailDataModel) {
        this.boatDetailDataModel = boatDetailDataModel;
    }
}
