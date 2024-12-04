package interfaceAdapter.search;

import useCase.search.*;
import interfaceAdapter.SearchAssetViewModel;

public class SearchAssetPresenter implements SearchAssetOutputBoundary {
    private final SearchAssetViewModel viewModel;
    public SearchAssetPresenter(SearchAssetViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Override
    public void prepareSuccessView(SearchAssetOutputData outputData) {
        viewModel.updateSearchResults(outputData.getSearchResultList());
    }

    /**
     * @param errorMessage
     */
    @Override
    public void prepareFailView(String errorMessage) {
        // on failure, should I just clear search results or the whole view?
//        viewModel.updateSearchList(List.of());
    }

    public SearchAssetViewModel getViewModel() {
        return viewModel;
    }
}
