package app;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import data_access.*;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.*;
import interface_adapter.changePassword.ChangePasswordController;
import interface_adapter.changePassword.ChangePasswordPresenter;
import interface_adapter.changePassword.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioPresenter;
import interface_adapter.search.SearchAssetController;
import interface_adapter.search.SearchAssetPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.statistics.StatsController;
import interface_adapter.statistics.StatsPresenter;
import interface_adapter.transaction.TransactionController;
import interface_adapter.transaction_history.TransactionHistoryController;
import interface_adapter.transaction_history.TransactionHistoryPresenter;
import use_case.changePassword.ChangePasswordInputBoundary;
import use_case.changePassword.ChangePasswordInteractor;
import use_case.changePassword.ChangePasswordOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.portfolio.PortfolioInteractor;
import use_case.search.SearchAssetInteractor;
import use_case.search.SearchAssetOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;

import use_case.statistics.StatsInputBoundary;
import use_case.statistics.StatsInteractor;
import use_case.statistics.StatsOutputBoundary;
import use_case.transaction_history.TransactionHistoryInteractor;
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
        portfolioDAO = new FilePortfolioDataAccessObject("portfolio.csv");
        transactionHistoryDataAccessObject = new FileTransactionHistoryDataAccessObject("history.csv");
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
        portfolioViewModel = new PortfolioViewModel();
        portfolioView = new PortfolioView(portfolioViewModel);
        cardPanel.add(portfolioView, portfolioViewModel.getViewName());
        return this;
    }

    /**
     * Adds Transaction History View to the application.
     * @return this builder.
     */
    public AppBuilder addTransactionHistoryView() {
        transactionHistoryViewModel = new TransactionHistoryViewModel();
        transactionHistoryView = new TransactionHistoryView(transactionHistoryViewModel);
        cardPanel.add(transactionHistoryView, transactionHistoryViewModel.getViewName());
        return this;
    }

    /**
     * Adds Transaction History Use CAse to this application.
     * @return this builder.
     */
    public AppBuilder addTransactionHistoryUseCase() throws IOException {
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
        statsViewModel = new StatsViewModel();
        statsView = new StatsView(portfolioViewModel);
        cardPanel.add(statsView, statsViewModel.getViewName());
        return this;
    }

    /**
     * Adds the Transactions (Search Assets) View to the application.
     * @return this builder
     */
    public AppBuilder addTransactionsView() {
        transactionsViewModel = new SearchAssetViewModel();
        transactionsView = new SearchAssetView(transactionsViewModel);
        cardPanel.add(transactionsView, transactionsViewModel.getViewName());
        return this;
    }

    public AppBuilder addTransactionUseCase() {
        transactionController = new TransactionController(portfolioViewModel,
                transactionHistoryViewModel);
        transactionsView.setTransactionController(transactionController);
        portfolioView.setTransactionController(transactionController);
        transactionsViewModel.setSearchController(searchController);
        return this;
    }

    /**
     * Adds the Portfolio Use Case to the application.
     * @return this builder
     */
    public AppBuilder addPortfolioUseCase() throws IOException {
        portfolioPresenter = new PortfolioPresenter(portfolioViewModel);
        final PortfolioInteractor portfolioInteractor = new PortfolioInteractor(portfolioDAO, portfolioPresenter);
        portfolioController = new PortfolioController(portfolioInteractor);
        portfolioViewModel.setController(portfolioController);
        portfolioViewModel.setCurrencyDAO(currencyDataAccessObject);
        portfolioViewModel.setPriceDAO(assetPriceDataAccessObject);
        return this;
    }

//        transactionsViewModel = new SearchAssetViewModel(searchController,userDataAccessObject.getCurrentUsername());

    /**
     * Adds the Statsitics Use Case to the application.
     * @return this builder
     */
    public AppBuilder addStatsUseCase() throws IOException {
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
        SearchAssetOutputBoundary searchPresenter = new SearchAssetPresenter(transactionsViewModel);
        SearchAssetInteractor searchInteractor = new SearchAssetInteractor(searchDataAccessObject, searchPresenter);
        searchController = new SearchAssetController(searchInteractor);
        transactionsViewModel.setSearchController(searchController);
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
        final LoginPresenter loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel,portfolioController, statsController,transactionHistoryController);

        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginOutputBoundary.setPortfolioViewModel(portfolioViewModel);
        loginOutputBoundary.setStatsViewModel(statsViewModel);
        loginOutputBoundary.setTransactionHistoryViewModel(transactionHistoryViewModel);
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
