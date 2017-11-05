package com.example.lightdance.appointment.Model;

import cn.bmob.v3.BmobObject;

/**
 * Created by pope on 2017/11/5.
 */

public class BrowserMsgBean extends BmobObject {

    private String title;
    private String publishTime;
    private String startTime;
    private String endTime;
    private String place;
    private String content;
    private String contactWay;
    private String inviter;
    private String personNumberNeed;
    private String personNumberHave;
    private String participantsId;

    public int getParticipantsIconId() {
        return participantsIconId;
    }

    public void setParticipantsIconId(int participantsIconId) {
        this.participantsIconId = participantsIconId;
    }

    private int participantsIconId;
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

    public String getPersonNumberNeed() {
        return personNumberNeed;
    }

    public String getPersonNumberHave() {
        return personNumberHave;
    }

    public String getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(String participantsId) {
        this.participantsId = participantsId;
    }

    public void setPersonNumberHave(String personNumberHave) {
        this.personNumberHave = personNumberHave;
    }

    public void setPersonNumberNeed(String personNumberNeed) {
        this.personNumberNeed = personNumberNeed;

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
