package interface_adapter;

import entity.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class TransactionHistoryViewModelTest {
    @Test
    void updateTransactionHistoryViewModelTest() {
        TransactionHistoryViewModel viewModel = new TransactionHistoryViewModel();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("NVDA", 10, new Date(), 1000.0, "BUY"));
        transactions.add(new Transaction("AAPL", 20, new Date(), 1500.0, "SELL"));

        viewModel.updateTransactionHistory(transactions);

        List<Transaction> updatedTransactions = viewModel.getTransactionHistory();
        assertEquals(2, updatedTransactions.size());

        assertEquals("NVDA", updatedTransactions.get(0).getSymbol());
        assertEquals(10, updatedTransactions.get(0).getQuantity());
        assertEquals(new Date().toString(), updatedTransactions.get(0).getDate().toString());
        assertEquals(1000.0, updatedTransactions.get(0).getTotalCost());
        assertEquals("BUY", updatedTransactions.get(0).getType());

        assertEquals("AAPL", updatedTransactions.get(1).getSymbol());
        assertEquals(20, updatedTransactions.get(1).getQuantity());
        assertEquals(new Date().toString(), updatedTransactions.get(1).getDate().toString());
        assertEquals(1500.0, updatedTransactions.get(1).getTotalCost());
        assertEquals("SELL", updatedTransactions.get(1).getType());
    }

    @Test
    void getTransactionHistoryEmptyTest() {
        TransactionHistoryViewModel viewModel = new TransactionHistoryViewModel();
        List<Transaction> transactions = new ArrayList<>();

        assertNotNull(transactions);
        assertTrue(transactions.isEmpty());
        assertTrue(viewModel.getTransactionHistory().isEmpty());
    }

    @Test
    void getTransactionHistoryUpdatedTest() {
        TransactionHistoryViewModel viewModel = new TransactionHistoryViewModel();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("NVDA", 10, new Date(), 1000.0, "BUY"));
        viewModel.updateTransactionHistory(transactions);

        viewModel.updateTransactionHistory(transactions);
        List<Transaction> updatedTransactions = viewModel.getTransactionHistory();

        assertNotNull(updatedTransactions);
        assertEquals(1, updatedTransactions.size());

        assertEquals("NVDA", updatedTransactions.get(0).getSymbol());
        assertEquals(10, updatedTransactions.get(0).getQuantity());
        assertEquals(new Date().toString(), updatedTransactions.get(0).getDate().toString());
        assertEquals(1000.0, updatedTransactions.get(0).getTotalCost());
        assertEquals("BUY", updatedTransactions.get(0).getType());
    }

    @Test
    void getTransactionHistoryEmptyUpdatedTest() {
        TransactionHistoryViewModel viewModel = new TransactionHistoryViewModel();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("AMZN", 10, new Date(), 1000.0, "BUY"));
        viewModel.updateTransactionHistory(transactions);

        viewModel.updateTransactionHistory(new ArrayList<>());
        List<Transaction> updatedTransactions = viewModel.getTransactionHistory();

        assertNotNull(updatedTransactions);
        assertTrue(updatedTransactions.isEmpty());
    }
  
}