package use_case.statistic;

import entity.Asset;
import java.util.List;

/**
 * Output Data for the Statistic Use Case.
 */
public class StatsOutputData {

    private final List<Asset> assets;
    private final boolean useCaseFail;

    public StatsOutputData(List<Asset> assets, boolean useCaseFail) {
        this.assets = assets;
        this.useCaseFail = useCaseFail;
    }

    public List<Asset> getAssets() {
        return assets;
    }
    public void addAssets(Asset asset) {
        assets.add(asset);
    }
}
