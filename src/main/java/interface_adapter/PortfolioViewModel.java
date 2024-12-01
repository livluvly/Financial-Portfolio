package interface_adapter;

import entity.Asset;
import java.util.List;

import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioState;
import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioOutputData;

public class PortfolioViewModel extends ViewModel<PortfolioState> {
//    private final List<Asset> assets;
    private PortfolioController controller;

    public PortfolioViewModel() {
        super("Portfolio");
        this.setState(new PortfolioState(List.of()));
    }

    public void setController(PortfolioController controller) {
        this.controller = controller;
    }
    public void updatePortfolio(List<Asset> assets) {
        PortfolioState newState = new PortfolioState(assets);
        this.setState(newState);
        this.firePropertyChanged();
    }


    public List<Asset> getAssets() {
        return this.getState().getAssets();
    }

    public void savePortfolio(String username) {
        controller.savePortfolio(username);
    }

    public void addTransaction(String username, String symbol, double quantity, double value, double gain) {
        controller.addTransaction(username, symbol, quantity, value, gain);
        updatePortfolio(controller.fetchAssets(username).getAssets());
    }

    public void updateAsset(String username, String symbol, double newQuantity) {
        controller.updateAsset(username, symbol, newQuantity);
        updatePortfolio(controller.fetchAssets(username).getAssets());
    }

    public void deleteAsset(String username, String symbol) {
        controller.deleteAsset(username, symbol);
        updatePortfolio(controller.fetchAssets(username).getAssets());
    }

}