package interface_adapter.portfolio;

import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioInputData;
import use_case.portfolio.PortfolioOutputData;

public class PortfolioController {
    private final PortfolioInputBoundary portfolioInputBoundary;

    public PortfolioController(PortfolioInputBoundary portfolioInputBoundary) {
        this.portfolioInputBoundary = portfolioInputBoundary;
    }

    public PortfolioOutputData fetchAssets(String username) {
        PortfolioInputData inputData= new PortfolioInputData(username);
        return portfolioInputBoundary.getAssets(inputData);
    }

    public void execute(String username) {
    }

    public void savePortfolio(String username) {
        portfolioInputBoundary.savePortfolio(new PortfolioInputData(username));
    }

    public void addTransaction(String username, String symbol, double quantity, double value, double gain) {
        portfolioInputBoundary.addTransaction(new PortfolioInputData(username, symbol, quantity, value, gain));
    }

    public void updateAsset(String username, String symbol, double quantity) {
        portfolioInputBoundary.addTransaction(new PortfolioInputData(username,symbol,quantity));
    }

    public void deleteAsset(String username, String symbol) {
        portfolioInputBoundary.deleteAsset(new PortfolioInputData(username, symbol));
    }

}
