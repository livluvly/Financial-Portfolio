package interface_adapter;

import interface_adapter.statistics.StatsState;
import use_case.statistics.StatsOutputData;

import java.util.List;

public class StatsViewModel extends ViewModel<StatsState> {

    public StatsViewModel() {
        super("Statistics");
        this.setState(new StatsState(new StatsOutputData(List.of(), 0.0, 0.0, 0.0, true)));
        // instantiate with "empty" stats state
    }

    public void updateStats(StatsOutputData statsOutputData) {
        StatsState newState = new StatsState(statsOutputData);
        this.setState(newState);
        this.firePropertyChanged();
    }

    public StatsOutputData getStatsData() {
        return this.getState().getStatsOutputData();
    }

}

//    private final List<Asset> assets;
//    private final Double totalBalance;
//    private final Double totalDailyGain;
//    private final Double totalDailyPercentageGain;
//
//    public StatsViewModel(List<Asset> assets) {
//        this.assets = assets;
//        this.totalBalance = retrieveTotalBalance();
//        this.totalDailyGain = retrieveTotalDailyGain();
//        this.totalDailyPercentageGain = retrieveTotalPercentageGain();
//    }
//
//    public List<Asset> getAssets() {
//        return assets;
//    }
//
//    public Double getTotalBalance() {
//        return totalBalance;
//    }
//
//    public Double getTotalDailyGain() {
//        return totalDailyGain;
//    }
//
//    public Double getTotalDailyPercentageGain() {
//        return totalDailyPercentageGain;
//    }
//
//    private Double retrieveTotalBalance() {
//        double totalBalance = 0.0;
//        for (Asset asset : assets) {
//            double totalValue = asset.getTotalValue();
//            totalBalance += totalValue;
//        }
//        return totalBalance;
//    }
//
//    private Double retrieveTotalDailyGain() {
//        double totalDailyGain = 0.0;
//        for (Asset asset : assets) {
//            double totalGain = asset.getDailyGain();
//            totalDailyGain += totalGain;
//        }
//        return totalDailyGain;
//    }
//
//    private Double retrieveTotalPercentageGain() {
//        double totalDailyPercentageGain = 0.0;
//        for (Asset asset : assets) {
//            double totalPercentageGain = asset.getDailyGainPercentage();
//            totalDailyPercentageGain += totalPercentageGain;
//        }
//        return totalDailyPercentageGain;
//    }
//}
