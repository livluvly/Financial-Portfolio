package interface_adapter.search;
import use_case.search.*;

public class SearchAssetController {
    private final SearchAssetInputBoundary inputBoundary;

    public SearchAssetController(SearchAssetInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public SearchAssetOutputData search(String keyword) {
        SearchAssetInputData inputData = new SearchAssetInputData(keyword);
        return inputBoundary.search(inputData);
    }
}
