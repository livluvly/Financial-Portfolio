package view;

import interface_adapter.PortfolioViewModel;
import entity.Asset;
import entity.Transaction;
import interface_adapter.transaction.TransactionController;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class PortfolioView extends JPanel {
    private PortfolioViewModel viewModel;
    final JTable portfolioTable;
    private final PortfolioTableModel tableModel;
    final JComboBox<String> currencySelector;
    final JLabel exchangeRateLabel;
    private final HashMap<Object, Object> exchangeRates;
    TransactionController transactionController; // Nullable initially

    public PortfolioView(PortfolioViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new BorderLayout());

        // Add instruction label at the top
        JLabel sortingInstructions = new JLabel("Click on a column header to sort the table.", JLabel.RIGHT);
        sortingInstructions.setFont(new Font("Arial", Font.ITALIC, 12));
        sortingInstructions.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // Control panels
        currencySelector = new JComboBox<>(new String[] {"USD", "EUR", "GBP", "JPY"});
        exchangeRateLabel = new JLabel("Exchange Rate: 1 USD = 1.0 USD");
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);
        currencySelector.addActionListener(e -> {
            try {
                updateExchangeRate();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(new JLabel("Select Currency: "), BorderLayout.WEST);
        controlPanel.add(currencySelector, BorderLayout.CENTER);
        controlPanel.add(exchangeRateLabel, BorderLayout.EAST);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10)); // Add gap around the control panel

        // both instructions and controls go to a single panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(controlPanel, BorderLayout.SOUTH);
        northPanel.add(sortingInstructions, BorderLayout.NORTH);
        this.add(northPanel, BorderLayout.NORTH);

        // Initialize table for displaying assets
        portfolioTable = new JTable();
        tableModel = new PortfolioTableModel();
        portfolioTable.setModel(tableModel);

        // sorting feature and tooltips
        TableRowSorter<PortfolioTableModel> sorter = new TableRowSorter<>(tableModel);
        portfolioTable.setRowSorter(sorter);
        JTableHeader header = portfolioTable.getTableHeader();
        header.setToolTipText("Click on a column header to sort");
        header.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Set special renderer for the Daily Gain column
        portfolioTable.getColumnModel().getColumn(3).setCellRenderer(new DailyGainCellRenderer());


        this.add(new JScrollPane(portfolioTable), BorderLayout.CENTER);

        // Add a click listener for the table rows
        portfolioTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = portfolioTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    Asset asset = ((PortfolioTableModel) portfolioTable.getModel()).getAssetAt(row);
                    handleAssetSell(asset);
                }
            }
        });

        // Update view when the state changes
        viewModel.addPropertyChangeListener(evt -> updateView());
    }

    void updateExchangeRate() throws IOException {
        String selectedCurrency = (String) currencySelector.getSelectedItem();
        if (selectedCurrency != null && !selectedCurrency.equals("USD")) {
            // Fetch the exchange rate from AlphaVantage API
            Double rate = fetchExchangeRate(selectedCurrency);
            if (rate != 9) {
                exchangeRates.put(selectedCurrency, rate);
                exchangeRateLabel.setText(String.format("Exchange Rate: 1 USD = %.2f %s", rate, selectedCurrency));
            } else {
                throw new RuntimeException("Failed to fetch exchange rate.");
//                JOptionPane.showMessageDialog(this, "Failed to fetch exchange rate.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateView();
    }

    private Double fetchExchangeRate(String currency) throws IOException {
            return viewModel.getExchangeRate("USD", currency);
    }

    /**
     * Updates the table view when the portfolio changes.
     */
    void updateView() {
        List<Asset> assets = viewModel.getAssets();
        PortfolioTableModel tableModel = (PortfolioTableModel) portfolioTable.getModel();
        String selectedCurrency = (String) currencySelector.getSelectedItem();
        double rate = (double) exchangeRates.getOrDefault(selectedCurrency, 1.0);
        tableModel.updateData(assets, rate);
    }

    /**
     * Custom cell renderer for coloring the Daily Gain column.
     */
    private static class DailyGainCellRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            super.setValue(value);
            if (value instanceof String) {
                String[] parts = ((String) value).split(" ");
                try {
                    double gain = Double.parseDouble(parts[0]);
                    if (gain > 0) {
                        setForeground(Color.GREEN);
                    } else if (gain < 0) {
                        setForeground(Color.RED);
                    } else {
                        setForeground(Color.RED);
                    }
                } catch (NumberFormatException ignored) {
                    setForeground(Color.BLACK);
                }
            }
        }
    }

    /**
     * Handles the process of selling an asset by prompting the user for the amount.
     *
     * @param asset the asset to sell
     */
    void handleAssetSell(Asset asset) {
        if (transactionController == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "TransactionController is not initialized!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }


        Double quantityToSell = TransactionPopup.promptForQuantity(asset.getSymbol(),asset.getValuePerUnit(),"SELL");
        if (quantityToSell != null && quantityToSell > 0) {
            if (quantityToSell > asset.getQuantity()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Quantity to sell exceeds current holdings!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Create a sell transaction
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY,0);
            Transaction transaction = new Transaction(asset.getSymbol(),
                    quantityToSell, today.getTime(),
                    asset.getValuePerUnit()*quantityToSell,
                    "SELL");
            
            transactionController.addTransaction(
                    this.viewModel.getState().getUsername(),
                    transaction);
        }
    }

    /**
     * Sets the TransactionController for handling transactions.
     *
     * @param transactionController the TransactionController instance
     */
    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    /**
     * Inner class for managing table data.
     */
    static class PortfolioTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Symbol", "Quantity", "Total Value", "Daily Gain (%)"};
        private List<Asset> assets = List.of();
        private double exchangeRate = 1.0;

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public int getRowCount() {
            return assets.size();
        }

        @Override
        public int getColumnCount() {
            return 4; // Symbol, Quantity, Total Value, Daily Gain (%)
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Asset asset = assets.get(rowIndex);
            switch (columnIndex) {
                case 0: return asset.getSymbol();
                case 1: return asset.getQuantity();
                case 2: return asset.getTotalValue() * exchangeRate;
                case 3: return String.format("%.2f (%.2f%%)", asset.getDailyGain(), asset.getDailyGainPercentage());
                default: return null;
            }
        }

        public void updateData(List<Asset> assets, double exchangeRate) {
            this.assets = assets;
            this.exchangeRate = exchangeRate;
            fireTableDataChanged();
        }

        public Asset getAssetAt(int rowIndex) {
            return assets.get(rowIndex);
        }
    }

}
