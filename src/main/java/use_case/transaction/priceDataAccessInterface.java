package use_case.transaction;

import java.io.IOException;

public interface priceDataAccessInterface {

    double getLatestPrice(String symbol) throws IOException;

    double[] getLatestPrices(String symbol);

    double getExchangeRate(String fromCurrency, String toCurrency) throws IOException;
}
