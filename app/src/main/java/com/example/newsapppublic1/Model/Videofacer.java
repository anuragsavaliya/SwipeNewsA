package com.example.newsapppublic1.Model;


public class Videofacer {
    private String vidName;
    private String vidPath;
    private String thumbPath;
    private String vidSize;
    private String vidUri;
    private Boolean selected = false;

    public String getVidName() {
        return vidName;
    }

    public void setVidName(String vidName) {
        this.vidName = vidName;
    }

    public String getVidPath() {
        return vidPath;
    }

    public void setVidPath(String vidPath) {
        this.vidPath = vidPath;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getVidSize() {
        return vidSize;
    }

    public void setVidSize(String vidSize) {
        this.vidSize = vidSize;
    }

    public String getVidUri() {
        return vidUri;
    }

    public void setVidUri(String vidUri) {
        this.vidUri = vidUri;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
