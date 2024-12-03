package interfaceAdapter.login;

import interfaceAdapter.ViewManagerModel;
import interfaceAdapter.changePassword.LoggedInState;
import interfaceAdapter.changePassword.LoggedInViewModel;
import interfaceAdapter.portfolio.PortfolioController;
import interfaceAdapter.statistics.StatsController;
import interfaceAdapter.transactionHistory.TransactionHistoryController;
import useCase.login.LoginOutputBoundary;
import useCase.login.LoginOutputData;

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

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the logged in view.

        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.firePropertyChanged();

        // fetch portfolio
        portfolioController.fetchAssets(response.getUsername());

        // fetch stats
        statsController.execute(response.getUsername());

        // fetch history
        historyController.fetchTransactionHistory(response.getUsername());


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
