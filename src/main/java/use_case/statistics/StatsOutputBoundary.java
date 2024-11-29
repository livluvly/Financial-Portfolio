package use_case.statistics;

/**
 * The output boundary for the Statistic Use Case.
 */
public interface StatsOutputBoundary {
    /**
     * Prepares the success view for the Statistic Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(StatsOutputData outputData);

    /**
     * Prepares the failure view for the Statistic Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}