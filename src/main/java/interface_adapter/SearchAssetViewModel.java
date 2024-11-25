package interface_adapter;

import java.util.List;
import interface_adapter.ViewModel;
import entity.SearchResult;
import interface_adapter.search.SearchAssetState;

/**
 * The ViewModel for SearchAsset functionality.
 */
public class SearchAssetViewModel extends ViewModel<SearchAssetState> {

    public SearchAssetViewModel() {
        super("Transactions");
        this.setState(new SearchAssetState(List.of()));
    }

    /**
     * Updates the list of search results in the view model.
     *
     * @param results the new list of search results
     */
    public void updateSearchResults(List<SearchResult> searchResults) {
        SearchAssetState newState = new SearchAssetState(searchResults);
        this.setState(newState);
        this.firePropertyChanged();
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
