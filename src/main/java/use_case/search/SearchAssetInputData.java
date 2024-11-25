package use_case.search;

public class SearchAssetInputData {
    private final String keyword;
    public SearchAssetInputData(String keyword) {
        this.keyword = keyword;
    }
    public String getKeyword() {
        return keyword;
    }
}
