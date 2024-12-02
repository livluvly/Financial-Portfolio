package use_case.portfolio;

import java.util.List;
import entity.Asset;

/**
 * The Portfolio Interactor.
 */

public class PortfolioInteractor implements PortfolioInputBoundary {
    private final PortfolioDataAccessInterface dataAccess;
    private final PortfolioOutputBoundary portfolioOutputBoundary;
    // the output boundary is a layer that actually helps interactor to talk with the presenter.

    public PortfolioInteractor(PortfolioDataAccessInterface assetDataAccess,
                               PortfolioOutputBoundary portfolioOutputBoundary) {
        this.dataAccess = assetDataAccess;
        this.portfolioOutputBoundary = portfolioOutputBoundary;
        // this.portfolioDataAccessInterface =
        // {"IBM": {"Name": "International Business Machine", "Amount": 100.0},
        // "AAPL": {"Name": "Apple Inc.", "Amount": 50.0};
    }

    /**
     * Should be an override method from implementing PortfolioInputBoundary.
//     * @param loginInputData should there be any input data for portfolio?
     */

    @Override
    public void execute(PortfolioInputData portfolioInputData) {

    }

    @Override
    public PortfolioOutputData getAssets(PortfolioInputData inputData) {
        List<Asset> assets = dataAccess.getAssetsForUser(inputData.getUsername());
        PortfolioOutputData outputData = new PortfolioOutputData(assets, false);
        if (!assets.isEmpty()) {
            portfolioOutputBoundary.prepareSuccessView(outputData);
        } else {
            portfolioOutputBoundary.prepareFailView("No assets found for " + inputData.getUsername());
        }
        return outputData;
    }

    /**
     * @param inputData
     */
    @Override
    public void savePortfolio(PortfolioInputData inputData) {
        List<Asset> assets = dataAccess.getAssetsForUser(inputData.getUsername());
        dataAccess.savePortfolio(inputData.getUsername(), assets);
    }

    /**
     * @param inputData
     */
    @Override
    public void addTransaction(PortfolioInputData inputData) {

        Asset newAsset = new Asset(inputData.getSymbol(), inputData.getQuantity(), inputData.getTotalValue(), inputData.getDailyGain(),0);
        dataAccess.addTransaction(inputData.getUsername(), newAsset);
    }
    

    /**
     * @param inputData
     */
    @Override
    public void updateAsset(PortfolioInputData inputData) {
        Asset updatedAsset = new Asset(inputData.getSymbol(), inputData.getQuantity(), inputData.getTotalValue(), 0,0);
        dataAccess.updateAsset(inputData.getUsername(), updatedAsset);
    }

    /**
     * @param inputData
     */
    @Override
    public void deleteAsset(PortfolioInputData inputData) {
        dataAccess.deleteAsset(inputData.getUsername(), inputData.getSymbol());
    }

}
