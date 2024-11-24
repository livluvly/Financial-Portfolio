package use_case.statistic;

import java.util.List;
import entity.Asset;

/**
 * The Statistic Interactor.
 */

public class StatsInteractor implements StatsInputBoundary {
    private final StatsDataAccessInterface statsDataAccess;
    private final StatsOutputBoundary statsOutputBoundary;
    // the output boundary is a layer that actually helps interactor to talk with the presenter.

    public StatsInteractor(StatsDataAccessInterface assetDataAccess,
                           StatsOutputBoundary statsOutputBoundary) {
        this.statsDataAccess = assetDataAccess;
        this.statsOutputBoundary = statsOutputBoundary;
        // this.portfolioDataAccessInterface =
        // {"IBM": {"Name": "International Business Machine", "Amount": 100.0},
        // "AAPL": {"Name": "Apple Inc.", "Amount": 50.0};
    }

    /**
     * Should be an override method from implementing PortfolioInputBoundary.
     * @param statsInputData should there be any input data for portfolio?
     */

    @Override
    public void execute(StatsInputData statsInputData) {

    }

    @Override
    public StatsOutputData getAssets(StatsInputData inputData) {
        List<Asset> assets = statsDataAccess.getAssetsForUser(inputData.getUserId());
        StatsOutputData outputData = new StatsOutputData(assets, false);
        statsOutputBoundary.prepareSuccessView(outputData);
        return outputData;
    }

}
