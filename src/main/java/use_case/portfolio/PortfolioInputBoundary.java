package use_case.portfolio;

/**
 * Input Boundary for actions which are related to setting up the portfolio, which should be only one that the application is running.
 */
public interface PortfolioInputBoundary {

    /**
     * Executes the portfolio use case.
     * @param portfolioInputData the input data
     */
    void execute(PortfolioInputData portfolioInputData);
}
