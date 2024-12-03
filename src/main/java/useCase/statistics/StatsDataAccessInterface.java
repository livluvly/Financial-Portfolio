package useCase.statistics;

import entity.Asset;

import java.util.List;

/**
 * DAO for the Statistic Use Case.
 */
public interface StatsDataAccessInterface {

    List<Asset> getAssetsForUser(String userId);

    /**
     * Checks if the given username exists.
     * @param userId the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String userId);
    void savePortfolio(String userId, List<Asset> assets);
}