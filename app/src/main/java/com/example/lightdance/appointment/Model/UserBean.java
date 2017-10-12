package com.example.lightdance.appointment.Model;

import org.litepal.crud.DataSupport;

/**
 * Created by LightDance on 2017/10/11.
 * 用于注册时存放学生信息，以便于通过该类把学生信息存入数据库,
 * 之后会通过调用学校数据库实现，目前先用注册方式进行存储
 */

public class UserBean extends DataSupport {

    private static final int MAN=1;
    private static final int WOMAN=0;

    private String userStudentNum;
    private String userPassword;
    private String userName;
    private String userNickName;
    private int userSex;
    private String userDescription;
    private String userPhoneNumber;
    private int userIconId;

    public static int getMAN() {
        return MAN;
    }

    public static int getWOMAN() {
        return WOMAN;
    }

    public String getUserStudentNum() {
        return userStudentNum;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public int getUserSex() {
        return userSex;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public int getUserIconId() {
        return userIconId;
    }

    public void setUserStudentNum(String userStudentNum) {
        this.userStudentNum = userStudentNum;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setUserIconId(int userIconId) {
        this.userIconId = userIconId;
    }
}
