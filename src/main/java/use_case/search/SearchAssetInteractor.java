package use_case.search;
import java.util.List;
import entity.SearchResult;

public class SearchAssetInteractor implements SearchAssetInputBoundary{
    private final ExternalSearchDataAccessInterface searchDataAccess;
    private final SearchAssetOutputBoundary searchAssetOutputBoundary;
    public SearchAssetInteractor(ExternalSearchDataAccessInterface searchDataAccess, SearchAssetOutputBoundary searchAssetOutputBoundary) {
        this.searchDataAccess = searchDataAccess;
        this.searchAssetOutputBoundary = searchAssetOutputBoundary;
    }

    /**
     * @param input 
     * @return
     */
    @Override
    public SearchAssetOutputData search(SearchAssetInputData input) {
        List<SearchResult> results = searchDataAccess.searchByKeyword(input.getKeyword());
        SearchAssetOutputData outputData = new SearchAssetOutputData(results);
        searchAssetOutputBoundary.prepareSuccessView(outputData);
        return outputData;
    }
}
