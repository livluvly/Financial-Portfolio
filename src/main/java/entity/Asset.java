package entity;

public class Asset {
    private String symbol;
    private double quantity;
    private double totalValue;
    private double valuePerUnit;
    private double dailyGain;
    private double dailyGainPercentage;


    public Asset(String symbol, double quantity, double valuePerUnit, double v, double v1) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.valuePerUnit = valuePerUnit;
        this.totalValue = v;
        this.dailyGain = v1;
        this.dailyGainPercentage = v1/v;
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

    public double getValuePerUnit() {return valuePerUnit;}
}
