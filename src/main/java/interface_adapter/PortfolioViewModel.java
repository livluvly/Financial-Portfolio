package interface_adapter;

import entity.Asset;
import java.util.List;
import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioOutputData;
import view.AssetsPageView;

public class PortfolioViewModel {
    private final List<Asset> assets;

    public PortfolioViewModel(List<Asset> assets) {
        this.assets = assets;
    }

//    public PortfolioView getPortfolioView(String userId) {
//        // Execute the use case
//        PortfolioOutputData outputData = portfolioInteractor.fetchPortfolio(userId);
//
//        // Transform OutputData into PortfolioView
//        return new PortfolioView(outputData.getPortfolioName(),
//                outputData.getTotalValue(),
//                outputData.getAssets());
//    }

    public List<Asset> getAssets() {
        return assets;
    }
}