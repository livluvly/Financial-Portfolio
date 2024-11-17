package use_case.portfolio;

import use_case.login.LoginInputData;

/**
 * The Portfolio Interactor.
 */
public class PortfolioInteractor {
    private final PortfolioDataAccessInterface portfolioDataAccessInterface;
    private final PortfolioOutputBoundary portfolioOutputBoundary;
    // the output boundary is a layer that actually helps interactor to talk with the presenter.

    public PortfolioInteractor(PortfolioDataAccessInterface assetDataAccessInterface,
                               PortfolioOutputBoundary portfolioOutputBoundary) {
        this.portfolioDataAccessInterface = assetDataAccessInterface;
        this.portfolioOutputBoundary = portfolioOutputBoundary;
        // this.portfolioDataAccessInterface =
        // {"IBM": {"Name": "International Business Machine", "Amount": 100.0},
        // "AAPL": {"Name": "Apple Inc.", "Amount": 50.0};
    }

    /**
     * Should be an override method from implementing PortfolioInputBoundary.
     * @param loginInputData should there be any input data for portfolio?
     */
    public void execute(LoginInputData loginInputData) {
    }
}
