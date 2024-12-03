package entity;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    final Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));

    @Test
    void testValidTransactionCreation() {
        Transaction transaction = new Transaction("AAPL", 10, now, 11.0, "BUY");

        assertEquals("AAPL", transaction.getSymbol());
        assertEquals(10, transaction.getQuantity());
        assertEquals(150.0, transaction.getTotalCost());
        assertEquals(now, transaction.getDate());
        assertEquals("BUY", transaction.getType());
    }

    @Test
    void testInvalidQuantityThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Transaction("AAPL", -5, now,150.0,  "SELL")
        );
    }

    @Test
    void testInvalidPriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Transaction("AAPL", 10, now,-150.0,  "SELL")
        );
    }

    @Test
    void testNullAssetSymbolThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Transaction(null, 10, now, 150.0, "BUY")
        );
    }
}
