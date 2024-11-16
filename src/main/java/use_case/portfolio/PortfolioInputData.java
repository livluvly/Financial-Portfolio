package use_case.portfolio;

/**
 * The Input Data for the Portfolio Use Case.
 */
public class PortfolioInputData {

    private boolean setup;

    /**
     * Initialize with a portfolio that is not setup.
     * @param setup the setup of the portfolio view.
     */
    public PortfolioInputData(boolean setup) {
        this.setup = false;
    }

    /**
     * Return the status of current portfolio setup.
     * @return the status of current portfolio setup.
     */
    public boolean isSetup() {
        return setup;
    }

    /**
     * Change setup status.
     * @param state the state to which to set the setup.
     */
    public void setSetup(boolean state) {
        this.setup = state;
    }
}
