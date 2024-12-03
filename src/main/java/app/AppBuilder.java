package app;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import data_access.*;
import entity.CommonUserFactory;
import entity.UserFactory;
import interfaceAdapter.*;
import interfaceAdapter.changePassword.ChangePasswordController;
import interfaceAdapter.changePassword.ChangePasswordPresenter;
import interfaceAdapter.changePassword.LoggedInViewModel;
import interfaceAdapter.login.LoginController;
import interfaceAdapter.login.LoginPresenter;
import interfaceAdapter.login.LoginViewModel;
import interfaceAdapter.logout.LogoutController;
import interfaceAdapter.logout.LogoutPresenter;
import interfaceAdapter.portfolio.PortfolioController;
import interfaceAdapter.portfolio.PortfolioPresenter;
import interfaceAdapter.search.SearchAssetController;
import interfaceAdapter.search.SearchAssetPresenter;
import interfaceAdapter.signup.SignupController;
import interfaceAdapter.signup.SignupPresenter;
import interfaceAdapter.signup.SignupViewModel;
import interfaceAdapter.statistics.StatsController;
import interfaceAdapter.statistics.StatsPresenter;
import interfaceAdapter.transaction.TransactionController;
import interfaceAdapter.transactionHistory.TransactionHistoryController;
import interfaceAdapter.transactionHistory.TransactionHistoryPresenter;
import useCase.changePassword.ChangePasswordInputBoundary;
import useCase.changePassword.ChangePasswordInteractor;
import useCase.changePassword.ChangePasswordOutputBoundary;
import useCase.login.LoginInputBoundary;
import useCase.login.LoginInteractor;
import useCase.login.LoginOutputBoundary;
import useCase.logout.LogoutInputBoundary;
import useCase.logout.LogoutInteractor;
import useCase.logout.LogoutOutputBoundary;
import useCase.portfolio.PortfolioInteractor;
import useCase.search.SearchAssetInteractor;
import useCase.search.SearchAssetOutputBoundary;
import useCase.signup.SignupInputBoundary;
import useCase.signup.SignupInteractor;
import useCase.signup.SignupOutputBoundary;

