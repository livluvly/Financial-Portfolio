package view;

import entity.Asset;
import interface_adapter.PortfolioViewModel;
import interface_adapter.SearchAssetViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchAssetController;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel viewsPanel;
    private final ViewManager viewManager;

    public MainFrame() {
        // Initialize CardLayout and JPanel for views
        cardLayout = new CardLayout();
        viewsPanel = new JPanel(cardLayout);

        // Create ViewManager to manage which view is visible
        viewManager = new ViewManager(viewsPanel, cardLayout, new ViewManagerModel());

        // Add the PortfolioView and SearchAssetView

        PortfolioViewModel portfolioViewModel = new PortfolioViewModel();
//        SearchAssetViewModel searchAssetViewModel = new SearchAssetViewModel(new SearchAssetController());
        PortfolioView portfolioView = new PortfolioView(portfolioViewModel);
//        SearchAssetView searchAssetView = new SearchAssetView(searchAssetViewModel);


        // Add the views to the viewsPanel (with a unique name for each)
        viewsPanel.add(portfolioView, portfolioViewModel.getViewName());
//        viewsPanel.add(searchAssetView, searchAssetViewModel.getViewName());

        // Set up navigation buttons
        JPanel buttonPanel = new JPanel();
        JButton portfolioButton = new JButton("Portfolio");
        JButton searchButton = new JButton("Search Assets");

        portfolioButton.addActionListener(e -> viewManager.updateView("Portfolio"));
        searchButton.addActionListener(e -> viewManager.updateView("Transactions"));

        buttonPanel.add(portfolioButton);
        buttonPanel.add(searchButton);

        // Add everything to the frame
        this.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(viewsPanel, BorderLayout.CENTER);

        // Set up frame settings
        this.setTitle("Portfolio and Transactions");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
