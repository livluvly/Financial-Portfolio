package use_case.portfolio;

import entity.Asset;

/**
 * Output Data for the Portfolio Use Case.
 */
public class PortfolioOutputData {

    private final Asset asset;
    private final boolean useCaseFail;

    public PortfolioOutputData(Asset asset, boolean useCaseFail) {
        this.asset = asset;
        this.useCaseFail = useCaseFail;
    }

    public Asset getAsset() {
        return asset;
    }
}
