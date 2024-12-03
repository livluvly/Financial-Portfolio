package entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void getDate() {
        Date date = new Date();
        Transaction transaction = new Transaction("AAPL", 10, date, 1500.0, "BUY");

        assertEquals(date, transaction.getDate());

    }

    @Test
    void getSymbol() {
        String symbol = "AAPL";
        Transaction transaction = new Transaction(symbol, 10, new Date(), 1500.0, "BUY");

        assertEquals(symbol, transaction.getSymbol());
    }

    @Test
    void getQuantity() {
        int quantity = 10;
        Transaction transaction = new Transaction("AAPL", quantity, new Date(), 1500.0, "BUY");

        assertEquals(quantity, transaction.getQuantity());

    }

    @Test
    void getTotalCost() {
        double cost = 1500.0;
        Transaction transaction = new Transaction("AAPL", 10, new Date(), cost, "BUY");

        assertEquals(cost, transaction.getTotalCost());
    }

    @Test
    void getType() {
        String type = "BUY";
        Transaction transaction = new Transaction("AAPL", 10, new Date(), 1500.0, type);

        assertEquals(type, transaction.getType());
    }

}