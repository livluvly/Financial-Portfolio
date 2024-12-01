package interface_adapter.search;

import entity.SearchResult;

import java.util.List;

public class SearchAssetState {
    private List<SearchResult> searchResults;
    private String username;

    public SearchAssetState(List<SearchResult> searchResults, String username) {
        this.username = username;
        this.searchResults = searchResults;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    public String getUsername() {
        return username;
    }
}