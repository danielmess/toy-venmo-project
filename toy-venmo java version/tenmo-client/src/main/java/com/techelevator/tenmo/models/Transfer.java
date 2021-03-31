package com.techelevator.tenmo.models;

public class Transfer {

    private long transfer_id;
    private Integer transfer_type_id;
    private Integer transfer_status_id;
    private long account_from;
    private long account_to;
    private double amount;

    public Transfer(){}

    //getters
    public long getTransfer_id() {
        return transfer_id;
    }
    public Integer getTransfer_type_id() {
        return transfer_type_id;
    }
    public Integer getTransfer_status_id() {
        return transfer_status_id;
    }
    public long getAccount_from() {
        return account_from;
    }
    public long getAccount_to() {
        return account_to;
    }
    public double getAmount() {
        return amount;
    }

    //setters
    public void setTransfer_id(long transfer_id) {
        this.transfer_id = transfer_id;
    }
    public void setTransfer_type_id(Integer transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }
    public void setTransfer_status_id(Integer transfer_status_id) {
        this.transfer_status_id = transfer_status_id;
    }
    public void setAccount_from(long account_from) {
        this.account_from = account_from;
    }
    public void setAccount_to(long account_to) {
        this.account_to = account_to;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
