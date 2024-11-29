package entity;

public class Transaction {
    private final String date;
    private String symbol;
    private String name;
    private int quantity;
    private double totalValue;
    private String type; // Either "BUY" or "SELL"


    public Transaction(String symbol, String name, int quantity, String date, double totalValue, String type) {
        this.date = date; // Format:  YYYY-MM-DD
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.totalValue = totalValue;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public String setDate(String date) {
        return date;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
