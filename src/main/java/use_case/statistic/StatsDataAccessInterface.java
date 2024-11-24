package use_case.statistic;

import entity.Asset;
import entity.User;

import java.util.List;

/**
 * DAO for the Statistic Use Case.
 */
public interface StatsDataAccessInterface {

    List<Asset> getAssetsForUser(String userId);
}
