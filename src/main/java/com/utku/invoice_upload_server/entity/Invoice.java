package com.utku.invoice_upload_server.entity;

public class Invoice {
    private int id;
    private String seri;
    private String number;
    private double totalAmount;
    private double discount;
    private double amountToPay;
    private int customerId;

    public Invoice() {
    }

    public Invoice(int id, String seri, String number, double totalAmount, double discount, double amountToPay, int customerId) {
        this.id = id;
        this.seri = seri;
        this.number = number;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.amountToPay = amountToPay;
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeri() {
        return seri;
    }

    public void setSeri(String seri) {
        this.seri = seri;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
