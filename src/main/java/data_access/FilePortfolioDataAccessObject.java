package data_access;

import entity.Asset;
import use_case.portfolio.PortfolioDataAccessInterface;
import use_case.statistics.StatsDataAccessInterface;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FilePortfolioDataAccessObject implements PortfolioDataAccessInterface, StatsDataAccessInterface {
    /**
     * @param userId
     * @return
     */
    private static final String HEADER = "username,symbol,quantity,totalValue,dailyGain,dailyGainPercentage";
    private final File csvFile;
    private final List<String> headers = Arrays.asList(HEADER.split(","));

    public FilePortfolioDataAccessObject(String csvPath) throws IOException {
        csvFile = new File(csvPath);
        if (!csvFile.exists() || csvFile.length() == 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                writer.write(HEADER);
                writer.newLine();
            }
        }
    }

    @Override
    public List<Asset> getAssetsForUser(String username) {
        List<Asset> portfolio = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] columns = row.split(",");
                if (columns[0].equals(username)) {
                    portfolio.add(new Asset(
                            columns[1], // symbol
                            Double.parseDouble(columns[2]), // quantity
                            Double.parseDouble(columns[3])/Double.parseDouble(columns[2]), //valuePerUnit
                            Double.parseDouble(columns[3]), // totalValue
                            Double.parseDouble(columns[4]),  // dailyGain
                            Double.parseDouble(columns[5]) // dailyGainPercentage
                    ));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading portfolio file.", e);
        }

        return portfolio;
    }

    @Override
    public boolean existsByName(String userId) {
        return this.getAssetsForUser(userId).size() > 0;
    }

    /**
     * @param username
     * @param portfolio
     */
    @Override
    public void savePortfolio(String username, List<Asset> portfolio) {
        List<String> rows = new ArrayList<>();
        Map<String, Asset> assetMap = new HashMap<>();

        // Read all existing rows and filter out old portfolio data
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            rows = reader.lines()
                    .filter(line -> !line.startsWith(username + ","))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error reading portfolio file.", e);
        }
        // Append new portfolio data // TODO: extract methods
        // Update or add new assets
        for (Asset asset : portfolio) {
            if (assetMap.containsKey(asset.getSymbol())) {
                // If asset already exists, update quantity and totalValue
                Asset existingAsset = assetMap.get(asset.getSymbol());
                existingAsset.setQuantity(existingAsset.getQuantity() + asset.getQuantity());
                existingAsset.setTotalValue(existingAsset.getTotalValue() + asset.getTotalValue());
            } else {
                // Otherwise, add new asset
                assetMap.put(asset.getSymbol(), asset);
            }
        }

        // Append the updated or new assets to rows
        for (Asset asset : assetMap.values()) {
            rows.add(String.format("%s,%s,%.2f,%.2f,%.2f,%.2f",
                    username,
                    asset.getSymbol(),
                    asset.getQuantity(),
                    asset.getTotalValue(),
                    asset.getDailyGain(),
                    asset.getDailyGainPercentage()));
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
//            writer.write(HEADER);
//            writer.newLine();
            for (String row : rows) {
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving portfolio file.", e);
        }
    }
    /**
     * @param username
     * @param asset
     */
    @Override
    public void addTransaction(String username, Asset asset) {
        List<Asset> portfolio = getAssetsForUser(username);

        portfolio.add(asset);

        savePortfolio(username, portfolio);
    }

    /**
     * @param username
     * @param asset
     */
    @Override
    public void updateAsset(String username, Asset asset) {
        List<Asset> portfolio = getAssetsForUser(username);

        portfolio.removeIf(a -> a.getSymbol().equals(asset.getSymbol()));
        portfolio.add(asset);

        savePortfolio(username, portfolio);
    }

    /**
     * @param username
     * @param symbol
     */
    @Override
    public void deleteAsset(String username, String symbol) {
        List<Asset> portfolio = getAssetsForUser(username);
        portfolio.removeIf(a -> a.getSymbol().equals(symbol));
        savePortfolio(username, portfolio);
    }
}
