package view;

import entity.Asset;
import interfaceAdapter.PortfolioViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioViewTest {
    private PortfolioViewModel mockViewModel;
    private PortfolioView portfolioView;

    @BeforeEach
    void setUp() throws IOException {
        mockViewModel = new TestPortfolioViewModel();
        portfolioView = new PortfolioView(mockViewModel);
    }

    @Test
    void testPortfolioTableInitialSetup() {
        JTable table = portfolioView.portfolioTable;
        assertNotNull(table, "Portfolio table should be initialized.");
        assertEquals(4, table.getColumnCount(), "Table should have 4 columns.");
    }

    @Test
    public void testCurrencySelector(){
        assertEquals(4, portfolioView.currencySelector.getItemCount());
        assertTrue(Arrays.asList("USD", "EUR", "GBP", "JPY").containsAll(Arrays.asList(portfolioView.currencySelector.getItemAt(0), portfolioView.currencySelector.getItemAt(1), portfolioView.currencySelector.getItemAt(2), portfolioView.currencySelector.getItemAt(3))));
    }


    @Test
    void testUpdateExchangeRateWithValidCurrency() throws Exception {
        portfolioView.currencySelector.setSelectedItem("EUR");
        portfolioView.updateExchangeRate();

        JLabel exchangeRateLabel = portfolioView.exchangeRateLabel;
        assertEquals("Exchange Rate: 1 USD = 0.85 EUR", exchangeRateLabel.getText());
    }



    @Test
    void testUpdateViewUpdatesTableModel() {

        mockViewModel.updateAsset("foobar","APPL",1);
        portfolioView.updateView();

        PortfolioView.PortfolioTableModel tableModel = (PortfolioView.PortfolioTableModel) portfolioView.portfolioTable.getModel();
        assertEquals(2, tableModel.getRowCount(), "Table model should have updated row count.");
        assertEquals("AAPL", tableModel.getValueAt(0, 0), "First row symbol should match.");
    }

    @Test
    void testHandleAssetSellWithoutControllerShowsError() {
        Asset mockAsset = new Asset("GOOGL", 5, 2800.0, 100.0);

        portfolioView.handleAssetSell(mockAsset);

        // Ensure error dialog is shown
        assertNull(portfolioView.transactionController, "TransactionController should not be initialized.");
    }
}
