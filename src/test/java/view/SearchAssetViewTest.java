package view;

import entity.SearchResult;
import entity.Transaction;
import interfaceAdapter.PortfolioViewModel;
import interfaceAdapter.SearchAssetViewModel;
import interfaceAdapter.TransactionHistoryViewModel;
import interfaceAdapter.portfolio.PortfolioController;
import interfaceAdapter.search.SearchAssetController;
import interfaceAdapter.transaction.TransactionController;
import interfaceAdapter.transactionHistory.TransactionHistoryController;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SearchAssetViewTest {
    private SearchAssetViewModel mockViewModel;
    private SearchAssetView searchAssetView;

    @BeforeEach
    void setUp() {
        SearchAssetController mockController = mock(SearchAssetController.class);
        String mockUsername = "bill";

        mockViewModel = new SearchAssetViewModel(mockController, mockUsername);
        searchAssetView = new SearchAssetView(mockViewModel);
    }

    @Test
    public void testSearchResultUpdateTable() {
        List<SearchResult> searchResults = List.of(
                new SearchResult("AAPL", "Apple Inc.", "Equity", "United States"),
                new SearchResult("TSLA", "Tesla Inc.", "Equity", "United States")
        );
        when(mockViewModel.getSearchResults()).thenReturn(searchResults);

        mockViewModel.updateSearchResults(searchResults);

        JTable table = (JTable) searchAssetView.getComponent(1);
        TableModel tableModel = table.getModel();

        assertEquals(2, tableModel.getRowCount());
        assertEquals("AAPL", tableModel.getValueAt(0, 0));
        assertEquals("Apple Inc.", tableModel.getValueAt(0, 1));
        assertEquals("TSLA", tableModel.getValueAt(1, 0));
        assertEquals("Tesla Inc.", tableModel.getValueAt(1, 1));
    }




}