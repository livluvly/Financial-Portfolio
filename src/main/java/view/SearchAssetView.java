package view;

import entity.Transaction;
import interface_adapter.SearchAssetViewModel;
import interface_adapter.search.SearchAssetController;
import entity.SearchResult;
import interface_adapter.transaction.TransactionController;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

/**
 * The SearchAssetView is the Transactions page view. It allows users to search for assets
 * and displays the search results in a table format.
 */
public class SearchAssetView extends JPanel {
    private final SearchAssetViewModel viewModel;
    private final JTextField searchField;
    private final JButton searchButton;
    private final JTable resultTable;
    private final SearchResultTableModel tableModel;
    private TransactionController transactionController;

    /**
     * Constructor for the SearchAssetView.
     *
     * @param viewModel The ViewModel managing the state of this view.
     */
    public SearchAssetView(SearchAssetViewModel viewModel) {
        this.viewModel = viewModel;
        this.searchField = new JTextField(20);
        this.searchButton = new JButton("Search");
        this.tableModel = new SearchResultTableModel();
        this.resultTable = new JTable(tableModel);
//        this.transactionController = new TransactionController(view);
        this.setLayout(new BorderLayout());
        this.initializeView();
        this.registerListeners();
    }

    /**
     * Initializes the layout and components of the view.
     */
    private void initializeView() {
        // Top panel for search bar
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Search: "));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        // Main layout
        this.add(topPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(resultTable), BorderLayout.CENTER);
    }

    /**
     * Registers listeners for UI actions and binds the ViewModel.
     */
    private void registerListeners() {
        // Bind search button click to the ViewModel's controller
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                viewModel.search(query);
            }
        });

        resultTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = resultTable.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    String symbol = (String) resultTable.getValueAt(row, 0); // Assuming column 0 is the symbol
                    double pricePerUnit = 1; // Assuming unit price

                    Double quantity = TransactionPopup.promptForQuantity(symbol);
                    if (quantity != null) {
                        // Post transaction to the controller
                        Transaction transaction = new Transaction(symbol,quantity,new Date(),pricePerUnit,"BUY");

                        if (transactionController != null) {
                            transactionController.addTransaction(transaction);
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Transaction controller is not initialized!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                }
            }
        });

        // Bind ViewModel changes to update the table
        viewModel.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                List<SearchResult> results = viewModel.getSearchResults();
                tableModel.updateData(results);
            }
        });
    }

    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    /**
     * Custom table model for displaying search results.
     */
    private static class SearchResultTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Symbol", "Name", "Type", "Region"};
        private List<SearchResult> data = List.of();

        public void updateData(List<SearchResult> data) {
            this.data = data;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            SearchResult result = data.get(rowIndex);
            switch (columnIndex) {
                case 0: return result.getSymbol();
                case 1: return result.getName();
                case 2: return result.getType();
                case 3: return result.getRegion();
                default: return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }
}
