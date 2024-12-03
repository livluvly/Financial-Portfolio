package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.io.IOException;
import java.util.List;
import entity.Asset;


class PortfolioViewIntegrationTest {
    private PortfolioView portfolioView;
    private TestPortfolioViewModel testViewModel;
    private JComboBox<String> currencySelector;

    @BeforeEach
    void setUp() {
        // Mock the PortfolioViewModel
        testViewModel = new TestPortfolioViewModel();
        portfolioView = new PortfolioView(testViewModel);

        // Initialize the JComboBox for currency selection
        currencySelector = portfolioView.currencySelector;
    }

    @Test
    void testInitialCurrencySelection() {
        // Test the initial state of the currency selector
        assertEquals("USD", currencySelector.getSelectedItem(), "Default currency should be USD");
    }

    @Test
    void testUpdateExchangeRate() throws IOException {
        // Set mock exchange rates and trigger the update
        testViewModel.setMockExchangeRate("USD", "EUR", 0.9);
        portfolioView.currencySelector.setSelectedItem("EUR");

        // Trigger exchange rate update
        portfolioView.updateExchangeRate();

        // Verify the update happened correctly
        assertEquals("Exchange Rate: 1 USD = 0.90 EUR", portfolioView.exchangeRateLabel.getText());
    }

    @Test
    void testPortfolioTableUpdates() {
        // Mock some assets
        Asset asset1 = new Asset("AAPL", 10, 150, 100);
        Asset asset2 = new Asset("GOOG", 5, 2800, 120);
        testViewModel.setAssets(List.of(asset1, asset2));

        // Simulate portfolio update
        portfolioView.updateView();

        // Assert that table is updated
        PortfolioView.PortfolioTableModel tableModel = (PortfolioView.PortfolioTableModel) portfolioView.portfolioTable.getModel();
        assertEquals(2, tableModel.getRowCount(), "Table should have 2 rows");
        assertEquals("AAPL", tableModel.getValueAt(0, 0), "First row should contain AAPL symbol");
    }

    @Test
    void testHandleAssetSell() {
        Asset asset = new Asset("AAPL", 10, 150, 100);
        testViewModel.setAssets(List.of(asset));
        portfolioView.updateView();

        // Mock the sell process
        portfolioView.handleAssetSell(asset);

        // Verify that transaction controller methods are called (mocked)
        assertTrue(testViewModel.isTransactionCalled(), "Transaction should have been initiated");
    }


    // Add more tests as needed for error cases, UI interactions, etc.
}
