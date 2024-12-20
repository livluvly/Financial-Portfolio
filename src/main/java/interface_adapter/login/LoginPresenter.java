package interface_adapter.login;

import interface_adapter.PortfolioViewModel;
import interface_adapter.StatsViewModel;
import interface_adapter.TransactionHistoryViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.changePassword.LoggedInState;
import interface_adapter.changePassword.LoggedInViewModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.statistics.StatsController;
import interface_adapter.transaction_history.TransactionHistoryController;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private final PortfolioController portfolioController;
    private final StatsController statsController;
    private final TransactionHistoryController historyController;
    private TransactionHistoryViewModel transactionHistoryViewModel;
    private StatsViewModel statsViewModel;
    private PortfolioViewModel portfolioViewModel;
  
    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel, PortfolioController portfolioController,
                          StatsController statsController, TransactionHistoryController historyController) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
        this.portfolioController = portfolioController;
        this.statsController = statsController;
        this.historyController = historyController;
    }

    public void setTransactionHistoryViewModel(TransactionHistoryViewModel transactionHistoryViewModel) {
        this.transactionHistoryViewModel = transactionHistoryViewModel;
    }

    public void setStatsViewModel(StatsViewModel statsViewModel) {
        this.statsViewModel = statsViewModel;
    }

    public void setPortfolioViewModel(PortfolioViewModel portfolioViewModel) {
        this.portfolioViewModel = portfolioViewModel;
    }


    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the logged in view.

        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.firePropertyChanged();

        // fetch portfolio
        portfolioController.fetchAssets(response.getUsername());
        portfolioViewModel.setName(response.getUsername());
        // fetch stats
        statsController.execute(response.getUsername());

        // fetch history
        historyController.fetchTransactionHistory(response.getUsername());
        transactionHistoryViewModel.setName(response.getUsername());
        this.viewManagerModel.setState(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }
}
