package entity;

public class Asset {
    private String symbol;
    private double quantity;
    private double totalValue;
    private double dailyGain;
    private double dailyGainPercentage;

    public Asset(String symbol, double quantity, double totalValue, double dailyGain) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.totalValue = totalValue;
        this.dailyGain = dailyGain;
        this.dailyGainPercentage = dailyGain/totalValue;
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

    public double getDailyGain() {
        return dailyGain;
    }

    public void setDailyGain(double dailyGain) {
        this.dailyGain = dailyGain;
    }

    public double getDailyGainPercentage() {
        return dailyGainPercentage;
    }

    public void setDailyGainPercentage(double dailyGainPercentage) {
        this.dailyGainPercentage = dailyGainPercentage;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
