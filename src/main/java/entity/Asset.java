package entity;

public class Asset {
    private String symbol;
    private double quantity;
    private double price;
    private double dailyGain;
    private double dailyGainPercentage;

    public Asset(String symbol, double quantity, double price, double dailyGain) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.dailyGain = dailyGain;
        this.dailyGainPercentage = dailyGain/price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
