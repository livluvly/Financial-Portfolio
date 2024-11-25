package interface_adapter.portfolio;

import entity.Asset;

import java.util.List;

public class PortfolioState {
    private List<Asset> assets;

    public PortfolioState(List<Asset> assets) {
        this.assets = assets;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }
}
