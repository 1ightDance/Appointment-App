package com.example.lightdance.appointment.Model;

import cn.bmob.v3.BmobUser;

/**
 * Created by pope on 2017/11/5.
 */

public class AccountMsgBean extends BmobUser {

    private String userStudentNum;
    private String userPassword;
    private String userName;
    private String userNickName;
    private String userCollege;
    private int userSex;
    private String userDescription;

    public AccountMsgBean() {
    }

    public String getUserStudentNum() {
        return userStudentNum;
    }

    public void setUserStudentNum(String userStudentNum) {
        this.userStudentNum = userStudentNum;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserCollege() {
        return userCollege;
    }

    public void setUserCollege(String userCollege) {
        this.userCollege = userCollege;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public int getUserIconId() {
        return userIconId;
    }

    public void setUserIconId(int userIconId) {
        this.userIconId = userIconId;
    }

    private String userPhoneNumber;
    private int userIconId;

}
