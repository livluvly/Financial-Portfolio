package view;

import entity.Asset;
import interfaceAdapter.PortfolioViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPortfolioViewModel extends PortfolioViewModel {

    private List<Asset> assets;
    private Map<String, Double> ratesMap;
    private boolean transactionCalled;

    public TestPortfolioViewModel() {
        super("foobar", null, null);
        assets = List.of(
                new Asset("AAPL", 10, 100, 1000, 5.0, 0.03),
                new Asset("GOOGL", 5, 2800.0, 100.0));
        ratesMap = new HashMap<>();
        ratesMap.put("EUR", 0.85);
        ratesMap.put("USD", 1.0);
        ratesMap.put("GBP", 0.95);
        transactionCalled = false;
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
        transactionCalled = true;
    }
    @Override
    public double getExchangeRate(String fromCurrency, String toCurrency) {
        return ratesMap.get(toCurrency);
    }

    public void setMockExchangeRate(String fromCurrency, String toCurrency, double rate) {
        ratesMap.put(toCurrency, rate);
    }


    public boolean isTransactionCalled() {
        return transactionCalled;
    }
    public void setAssets(List<Asset> assets) {
        this.assets = assets;
        this.transactionCalled = true;
    }

}
