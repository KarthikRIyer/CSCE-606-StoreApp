package com.example;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;


public class Order {
    private int orderID;
    private int buyerID;
    private double totalCost;
    private double totalTax;
    private String date;
    private String shippingAddress;
    private String billingAddress;
    private String CCNumber;
    private int CVV;
    private int validThruMM;
    private int validThruYYYY;

    private List<OrderLine> lines;

    public Order() {
        lines = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }

    public void addLine(OrderLine line) {
        lines.add(line);
    }

    public void removeLine(OrderLine line) {
        lines.remove(line);
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getCCNumber() {
        return CCNumber;
    }

    public int getCVV() {
        return CVV;
    }

    public int getValidThruMM() {
        return validThruMM;
    }

    public int getValidThruYYYY() {
        return validThruYYYY;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setCCNumber(String CCNumber) {
        this.CCNumber = CCNumber;
    }

    public void setCVV(int CVV) {
        this.CVV = CVV;
    }

    public void setValidThruMM(int validThruMM) {
        this.validThruMM = validThruMM;
    }

    public void setValidThruYYYY(int validThruYYYY) {
        this.validThruYYYY = validThruYYYY;
    }
}
