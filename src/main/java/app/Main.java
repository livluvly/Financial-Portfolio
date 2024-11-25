package app;

import interface_adapter.*;
import interface_adapter.portfolio.*;

import java.util.List;
import entity.*;
import view.*;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
//        PortfolioViewModel portfolioViewModel = new PortfolioViewModel();
//        SearchAssetViewModel searchAssetViewModel = new SearchAssetViewModel();
//
//        // Create ViewManagerModel
//        ViewManagerModel viewManagerModel = new ViewManagerModel();
//
//
//        ViewManager viewManager = new ViewManager(viewManagerModel, portfolioViewModel, searchAssetViewModel);
//
//
//        JFrame frame = new JFrame("CSC207 Financial Portfolio");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//        frame.setContentPane(viewManager);
//        frame.setVisible(true);
//
//        portfolioViewModel.updatePortfolio(List.of(
//                new Asset("AAPL", 100, 15000, 200),
//                new Asset("GOOGL", 50, 10000, 100)
//        ));
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });


    }
}
