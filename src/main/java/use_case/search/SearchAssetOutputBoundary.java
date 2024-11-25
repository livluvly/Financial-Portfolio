package use_case.search;

public interface SearchAssetOutputBoundary {
    void prepareSuccessView(SearchAssetOutputData data);
    void prepareFailView(String errorMessage);
}
