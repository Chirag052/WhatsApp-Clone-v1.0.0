package com.example.whatsappclonev100.Model;

public class CallsModel {

    private String name,incoming,callfrom,videocall;
    private int profilepic;

    public CallsModel(String name, int profilepic, String incoming,String callfrom,String videocall) {
        this.name = name;
        this.profilepic = profilepic;
        this.incoming = incoming;
        this.callfrom=callfrom;
        this.videocall=videocall;
    }

    public CallsModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(int profilepic) {
        this.profilepic = profilepic;
    }

    public String getIncoming() {
        return incoming;
    }

    public void setIncoming(String incoming) {
        this.incoming = incoming;
    }

    public String getCallfrom() {
        return callfrom;
    }

    public void setCallfrom(String callfrom) {
        this.callfrom = callfrom;
    }

    public String getVideocall() {
        return videocall;
    }

    public void setVideocall(String videocall) {
        this.videocall = videocall;
    }
}
