package com.example.lightdance.appointment.Model;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by pope on 2017/12/7.
 * @author pope
 */

public class HistoryBean extends BmobObject {

    private String userObjectId;
    private String userName;
    private List<String> noComment;
    private List<String> haveComment;
    private List<String> keepAppointment;
    private List<String> breakAppointment;
    private List<String> ongoingAppointment;
    private List<String> finishedAppointment;
    private List<String> organizeAppointment;
    private List<String> joinedAppointment;

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getNoComment() {
        return noComment;
    }

    public void setNoComment(List<String> noComment) {
        this.noComment = noComment;
    }

    public List<String> getHaveComment() {
        return haveComment;
    }

    public void setHaveComment(List<String> haveComment) {
        this.haveComment = haveComment;
    }

    public List<String> getKeepAppointment() {
        return keepAppointment;
    }

    public void setKeepAppointment(List<String> keepAppointment) {
        this.keepAppointment = keepAppointment;
    }

    public List<String> getBreakAppointment() {
        return breakAppointment;
    }

    public void setBreakAppointment(List<String> breakAppointment) {
        this.breakAppointment = breakAppointment;
    }

    public List<String> getOngoingAppointment() {
        return ongoingAppointment;
    }

    public void setOngoingAppointment(List<String> ongoingAppointment) {
        this.ongoingAppointment = ongoingAppointment;
    }

    public List<String> getFinishedAppointment() {
        return finishedAppointment;
    }

    public void setFinishedAppointment(List<String> finishedAppointment) {
        this.finishedAppointment = finishedAppointment;
    }

    public List<String> getOrganizeAppointment() {
        return organizeAppointment;
    }

    public void setOrganizeAppointment(List<String> organizeAppointment) {
        this.organizeAppointment = organizeAppointment;
    }

    public List<String> getJoinedAppointment() {
        return joinedAppointment;
    }

    public void setJoinedAppointment(List<String> joinedAppointment) {
        this.joinedAppointment = joinedAppointment;
    }

}
