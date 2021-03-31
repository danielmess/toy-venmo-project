package com.techelevator.tenmo.model;

public class Transfer {

    private long transfer_id;
    private int transfer_type_id;
    private int transfer_status_id;
    private long account_from;
    private long account_to;
    private double amount;

    public Transfer(){}

    public Transfer(long transfer_id, int transfer_type_id, int transfer_status_id, long account_from, long account_to, double amount){
        this.transfer_id = transfer_id;
        this.transfer_type_id = transfer_type_id;
        this.transfer_status_id=transfer_status_id;
        this.account_from=account_from;
        this.account_to=account_to;
        this.amount=amount;
    }

    //getters


    public long getTransfer_id() {
        return transfer_id;
    }

    public int getTransfer_type_id() {
        return transfer_type_id;
    }

    public int getTransfer_status_id() {
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

    public void setTransfer_type_id(int transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }

    public void setTransfer_status_id(int transfer_status_id) {
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


    @Override
    public String toString(){
        return "Transfer{"+
                ", transfer_id='" + transfer_id + '\'' +
                ", transfer_type_id='" + transfer_type_id +   '\'' +
                ", transfer_status_id = " + transfer_status_id + '\''+
                ", account_from = " + account_from + '\''+
                ", account_to= " + account_to + '\''+
                ", amount = " + amount + '\''+
                '}';
    }


}
