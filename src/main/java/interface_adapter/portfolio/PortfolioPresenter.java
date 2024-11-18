package interface_adapter.portfolio;

import interface_adapter.PortfolioViewModel;
import use_case.portfolio.PortfolioOutputBoundary;
import use_case.portfolio.PortfolioOutputData;

public class PortfolioPresenter implements PortfolioOutputBoundary {
    private PortfolioViewModel portfolioViewModel;
    /**
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(PortfolioOutputData outputData) {
        portfolioViewModel = new PortfolioViewModel(outputData.getAssets());
    }

    /**
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {

    }

    public PortfolioViewModel getPortfolioViewModel() {
        return portfolioViewModel;
    }
}
