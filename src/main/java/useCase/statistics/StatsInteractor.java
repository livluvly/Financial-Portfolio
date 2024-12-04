package useCase.statistics;

import entity.Asset;

import java.util.List;

/**
 * The Statistic Interactor.
 */

public class StatsInteractor implements StatsInputBoundary {
    private final StatsDataAccessInterface statsDataAccess;
    private final StatsOutputBoundary statsPresenter;

    private List<Asset> assets;
    private Double totalBalance;
    private Double totalDailyGain;
    private Double totalDailyPercentageGain;
    // the output boundary is a layer that actually helps interactor to talk with the presenter.

    public StatsInteractor(StatsDataAccessInterface assetDataAccess,
                           StatsOutputBoundary statsPresenter) {
        this.statsDataAccess = assetDataAccess;
        this.statsPresenter = statsPresenter;
        // this.portfolioDataAccessInterface =
        // {"IBM": {"Name": "International Business Machine", "Amount": 100.0},
        // "AAPL": {"Name": "Apple Inc.", "Amount": 50.0};
    }

    /**
     * Should be an override method from implementing StatsInputBoundary.
     * @param statsInputData should there be any input data for portfolio?
     */

    @Override
    public void execute(StatsInputData statsInputData) {
        final String userId = statsInputData.getUserId();
        if (!statsDataAccess.existsByName(userId)) {
            statsPresenter.prepareFailView(userId + ": Account does not exist");
        }
        else {
            this.assets = statsDataAccess.getAssetsForUser(statsInputData.getUserId());
            this.totalBalance = retrieveTotalBalance();
            this.totalDailyGain = retrieveTotalDailyGain();
            this.totalDailyPercentageGain = retrieveTotalPercentageGain();
            StatsOutputData outputData = new StatsOutputData(assets, totalBalance, totalDailyGain, totalDailyPercentageGain, true);
            statsPresenter.prepareSuccessView(outputData);
        }
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public Double getTotalDailyGain() {
        return totalDailyGain;
    }

    public Double getTotalDailyPercentageGain() {
        return totalDailyPercentageGain;
    }

    private Double retrieveTotalBalance() {
        double totalBalance = 0.0;
        for (Asset asset : assets) {
            double totalValue = asset.getValuePerUnit() * asset.getQuantity();
            totalBalance += totalValue;
        }
        return totalBalance;
    }

    private Double retrieveTotalDailyGain() {
        double totalDailyGain = 0.0;
        for (Asset asset : assets) {
            double totalGain = asset.getDailyGain() * asset.getQuantity();
            totalDailyGain += totalGain;
        }
        return totalDailyGain;
    }

    private Double retrieveTotalPercentageGain() {
        if (assets.isEmpty()) {
            return 0.0;
        }
        double totalBalance = retrieveTotalBalance();
        double totalDailyGain = retrieveTotalDailyGain();
        return totalDailyGain / totalBalance;
    }

}