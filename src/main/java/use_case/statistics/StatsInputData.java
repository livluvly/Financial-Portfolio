package use_case.statistics;

/**
 * The Input Data for the Portfolio Use Case.
 */
public class StatsInputData {

    //    private boolean setup;
    private final String userId;
    /**
     * Initialize with a portfolio that is not setup.
     * @param userId the userId of the portfolio.
     */
    public StatsInputData(String userId) {
        this.userId = userId;
    }

    /**
     * Return the status of current portfolio setup.
     * @return the status of current portfolio setup.
     */
    public String getUserId() {
        return userId;
    }

//    /**
//     * Change setup status.
//     * @param state the state to which to set the setup.
//     */
//    public void setSetup(boolean state) {
//        this.setup = state;
//    }
}