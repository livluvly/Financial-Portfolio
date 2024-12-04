package interfaceAdapter.statistics;

import interfaceAdapter.StatsViewModel;
import useCase.statistics.StatsOutputBoundary;
import useCase.statistics.StatsOutputData;

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
//        statsViewModel.updateStats(new StatsOutputData(null, 0.0, 0.0, 0.0, true));
//        final StatsState statsState = statsViewModel.getState();
//        statsState.setStatsErrorMessage(errorMessage);
    }
}