package interface_adapter.portfolio;

import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioInputData;
import use_case.portfolio.PortfolioOutputData;

public class PortfolioController {
    private final PortfolioInputBoundary portfolioInputBoundary;

    public PortfolioController(PortfolioInputBoundary portfolioInputBoundary) {
        this.portfolioInputBoundary = portfolioInputBoundary;
    }

    public PortfolioOutputData fetchAssets(String userId) {
        PortfolioInputData inputData= new PortfolioInputData(userId);
        return portfolioInputBoundary.getAssets(inputData);
    }
}
