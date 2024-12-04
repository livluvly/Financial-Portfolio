package interface_adapter;

import java.util.List;

import interface_adapter.search.*;
import entity.SearchResult;
import interface_adapter.search.SearchAssetState;
import use_case.search.SearchAssetOutputData;

/**
 * The ViewModel for SearchAsset functionality.
 */
public class SearchAssetViewModel extends ViewModel<SearchAssetState> {

    private SearchAssetController searchController;

    public SearchAssetViewModel(SearchAssetController searchController, String username) {
        super("Transactions");
        this.setState(new SearchAssetState(List.of(), username));
        this.searchController = searchController;
    }

    public void setSearchController(SearchAssetController searchController) {
        this.searchController = searchController;
    }

    /**
     * Updates the list of search results in the view model.
     *
     * @param searchResults new list of search results
     */
    public void updateSearchResults(List<SearchResult> searchResults) {
        SearchAssetState newState = new SearchAssetState(searchResults, this.getState().getUsername());
        this.setState(newState);
        this.firePropertyChanged();
    }
    public void search(String query) {
        SearchAssetOutputData output;
        if (searchController != null && query != null && !query.trim().isEmpty()) {
            output = searchController.search(query);
            this.updateSearchResults(output.getSearchResultList());
        }
    }
    /**
     * Returns the list of search results.
     *
     * @return the current list of search results
     */
    public List<SearchResult> getSearchResults() {
        return this.getState().getSearchResults();
    }

}
