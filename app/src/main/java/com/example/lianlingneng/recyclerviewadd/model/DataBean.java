package com.example.lianlingneng.recyclerviewadd.model;


public class DataBean {

    public static final int PNG = 0x122;
    public static final int MP4 = 0x123;

    private int type;

    private String image;

    private String video;

    public DataBean(int type, String image, String video) {
        this.type = type;
        this.image = image;
        this.video = video;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
