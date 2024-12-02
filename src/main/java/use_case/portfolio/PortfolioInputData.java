package use_case.portfolio;

import entity.User;

/**
 * A data transfer object (DTO) for inputting data into the portfolio use case.
 */
public class PortfolioInputData {
    private User user;
    private String username;
    private String symbol; // Optional, for transactions or asset-specific actions
    private double quantity; // Optional, for adding or updating assets
    private double totalValue; // Optional, for transactions
    private double dailyGain; // Optional, for transactions

    public PortfolioInputData(User user, String symbol){
        this.user = user;
        this.symbol = symbol;
    }
    public PortfolioInputData(User user, String symbol, double quantity, double totalValue){
        this.user = user;
        this.symbol = symbol;
        this.quantity = quantity;
        this.totalValue = totalValue;
    }

    // Constructor for general portfolio actions (e.g., fetch, save)
    public PortfolioInputData(String username) {
        this.username = username;
    }

    public PortfolioInputData(String username, String symbol) {
        this.username = username;
    }
    // Constructor for transactions or asset-specific actions
    public PortfolioInputData(String username, String symbol, double quantity, double  totalValue) {
        this.username = username;
        this.symbol = symbol;
        this.quantity = quantity;
        this.totalValue = totalValue;
    }

    // Constructor for transactions involving full details
    public PortfolioInputData(String username, String symbol, double quantity, double totalValue, double dailyGain) {
        this.username = username;
        this.symbol = symbol;
        this.quantity = quantity;
        this.totalValue = totalValue;
        this.dailyGain = dailyGain;
    }

    public String getUsername() {
        return username;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public double getDailyGain() {
        return dailyGain;
    }

    public User getUser() {
        return user;
    }
}
