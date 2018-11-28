package techease.com.seaweb.Activities.Models;

public class BoatOnLocationModel {

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(String fullPrice) {
        this.fullPrice = fullPrice;
    }

    String userImg;
    String file;
    String title;
    String location;
    String fullPrice;

    public String getIs_fvrt() {
        return is_fvrt;
    }

    public void setIs_fvrt(String is_fvrt) {
        this.is_fvrt = is_fvrt;
    }

    String is_fvrt;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    String pid;
}
