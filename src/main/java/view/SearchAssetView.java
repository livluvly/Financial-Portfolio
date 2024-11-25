package view;

import interface_adapter.SearchAssetViewModel;
import interface_adapter.search.SearchAssetController;
import entity.SearchResult;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        // Bind ViewModel changes to update the table
        viewModel.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                List<SearchResult> results = viewModel.getSearchResults();
                tableModel.updateData(results);
            }
        });
    }

    /**
     * Custom table model for displaying search results.
     */
    private static class SearchResultTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Symbol", "Name"};
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
                default: return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }
}
