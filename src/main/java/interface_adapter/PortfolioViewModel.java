package interface_adapter;

import entity.Asset;
import java.util.List;

import interface_adapter.portfolio.PortfolioState;
import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioOutputData;

public class PortfolioViewModel extends ViewModel<PortfolioState> {
//    private final List<Asset> assets;

    public PortfolioViewModel() {
        super("Portfolio");
        this.setState(new PortfolioState(List.of()));
    }
    public void updatePortfolio(List<Asset> assets) {
        PortfolioState newState = new PortfolioState(assets);
        this.setState(newState);
        this.firePropertyChanged();
    }


    public List<Asset> getAssets() {
        return this.getState().getAssets();
    }
}