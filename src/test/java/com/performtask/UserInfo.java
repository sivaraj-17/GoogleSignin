package com.performtask;

import java.io.Serializable;
import java.util.ArrayList;


public class UserInfo implements Serializable {

    private ArrayList<UserData> userInfo;

    public ArrayList<UserData> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ArrayList<UserData> guestsInfo) {
        this.userInfo = guestsInfo;
    }

    @Override
    public String toString() {
        return "UserInfo{" + "userInfo=" + userInfo + '}';
    }
}

class UserData implements Serializable {

    private PersonalData personalData;

    public PersonalData getPersonalData() {
        return personalData;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "personalData=" + personalData +
                '}';
    }
}

class PersonalData implements Serializable {

    private String id;
    private String user;
    private String passwd;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PersonalData{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
