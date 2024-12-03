package data_access;

import entity.Asset;
import useCase.statistics.StatsDataAccessInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStatsDataAccessObject implements StatsDataAccessInterface {
    private final Map<String, List<Asset>> database = new HashMap<>();

    public InMemoryStatsDataAccessObject() {
    }

    @Override
    public List<Asset> getAssetsForUser(String userId) {
        return database.get(userId);
    }

    @Override
    public boolean existsByName(String userId) {
        return database.containsKey(userId);
    }

    public void savePortfolio(String userId, List<Asset> assets) {
        database.put(userId, assets);
    }

}
