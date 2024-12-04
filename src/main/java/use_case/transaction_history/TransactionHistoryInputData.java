package use_case.transaction_history;

/**
 * The Input Data for the Transactioin History Use Case.
 */
public class TransactionHistoryInputData {

    private final String userId;

    /**
     * Constructs a TransactionHistoryInputData object with the specific userID.
     * @param userId the ID of the user.
     */
    public TransactionHistoryInputData(String userId) {
        this.userId = userId;
    }

    /**
     * Return the status of current portfolio setup.
     * @return the status of current portfolio setup.
     */
    public String getUserId() {
        return userId;
    }
}
