package entity;

public class SearchResult {
    private String symbol;
    private String name;
    private String type;
    private String region;

    public  SearchResult(String symbol, String name, String type, String region) {
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
        return "SearchResult [symbol=" + symbol + ", name=" + name + "]";
    }

    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }
}
