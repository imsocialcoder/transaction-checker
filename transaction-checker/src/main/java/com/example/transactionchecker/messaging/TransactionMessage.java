package com.example.transactionchecker.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class TransactionMessage {
    private int PID;
    private double PAMOUNT;
    private long PDATA;

    public TransactionMessage(){ }
    public TransactionMessage(int PID, double PAMOUNT, long PDATA) {
        this.PID = PID;
        this.PAMOUNT = PAMOUNT;
        this.PDATA = PDATA;
    }

    @JsonProperty("PID")
    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    @JsonProperty("PAMOUNT")
    public double getPAMOUNT() {
        return PAMOUNT;
    }

    public void setPAMOUNT(double PAMOUNT) {
        this.PAMOUNT = PAMOUNT;
    }

    @JsonProperty("PDATA")
    public long getPDATA() {
        return PDATA;
    }

    public void setPDATA(long PDATA) {
        this.PDATA = PDATA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionMessage that = (TransactionMessage) o;
        return PID == that.PID && Double.compare(that.PAMOUNT, PAMOUNT) == 0 && PDATA == that.PDATA;
    }

    @Override
    public int hashCode() {
        return Objects.hash(PID, PAMOUNT, PDATA);
    }
}

