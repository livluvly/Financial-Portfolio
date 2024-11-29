package use_case.statistics;

/**
 * Input Boundary for actions which are related to setting up the portfolio, which should be only one that the application is running.
 */
public interface StatsInputBoundary {

    /**
     * Executes the portfolio use case.
     * @param statsInputData the input data
     */
    void execute(StatsInputData statsInputData);
}
