package com.example.lightdance.appointment.Model;

import org.litepal.crud.DataSupport;

/**
 * Created by LightDance on 2017/10/7.
 * 继承DataSupport类以使用数据库的操作方法
 */

public class BrowseMsgBean extends DataSupport{

    private String title;
    private String publishTime;
    private String beginTime;
    private String endTime;
    private String place;
    private String inviter;
    private int personNumber;
    private int inviterIconId;
    private int typeIconId;
    private int msgId;
    //msgId用作数据库主键


    public BrowseMsgBean(String title, String publishTime, String beginTime,
                         String endTime, String place, String inviter,
                         int personNumber, int inviterIconId, int typeIconId, int msgId) {
        this.title = title;
        this.publishTime = publishTime;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.place = place;
        this.inviter = inviter;
        this.personNumber = personNumber;
        this.inviterIconId = inviterIconId;
        this.typeIconId = typeIconId;
        this.msgId = msgId;
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

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    public void setInviterIconId(int inviterIconId) {
        this.inviterIconId = inviterIconId;
    }

    public void setTypeIconId(int typeIconId) {
        this.typeIconId = typeIconId;
    }

}
