package com.example.lightdance.appointment.Model;

/**
 * Created by LightDance on 2017/10/7.
 */

public class BrowseMsgBean {

    private String title;
    private String publishTime;
    private String beginTime;
    private String endTime;
    private String place;
    private String inviter;
    private int personNumber;
    private int inviterIconId;
    private int typeIconId;

    public BrowseMsgBean(String title, String publishTime, String beginTime,
                         String endTime, String place, String inviter, int personNumber,
                         int inviterIconId, int typeIconId) {
        this.title = title;
        this.publishTime = publishTime;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.place = place;
        this.inviter = inviter;
        this.personNumber = personNumber;
        this.inviterIconId = inviterIconId;
        this.typeIconId = typeIconId;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getPlace() {
        return place;
    }

    public String getInviter() {
        return inviter;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public int getInviterIconId() {
        return inviterIconId;
    }

    public int getTypeIconId() {
        return typeIconId;
    }
}
