package use_case.portfolio;

/**
 * Input Boundary for actions which are related to setting up the portfolio, which should be only one that the application is running.
 */
public interface PortfolioInputBoundary {

    /**
     * Executes the portfolio use case.
     * @param portfolioInputData the input data
     */
    PortfolioOutputData getAssets(PortfolioInputData inputData);

    void savePortfolio(PortfolioInputData inputData);

    void addTransaction(PortfolioInputData inputData);

    void updateAsset(PortfolioInputData inputData);

    void deleteAsset(PortfolioInputData inputData);

    void execute(PortfolioInputData portfolioInputData);
}
