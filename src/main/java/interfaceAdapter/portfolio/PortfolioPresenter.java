package interfaceAdapter.portfolio;
import java.util.List;
import interfaceAdapter.PortfolioViewModel;
import interfaceAdapter.TransactionHistoryViewModel;
import useCase.portfolio.PortfolioOutputBoundary;
import useCase.portfolio.PortfolioOutputData;

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
