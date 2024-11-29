package view;

import entity.Asset;
import interface_adapter.StatsViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.List;

public class StatsView extends JFrame {
    private final StatsViewModel statsViewModel;
    private final JPanel statsPanel;

    public StatsView(StatsViewModel statsViewModel) {
        this.statsViewModel = statsViewModel;

        setTitle("Statistics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        List<Asset> assets = statsViewModel.getStatsData().getAssets();

        String[] columnNames = {"Total Balance", "Total Daily Gain", "%"};
        Object[][] data = new Object[1][3];
        data[0][0] = statsViewModel.getStatsData().getTotalBalance();
        data[0][1] = statsViewModel.getStatsData().getTotalDailyGain();
        data[0][2] = statsViewModel.getStatsData().getTotalDailyPercentageGain();
        JTable statsTable = new JTable(data, columnNames);

        this.statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.add(statsTable);
        statsPanel.add(createPieChartPanel(assets));

        // Update view when the state changes
        statsViewModel.addPropertyChangeListener(evt -> updateView());
    }

    private void updateView() {
        // Update the stats data & pie chart
    }

    private static JPanel createPieChartPanel(List<Asset> assets) {
        // Create the dataset with mock data
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Asset asset: assets) {
            dataset.setValue(asset.getSymbol(), asset.getQuantity());
        }

        // Create the pie chart using dataset
        JFreeChart chart = ChartFactory.createPieChart(
                "Portfolio Asset Allocation", // Chart title
                dataset,            // Data
                true,               // Include legend
                true,               // Tooltips
                false               // URLs
        );

        // Create a panel to display the chart
        return new ChartPanel(chart);
    }

}
