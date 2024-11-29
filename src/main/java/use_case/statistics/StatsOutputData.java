package use_case.statistics;

import entity.Asset;

import java.util.List;

/**
 * Output Data for the Statistic Use Case.
 */
public class StatsOutputData {

    private final List<Asset> assets;
    private final Double totalBalance;
    private final Double totalDailyGain;
    private final Double totalDailyPercentageGain;
    private final boolean useCaseFail;

    public StatsOutputData(List<Asset> assets, Double totalBalance, Double totalDailyGain, Double totalDailyPercentageGain, boolean useCaseFail) {
        this.assets = assets;
        this.totalBalance = totalBalance;
        this.totalDailyGain = totalDailyGain;
        this.totalDailyPercentageGain = totalDailyPercentageGain;
        this.useCaseFail = useCaseFail;
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

    public boolean isUseCaseFail() {
        return useCaseFail;
    }

    public List<Asset> getAssets() {
        return assets;
    }

//    public List<Asset> getAssets() {
//        return assets;
//    }
//    public void addAssets(Asset asset) {
//        assets.add(asset);
//    }

}