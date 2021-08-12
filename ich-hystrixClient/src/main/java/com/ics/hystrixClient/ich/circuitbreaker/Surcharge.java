package com.ics.hystrixClient.ich.circuitbreaker;


public class Surcharge {
    private int surcharge;
    private int tax;

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }


    public int getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(int surcharge) {
        this.surcharge = surcharge;
    }
}
