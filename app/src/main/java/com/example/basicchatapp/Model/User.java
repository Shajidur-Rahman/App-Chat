package com.example.basicchatapp.Model;

public class User {
    private  String uid = null;
    private String name = null;
    private String email = null;
    private String password = null;
    private String uId = null;
    private String lastMsg = null;


    public String getLastMsg() {
        return lastMsg;
    }



    public User() {

    }

    public User(String name, String email, String password, String uid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.uid = uid;
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




