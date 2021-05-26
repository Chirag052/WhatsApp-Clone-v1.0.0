package com.example.whatsappclonev100.Model;

public class StatusModel {
    private String name,time;
    private int profilePic;

    public StatusModel(String name, String time, int profilePic) {
        this.name = name;
        this.time = time;
        this.profilePic = profilePic;
    }

    public StatusModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }
}
