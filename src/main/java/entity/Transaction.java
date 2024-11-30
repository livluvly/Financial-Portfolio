package entity;

import java.util.Date;

public class Transaction {
    private final Date date;
    private final String symbol;
    private final String name;
    private final int quantity;
    private final double totalCost;
    private final String type;

    /**
     * Constructor for Transaction Class.
     * @param symbol The stock symbol.
     * @param name The name of the stock.
     * @param quantity The number of units.
     * @param date The date of the transaction.
     * @param totalCost The total cost of the transaction.
     * @param type The type of transaction: "BUY" or "SELL".
     * @throws IllegalArgumentException If the transaction type is not "BUY" or "SELL".
     * @throws NullPointerException If symbol and date are null.
     */
    public Transaction(String symbol, String name, int quantity, Date date, double totalCost, String type) {
        // Transaction type should either be "BUY" or "SELL"
        if (!"BUY".equals(type) && !"SELL".equals(type)) {
            throw new IllegalArgumentException("Invalid transaction type, must be either 'BUY' or 'SELL'.");
        }

        if (symbol == null || date == null) {
            throw new NullPointerException("Symbol and date cannot be null.");
        }
        this.date = date;
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.type = type;
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
     * Getter for the name of the stock.
     * @return the full name of the stock.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the quantity of units.
     * @return the number of units.
     */
    public int getQuantity() {
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
     * @return  the type of transaction ("BUY" or "SELL").
     */
    public String getType() {
        return type;
    }
}
