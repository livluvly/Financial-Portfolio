package entity;

import java.util.Date;

public class Transaction {
    private Date date;
    private String symbol;
    private double quantity;
    private double totalValue;
    private String type; // Either "BUY" or "SELL"


    public Transaction(String symbol, double quantity, Date date, double totalValue, String type) {
        this.date = date;
        this.symbol = symbol;
        this.quantity = quantity;
        this.totalValue = totalValue;
        this.type = type;
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

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public String getType() {
        return type;
    }

    public void setTotalValue(String type) {
        this.type = type;
    }

}
