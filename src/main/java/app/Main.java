package app;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize the AppBuilder
            AppBuilder appBuilder = new AppBuilder();

            // Add all views and use cases
            appBuilder
//                    .addSignupUseCase()
//                    .addLoginUseCase()
//                    .addChangePasswordUseCase()
//                    .addLogoutUseCase()
                    .addSearchAssetUseCase()
                    .addPortfolioUseCase()
                    .addTransactionController() // Ensures transaction handling is set up
                    .addSignupView()
                    .addLoginView()
                    .addLoggedInView()
                    .addTransactionsView()
                    .addPortfolioView()   // Add the PortfolioView
                    .addStatsView();  // Add the StatisticsView (if implemented)

            // Build the application and create the frame
            JFrame application = appBuilder.build();

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
