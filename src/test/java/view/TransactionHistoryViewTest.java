package view;

import entity.Transaction;
import interface_adapter.TransactionHistoryViewModel;
import org.junit.jupiter.api.Test;

import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionHistoryViewTest {
    @Test
    void populateTableTest() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("NVDA", 10, new Date(), 1000.0, "BUY"));
        transactions.add(new Transaction("AAPL", 20, new Date(), 1500.0, "SELL"));

        TransactionHistoryViewModel viewModel = new TransactionHistoryViewModel();
        viewModel.updateTransactionHistory(transactions);

        TransactionHistoryView view = new TransactionHistoryView(viewModel);
        TableModel tableModel = view.getTableModel();
        assertEquals(2, tableModel.getRowCount(), "The table should have 2 rows.");

        assertEquals("NVDA", tableModel.getValueAt(0, 1), "The first row should display the NVDA symbol.");
        assertEquals(10.0, tableModel.getValueAt(0, 2), "The first row should display the NVDA quantity.");
        assertEquals(1000.0, tableModel.getValueAt(0, 3), "The first row should display the NVDA total cost.");
        assertEquals("BUY", tableModel.getValueAt(0, 4), "The first row should display the NVDA transaction type.");

        assertEquals("AAPL", tableModel.getValueAt(1, 1), "The second row should display the AAPL symbol.");
        assertEquals(20.0, tableModel.getValueAt(1, 2), "The second row should display the AAPL quantity.");
        assertEquals(1500.0, tableModel.getValueAt(1, 3), "The second row should display the AAPL total cost.");
        assertEquals("SELL", tableModel.getValueAt(1, 4), "The second row should display the AAPL transaction type.");
    }

    @Test
    void sortingTestAlphabetically() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("AAPL", 20, new Date(), 1500.0, "SELL"));
        transactions.add(new Transaction("NVDA", 10, new Date(), 1000.0, "BUY"));

        TransactionHistoryViewModel viewModel = new TransactionHistoryViewModel();
        viewModel.updateTransactionHistory(transactions);

        TransactionHistoryView view = new TransactionHistoryView(viewModel);

        TableModel tableModel = view.getTableModel();

        assertEquals("AAPL", tableModel.getValueAt(0, 1), "The first row should display the AAPL symbol after sorting.");
        assertEquals("NVDA", tableModel.getValueAt(1, 1), "The second row should display the NVDA symbol after sorting.");
    }
}