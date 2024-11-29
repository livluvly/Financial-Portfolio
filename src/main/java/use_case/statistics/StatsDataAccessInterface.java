package use_case.statistics;

import entity.Asset;

import java.util.List;

/**
 * DAO for the Statistic Use Case.
 */
public interface StatsDataAccessInterface {

    List<Asset> getAssetsForUser(String userId);
}