package interfaceAdapter.statistics;

import useCase.statistics.StatsInputBoundary;
import useCase.statistics.StatsInputData;

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
