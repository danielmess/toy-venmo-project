package com.techelevator.tenmo.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class Account {

    @Positive (message = "account must exist")
    private long account_id;
    @Positive (message = "user id must exist")
    private long user_id;
    @NotNull (message = "there must be a balance")
    private Double balance = 0.00;

    //set constructors

    public Account (){}

    public Account (long account_id, long user_id, double balance){
        this.account_id = account_id;
        this.user_id = user_id;
        this.balance = balance;
    }

    //getters


    public long getAccount_id() { return account_id; }

    public long getUser_id() { return user_id; }

    public double getBalance() { return balance; }

    //setters


    public void setAccount_id(long account_id) { this.account_id = account_id; }

    public void setUser_id(long user_id) { this.user_id = user_id; }

    public void setBalance(double balance) { this.balance = balance; }

    //utilities


    @Override
    public int hashCode() {
        return Objects.hash(account_id, user_id, balance);
    }

    @Override
    public String toString(){
    return "Account{"+
            "account_id='" + account_id + '\'' +
            ", user_id='" +user_id +   '\'' +
            ", balance = " + balance +
            '}';
    }
}