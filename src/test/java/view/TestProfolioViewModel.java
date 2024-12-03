package view;

import entity.Asset;
import interface_adapter.PortfolioViewModel;
import use_case.transaction.priceDataAccessInterface;

import java.util.List;

public class TestProfolioViewModel  extends PortfolioViewModel {
    private List<Asset> assets;
    public TestProfolioViewModel() {
        super("foobar", null, null);
        assets = List.of(
                new Asset("AAPL", 10, 100, 1000, 5.0, 0.03),
                new Asset("GOOGL", 5, 2800.0, 100.0));
    }

    @Override
    public List<Asset> getAssets() {
        return assets;
    }

    @Override
    public void updateAsset(String username, String symbol, double quantity){
        assets =  List.of(
                new Asset("AAPL", 10, 100, 1000, 5.0, 0.03),
                new Asset("GOOGL", 5, 2800.0, 100.0));
    }
    @Override
    public double getExchangeRate(String fromCurrency, String toCurrency) {

        double res = toCurrency.equals("EUR") ? 0.85 :
                toCurrency.equals("USD") ? 1.0 : toCurrency.equals("GBP") ? 0.95: 0;
        return res; // Example rate
    }

}
