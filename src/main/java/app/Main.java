package app;

import data_access.InMemoryPortfolioDataAcessObject;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioPresenter;
import use_case.portfolio.PortfolioInteractor;
import use_case.portfolio.PortfolioOutputData;
import view.AssetsPageView;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
//        final app.AppBuilder appBuilder = new app.AppBuilder();
//        appBuilder.addLogoutUseCase();
//
//        final JFrame application = appBuilder
//                                            .addLoginView()
//                                            .addSignupView()
//                                            .addLoggedInView()
//                                            .addSignupUseCase()
//                                            .addLoginUseCase()
//                                            .addLogoutUseCase()
//                                            .addChangePasswordUseCase()
//                                            .build();
//
//        application.pack();
//        application.setVisible(true);
        // setup
        InMemoryPortfolioDataAcessObject dataAccess = new InMemoryPortfolioDataAcessObject();
        PortfolioPresenter presenter = new PortfolioPresenter();
        PortfolioInteractor interactor = new PortfolioInteractor(dataAccess, presenter);
        PortfolioController controller = new PortfolioController(interactor);

        PortfolioOutputData outputData = controller.fetchAssets("alice");
        presenter.prepareSuccessView(outputData);

        new AssetsPageView(presenter.getPortfolioViewModel());
    }
}
