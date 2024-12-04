package data_access;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AlphaVantageAssetPriceDataAccessObjectTest {
    private AlphaVantageAssetPriceDataAccessObject dataAccessObject;

    @BeforeEach
    void setUp() {
        dataAccessObject = new AlphaVantageAssetPriceDataAccessObject() {
            @Override
            public double[] getLatestPrices(String symbol) {
                if (symbol.equals("INVALID")) {
                    return new double[]{-1, -1};
                }

                else if (symbol.isEmpty()) {
                    return new double[]{-1, -1};
                }

                // mock JSON response
                String jsonResponse = """
                    {
                        "Time Series (Daily)": {
                            "2024-12-01": {"4. close": "150.0"},
                            "2024-11-30": {"4. close": "145.0"}
                        }
                    }
                """;

                try {
                    JSONObject json = new JSONObject(jsonResponse);
                    JSONObject timeSeries = json.getJSONObject("Time Series (Daily)");

                    // Extract latest and previous dates
                    String latestDate = "2024-12-01";
                    String previousDate = "2024-11-30";

                    JSONObject latestData = timeSeries.getJSONObject(latestDate);
                    JSONObject previousData = timeSeries.getJSONObject(previousDate);

                    double latestClose = latestData.getDouble("4. close");
                    double previousClose = previousData.getDouble("4. close");

                    return new double[]{latestClose, previousClose};
                } catch (Exception e) {
                    return new double[]{-1, -1};
                }
            }

            @Override
            public double getExchangeRate(String fromCurrency, String toCurrency) {
                return 0.0; // Mock exchange rate always returning 0
            }
        };
    }

    @Test
    void testGetLatestPrice() {
        double latestPrice = dataAccessObject.getLatestPrice("AAPL");
        assertEquals(150.0, latestPrice);
    }

    @Test
    void testGetLatestPrices() {
        double[] prices = dataAccessObject.getLatestPrices("AAPL");
        assertEquals(150.0, prices[0]);
        assertEquals(145.0, prices[1]);
    }

    @Test
    void testGetInvalidLatestPrices() {
        double[] prices = dataAccessObject.getLatestPrices("INVALID");
        assertEquals(-1, prices[0]);
        assertEquals(-1, prices[1]);
    }

    @Test
    void testGetLatestPricesWithEmptySymbol() {
        double[] prices = dataAccessObject.getLatestPrices("");
        assertEquals(-1, prices[0]);
        assertEquals(-1, prices[1]);
    }


    @Test
    void testGetExchangeRate() throws IOException {
        double exchangeRate = dataAccessObject.getExchangeRate("USD", "EUR");
        assertEquals(0.0, exchangeRate);
    }
}
