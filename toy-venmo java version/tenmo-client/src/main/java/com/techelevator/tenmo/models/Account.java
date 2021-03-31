package com.techelevator.tenmo.models;

public class Account {

    private long account_id;
    private long user_id;
    private Double balance = 0.00;

    //getters
    public long getUser_id() { return user_id; }
    public long getAccount_id() {return account_id;}
    public double getBalance() { return balance; }

    //setters
    public void setAccount_id(long account_id) { this.account_id = account_id; }
    public void setUser_id(long user_id) {this.user_id = user_id;}
    public void setBalance(double balance) {this.balance = balance;}


}
