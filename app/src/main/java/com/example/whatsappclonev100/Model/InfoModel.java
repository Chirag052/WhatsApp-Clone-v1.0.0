package com.example.whatsappclonev100.Model;

public class InfoModel {
    String profilePic,ProfileName,recieverId,senderId,mobileNo,senderMobileNo,recieverNo;
    boolean inContact=true;

    public InfoModel(String profilePic, String profileName, String recieverId, String senderId) {
        this.profilePic = profilePic;
        ProfileName = profileName;
        this.recieverId = recieverId;
        this.senderId=senderId;
    }

    public InfoModel() {
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public void setProfileName(String profileName) {
        ProfileName = profileName;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSenderMobileNo() {
        return senderMobileNo;
    }

    public void setSenderMobileNo(String senderMobileNo) {
        this.senderMobileNo = senderMobileNo;
    }

    public String getRecieverNo() {
        return recieverNo;
    }

    public void setRecieverNo(String recieverNo) {
        this.recieverNo = recieverNo;
    }


    public boolean isInContact() {
        return inContact;
    }

    public void setInContact(boolean inContact) {
        this.inContact = inContact;
    }
}
