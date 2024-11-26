package app;

import data_access.AlphaVantageSearchDataAccessObject;
import interface_adapter.*;
import interface_adapter.portfolio.*;

import java.util.List;
import entity.*;


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
        AppBuilder appBuilder = new AppBuilder();
        appBuilder
                .addSignupView()
                .addLoginView()
                .addLoggedInView()
                .addTransactionController()
                .addPortfolioView()
                .addSearchAssetUseCase()
                .addTransactionsView();// Add the search functionality
        JFrame app = appBuilder.build();
//   //      test search feature
//        AlphaVantageSearchDataAccessObject dao = new AlphaVantageSearchDataAccessObject();
//        System.out.println(dao.searchByKeyword("aza"));
    }
}
