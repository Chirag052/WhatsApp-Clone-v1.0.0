package com.example.whatsappclonev100.Model;

public class PhoneUsers {
    String  userName,userMobileNo, userId, lastMessage,status,profilepic;


    public PhoneUsers(String profilepic, String userName, String userMobileNo, String userId, String lastMessage, String status) {
        this.profilepic = profilepic;
        this.userName = userName;
        this.userMobileNo = userMobileNo;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.status = status;
    }

    public PhoneUsers() {
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
