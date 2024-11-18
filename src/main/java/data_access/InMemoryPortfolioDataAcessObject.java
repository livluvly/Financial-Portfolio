package data_access;

import entity.Asset;
import use_case.portfolio.PortfolioDataAccessInterface;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryPortfolioDataAcessObject implements PortfolioDataAccessInterface {
    private final Map<String, List<Asset>> database = new HashMap<>();
    public InMemoryPortfolioDataAcessObject(){
        // test data
        database.put("alice", List.of(
                new Asset("AAPL", 10, 1500.0, 1.5),
                new Asset("GOOGL", 5, 1400.0, 0.8),
                new Asset("AMZN", 2, 3200.0, -0.3)
        ));
    }
    /**
     * @param userId 
     * @return
     */
    @Override
    public List<Asset> getAssetsForUser(String userId) {
        return database.getOrDefault(userId, Collections.emptyList());
    }
}
