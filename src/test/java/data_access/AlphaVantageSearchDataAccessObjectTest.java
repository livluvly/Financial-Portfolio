package data_access;

import entity.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlphaVantageSearchDataAccessObjectTest {
    private AlphaVantageSearchDataAccessObject searchDAO;

    @BeforeEach
    void setUp() {
        // Inject mock ReaderFactory for test cases
        searchDAO = new AlphaVantageSearchDataAccessObject(keyword -> {
            String mockResponse;
            switch (keyword) {
                case "AAPL":
                    mockResponse = """
                        {
                            "bestMatches": [
                                {
                                    "1. symbol": "AAPL",
                                    "2. name": "Apple Inc.",
                                    "3. type": "Equity",
                                    "4. region": "United States"
                                },
                                {
                                    "1. symbol": "AAPL34",
                                    "2. name": "Apple Inc.",
                                    "3. type": "Equity",
                                    "4. region": "Brazil"
                                }
                            ]
                        }
                    """;
                    break;
                case "GOOGL":
                    mockResponse = """
                        {
                            "bestMatches": [
                                {
                                    "1. symbol": "GOOGL",
                                    "2. name": "Alphabet Inc.",
                                    "3. type": "Equity",
                                    "4. region": "United States"
                                }
                            ]
                        }
                    """;
                    break;
                default:
                    mockResponse = """
                        {
                            "bestMatches": []
                        }
                    """;
                    break;
            }
            return new BufferedReader(new StringReader(mockResponse));
        });
    }


    @Test
    void testSearchByValidKeyword1() {
        List<SearchResult> results = searchDAO.searchByKeyword("GOOGL");
        assertNotNull(results, "Results should not be null");
        assertEquals(1, results.size(), "Expected 1 result for GOOGL");

        SearchResult result = results.get(0);
        assertEquals("GOOGL", result.getSymbol());
        assertEquals("Alphabet Inc.", result.getName());
        assertEquals("Equity", result.getType());
        assertEquals("United States", result.getRegion());
    }


    @Test
    void testSearchByValidKeyword2() {
        List<SearchResult> results = searchDAO.searchByKeyword("AAPL");
        assertNotNull(results, "Results should not be null");
        assertEquals(2, results.size(), "Expected 2 results for AAPL");

        SearchResult firstResult = results.get(0);
        assertEquals("AAPL", firstResult.getSymbol());
        assertEquals("Apple Inc.", firstResult.getName());
        assertEquals("Equity", firstResult.getType());
        assertEquals("United States", firstResult.getRegion());

        SearchResult secondResult = results.get(1);
        assertEquals("AAPL34", secondResult.getSymbol());
        assertEquals("Apple Inc.", secondResult.getName());
        assertEquals("Equity", secondResult.getType());
        assertEquals("Brazil", secondResult.getRegion());
    }


    @Test
    void testSearchByInvalidKeyword() {
        List<SearchResult> results = searchDAO.searchByKeyword("INVALID");
        assertNotNull(results, "Results should not be null");
        assertTrue(results.isEmpty(), "Expected no results for INVALID");
    }

    @Test
    void testSearchByEmptyKeyword() {
        List<SearchResult> results = searchDAO.searchByKeyword("");
        assertNotNull(results, "Results should not be null");
        assertTrue(results.isEmpty(), "Expected no results for empty keyword");
    }


    @Test
    void testSearchHandlesException() {
        searchDAO = new AlphaVantageSearchDataAccessObject(keyword -> {
            throw new RuntimeException("Mocked exception");
        });

        List<SearchResult> results = searchDAO.searchByKeyword("AAPL");
        assertNotNull(results, "Results should not be null");
        assertTrue(results.isEmpty(), "Expected no results due to exception");
    }

}

