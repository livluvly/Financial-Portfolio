package view;

import interface_adapter.PortfolioViewModel;
import entity.Asset;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class PortfolioView extends JPanel {
    private final PortfolioViewModel viewModel;
    private final JComboBox sortOptions;
    private JTable portfolioTable;  // Declare the table here

    public PortfolioView(PortfolioViewModel viewModel) {
        this.viewModel = viewModel;
        this.sortOptions = new JComboBox(new String[]{"Symbol", "Quantity", "Total Value", "Daily Gain"});

        this.setLayout(new BorderLayout());
        this.registerListener();

        sortOptions.setBorder(BorderFactory.createTitledBorder("Sort Options"));
        this.add(sortOptions, BorderLayout.NORTH);
        // Initialize table for displaying assets
        portfolioTable = new JTable();
        portfolioTable.setModel(new PortfolioTableModel());
        this.add(new JScrollPane(portfolioTable), BorderLayout.CENTER);

        // Update view when the state changes
        viewModel.addPropertyChangeListener(evt -> updateView());
    }

    private void registerListener() {
        // Bind search button click to the ViewModel's controller
        sortOptions.addItemListener(e -> {
            String selectedSortOption = (String) sortOptions.getSelectedItem();
            if (selectedSortOption != null) {
                viewModel.sortAssets(selectedSortOption);

            }
        });
        }


    private void updateView() {
        List<Asset> assets = viewModel.getAssets();
        PortfolioTableModel tableModel = (PortfolioTableModel) portfolioTable.getModel();
        tableModel.updateData(assets);  // Update the table data
    }

    private static class PortfolioTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Symbol", "Quantity", "Total Value", "Daily Gain (%)"};
        private List<Asset> assets = List.of();

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
            return 4;  // Symbol, Quantity, Total Value, Daily Gain, Daily Gain %
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Asset asset = assets.get(rowIndex);
            switch (columnIndex) {
                case 0: return asset.getSymbol();
                case 1: return asset.getQuantity();
                case 2: return asset.getTotalValue();
                case 3: return String.format("%n, (%n %)", asset.getDailyGain(),asset.getDailyGainPercentage());
                default: return null;
            }
        }

        public void updateData(List<Asset> assets) {
            this.assets = assets;
            fireTableDataChanged();
        }
    }
}
