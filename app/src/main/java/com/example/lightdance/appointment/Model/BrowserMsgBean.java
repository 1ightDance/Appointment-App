package com.example.lightdance.appointment.Model;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * @author pope
 * 继承BmobObject类以使用云端数据库的操作方法，
 * BrowseMsgBean已被弃用，但还没删除掉
 * int建议用Integer，因为大量用户并发操作，用普通的更新方法操作的话，会存在数据不一致的情况，
 * Bmob提供了原子计数器来保证原子性的修改某一数值字段的值，但只支持Integer型而不是int
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
    private int personNumberHave;
    private String title;
    private int typeCode;
    private List<String> members;

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public int getPersonNumberHave() {
        return personNumberHave;
    }

    public void setPersonNumberHave(int personNumHave) {
        this.personNumberHave = personNumHave;
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

    public void setPersonNumberNeed(String personNumberNeed) {
        this.personNumberNeed = personNumberNeed;

    }
}
