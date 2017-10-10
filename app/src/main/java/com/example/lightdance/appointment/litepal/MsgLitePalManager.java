package com.example.lightdance.appointment.litepal;

import com.example.lightdance.appointment.Model.BrowseMsgBean;

import org.litepal.tablemanager.Connector;

/**
 * Created by LightDance on 2017/10/10.
 * 用于存储对BrowseMsg的数据库增删查改方法，管理对数据库的操作
 */



public class MsgLitePalManager {

    //几个常量字符串代表对数据库的指令，如果指令很长要用+连接，否则容易出错

    private void createMsgDatabase(){
        Connector.getDatabase();
    }

    private void savaMsg(BrowseMsgBean msg){
        msg.save();
    }

    //打算用limit，offset，find等方法查找，然后使用查询结果List<>中的迭代器进行视图绑定
    private void showByClass(){}

    //打算用limit，offset，find等方法查找，然后使用查询结果List<>中的迭代器进行视图绑定
    private void showByPublishTime(){}

    //打算用limit，offset，find等方法查找，然后使用查询结果List<>中的迭代器进行视图绑定
    private void showByStartTime(){}

    //打算用deleteAll并指定msg的id（主键，唯一）进行更新
    private void deleteMsg(){}

    //打算通过id找到对应msg然后用.save进行更新
    private void updateMsg(){}


}
