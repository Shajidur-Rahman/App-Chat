package com.example.basicchatapp.Model;

public class User {
    private String name = null;
    private String email = null;
    private String password = null;
    private String uId;
    private String lastMsg = null;


    public String getLastMsg() {
        return lastMsg;
    }



    public User() {

    }

    // constructor
    public User(String name, String email, String password, String uid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.uId = uid;
    }

    public User(String uid, String name, String email) {
        this.uId = uid;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public User(String name, String lastMsg) {
        this.name = name;
        this.lastMsg = lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

}




