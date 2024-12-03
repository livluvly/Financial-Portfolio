package useCase.portfolio;

import entity.Asset;

import java.util.List;

/**
 * DAO for the Portfolio Use Case.
 */
public interface PortfolioDataAccessInterface {
    List<Asset> getAssetsForUser(String userId);
    void savePortfolio(String username, List<Asset> portfolio);
    void addTransaction(String username, Asset asset);
    void updateAsset(String username, Asset asset);
    void deleteAsset(String username, String symbol);
}
