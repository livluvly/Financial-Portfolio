package entity;

import java.util.Date;

public class Transaction {
    private Date date;
    private String symbol;
    private double quantity;
    private double totalCost;
    private String type; // Either "BUY" or "SELL"


    public Transaction(String symbol, double quantity, Date date, double totalValue, String type) {
        this.date = date;
        this.symbol = symbol;
        this.quantity = quantity;
        this.totalCost = totalValue;
        this.type = type; // "BUY" or "SELL"
    }

    public Date getDate() {
        return date;
    }

    public Date setDate(Date date) {
        return date;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getType() {
        return type;
    }

    public void setTotalValue(String type) {
        this.type = type;
    }

}
