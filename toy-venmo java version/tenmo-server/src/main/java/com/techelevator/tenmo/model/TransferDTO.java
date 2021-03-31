package com.techelevator.tenmo.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TransferDTO {
    @NotNull (message = "from id required!")
    private long from_id;
    @NotNull (message = "to id required!")
    private long to_id;
    @Positive (message = "No negative transfers!")
    private double amount;

    public TransferDTO(Transfer transfer){
        this.from_id = transfer.getAccount_from();
        this.to_id = transfer.getAccount_to();
        this.amount = transfer.getAmount();
    }

    public TransferDTO(){}

    public long getFrom_id() {
        return from_id;
    }

    public long getTo_id() {
        return to_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setFrom_id(long from_id) {
        this.from_id = from_id;
    }

    public void setTo_id(long to_id) {
        this.to_id = to_id;
    }

    public void setAmount(double amount) { this.amount = amount; }
}
