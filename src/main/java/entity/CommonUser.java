package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {
    // There're some redundancies here. The portfolio field is never used.

    private final String name;
    private final String password;
    private final List<Asset> portfolio;

    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
        this.portfolio = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void addAsset(Asset asset) {
        portfolio.add(asset);
    }
    public void removeAsset(Asset asset) {
        portfolio.remove(asset);
    }
}
