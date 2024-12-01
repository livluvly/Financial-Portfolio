package app;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize the AppBuilder
            AppBuilder appBuilder = new AppBuilder();

            // Add all views and use cases
            try {
                appBuilder
                        .addSignupView()
                        .addLoginView()
                        .addLoggedInView()
                        .addSignupUseCase()
                        .addLoginUseCase()
                        .addChangePasswordUseCase()
                        .addLogoutUseCase()
                        .addSearchAssetUseCase()
                        .addPortfolioUseCase()
                        .addTransactionUseCase() // Ensures transaction handling is set up
                        .addTransactionsView()
                        .addPortfolioView()   // Add the PortfolioView
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
