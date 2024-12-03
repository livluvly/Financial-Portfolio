package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchResultTest {

    @Test
    void testValidCreation() {
        SearchResult result = new SearchResult("AAPL", "Apple Inc.", "Equity", "United States");

        assertEquals("AAPL", result.getSymbol());
        assertEquals("Apple Inc.", result.getName());
        assertEquals("Equity", result.getType());
        assertEquals("United States", result.getRegion());
    }

    @Test
    void testInvalidSymbolThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new SearchResult(null, "Apple Inc.", "Equity", "United States"));
        assertThrows(IllegalArgumentException.class, () ->
                new SearchResult("   ", "Apple Inc.", "Equity", "United States"));
    }

    @Test
    void testInvalidNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new SearchResult("AAPL", null, "Equity", "United States"));
        assertThrows(IllegalArgumentException.class, () ->
                new SearchResult("AAPL", "   ", "Equity", "United States"));
    }

    @Test
    void testInvalidTypeThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new SearchResult("AAPL", "Apple Inc.", null, "United States"));
        assertThrows(IllegalArgumentException.class, () ->
                new SearchResult("AAPL", "Apple Inc.", "   ", "United States"));
    }

    @Test
    void testInvalidRegionThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new SearchResult("AAPL", "Apple Inc.", "Equity", null));
        assertThrows(IllegalArgumentException.class, () ->
                new SearchResult("AAPL", "Apple Inc.", "Equity", "   "));
    }

    @Test
    void testToString() {
        SearchResult result = new SearchResult("AAPL", "Apple Inc.", "Equity", "United States");
        String expected = "SearchResult{symbol='AAPL', name='Apple Inc.', type='Equity', region='United States'}";
        assertEquals(expected, result.toString());
    }
}
