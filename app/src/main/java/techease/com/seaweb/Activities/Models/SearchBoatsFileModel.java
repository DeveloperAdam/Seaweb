package techease.com.seaweb.Activities.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class SearchBoatsFileModel {


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
