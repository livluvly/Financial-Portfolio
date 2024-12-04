package data_access;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import use_case.transaction.*;


public class AlphaVantageAssetPriceDataAccessObject implements  priceDataAccessInterface{
    private static final String API_KEY = "OFZTKRAPDVNFE1PX";
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    @Override
    public double getLatestPrice(String symbol) {
        return getLatestPrices(symbol)[0];
    }
    
    @Override
    public double[] getLatestPrices(String symbol) {
        try {
            String urlStr = String.format("%s?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s",
                    BASE_URL, symbol, API_KEY);
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the JSON to extract the latest price
            JSONObject json = new JSONObject(response.toString());
            JSONObject timeSeries = json.getJSONObject("Time Series (Daily)");

            List<String> dates = new ArrayList<>();
            timeSeries.keys().forEachRemaining(dates::add);
            Collections.sort(dates, Collections.reverseOrder());

            System.out.println(dates);
            String latestDate = dates.get(0);
            String previousDate = dates.get(1);
            JSONObject latestData = timeSeries.getJSONObject(latestDate);
            JSONObject previousData = timeSeries.getJSONObject(previousDate);
            double latestClose = latestData.getDouble("4. close");
            double previousClose = previousData.getDouble("4. close");

            return new double[]{latestClose, previousClose}; // Fetch the closing price
        } catch (Exception e) {
            e.printStackTrace();
            return new double[]{-1,-1}; // Return -1 to indicate an error
        }
    }

    /**
     * @param fromCurrency 
     * @param toCurrency
     * @return
     * @throws IOException
     */
    @Override
    public double getExchangeRate(String fromCurrency, String toCurrency) throws IOException {
        return 0.0;
    }
}
