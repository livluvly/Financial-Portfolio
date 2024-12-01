package interface_adapter.transaction;

import entity.Asset;
import entity.Transaction;
import interface_adapter.PortfolioViewModel;

import java.util.ArrayList;
import java.util.List;

public class TransactionController {
    private final PortfolioViewModel portfolioViewModel;

    public TransactionController(PortfolioViewModel portfolioViewModel) {
        this.portfolioViewModel = portfolioViewModel;
    }

    public void addTransaction(Transaction transaction) {
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
                    1, transaction.getTotalCost(),
                    0);
            assets.add(newAsset);
        } else {
            // Update the existing asset
            existingAsset.setQuantity(existingAsset.getQuantity() + transaction.getQuantity());
            existingAsset.setTotalValue(existingAsset.getTotalValue() + transaction.getTotalCost());
        }

        // Update the portfolio view model
        portfolioViewModel.updatePortfolio(assets);
    }
}
