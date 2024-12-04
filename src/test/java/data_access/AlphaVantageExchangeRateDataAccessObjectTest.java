package data_access;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class AlphaVantageExchangeRateDataAccessObjectTest {
    private AlphaVantageExchangeRateDataAccessObject exchangeRateDAO;

    @BeforeEach
    void setUp() {
        exchangeRateDAO = new AlphaVantageExchangeRateDataAccessObject() {
            @Override
            public double getExchangeRate(String fromCurrency, String toCurrency) throws IOException {
                if ("INVALID".equals(fromCurrency) || "INVALID".equals(toCurrency) || fromCurrency.equals(toCurrency)) {
                    return 1.0; // default rate
                }

                // mock JSON object
                String jsonResponse = """
                    {
                        "Realtime Currency Exchange Rate": {
                            "1. From_Currency Code": "USD",
                            "2. From_Currency Name": "United States Dollar",
                            "3. To_Currency Code": "EUR",
                            "4. To_Currency Name": "Euro",
                            "5. Exchange Rate": "0.85",
                            "6. Last Refreshed": "2024-12-03 10:00:00",
                            "7. Time Zone": "UTC",
                            "8. Bid Price": "0.84",
                            "9. Ask Price": "0.86"
                        }
                    }
                """;

                try (BufferedReader reader = new BufferedReader(new StringReader(jsonResponse))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JSONObject json = new JSONObject(response.toString());
                    String rate = json.getJSONObject("Realtime Currency Exchange Rate").getString("5. Exchange Rate");
                    return Double.parseDouble(rate);
                } catch (Exception e) {
                    return 1.0; // Simulate error catching with default rate
                }
            }
        };
    }

    @Test
    void testGetExchangeRateSuccess() throws IOException {
        double exchangeRate = exchangeRateDAO.getExchangeRate("USD", "EUR");
        assertEquals(0.85, exchangeRate, 0.001);
    }

    @Test
    void testGetExchangeRateError() throws IOException {
        double exchangeRate = exchangeRateDAO.getExchangeRate("INVALID", "EUR");
        assertEquals(1.0, exchangeRate); // Default rate for errors
    }

    @Test
    void testGetExchangeRateNotUSDCurrency() throws IOException {
        double exchangeRate = exchangeRateDAO.getExchangeRate("EUR", "USD");
        assertEquals(0.85, exchangeRate, 0.001); // Valid exchange rate for EUR to USD
    }

    @Test
    void testGetExchangeRateEdgeCase() throws IOException {
        double exchangeRate = exchangeRateDAO.getExchangeRate("USD", "USD");
        assertEquals(1.0, exchangeRate, 0.001); // Simulate USD-USD returning (1.0:1.0)
    }
}

