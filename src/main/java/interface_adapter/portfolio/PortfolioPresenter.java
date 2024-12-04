package interface_adapter.portfolio;
import java.util.List;
import interface_adapter.PortfolioViewModel;
import interface_adapter.TransactionHistoryViewModel;
import use_case.portfolio.PortfolioOutputBoundary;
import use_case.portfolio.PortfolioOutputData;

public class PortfolioPresenter implements PortfolioOutputBoundary {
    private PortfolioViewModel portfolioViewModel;
    private TransactionHistoryViewModel transactionHistoryViewModel;
    
    public PortfolioPresenter(PortfolioViewModel portfolioViewModel) {
        this.portfolioViewModel = portfolioViewModel;
    }
    @Override
    public void prepareSuccessView(PortfolioOutputData outputData) {
        portfolioViewModel.updatePortfolio(outputData.getAssets());
    }

    /**
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        // clear portfolio on failure
        portfolioViewModel.updatePortfolio(List.of());
    }

    public PortfolioViewModel getPortfolioViewModel() {
        return portfolioViewModel;
    }
}
