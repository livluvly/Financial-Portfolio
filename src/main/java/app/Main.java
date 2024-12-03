package app;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            // Initialize the AppBuilder
            AppBuilder appBuilder = null;
            try {
                appBuilder = new AppBuilder();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Add all views and use cases
            // warning: portfolioController needs to go first
            try {
                appBuilder
                        .addPortfolioUseCase()
                        .addTransactionHistoryUseCase()
                        .addTransactionUseCase() // Ensures transaction handling is set up
                        .addSignupView()
                        .addLoginView()
                        .addLoggedInView()
                        .addPortfolioView()
                        .addSignupUseCase()
                        .addLoginUseCase()
                        .addLogoutUseCase()
                        .addChangePasswordUseCase()
                        .addSearchAssetUseCase()
                        .addTransactionsView()
                        .addTransactionHistoryView()
                        .addTransactionHistoryUseCase()
                        .addStatsView();  // Add the StatisticsView (if implemented)
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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
