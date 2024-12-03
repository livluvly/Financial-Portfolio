package interface_adapter.statistics;

import use_case.statistics.StatsInputBoundary;
import use_case.statistics.StatsInputData;
import use_case.statistics.StatsOutputData;

public class StatsController {
    private final StatsInputBoundary statsInteractor;

    public StatsController(StatsInputBoundary statsInteractor) {
        this.statsInteractor = statsInteractor;
    }

    public void execute(String username) {
        final StatsInputData statsInputData = new StatsInputData(username);
        statsInteractor.execute(statsInputData);
    }

}
