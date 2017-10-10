package com.example.lightdance.appointment.litepal;

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



}
