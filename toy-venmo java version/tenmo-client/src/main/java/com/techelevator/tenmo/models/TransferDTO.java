package com.techelevator.tenmo.models;

public class TransferDTO {

    private long from_id;
    private long to_id;
    private double amount;

    public TransferDTO(){}

    public TransferDTO(Transfer transfer){
        this.from_id = transfer.getAccount_from();
        this.to_id = transfer.getAccount_to();
        this.amount = transfer.getAmount();
    }

    //getters
    public long getFrom_id() {
        return from_id;
    }
    public long getTo_id() {
        return to_id;
    }
    public double getAmount() {
        return amount;
    }

    //setters
    public void setFrom_id(long from_id) {
        this.from_id = from_id;
    }
    public void setTo_id(long to_id) {
        this.to_id = to_id;
    }
    public void setAmount(double amount) { this.amount = amount; }
}
