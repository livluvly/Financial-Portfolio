package data_access;

import org.json.JSONObject;
import use_case.transaction.priceDataAccessInterface;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class AlphaVantageExchangeRateDataAccessObject implements priceDataAccessInterface {
    private static final String API_KEY = "WIDFJSNJ4249RPKM";
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    /**
     * @param symbol 
     * @return
     * @throws IOException
     */
    @Override
    public double getLatestPrice(String symbol) throws IOException {
        return 0.0;
    }

    /**
     * @param symbol 
     * @return
     */
    @Override
    public double[] getLatestPrices(String symbol) {
        return new double[0];
    }

    @Override
    public double getExchangeRate(String fromCurrency, String toCurrency) throws IOException {
        String queryUrl = String.format("%s?function=CURRENCY_EXCHANGE_RATE&from_currency=%s&to_currency=%s&apikey=%s",
                BASE_URL, fromCurrency, toCurrency, API_KEY);

        HttpURLConnection connection = (HttpURLConnection) new URL(queryUrl).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            JSONObject json = new JSONObject(response.toString());
            String rate = json.getJSONObject("Realtime Currency Exchange Rate").getString("5. Exchange Rate");
            return Double.parseDouble(rate);
        } catch (Exception e) {
            e.printStackTrace();
            return 1.0;
        }
    }
}

