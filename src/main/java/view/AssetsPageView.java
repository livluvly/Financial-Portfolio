package view;

import interface_adapter.PortfolioViewModel;
import entity.Asset;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AssetsPageView extends JFrame {
    public AssetsPageView(PortfolioViewModel viewModel) {
        setTitle("Assets Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        List<Asset> assets = viewModel.getAssets();

        String[] columnNames = {"Symbol", "Quantity", "Total Value", "Daily Gain (%)"};
        Object[][] data = new Object[assets.size()][4];

        for (int i = 0; i < assets.size(); i++) {
            Asset asset = assets.get(i);
            data[i][0] = asset.getSymbol();
            data[i][1] = asset.getQuantity();
            data[i][2] = asset.getTotalValue();
            data[i][3] = asset.getDailyGainPercentage();
        }

        JTable table = new JTable(data, columnNames);
        add(new JScrollPane(table), BorderLayout.CENTER);
        setVisible(true);
    }
}
