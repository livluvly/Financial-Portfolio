package interface_adapter;

import entity.Asset;
import java.util.List;

public class StatsViewModel {
    private final List<Asset> assets;
    private final Double totalBalance;
    private final Double totalDailyGain;
    private final Double totalDailyPercentageGain;

    public StatsViewModel(List<Asset> assets) {
        this.assets = assets;
        this.totalBalance = retrieveTotalBalance();
        this.totalDailyGain = retrieveTotalDailyGain();
        this.totalDailyPercentageGain = retrieveTotalPercentageGain();
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public Double getTotalDailyGain() {
        return totalDailyGain;
    }

    public Double getTotalDailyPercentageGain() {
        return totalDailyPercentageGain;
    }

    private Double retrieveTotalBalance() {
        double totalBalance = 0.0;
        for (Asset asset : assets) {
            double totalValue = asset.getTotalValue();
            totalBalance += totalValue;
        }
        return totalBalance;
    }


    private Double retrieveTotalDailyGain() {
        double totalDailyGain = 0.0;
        for (Asset asset : assets) {
            double totalGain = asset.getDailyGain();
            totalDailyGain += totalGain;
        }
        return totalDailyGain;
    }

    private Double retrieveTotalPercentageGain() {
        double totalDailyPercentageGain = 0.0;
        for (Asset asset : assets) {
            double totalPercentageGain = asset.getDailyGainPercentage();
            totalDailyPercentageGain += totalPercentageGain;
        }
        return totalDailyPercentageGain;
    }
}
