package com.techelevator.tenmo.models;

import java.util.List;

public class AccountDTO {

    private long account_id;
    private long user_id;
    private String username;

    public AccountDTO(){}

    //getters
    public long getAccount_id() {
        return account_id;
    }
    public long getUser_id() {
        return user_id;
    }
    public String getUsername(){
        return username;
    }

    //setters
    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    public void setUsername(String username){
        this.username = username;
    }

}
