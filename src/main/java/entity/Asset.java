package entity;

public class Asset {
    private String symbol;
    private double quantity;
    private double valuePerUnit;
    private double totalValue;
    private double dailyGain;
    private double dailyGainPercentage;
  
    public Asset(String symbol, double quantity, double valuePerUnit, double totalValue, double dailyGain, double dailyGainPercentage) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.totalValue = totalValue;
        this.valuePerUnit = valuePerUnit;
        this.dailyGain = dailyGain;
        this.dailyGainPercentage = dailyGainPercentage;
    }

    public Asset(String symbol, double quantity, double price, double dailyGain) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.totalValue = price*quantity;
        this.dailyGain = dailyGain;
        this.dailyGainPercentage = 0;
        this.valuePerUnit = price;
    }


    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public double getValuePerUnit() {
        return this.valuePerUnit;
    }

    public void setValuePerUnit(double price) {
        this.valuePerUnit = price;
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
  
    public double getTotalValue() {
        return this.getValuePerUnit()*this.getQuantity();
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
}
