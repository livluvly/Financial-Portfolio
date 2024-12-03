package view;

import entity.Asset;
import interface_adapter.PortfolioViewModel;
import interface_adapter.StatsViewModel;
import interface_adapter.statistics.StatsController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class StatsView extends JPanel {
    private final StatsViewModel statsViewModel;
    private final PortfolioViewModel portfolioViewModel;
    private StatsController statsController;
    private final JTable statsTable;
    private final StatsTableModel statsTableModel;
    private final JPanel statsPieChartPanel;
    private final StatsPieChartModel statsPieChartModel;

    public StatsView(StatsViewModel statsViewModel, PortfolioViewModel portfolioViewModel) {
        this.statsViewModel = statsViewModel;
        this.portfolioViewModel = portfolioViewModel;

        // Initialize table for displaying stats
        statsTable = new JTable();
        statsTableModel = new StatsTableModel(statsViewModel);
        statsTable.setModel(statsTableModel);

        // Initialize pie chart for displaying asset allocation
        statsPieChartModel = new StatsPieChartModel(statsViewModel.getStatsData().getAssets());
        statsPieChartPanel = statsPieChartModel.getChartPanel();

        // add table and pie chart to stats view (JPanel)
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(statsTable, BorderLayout.NORTH);
        this.add(statsPieChartPanel, BorderLayout.CENTER);

        // Update view when the state changes
        statsViewModel.addPropertyChangeListener(evt -> updateView());
    }


    private void updateView() {
        // Update the stats data & pie chart
        statsTableModel.updateStatsData(statsViewModel);
        statsPieChartModel.updatePieChart(statsViewModel.getStatsData().getAssets());
    }


    public void setStatsController(StatsController statsController) {
        this.statsController = statsController;
    }


    /**
     * Inner Table class for managing stats table data.
     */
    private static class StatsTableModel extends AbstractTableModel {
        private final StatsViewModel statsViewModel;
        private final String[] columnNames = {"Total Balance", "Total Daily Gain", "%"};
        private double totalBalance;
        private double totalDailyGain;
        private double totalDailyPercentageGain;

        private StatsTableModel(StatsViewModel statsViewModel) {
            this.statsViewModel = statsViewModel;
            this.totalBalance = statsViewModel.getStatsData().getTotalBalance();
            this.totalDailyGain = statsViewModel.getStatsData().getTotalDailyGain();
            this.totalDailyPercentageGain = statsViewModel.getStatsData().getTotalDailyPercentageGain();
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: return statsViewModel.getStatsData().getTotalBalance();
                case 1: return statsViewModel.getStatsData().getTotalDailyGain();
                case 2: return statsViewModel.getStatsData().getTotalDailyPercentageGain();
                default: return 0.0;
            }
        }

        public void updateStatsData(StatsViewModel statsViewModel) {
            this.totalBalance = statsViewModel.getStatsData().getTotalBalance();
            this.totalDailyGain = statsViewModel.getStatsData().getTotalDailyGain();
            this.totalDailyPercentageGain = statsViewModel.getStatsData().getTotalDailyPercentageGain();
            fireTableDataChanged();
        }
    }

    /**
     * Inner Pie Chart class for managing stats pie chart data
     */
    private static class StatsPieChartModel {
        private DefaultPieDataset dataset;
        private ChartPanel chartPanel;

        public StatsPieChartModel(List<Asset> assets) {
            this.dataset = new DefaultPieDataset();
            this.chartPanel = createChartPanel();
        }

        private DefaultPieDataset createPieDataset(List<Asset> assets) {
            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Asset asset: assets) {
                dataset.setValue(asset.getSymbol(), asset.getQuantity());
            }
            return dataset;
        }

        public ChartPanel createChartPanel() {
            JFreeChart chart = ChartFactory.createPieChart(
                    "Portfolio Asset Allocation", // Chart title
                    dataset,            // Data
                    true,               // Include legend
                    true,               // Tooltips
                    false               // URLs
            );
            return new ChartPanel(chart);
        }

        public ChartPanel getChartPanel() {
            return chartPanel;
        }

        public void updatePieChart(List<Asset> assets) {
            dataset.clear();
            for (Asset asset: assets) {
                dataset.setValue(asset.getSymbol(), asset.getQuantity());
            }
            chartPanel.repaint();
        }
    }

}
