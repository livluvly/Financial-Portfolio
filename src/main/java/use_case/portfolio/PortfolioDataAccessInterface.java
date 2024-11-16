package use_case.portfolio;

import entity.FinancialAsset;

/**
 * DAO for the Portfolio Use Case.
 */
public interface PortfolioDataAccessInterface {

    /**
     * Returns the portfolio including all the holdings of assets.
     * @return the portfolio including all the holdings of assets.
     */
    FinancialAsset get();

    /**
     * Returns the total balance of this portfolio.
     * @return the total balance of this portfolio.
     */
    Integer showBalance();
}
