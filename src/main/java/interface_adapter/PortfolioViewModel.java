package interface_adapter;

import entity.Asset;

import java.io.IOException;
import java.util.List;

import interface_adapter.transaction_history.TransactionHistoryState;
import use_case.transaction.priceDataAccessInterface;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioState;

import javax.sound.sampled.Port;

public class PortfolioViewModel extends ViewModel<PortfolioState> {
    private priceDataAccessInterface priceDAO;


    private priceDataAccessInterface currencyDAO;
    private PortfolioController controller;

    public PortfolioViewModel(){
        super("Portfolio");
        this.setState(new PortfolioState(List.of(), null));
    }

    public PortfolioViewModel(String username,
                              priceDataAccessInterface priceDAO,
                              priceDataAccessInterface currencyDAO) {
        super("Portfolio");
        this.priceDAO = priceDAO;
        this.currencyDAO = currencyDAO;
        this.setState(new PortfolioState(List.of(), username));
    }
    public void setName(String name){
        PortfolioState newState = this.getState();
        newState.setUsername(name);
        this.setState(newState);
    }

    public void setController(PortfolioController controller) {
        this.controller = controller;
    }

    public void setCurrencyDAO(priceDataAccessInterface currencyDAO) {

        this.currencyDAO = currencyDAO;
    }

    public void setPriceDAO(priceDataAccessInterface priceDAO) {

        this.priceDAO = priceDAO;
    }
    public void updatePortfolio(List<Asset> assets) {
        for (Asset asset : assets) {
            double[] dailyData = priceDAO.getLatestPrices(asset.getSymbol());
            if (dailyData[0] != -1 && dailyData[1] != -1) {
                asset.setValuePerUnit(dailyData[0]);
                asset.setDailyGain(asset.getQuantity()*(dailyData[0]-dailyData[1]));
                asset.setDailyGainPercentage(
                        (dailyData[0]-dailyData[1])*100/dailyData[1]
                );
                asset.setTotalValue(asset.getQuantity()*dailyData[0]);
            } else {
                System.out.println("Error fetching data for asset: " + asset.getSymbol());
            }
        }
        PortfolioState newState = new PortfolioState(assets, this.getState().getUsername());
        this.setState(newState);
        this.firePropertyChanged();
    }

    public double getExchangeRate(String in, String out) throws IOException {
        return currencyDAO.getExchangeRate(in,out);
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

    public void updateAsset(String username, String symbol, double newQuantity) {
        controller.updateAsset(username, symbol, newQuantity);
        updatePortfolio(controller.fetchAssets(username).getAssets());
    }

    public void deleteAsset(String username, String symbol) {
        controller.deleteAsset(username, symbol);
        updatePortfolio(controller.fetchAssets(username).getAssets());
    }

}