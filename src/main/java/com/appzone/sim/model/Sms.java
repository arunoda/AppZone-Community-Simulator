package com.appzone.sim.model;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class Sms {

    String message;
    String address;
    long receivedDate;

    public Sms(String message, String address, long receivedDate) {
        this.message = message;
        this.address = address;
        this.receivedDate = receivedDate;
    }

    public String getMessage() {
        return message;
    }

    public String getAddress() {
        return address;
    }

    public long getReceivedDate() {
        return receivedDate;
    }

    @Override
    public String toString() {

        return String.format("%s :: %s :: %s", message, address, receivedDate);
    }
}
