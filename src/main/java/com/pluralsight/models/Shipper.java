package com.pluralsight.models;

public class Shipper {
    private int shipperID;
    private String companyName;
    private String phoneNumber;

    public Shipper(int shipperID, String companyName, String phoneNumber) {
        this.shipperID = shipperID;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }

    public int getShipperID() {
        return shipperID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("%-9d %-40s %-24s", shipperID, companyName, phoneNumber);
    }
}
