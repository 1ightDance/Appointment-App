package com.example.lightdance.appointment.Model;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * @author pope
 */

public class JoinedHistoryBean extends BmobObject {

    private String userObjectId;
    private List<String> browserIdList;
    private List<String> noComment;

    public List<String> getNoComment() {
        return noComment;
    }

    public void setNoComment(List<String> noComment) {
        this.noComment = noComment;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public List<String> getBrowserIdList() {
        return browserIdList;
    }

    public void setBrowserIdList(List<String> browserIdList) {
        this.browserIdList = browserIdList;
    }
}
