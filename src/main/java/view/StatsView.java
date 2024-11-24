package view;

import org.jfreechart.chart.ChartFactory;
import org.jfreechart.chart.ChartPanel;
import org.jfreechart.chart.JFreeChart;
import org.jfreechart.data.general.DefaultPieDataset;

import interface_adapter.StatsViewModel;
import entity.Asset;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatsView extends JFrame {
    public StatsView(StatsViewModel statsViewModel) {
        setTitle("Statistics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        List<Asset> assets = statsViewModel.getAssets();

        String[] columnNames = {"Total Balance", "Total Daily Gain", "%"};
        Object[][] data = new Object[1][3];
        data[0][0] = statsViewModel.getTotalBalance();
        data[0][1] = statsViewModel.getTotalDailyGain();
        data[0][2] = statsViewModel.getTotalDailyPercentageGain();
        JTable statsTable = new JTable(data, columnNames);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.add(statsTable);
        statsPanel.add(createPieChartPanel(assets));

        JFrame frame = new JFrame("Statistics");
        frame.setContentPane(statsPanel);
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
