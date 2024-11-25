package entity;

public class SearchResult {
    private String symbol;
    private String name;
    public  SearchResult(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }
    public String getSymbol() {
        return symbol;
    }
    public String getName() {
        return name;
    }
}
