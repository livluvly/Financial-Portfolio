package interface_adapter;

import entity.Asset;
import java.util.List;

import entity.User;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioState;

public class PortfolioViewModel extends ViewModel<PortfolioState> {
//    private final List<Asset> assets;
    private PortfolioController controller;

    public PortfolioViewModel() {
        super("Portfolio");
        this.setState(new PortfolioState(List.of(), "foobar"));
    }

    public void setController(PortfolioController controller) {
        this.controller = controller;
    }
    public void updatePortfolio(List<Asset> assets) {
        PortfolioState newState = new PortfolioState(assets, "foobar");
        this.setState(newState);
        this.firePropertyChanged();
    }


    public List<Asset> getAssets() {
        return this.getState().getAssets();
    }

    public void savePortfolio(String username, List<Asset> assets) {
        controller.savePortfolio(username);
    }

    public void addTransaction(String username, String symbol, double quantity, double value, double gain) {
        controller.addTransaction(username, symbol, quantity, value, gain);
        updatePortfolio(controller.fetchAssets(username).getAssets());
    }

    public void updateAsset(String username, String symbol, double newQuantity, double newValue) {
        controller.updateAsset(username, symbol, newQuantity, newValue);
        updatePortfolio(controller.fetchAssets(username).getAssets());
    }

    public void deleteAsset(String username, String symbol) {
        controller.deleteAsset(username, symbol);
        updatePortfolio(controller.fetchAssets(username).getAssets());
    }

}