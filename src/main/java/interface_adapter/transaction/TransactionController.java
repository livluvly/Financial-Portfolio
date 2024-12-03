package interface_adapter.transaction;

import entity.Asset;
import entity.Transaction;
import interface_adapter.PortfolioViewModel;
import interface_adapter.TransactionHistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class TransactionController {
    private final PortfolioViewModel portfolioViewModel;
    private final TransactionHistoryViewModel historyViewModel;

    public TransactionController(PortfolioViewModel portfolioViewModel,
                                 TransactionHistoryViewModel historyViewModel) {
        this.portfolioViewModel = portfolioViewModel;
        this.historyViewModel = historyViewModel;
    }

    public void addTransaction(String username, Transaction transaction) {
        List<Asset> assets = new ArrayList<>(portfolioViewModel.getAssets());

        // Find if the asset already exists in the portfolio
        Asset existingAsset = assets.stream()
                .filter(asset -> asset.getSymbol().equals(transaction.getSymbol()))
                .findFirst()
                .orElse(null);

        if (existingAsset == null) {
            // Add a new asset to the portfolio
            Asset newAsset = new Asset(transaction.getSymbol(),
                    transaction.getQuantity(),
                    transaction.getTotalCost()/ transaction.getQuantity(),
                    transaction.getTotalCost(),
                    0,
                    0);
            assets.add(newAsset);
            portfolioViewModel.addTransaction(
                    username,
                    transaction.getSymbol(),
                    transaction.getQuantity() ,
                    transaction.getTotalCost(),
                    0);
        } else {
            // Update the existing asset
            if (transaction.getType().equals("BUY")) {
                existingAsset.setQuantity(existingAsset.getQuantity() + transaction.getQuantity());
                portfolioViewModel.updateAsset(
                        username,
                        transaction.getSymbol(),
                        transaction.getQuantity());
            } else {
                existingAsset.setQuantity(existingAsset.getQuantity() - transaction.getQuantity());
                if (existingAsset.getQuantity() <= 0) {
                    assets.remove(existingAsset);
                }
                portfolioViewModel.deleteAsset(username, transaction.getSymbol());
            }
        }

        // Update the portfolio view model
        portfolioViewModel.updatePortfolio(assets);

        List<Transaction> histories = new ArrayList<>(historyViewModel.getTransactionHistory());
        histories.add(transaction);
        historyViewModel.updateTransactionHistory(histories);
    }
}
