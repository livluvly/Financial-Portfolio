package entity;

import java.util.Date;

public class Transaction {
    private final Date date;
    private final String symbol;
    private final double quantity;
    private final double totalCost;
    private final String type;

    /**
     * Constructor for Transaction Class.
     * @param symbol The stock symbol.
     * @param quantity The number of units.
     * @param date The date of the transaction.
     * @param totalCost The total cost of the transaction.
     * @param type The type of transaction: "BUY" or "SELL".
     */
    public Transaction(String symbol, double quantity, Date date, double totalCost, String type) {
        this.date = date;
        this.symbol = symbol;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.type = type; // "BUY" or "SELL"
    }

    /**
     * Getter for the transaction date.
     * @return the date of the transaction.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter for the stock symbol.
     * @return the stock symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Getter for the quantity of units.
     * @return the number of units.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Getter for the total cost of the transaction.
     * @return the total cost.
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Getter for the transaction type.
     * @return the type of transaction ("BUY" or "SELL").
     */
    public String getType() {
        return type;
    }

}
