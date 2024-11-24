package use_case.statistic;

/**
 * Input Boundary for actions which are related to setting up the portfolio, which should be only one that the application is running.
 */
public interface StatsInputBoundary {

    /**
     * Executes the portfolio use case.
     * @param statsInputData the input data
     */
    StatsOutputData getAssets(StatsInputData statsInputData);
    
    void execute(StatsInputData statsInputData);
}
