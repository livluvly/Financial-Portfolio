package data_access;

import entity.SearchResult;

import use_case.search.ExternalSearchDataAccessInterface;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;


public class AlphaVantageSearchDataAccessObject implements ExternalSearchDataAccessInterface {
    private static final String API_KEY = "demo";
    private final ReaderFactory readerFactory;

    public AlphaVantageSearchDataAccessObject() {
        this.readerFactory = (keyword) -> {
            String url = String.format(
                    "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=%s&apikey=%s",
                    URLEncoder.encode(keyword, "UTF-8"), API_KEY);
            URLConnection connection = new URL(url).openConnection();
            return new BufferedReader(new InputStreamReader(connection.getInputStream()));
        };
    }

    // Constructor for test injection
    public AlphaVantageSearchDataAccessObject(ReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }

    @Override
    public List<SearchResult> searchByKeyword(String keyword) {
        List<SearchResult> results = new ArrayList<>();
        try (BufferedReader reader = readerFactory.createReader(keyword)) {
            StringBuilder responseBuffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuffer.append(line);
            }

            JSONObject responseJson = new JSONObject(responseBuffer.toString());
            JSONArray matches = responseJson.optJSONArray("bestMatches");

            if (matches != null) {
                for (int i = 0; i < matches.length(); i++) {
                    JSONObject match = matches.getJSONObject(i);
                    String symbol = match.optString("1. symbol", "");
                    String name = match.optString("2. name", "");
                    String type = match.optString("3. type", "");
                    String region = match.optString("4. region", "");

                    results.add(new SearchResult(symbol, name, type, region));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    @FunctionalInterface
    public interface ReaderFactory {
        BufferedReader createReader(String keyword) throws IOException;
    }
}
