package com.userinfo;

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

