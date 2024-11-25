package use_case.search;
import java.util.List;
import entity.SearchResult;

public interface ExternalSearchDataAccessInterface {
    List<SearchResult> searchByKeyword(String keyword);
}
