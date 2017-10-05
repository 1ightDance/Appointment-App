package com.example.lightdance.appointment;

/**
 * Created by LightDance on 2017/10/5.
 */

public class MessageAppointment {

    //暂未考虑限制条件
    private String title;
    private String publishTime;
    private String beginTime;
    private String endTime;
    private String place;
    private String inviter;
    private String type;
    private int personNumber;
    private int inviterIconId;

    //构造方法
    public MessageAppointment(String title, String publishTime, String beginTime,
                              String endTime, String place, String inviter, String type,
                              int personNumber, int inviterIconId) {
        this.title = title;
        this.publishTime = publishTime;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.place = place;
        this.inviter = inviter;
        this.type = type;
        this.personNumber = personNumber;
        this.inviterIconId = inviterIconId;
    }

    //各字段get方法
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

    public String getType() {
        return type;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public int getInviterIconId() {
        return inviterIconId;
    }
}
