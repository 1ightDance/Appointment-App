package com.example.lightdance.appointment.Model;

/**
 * Created by pope on 2017/10/21.
 */

public class MessageBean {

    private int senderAvatarImgId;
    private String sendTime;
    private String senderName;
    private String messageContent;

    public MessageBean(int senderAvatarImgId, String sendTime, String senderName, String messageContent) {
        this.senderAvatarImgId = senderAvatarImgId;
        this.sendTime = sendTime;
        this.senderName = senderName;
        this.messageContent = messageContent;
    }

    public int getSenderAvatarImgId() {
        return senderAvatarImgId;
    }

    public void setSenderAvatarImgId(int senderAvatarImgId) {
        this.senderAvatarImgId = senderAvatarImgId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
