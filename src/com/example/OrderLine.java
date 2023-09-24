package com.example;

public class OrderLine {

    private int productID;
    private int orderID;
    private double quantity;
    private double cost;
    private Product product;

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }
}
