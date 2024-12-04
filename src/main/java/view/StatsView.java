package view;

import entity.Asset;
import interface_adapter.PortfolioViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class StatsView extends JPanel {
    private final PortfolioViewModel portfolioViewModel;
    private final JTable statsTable;
    private final StatsTableModel statsTableModel;
    private final StatsPieChartModel statsPieChartModel;

    public StatsView(PortfolioViewModel portfolioViewModel) {
        this.portfolioViewModel = portfolioViewModel;

        // Initialize table for displaying stats
        statsTableModel = new StatsTableModel(portfolioViewModel.getAssets());
        statsTable = new JTable(statsTableModel);
        statsTable.setRowHeight(25);

        JScrollPane tableScrollPane = new JScrollPane(statsTable);
        tableScrollPane.setPreferredSize(new Dimension(400, 50)); // Limit table height

        // Initialize pie chart for displaying asset allocation
        statsPieChartModel = new StatsPieChartModel(portfolioViewModel.getAssets());

        // Layout components
        setLayout(new BorderLayout());

        // Add table in a fixed-height panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        tablePanel.setPreferredSize(new Dimension(400, 50));
        add(tablePanel, BorderLayout.NORTH);

        // Add pie chart
        add(statsPieChartModel.getChartPanel(), BorderLayout.CENTER);

        // Register listener to update table and pie chart on data changes
        portfolioViewModel.addPropertyChangeListener(evt -> {
            statsTableModel.updateStatsData(portfolioViewModel.getAssets());
            statsPieChartModel.updatePieChart(portfolioViewModel.getAssets());
        });
    }

    /**
     * Inner Table class for managing stats table data.
     */
    private static class StatsTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Total Balance", "Total Daily Gain", "% Daily Gain"};
        private double totalBalance;
        private double totalDailyGain;
        private double totalDailyPercentageGain;

        public StatsTableModel(List<Asset> assets) {
            updateStatsData(assets);
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public int getRowCount() {
            return 1; // Single-row table for aggregate stats
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: return totalBalance;
                case 1: return totalDailyGain;
                case 2: return totalDailyPercentageGain * 100; // Display percentage
                default: return null;
            }
        }

        public void updateStatsData(List<Asset> assets) {
            totalBalance = retrieveTotalBalance(assets);
            totalDailyGain = retrieveTotalDailyGain(assets);
            totalDailyPercentageGain = retrieveTotalPercentageGain(assets);
            fireTableDataChanged();
        }

        private double retrieveTotalBalance(List<Asset> assets) {
            double totalBalance = 0.0;
            for (Asset asset : assets) {
                double totalValue = asset.getValuePerUnit() * asset.getQuantity();
                totalBalance += totalValue;
            }
            return totalBalance;
        }

        private double retrieveTotalDailyGain(List<Asset> assets) {
            double totalDailyGain = 0.0;
            for (Asset asset : assets) {
                double totalGain = asset.getDailyGain() * asset.getQuantity();
                totalDailyGain += totalGain;
            }
            return totalDailyGain;
        }

        private double retrieveTotalPercentageGain(List<Asset> assets) {
            if (assets.isEmpty()) {
                return 0.0;
            }
            return totalDailyGain / totalBalance;
        }
    }

    /**
     * Inner Pie Chart class for managing stats pie chart data.
     */
    private static class StatsPieChartModel {
        private DefaultPieDataset dataset;
        private final ChartPanel chartPanel;

        public StatsPieChartModel(List<Asset> assets) {
            dataset = createPieDataset(assets);
            chartPanel = createChartPanel();
        }

        private DefaultPieDataset createPieDataset(List<Asset> assets) {
            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Asset asset : assets) {
                dataset.setValue(asset.getSymbol(), asset.getQuantity());
            }
            return dataset;
        }

        public ChartPanel createChartPanel() {
            JFreeChart chart = ChartFactory.createPieChart(
                    "Portfolio Asset Allocation",
                    dataset,
                    true, // Include legend
                    true, // Tooltips
                    false // URLs
            );
            return new ChartPanel(chart);
        }

        public ChartPanel getChartPanel() {
            return chartPanel;
        }

        public void updatePieChart(List<Asset> assets) {
            dataset.clear();
            for (Asset asset : assets) {
                dataset.setValue(asset.getSymbol(), asset.getQuantity());
            }
            chartPanel.repaint();
        }
    }
}
