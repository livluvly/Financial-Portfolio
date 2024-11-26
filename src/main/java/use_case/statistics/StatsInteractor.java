//package use_case.portfolio;
//
//import use_case.login.LoginInputData;
//
//import java.util.List;
//import entity.Asset;
//
///**
// * The Portfolio Interactor.
// */
//
//public class PortfolioInteractor implements PortfolioInputBoundary {
//    private final PortfolioDataAccessInterface portfolioDataAccess;
//    private final PortfolioOutputBoundary portfolioOutputBoundary;
//    // the output boundary is a layer that actually helps interactor to talk with the presenter.
//
//    public PortfolioInteractor(PortfolioDataAccessInterface assetDataAccess,
//                               PortfolioOutputBoundary portfolioOutputBoundary) {
//        this.portfolioDataAccess = assetDataAccess;
//        this.portfolioOutputBoundary = portfolioOutputBoundary;
//        // this.portfolioDataAccessInterface =
//        // {"IBM": {"Name": "International Business Machine", "Amount": 100.0},
//        // "AAPL": {"Name": "Apple Inc.", "Amount": 50.0};
//    }
//
//    /**
//     * Should be an override method from implementing PortfolioInputBoundary.
//     * @param loginInputData should there be any input data for portfolio?
//     */
//
//    @Override
//    public void execute(PortfolioInputData portfolioInputData) {
//
//    }
//
//    @Override
//    public PortfolioOutputData getAssets(PortfolioInputData inputData) {
//        List<Asset> assets = portfolioDataAccess.getAssetsForUser(inputData.getUserId());
//        PortfolioOutputData outputData = new PortfolioOutputData(assets, false);
//        portfolioOutputBoundary.prepareSuccessView(outputData);
//        return outputData;
//    }
//
//}
