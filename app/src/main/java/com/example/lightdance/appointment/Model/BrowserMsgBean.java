package com.example.lightdance.appointment.Model;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by pope on 2017/11/5.
 */

public class BrowserMsgBean extends BmobObject {

    private BmobDate publishTime;
    private String startTime;
    private String endTime;
    private String place;
    private String content;
    private String contactWay;
    private String inviter;
    private String personNumberNeed;
    private int personNumNeed;
    private int personNumberHave;
    private String participantsId;
    private String title;
    private int participantsIconId;
    private int inviterIconId;
    private int typeIconId;
    private int typeCode;
    private List<MemberBean> members;
    private String[] participantsStudentNum;

    public List<MemberBean> getMembers() {
        return members;
    }

    public void setMembers(List<MemberBean> members) {
        this.members = members;
    }

    public int getPersonNumNeed() {
        return personNumNeed;
    }

    public void setPersonNumNeed(int personNumNeed) {
        this.personNumNeed = personNumNeed;
    }

    public int getPersonNumberHave() {
        return personNumberHave;
    }

    public void setPersonNumberHave(int personNumHave) {
        this.personNumberHave = personNumHave;
    }

    public String[] getParticipantsStudentNum() {
        return participantsStudentNum;
    }

    public void setParticipantsStudentNum(String[] participantsStudentNum) {
        this.participantsStudentNum = participantsStudentNum;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
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

    public BmobDate getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(BmobDate publishTime) {
        this.publishTime = publishTime;
    }

    public int getParticipantsIconId() {
        return participantsIconId;
    }

    public void setParticipantsIconId(int participantsIconId) {
        this.participantsIconId = participantsIconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(String participantsId) {
        this.participantsId = participantsId;
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
}
