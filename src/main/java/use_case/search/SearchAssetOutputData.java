package use_case.search;

import entity.SearchResult;

import java.util.List;

public class SearchAssetOutputData {
    private final List<SearchResult> searchResultList;
    public SearchAssetOutputData(List<SearchResult> searchResultList) {
        this.searchResultList = searchResultList;
    }
    public List<SearchResult> getSearchResultList() {
        return searchResultList;
    }
}
