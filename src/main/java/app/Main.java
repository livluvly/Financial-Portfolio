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
                        .addLoginView()
                        .addSignupView()
                        .addLoggedInView()
                        .addPortfolioView()
                        .addTransactionsView()
                        .addTransactionHistoryView()
                        .addStatsView()  // Add the StatisticsView (if implemented)
                        .addChangePasswordUseCase()
                        .addPortfolioUseCase()
                        .addTransactionHistoryUseCase()
                        .addStatsUseCase()
                        .addSignupUseCase()
                        .addLoginUseCase()
                        .addLogoutUseCase()
                        .addTransactionUseCase() // Ensures transaction handling is set up
                        .addSearchAssetUseCase();
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
