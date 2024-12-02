package interface_adapter.transaction;

import entity.Asset;
import entity.Transaction;
import interface_adapter.PortfolioViewModel;
import use_case.portfolio.PortfolioDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class TransactionController {
    private final PortfolioViewModel portfolioViewModel;

    public TransactionController(PortfolioViewModel portfolioViewModel) {
        this.portfolioViewModel = portfolioViewModel;
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
                existingAsset.setTotalValue(existingAsset.getTotalValue() + transaction.getTotalCost());
                portfolioViewModel.updateAsset(
                        username,
                        transaction.getSymbol(),
                        transaction.getQuantity() ,
                        transaction.getTotalCost());
            } else {
                existingAsset.setQuantity(existingAsset.getQuantity() - transaction.getQuantity());
                existingAsset.setTotalValue(existingAsset.getTotalValue() - transaction.getTotalCost());
                if (existingAsset.getQuantity() <= 0) {
                    assets.remove(existingAsset);
                }
                portfolioViewModel.deleteAsset(username, transaction.getSymbol());
            }
        }

        // Update the portfolio view model
        portfolioViewModel.updatePortfolio(assets);
    }
}
