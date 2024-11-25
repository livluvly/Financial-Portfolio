package interface_adapter.search;

import entity.SearchResult;
import java.util.List;

public class SearchAssetState {
    private List<SearchResult> searchResults;

    public SearchAssetState(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }
}