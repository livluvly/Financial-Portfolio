package interface_adapter.statistic;

import interface_adapter.StatsViewModel;
import use_case.statistic.StatsOutputBoundary;
import use_case.statistic.StatsOutputData;

public class StatsPresenter implements StatsOutputBoundary {
    private StatsViewModel statsViewModel;

    /**
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(StatsOutputData outputData) {
        statsViewModel = new StatsViewModel(outputData.getAssets());
    }

    /**
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
    }

    public StatsViewModel getStatsViewModel() {
        return statsViewModel;
    }
}
