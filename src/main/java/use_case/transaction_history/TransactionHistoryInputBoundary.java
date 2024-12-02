package use_case.transaction_history;

/**
 * Input boundary interface for the Transaction History use case.
 * Defines methods for interacting with transaction history.
 */
public interface TransactionHistoryInputBoundary {

    /**
     * Fetches the transaction history.
     * @param userId The ID of the user whose transaction history is being fetched.
     */
    void fetchTransactionHistory(String userId);
}

