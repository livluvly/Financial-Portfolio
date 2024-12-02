package app;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import data_access.AlphaVantageSearchDataAccessObject;
import data_access.FilePortfolioDataAccessObject;
import data_access.FileUserDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.PortfolioViewModel;
import interface_adapter.SearchAssetViewModel;
import interface_adapter.StatsViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
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
import interface_adapter.transaction.TransactionController;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.portfolio.PortfolioDataAccessInterface;
import use_case.portfolio.PortfolioInteractor;
import use_case.portfolio.PortfolioOutputBoundary;
import use_case.search.SearchAssetInteractor;
import use_case.search.SearchAssetOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
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
    private final FileUserDataAccessObject userDataAccessObject;;
    private final AlphaVantageSearchDataAccessObject searchDataAccessObject;
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
    private FilePortfolioDataAccessObject portfolioDAO;

    public AppBuilder() throws IOException {
        cardPanel.setLayout(cardLayout);

        // Initialize FileUserDataAccessObject
        String userDataFilePath = "users.csv";
        userDataAccessObject = new FileUserDataAccessObject(userDataFilePath, userFactory);
        searchDataAccessObject = new AlphaVantageSearchDataAccessObject();
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
     * Adds the Stats View to the application.
     * @return this builder
     */
    public AppBuilder addStatsView() {
        statsViewModel = new StatsViewModel();
        statsView = new StatsView(statsViewModel);
        cardPanel.add(statsView, statsViewModel.getViewName());
        return this;
    }

    public AppBuilder addTransactionUseCase() {
        transactionController = new TransactionController(portfolioViewModel);
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
        portfolioViewModel = new PortfolioViewModel(userDataAccessObject.getCurrentUsername());
        portfolioPresenter = new PortfolioPresenter(portfolioViewModel);
        PortfolioInteractor portfolioInteractor = new PortfolioInteractor(portfolioDAO, portfolioPresenter);
        portfolioController = new PortfolioController(portfolioInteractor);
        portfolioViewModel.setController(portfolioController);
//      PortfolioInteractor portfolioInteractor = new PortfolioInteractor(portfolioPresenter);
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
                loggedInViewModel, loginViewModel,portfolioController);
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


        // Switch views when buttons are clicked
        loginButton.addActionListener(e -> {viewManagerModel.setState(loginViewModel.getViewName());
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

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        buttonPanel.add(portfolioButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(transactionsButton);


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
