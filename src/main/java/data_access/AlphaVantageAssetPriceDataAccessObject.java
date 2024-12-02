package data_access;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class AlphaVantageAssetPriceDataAccessObject {
    private static final String API_KEY = "OFZTKRAPDVNFE1PX";
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    public static double getLatestPrice(String symbol) {
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
            String latestDate = timeSeries.keys().next(); // Get the most recent date
            JSONObject latestData = timeSeries.getJSONObject(latestDate);

            return latestData.getDouble("4. close"); // Fetch the closing price
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return -1 to indicate an error
        }
    }
}
