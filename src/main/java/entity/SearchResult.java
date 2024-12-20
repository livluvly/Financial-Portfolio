package entity;

public class SearchResult {
    private String symbol;
    private String name;
    private String type;
    private String region;

    public  SearchResult(String symbol, String name, String type, String region) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol must not be null or blank.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank.");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type must not be null or blank.");
        }
        if (region == null || region.isBlank()) {
            throw new IllegalArgumentException("Region must not be null or blank.");
        }

        this.symbol = symbol;
        this.name = name;
        this.type = type;
        this.region = region;
    }
    public String getSymbol() {
        return symbol;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", region='" + region + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }
}
