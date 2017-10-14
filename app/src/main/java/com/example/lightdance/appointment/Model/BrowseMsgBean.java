package com.example.lightdance.appointment.Model;

import org.litepal.crud.DataSupport;

/**
 * Created by LightDance on 2017/10/7.
 * 继承DataSupport类以使用数据库的操作方法
 */

public class BrowseMsgBean extends DataSupport{

    private String title;
    private String publishTime;
    private String startTime;
    private String endTime;
    private String place;
    private String content;
    private String contactWay;
    private String inviter;
    private String personNumber;
    private int inviterIconId;
    private int typeIconId;
    //msgId用作数据库主键
    private int msgId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public int getInviterIconId() {
        return inviterIconId;
    }

    public void setInviterIconId(int inviterIconId) {
        this.inviterIconId = inviterIconId;
    }

    public int getTypeIconId() {
        return typeIconId;
    }

    public void setTypeIconId(int typeIconId) {
        this.typeIconId = typeIconId;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }
}
