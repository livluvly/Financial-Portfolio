package data_access;
import entity.SearchResult;
import use_case.search.ExternalSearchDataAccessInterface;
import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;
import java.util.List;

public class AlphaVantageSearchDataAccessObject  implements ExternalSearchDataAccessInterface {
    private static final String API_KEY = "OFZTKRAPDVNFE1PX";

    /**
     * @param keyword 
     * @return
     */
    @Override
    public List<SearchResult> searchByKeyword(String keyword) {
        List<SearchResult> results = new ArrayList<>();
        try {
            URL url = new URL(String.format(
                    "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=%s&apikey=%s",
                    URLEncoder.encode(keyword, "UTF-8"), API_KEY));
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine=in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray resultArray = jsonObject.getJSONArray("bestMatches");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject resultObject = resultArray.getJSONObject(i);
                String symbol = resultObject.getString("1. symbol");
                String name = resultObject.getString("2. name");
                results.add(new SearchResult(name, symbol));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
