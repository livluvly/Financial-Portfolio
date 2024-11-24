package interface_adapter.statistic;

import use_case.statistic.StatsInputBoundary;
import use_case.statistic.StatsInputData;
import use_case.statistic.StatsOutputData;

public class StatsController {
    private final StatsInputBoundary statsInputBoundary;

    public StatsController(StatsInputBoundary statsInputBoundary) {
        this.statsInputBoundary = statsInputBoundary;
    }

    public StatsOutputData fetchAssets(String userId) {
        StatsInputData inputData = new StatsInputData(userId);
        return statsInputBoundary.getAssets(inputData);
    }
}
