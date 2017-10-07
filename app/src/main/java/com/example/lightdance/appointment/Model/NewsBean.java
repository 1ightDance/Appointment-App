package com.example.lightdance.appointment.Model;

/**
 * Created by pope on 2017/10/7.
 */

public class NewsBean {

    private int imgId;
    private String newsTitle;
    private String newsContent;

    public int getImgId() {
        return imgId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public NewsBean(int imgId, String newsTitle, String newsContent) {
        this.imgId = imgId;
        this.newsTitle = newsTitle;
        this.newsContent = newsContent;
    }
}
