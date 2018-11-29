package techease.com.seaweb.Activities.Models.BoatDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoatFileModel {
    @SerializedName("file")
    @Expose
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
