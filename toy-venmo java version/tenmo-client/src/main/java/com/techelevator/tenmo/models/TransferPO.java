package com.techelevator.tenmo.models;

public class TransferPO {
    private long transfer_id;
    private String from_username;
    private String to_username;
    private String transfer_type_desc;
    private String transfer_status_desc;
    private double amount;

    //constructors
    public TransferPO(){}

    public TransferPO(long transfer_id, String from_username, String to_username, String transfer_type_desc, String transfer_status_desc, double amount){
        this.transfer_id = transfer_id;
        this.from_username = from_username;
        this.to_username = to_username;
        this.transfer_type_desc = transfer_type_desc;
        this.transfer_status_desc = transfer_status_desc;
        this.amount = amount;
    }

    //setters


    public void setTransfer_id(long transfer_id) {
        this.transfer_id = transfer_id;
    }

    public void setFrom_username(String from_username) {
        this.from_username = from_username;
    }

    public void setTo_username(String to_username) {
        this.to_username = to_username;
    }

    public void setTransfer_type_desc(String transfer_type_desc) {
        this.transfer_type_desc = transfer_type_desc;
    }

    public void setTransfer_status_desc(String transfer_status_desc) {
        this.transfer_status_desc = transfer_status_desc;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    //getters


    public Long getTransfer_id() {
        return transfer_id;
    }

    public String getFrom_username() {
        return from_username;
    }

    public String getTo_username() {
        return to_username;
    }

    public String getTransfer_type_desc() {
        return transfer_type_desc;
    }

    public String getTransfer_status_desc() {
        return transfer_status_desc;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public String toString(){
        return "Transfer{"+
                ", transfer_id='" + transfer_id + '\'' +
                ", from_username='" + from_username +   '\'' +
                ", to_username = " + to_username + '\''+
                ", transfer_type_desc = " + transfer_type_desc + '\''+
                ", transfer_status_desc " + transfer_status_desc + '\''+
                ", amount = " + amount + '\''+
                '}';
    }
}

