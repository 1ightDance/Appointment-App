package com.example.lightdance.appointment.Model;

/**
 * Created by pope on 2017/10/31.
 */

public class MemberBean {

    private String memberUserBeanId;
    private int memberAvatar;
    private String memberNickname;

    public String getMemberUserBeanId() {
        return memberUserBeanId;
    }

    public void setMemberUserBeanId(String memberUserBeanId) {
        this.memberUserBeanId = memberUserBeanId;
    }

    public int getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(int memberAvatar) {
        this.memberAvatar = memberAvatar;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }
}
