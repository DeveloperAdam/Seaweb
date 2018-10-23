package techease.com.seaweb.Activities.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BoatDetailsFileModel {

    @SerializedName("file0")
    @Expose
    private String file0;
    @SerializedName("file1")
    @Expose
    private String file1;

    public String getFile0() {
        return file0;
    }

    public void setFile0(String file0) {
        this.file0 = file0;
    }

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

}
