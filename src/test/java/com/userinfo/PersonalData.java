package com.userinfo;

import java.io.Serializable;

public class PersonalData implements Serializable{

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
