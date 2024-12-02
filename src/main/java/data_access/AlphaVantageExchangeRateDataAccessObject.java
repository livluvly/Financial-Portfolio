package data_access;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class AlphaVantageExchangeRateDataAccessObject {
    private static final String API_KEY = "demo";
    private static final String BASE_URL = "https://www.alphavantage.co/query";


    public static Double getExchangeRate(String fromCurrency, String toCurrency) throws IOException {
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
            return null;
        }
    }
}