import useCase.statistics.StatsInputBoundary;
import useCase.statistics.StatsInteractor;
import useCase.statistics.StatsOutputBoundary;
import useCase.transactionHistory.TransactionHistoryInteractor;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
//    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    private final FileUserDataAccessObject userDataAccessObject;;
    private final AlphaVantageSearchDataAccessObject searchDataAccessObject;
    private final AlphaVantageExchangeRateDataAccessObject currencyDataAccessObject;
    private final AlphaVantageAssetPriceDataAccessObject assetPriceDataAccessObject;
    private PortfolioView portfolioView;
    private PortfolioViewModel portfolioViewModel;
    private StatsView statsView;
    private StatsViewModel statsViewModel;
    private SearchAssetView transactionsView;
    private SearchAssetViewModel transactionsViewModel;
    private SearchAssetController searchController;
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private TransactionController transactionController;
    private PortfolioController portfolioController;
    private PortfolioPresenter portfolioPresenter;
    private StatsController statsController;
    private FilePortfolioDataAccessObject portfolioDAO;

    private TransactionHistoryController transactionHistoryController;
    private TransactionHistoryViewModel transactionHistoryViewModel;
    private TransactionHistoryView transactionHistoryView;
    private FileTransactionHistoryDataAccessObject transactionHistoryDataAccessObject;


    public AppBuilder() throws IOException {

        cardPanel.setLayout(cardLayout);

        // Initialize FileUserDataAccessObject
        String userDataFilePath = "users.csv";
        userDataAccessObject = new FileUserDataAccessObject(userDataFilePath, userFactory);
        searchDataAccessObject = new AlphaVantageSearchDataAccessObject();
        assetPriceDataAccessObject = new AlphaVantageAssetPriceDataAccessObject();
        currencyDataAccessObject = new AlphaVantageExchangeRateDataAccessObject();
    }

    /**
     * Adds the Portfolio View to the application.
     * @return this builder
     */
    public AppBuilder addPortfolioView() {
        if (portfolioController == null) {
            throw new IllegalStateException("PortfolioController must be initialized before adding the Portfolio View!");
        }
        portfolioView = new PortfolioView(portfolioViewModel);
        portfolioView.setTransactionController(transactionController);
        cardPanel.add(portfolioView, portfolioViewModel.getViewName());
        return this;
    }

    /**
     * Adds Transaction History View to the application.
     * @return this builder.
     */
    public AppBuilder addTransactionHistoryView() {
        transactionHistoryView = new TransactionHistoryView(transactionHistoryViewModel);
        cardPanel.add(transactionHistoryView, transactionHistoryViewModel.getViewName());
        return this;
    }

    /**
     * Adds Transaction History Use CAse to this application.
     * @return this builder.
     */
    public AppBuilder addTransactionHistoryUseCase() throws IOException {
        transactionHistoryDataAccessObject = new FileTransactionHistoryDataAccessObject("history.csv");
        transactionHistoryViewModel = new TransactionHistoryViewModel();
        TransactionHistoryPresenter presenter = new TransactionHistoryPresenter(transactionHistoryViewModel);
        TransactionHistoryInteractor interactor = new TransactionHistoryInteractor(
                presenter,
                transactionHistoryDataAccessObject);
        transactionHistoryController = new TransactionHistoryController(interactor);
        transactionHistoryViewModel.setController(transactionHistoryController);
        return this;
    }

    /**
     * Adds the Stats View to the application.
     * @return this builder
     */
    public AppBuilder addStatsView() {
        statsView = new StatsView(portfolioViewModel);
        cardPanel.add(statsView, statsViewModel.getViewName());
        return this;
    }

    public AppBuilder addTransactionUseCase() {
        transactionController = new TransactionController(portfolioViewModel,
                transactionHistoryViewModel);
        if (transactionsView != null) {
            transactionsView.setTransactionController(transactionController);
        }
        return this;
    }

    /**
     * Adds the Transactions (Search Assets) View to the application.
     * @return this builder
     */
    public AppBuilder addTransactionsView() {
        if (searchController == null) {
            throw new IllegalStateException("SearchAssetController must be initialized before adding TransactionsView!");
        }
        if (transactionController == null) {
            throw new IllegalStateException("TransactionController must be initialized before adding TransactionsView!");
        }
        transactionsView = new SearchAssetView(transactionsViewModel);
        transactionsViewModel.setSearchController(searchController);
        transactionsView.setTransactionController(transactionController);
        cardPanel.add(transactionsView, transactionsViewModel.getViewName());
        return this;
    }

    /**
     * Adds the Portfolio Use Case to the application.
     * @return this builder
     */
    public AppBuilder addPortfolioUseCase() throws IOException {
        portfolioDAO = new FilePortfolioDataAccessObject("portfolio.csv");
        portfolioViewModel = new PortfolioViewModel(
                userDataAccessObject.getCurrentUsername(),
                assetPriceDataAccessObject,
                currencyDataAccessObject);
        portfolioPresenter = new PortfolioPresenter(portfolioViewModel);
        PortfolioInteractor portfolioInteractor = new PortfolioInteractor(portfolioDAO, portfolioPresenter);
        portfolioController = new PortfolioController(portfolioInteractor);
        portfolioViewModel.setController(portfolioController);
        return this;
    }


    /**
     * Adds the Statsitics Use Case to the application.
     * @return this builder
     */
    public AppBuilder addStatsUseCase() throws IOException {
        statsViewModel  = new StatsViewModel();
        final StatsOutputBoundary statsOutputBoundary = new StatsPresenter(statsViewModel);
        final StatsInputBoundary statsInteractor = new StatsInteractor(portfolioDAO, statsOutputBoundary);
        statsController = new StatsController(statsInteractor);
        return this;
    }


    /**
     * Adds the Search Case to the application.
     * @return this builder
     */
    public AppBuilder addSearchAssetUseCase() {
        transactionsViewModel = new SearchAssetViewModel(searchController,userDataAccessObject.getCurrentUsername());
        SearchAssetOutputBoundary searchPresenter = new SearchAssetPresenter(transactionsViewModel);
        SearchAssetInteractor searchInteractor = new SearchAssetInteractor(searchDataAccessObject, searchPresenter);
        searchController = new SearchAssetController(searchInteractor);
        return this;
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        if (portfolioController == null) {
            throw new IllegalStateException("PortfolioController must be initialized before adding the Login Use Case!");
        }
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel,portfolioController, statsController,transactionHistoryController);

        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Financial Portfolio App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign up");
        JButton portfolioButton = new JButton("Portfolio");
        JButton transactionsButton = new JButton("Transactions");
        JButton statsButton = new JButton("Statistics");
        JButton HistoryButton = new JButton("History");


        // Switch views when buttons are clicked
        loginButton.addActionListener(
                e -> {viewManagerModel.setState(loginViewModel.getViewName());
            viewManagerModel.firePropertyChanged();});
        signupButton.addActionListener(e -> {viewManagerModel.setState(signupViewModel.getViewName());
            viewManagerModel.firePropertyChanged();});
        portfolioButton.addActionListener(e -> {viewManagerModel.setState(portfolioViewModel.getViewName());
            viewManagerModel.firePropertyChanged();});
        statsButton.addActionListener(e -> {viewManagerModel.setState(statsViewModel.getViewName());
//            System.out.println("switching to view: " + statsViewModel.getViewName());
            viewManagerModel.firePropertyChanged();});
        transactionsButton.addActionListener(e -> {viewManagerModel.setState(transactionsViewModel.getViewName());
            viewManagerModel.firePropertyChanged();});
        HistoryButton.addActionListener(e -> {viewManagerModel.setState(transactionHistoryViewModel.getViewName());
            viewManagerModel.firePropertyChanged(); });


        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        buttonPanel.add(portfolioButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(transactionsButton);
        buttonPanel.add(HistoryButton);


        // Add panels to the frame
        application.setLayout(new BorderLayout());
        application.add(buttonPanel, BorderLayout.NORTH);
        application.add(cardPanel, BorderLayout.CENTER);

        // Set the initial state
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        application.setSize(800, 600);
        application.setVisible(true);

        return application;
    }
}
