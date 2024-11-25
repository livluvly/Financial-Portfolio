package view;

import interface_adapter.SearchAssetViewModel;
import entity.SearchResult;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class SearchAssetView extends JPanel {
    private final SearchAssetViewModel viewModel;
    private JTable resultsTable;  // Declare the table here

    public SearchAssetView(SearchAssetViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new BorderLayout());

        // Initialize table for displaying search results
        resultsTable = new JTable();
        resultsTable.setModel(new SearchResultTableModel());
        this.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        // Update view when the state changes
        viewModel.addPropertyChangeListener(evt -> updateView());
    }

    private void updateView() {
        List<SearchResult> results = viewModel.getSearchResults();
        SearchResultTableModel tableModel = (SearchResultTableModel) resultsTable.getModel();
        tableModel.updateData(results);  // Update the table data
    }

    private static class SearchResultTableModel extends AbstractTableModel {
        private List<SearchResult> results = List.of();

        @Override
        public int getRowCount() {
            return results.size();
        }

        @Override
        public int getColumnCount() {
            return 3;  // Symbol, Name, Description
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            SearchResult result = results.get(rowIndex);
            switch (columnIndex) {
                case 0: return result.getSymbol();
                case 1: return result.getName();
                default: return null;
            }
        }

        public void updateData(List<SearchResult> results) {
            this.results = results;
            fireTableDataChanged();
        }
    }
}
