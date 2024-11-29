package interface_adapter.statistics;

import interface_adapter.StatsViewModel;
import use_case.statistics.StatsOutputBoundary;
import use_case.statistics.StatsOutputData;

public class StatsPresenter implements StatsOutputBoundary {
    private final StatsViewModel statsViewModel;

    public StatsPresenter(StatsViewModel statsViewModel) {
        this.statsViewModel = statsViewModel;
    }

    /**
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(StatsOutputData outputData) {
        statsViewModel.updateStats(outputData);
    }

    /**
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        statsViewModel.updateStats(null);
        final StatsState statsState = statsViewModel.getState();
        statsState.setStatsErrorMessage(errorMessage);
    }

}
